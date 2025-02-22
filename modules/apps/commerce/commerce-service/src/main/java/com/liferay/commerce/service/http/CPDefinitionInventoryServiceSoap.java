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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CPDefinitionInventoryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CPDefinitionInventorySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CPDefinitionInventory</code>, that is translated to a
 * <code>com.liferay.commerce.model.CPDefinitionInventorySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionInventoryServiceSoap {

	public static com.liferay.commerce.model.CPDefinitionInventorySoap
			addCPDefinitionInventory(
				long userId, long cpDefinitionId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				int minStockQuantity, boolean backOrders, int minOrderQuantity,
				int maxOrderQuantity, String allowedOrderQuantities,
				int multipleOrderQuantity)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue =
				CPDefinitionInventoryServiceUtil.addCPDefinitionInventory(
					userId, cpDefinitionId, cpDefinitionInventoryEngine,
					lowStockActivity, displayAvailability, displayStockQuantity,
					minStockQuantity, backOrders, minOrderQuantity,
					maxOrderQuantity, allowedOrderQuantities,
					multipleOrderQuantity);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws RemoteException {

		try {
			CPDefinitionInventoryServiceUtil.deleteCPDefinitionInventory(
				cpDefinitionInventoryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventorySoap
			fetchCPDefinitionInventoryByCPDefinitionId(long cpDefinitionId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue =
				CPDefinitionInventoryServiceUtil.
					fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CPDefinitionInventorySoap
			updateCPDefinitionInventory(
				long groupId, long cpDefinitionInventoryId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				int minStockQuantity, boolean backOrders, int minOrderQuantity,
				int maxOrderQuantity, String allowedOrderQuantities,
				int multipleOrderQuantity)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue =
				CPDefinitionInventoryServiceUtil.updateCPDefinitionInventory(
					groupId, cpDefinitionInventoryId,
					cpDefinitionInventoryEngine, lowStockActivity,
					displayAvailability, displayStockQuantity, minStockQuantity,
					backOrders, minOrderQuantity, maxOrderQuantity,
					allowedOrderQuantities, multipleOrderQuantity);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventorySoap
			updateCPDefinitionInventory(
				long cpDefinitionInventoryId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				int minStockQuantity, boolean backOrders, int minOrderQuantity,
				int maxOrderQuantity, String allowedOrderQuantities,
				int multipleOrderQuantity)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue =
				CPDefinitionInventoryServiceUtil.updateCPDefinitionInventory(
					cpDefinitionInventoryId, cpDefinitionInventoryEngine,
					lowStockActivity, displayAvailability, displayStockQuantity,
					minStockQuantity, backOrders, minOrderQuantity,
					maxOrderQuantity, allowedOrderQuantities,
					multipleOrderQuantity);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionInventoryServiceSoap.class);

}