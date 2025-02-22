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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class OrganizationResourceTest extends BaseOrganizationResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	@Test
	public void testDeleteUserAccountByEmailAddress() throws Exception {
		Organization organization = _toOrganization(
			_addOrganization(randomOrganization(), "0"));
		User user = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			user.getUserId(), GetterUtil.getLong(organization.getId()));

		Assert.assertTrue(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));

		organizationResource.deleteUserAccountByEmailAddress(
			organization.getId(), user.getEmailAddress());

		Assert.assertFalse(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));
	}

	@Override
	@Test
	public void testDeleteUserAccountsByEmailAddress() throws Exception {
		Organization organization = _toOrganization(
			_addOrganization(randomOrganization(), "0"));

		long organizationId = GetterUtil.getLong(organization.getId());

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		_userLocalService.addOrganizationUsers(organizationId, users);

		for (User user : users) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		List<User> removeUsers = users.subList(0, 2);

		organizationResource.deleteUserAccountsByEmailAddress(
			organization.getId(), _toEmailAddresses(removeUsers));

		for (User user : removeUsers) {
			Assert.assertFalse(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		List<User> keepUsers = users.subList(2, 4);

		for (User user : keepUsers) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}
	}

	@Override
	@Test
	public void testGetOrganizationsPage() throws Exception {
		Page<Organization> page = organizationResource.getOrganizationsPage(
			null, RandomTestUtil.randomString(), null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());
		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		page = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(page);

		_userLocalService.deleteOrganizationUser(
			GetterUtil.getLong(organization1.getId()), _user.getUserId());

		organizationResource.deleteOrganization(organization1.getId());

		_userLocalService.deleteOrganizationUser(
			GetterUtil.getLong(organization2.getId()), _user.getUserId());

		organizationResource.deleteOrganization(organization2.getId());
	}

	@Override
	@Test
	public void testPostUserAccountByEmailAddress() throws Exception {
		Organization organization = _toOrganization(
			_addOrganization(randomOrganization(), "0"));
		User user = UserTestUtil.addUser();

		Assert.assertFalse(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));

		organizationResource.postUserAccountByEmailAddress(
			organization.getId(), user.getEmailAddress());

		Assert.assertTrue(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));
	}

	@Override
	@Test
	public void testPostUserAccountsByEmailAddress() throws Exception {
		Organization organization = _toOrganization(
			_addOrganization(randomOrganization(), "0"));

		long organizationId = GetterUtil.getLong(organization.getId());

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		for (User user : users) {
			Assert.assertFalse(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		organizationResource.postUserAccountsByEmailAddress(
			organization.getId(), _toEmailAddresses(users));

		for (User user : users) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Organization testDeleteOrganization_addOrganization()
		throws Exception {

		Organization organization = randomOrganization();

		return _toOrganization(
			_organizationLocalService.addOrganization(
				_user.getUserId(), 0, organization.getName(), true));
	}

	@Override
	protected Organization testGetOrganization_addOrganization()
		throws Exception {

		return _addUserOrganization(_user.getUserId(), randomOrganization());
	}

	@Override
	protected Organization testGetOrganizationOrganizationsPage_addOrganization(
			String parentOrganizationId, Organization organization)
		throws Exception {

		return _toOrganization(
			_addOrganization(organization, parentOrganizationId));
	}

	@Override
	protected String
			testGetOrganizationOrganizationsPage_getParentOrganizationId()
		throws Exception {

		com.liferay.portal.kernel.model.Organization organization =
			_addOrganization(randomOrganization(), "0");

		return String.valueOf(organization.getOrganizationId());
	}

	@Override
	protected Organization testGetOrganizationsPage_addOrganization(
			Organization organization)
		throws Exception {

		return _addUserOrganization(_user.getUserId(), organization);
	}

	@Override
	protected Organization testGraphQLOrganization_addOrganization()
		throws Exception {

		return _toOrganization(_addOrganization(randomOrganization(), "0"));
	}

	@Override
	protected Organization testPatchOrganization_addOrganization()
		throws Exception {

		return _addUserOrganization(_user.getUserId(), randomOrganization());
	}

	@Override
	protected Organization testPostOrganization_addOrganization(
			Organization organization)
		throws Exception {

		return _addUserOrganization(_user.getUserId(), organization);
	}

	@Override
	protected Organization testPutOrganization_addOrganization()
		throws Exception {

		return _addUserOrganization(_user.getUserId(), randomOrganization());
	}

	private com.liferay.portal.kernel.model.Organization _addOrganization(
			Organization organization, String parentOrganizationId)
		throws Exception {

		return _organizationLocalService.addOrganization(
			_user.getUserId(), GetterUtil.getLong(parentOrganizationId),
			organization.getName(), true);
	}

	private Organization _addUserOrganization(
			Long userAccountId, Organization organization)
		throws Exception {

		Organization parentOrganization = _toOrganization(
			_addOrganization(organization, "0"));

		if (userAccountId != null) {
			_userLocalService.addOrganizationUser(
				GetterUtil.getLong(parentOrganization.getId()), userAccountId);
		}

		return parentOrganization;
	}

	private String[] _toEmailAddresses(List<User> users) {
		return TransformUtil.transformToArray(
			users, User::getEmailAddress, String.class);
	}

	private Organization _toOrganization(
		com.liferay.portal.kernel.model.Organization organization) {

		return new Organization() {
			{
				dateCreated = organization.getCreateDate();
				dateModified = organization.getModifiedDate();
				id = String.valueOf(organization.getOrganizationId());
				name = organization.getName();
			}
		};
	}

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}