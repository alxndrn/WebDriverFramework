package com.selendroidtest.tests;

import org.testng.annotations.Test;

import com.selendroidtest.core.BaseTest;
import com.selendroidtest.core.TestUtil;
import com.selendroidtest.tasks.LoginPage;
import com.selendroidtest.tasks.NavigationBar;

public class LoginTest extends TestUtil {

	@Test
	public void login() throws Exception {
		BaseTest test = initTest();
		LoginPage loginPage = new LoginPage(test.getDriver());
		NavigationBar navigationBar = new NavigationBar(test.getDriver());

		test.startTest();
		try {
			test.startStep("Login");
			loginPage.login(test.getUsername(), test.getPassword());
			test.endStep();

			test.startStep("Verify that home page is displayed");
			test.endStep(navigationBar.isCurrentUserDisplayed());

		} catch (Exception e) {
			test.endStep(false, e.getMessage());
		}

		test.endTest();
	}

	@Test
	public void logout() throws Exception {
		BaseTest test = initTest();
		LoginPage loginPage = new LoginPage(test.getDriver());
		NavigationBar navigationBar = new NavigationBar(test.getDriver());

		test.startTest();
		try {
			test.startStep("Login");
			loginPage.login(test.getUsername(), test.getPassword());
			test.endStep();

			test.startStep("Verify that home page is displayed");
			test.endStep(navigationBar.isCurrentUserDisplayed());

			test.startStep("Logout");
			navigationBar.logout();
			test.endStep();

			test.startStep("Verify Login screen is displayed");
			test.endStep(loginPage.verifyLoginScreenDisplayed());

		} catch (Exception e) {
			test.endStep(false, e.getMessage());
		}

		test.endTest();
	}
}
