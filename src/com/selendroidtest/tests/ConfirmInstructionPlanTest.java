package com.selendroidtest.tests;

import org.testng.annotations.Test;

import com.selendroidtest.core.BaseTest;
import com.selendroidtest.core.TestUtil;
import com.selendroidtest.tasks.HomePage;
import com.selendroidtest.tasks.LoginPage;
import com.selendroidtest.tasks.NavigationBar;

public class ConfirmInstructionPlanTest extends TestUtil {

	@Test
	public void confirmInstructionPlan() throws Exception {
		BaseTest test = initTest();
		LoginPage loginPage = new LoginPage(test.getDriver());
		NavigationBar navigationBar = new NavigationBar(test.getDriver());
		HomePage homePage = new HomePage(test.getDriver());

		test.startTest();
		try {
			test.startStep("Login");
			loginPage.login(test.getUsername(), test.getPassword());
			test.endStep();

			test.startStep("Verify that home page is displayed");
			test.endStep(navigationBar.isCurrentUserDisplayed());

			test.startStep("Confirm Instruction Plan");
			homePage.confirmInstructionPlan();
			test.endStep();

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
