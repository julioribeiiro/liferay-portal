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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return file.exists();
	}

	public static byte[] getBytes(File file) throws IOException {
		return FileUtils.readFileToByteArray(file);
	}

	public static String read(File file) throws IOException {
		return read(file, true);
	}

	public static String read(File file, boolean escapeReturnCharacter)
		throws IOException {

		try {
			String s = FileUtils.readFileToString(file, StringPool.UTF8);

			if (!escapeReturnCharacter) {
				return s;
			}

			return StringUtil.replace(
				s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
		}
		catch (FileNotFoundException fileNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(fileNotFoundException, fileNotFoundException);
			}

			return null;
		}
	}

	public static void write(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, StringPool.UTF8);
	}

	private static final Log _log = LogFactoryUtil.getLog(FileUtil.class);

}