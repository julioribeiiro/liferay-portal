/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.knowledge.base.internal.upgrade.v4_1_0;

import com.liferay.knowledge.base.internal.upgrade.v4_1_0.util.KBArticleTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Miguel Barcos
 */
public class KBArticleExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(KBArticleTable.TABLE_NAME, "externalReferenceCode")) {
			alter(
				KBArticleTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75)"));

			runSQL(
				"update KBArticle set externalReferenceCode = " +
					"CAST_TEXT(resourcePrimKey) where externalReferenceCode " +
						"is null or externalReferenceCode = ''");
		}
	}

}