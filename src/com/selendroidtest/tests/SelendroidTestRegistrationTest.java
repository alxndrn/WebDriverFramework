package com.selendroidtest.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selendroidtest.core.BaseTest;
import com.selendroidtest.core.TestUtil;
import com.selendroidtest.tasks.PreferedProgrammingLanguage;
import com.selendroidtest.tasks.SelendroidTestRegistrationTask;

public class SelendroidTestRegistrationTest extends TestUtil {

	BaseTest test;
	SelendroidTestRegistrationTask selendroidTestRegistrationTask;

	@DataProvider(name = "registerNewUser")
	public String[][] testData() {
		return readTestDataFromXls("user", "userData");
	}

	@Test(dataProvider = "registerNewUser")
	public void successfulSelendroidTestRegistration(String username, String email, String password, String name,
			PreferedProgrammingLanguage preferedProgrammingLanguage) throws Exception {
		test = initTest();

		test.startTest();
		try {
			test.startStep("Launch");
			selendroidTestRegistrationTask.clickOnRegistrationImageButton();
			test.endStep();

			test.startStep("Populate text field elements in registration form");
			selendroidTestRegistrationTask.populateTextElements(username, email, password, name);
			test.endStep();

			test.startStep("Select preferred programming language from radio buttons list");
			selendroidTestRegistrationTask.selectRadioButton(preferedProgrammingLanguage);
			test.endStep();

			test.startStep("Pick the checkbox agreement for advertizements");
			selendroidTestRegistrationTask.pickAdsCheckbox();
			test.endStep();

			test.startStep("Submit registration form");
			selendroidTestRegistrationTask.submitForm();
			test.endStep();

		} catch (Exception e) {
			test.endStep(false, e.getMessage());
		}

		test.endTest();
	}

}
