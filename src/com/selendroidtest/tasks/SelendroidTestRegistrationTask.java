package com.selendroidtest.tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.selendroidtest.objects.SelendroidTestRegistrationObjects;

public class SelendroidTestRegistrationTask {
	private SelendroidTestRegistrationObjects selendroidTestRegistrationObjects;

	private WebDriver driver;

	public SelendroidTestRegistrationTask(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void populateTextElements(String username, String email, String password, String name) {
		getRegistrationPageWebElement("usernameInput").sendKeys(username);
		getRegistrationPageWebElement("emailInput").sendKeys(email);
		getRegistrationPageWebElement("passwordInput").sendKeys(password);
		if (name != null) {
			// clear the input if it has value
			if (!getRegistrationPageWebElement("nameInput").getAttribute("value").isEmpty()) {
				getRegistrationPageWebElement("nameInput").clear();
			}
			getRegistrationPageWebElement("nameInput").sendKeys(name);
		}
	}

	public WebElement getRegistrationPageWebElement(String webElement) {
		return selendroidTestRegistrationObjects.getRegistrationPageWebElement(webElement);
	}

	public void clickOnRegistrationImageButton() {
		getRegistrationPageWebElement("startUserRegistrationButton").click();
	}

	public void submitForm() {
		getRegistrationPageWebElement("registerUserButton").click();
	}

	public void pickAdsCheckbox() {
		getRegistrationPageWebElement("addsCheckbox").click();
	}

	public void selectRadioButton(PreferedProgrammingLanguage preferedProgrammingLanguage) {
		getRegistrationPageWebElement("preferedProgrammingLanguageDropdown").click();

		driver.findElement(By.linkText(preferedProgrammingLanguage.getValue())).click();
	}

	public void selectDropDownValue(WebElement webElement, String value) {
		getRegistrationPageWebElement("preferedProgrammingLanguageDropdown").click();

		Select preferedProgrammingLanguage = new Select(webElement);
		preferedProgrammingLanguage.selectByValue(value);
	}

	public boolean verifyRegistration(String username, String email, String password, String name,
			String preferedProgrammingLanguage) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement inputUsername = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("label_username_data")));
		Assert.assertEquals(inputUsername.getText(), username);
		Assert.assertEquals(driver.findElement(By.id("label_email_data")).getText(), email);
		Assert.assertEquals(driver.findElement(By.id("label_password_data")).getText(), password);
		Assert.assertEquals(driver.findElement(By.id("label_name_data")).getText(), name);
		Assert.assertEquals(driver.findElement(By.id("label_preferedProgrammingLanguage_data")).getText(),
				preferedProgrammingLanguage);
		Assert.assertEquals(driver.findElement(By.id("label_acceptAdds_data")).getText(), "true");
		driver.findElement(By.id("buttonRegisterUser")).click();
		return true;
	}
}
