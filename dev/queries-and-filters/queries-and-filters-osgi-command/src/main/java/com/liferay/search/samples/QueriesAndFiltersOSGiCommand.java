package com.liferay.search.samples;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	property = {"osgi.command.function=search", "osgi.command.scope=liferay"},
	service = QueriesAndFiltersOSGiCommand.class
)
public class QueriesAndFiltersOSGiCommand {

	public void search(String keywords) {
		MatchQuery titleQuery = _queries.match(
			Field.getLocalizedName(LocaleUtil.US, Field.TITLE), keywords);

		TermsQuery rootFolderQuery = _queries.terms(Field.ENTRY_CLASS_NAME);

		rootFolderQuery.addValues("com.liferay.journal.model.JournalArticle");

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

		System.out.println(
			"Request String:\n" + searchResponse.getRequestString());
		System.out.println(
			"Response String:\n" + searchResponse.getResponseString());
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