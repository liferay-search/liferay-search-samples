/**
* SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
* SPDX-License-Identifier: MIT
*/

package com.liferay.search.experiences.blueprints.sample.commerce.related.contents.portlet.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.search.experiences.blueprints.sample.commerce.related.contents.constants.CProductRelatedContentsPortletKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CProductRelatedContentsPortletKeys.COMMERCE_PRODUCT_RELATED_CONTENTS,
	service = ConfigurationAction.class
)
public class CProductRelatedContentsConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.search.experiences.blueprints.sample.commerce.related.contents)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}
