package com.selendroidtest.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selendroidtest.core.BaseTest;
import com.selendroidtest.core.TestUtil;
import com.selendroidtest.tasks.ActivityPage;
import com.selendroidtest.tasks.HomePage;
import com.selendroidtest.tasks.LoginPage;
import com.selendroidtest.tasks.NavigationBar;
import com.selendroidtest.tasks.VehicleCheckPage;

public class VehicleActivityTest extends TestUtil {

	@DataProvider(name = "createVehicleActivity")
	public String[][] createTestDataForVehicleActivity() {
		return readTestDataFromXls("vehicleActivity", "createVehicleActivity");
	}

	@Test(dataProvider = "createVehicleActivity")
	public void createVehicleActivity(String address, String vehicleDkn) throws Exception {
		BaseTest test = initTest();
		LoginPage loginPage = new LoginPage(test.getDriver());
		NavigationBar navigationBar = new NavigationBar(test.getDriver());
		HomePage homePage;
		ActivityPage activityPage;
		VehicleCheckPage vehicleCheckPage;

		test.startTest();
		try {
			test.startStep("Login");
			homePage = loginPage.login(test.getUsername(), test.getPassword());
			test.endStep();

			test.startStep("Go to Vehicle Activity page");
			activityPage = navigationBar.getActivityPage();
			test.endStep();

			test.startStep("Fill in mandatory fields and save vehicle activity");
			test.endStep(activityPage.setActivityDataAndSave(address));

			test.startStep("Open Check list tab");
			test.endStep(activityPage.openCheckListTab());

			test.startStep("Open Vehicle Check page");
			vehicleCheckPage = activityPage.openVehiclePage();
			test.endStep();

			// test.startStep("Go back to Vehicle Activity Page");
			// activityPage = vehicleCheckPage.goBackToVehicleActivityPage();
			// test.endStep();

			test.startStep("Fill vehicle data and save");
			activityPage = vehicleCheckPage.fillVehicleDataAndSubmit(vehicleDkn);
			test.endStep();

			test.startStep("Logout");
			loginPage = navigationBar.logout();
			test.endStep();

		} catch (Exception e) {
			e.printStackTrace();
			test.endStep(false, e.getMessage());
		}

		test.endTest();
	}
}
