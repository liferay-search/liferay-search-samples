/**
* SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
* SPDX-License-Identifier: MIT
*/

package com.liferay.search.experiences.blueprints.sample.commerce.related.contents.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.search.experiences.blueprints.sample.commerce.related.contents.constants.CProductRelatedContentsPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CProductRelatedContentsPortlet",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CProductRelatedContentsPortletKeys.COMMERCE_PRODUCT_RELATED_CONTENTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CProductRelatedContentsPortlet extends MVCPortlet {
}
