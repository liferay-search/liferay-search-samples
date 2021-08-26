/**
* SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
* SPDX-License-Identifier: MIT
*/

package com.liferay.search.samples.osgi.commands;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

@Component(
	property = {"osgi.command.function=search", "osgi.command.scope=liferay"},
	service = QueriesAndFiltersOSGiCommand.class
)
public class QueriesAndFiltersOSGiCommand {

	public void search(String keywords) {
		MatchQuery titleQuery = _queries.match(
			Field.getLocalizedName(LocaleUtil.US, Field.TITLE), keywords);

		TermQuery rootFolderQuery = _queries.term(Field.FOLDER_ID, "0");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustQueryClauses(rootFolderQuery, titleQuery);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		long companyId = _portal.getDefaultCompanyId();

		searchRequestBuilder.explain(
			true
		).includeResponseString(
			true
		).withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(companyId);
				searchContext.setKeywords(keywords);
			}
		);

		SearchRequest searchRequest = searchRequestBuilder.query(
			booleanQuery
		).build();

		SearchResponse searchResponse = _searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		List<String> resultsList = new ArrayList<>(searchHitsList.size());
		
		if (!searchHitsList.isEmpty()) {

		searchHitsList.forEach(
			searchHit -> {
				float hitScore = searchHit.getScore();

				Document doc = searchHit.getDocument();

				String uid = doc.getString(Field.UID);

				System.out.println(
					StringBundler.concat(
						"Document ", uid, " had a score of ", hitScore));

				resultsList.add(uid);
			});
		}
		else {
		System.out.println(
			"Request String:\n" + searchResponse.getRequestString());
		System.out.println(
			"Response String:\n" + searchResponse.getResponseString());
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}
