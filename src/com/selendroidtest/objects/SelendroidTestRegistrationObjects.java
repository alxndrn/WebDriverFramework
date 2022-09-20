package com.selendroidtest.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelendroidTestRegistrationObjects {
	@FindBy(id = "startUserRegistration")
	private WebElement startUserRegistrationButton;

	@FindBy(id = "inputUsername")
	private WebElement usernameInput;

	@FindBy(name = "email of the customer")
	private WebElement emailInput;

	@FindBy(id = "inputPassword")
	private WebElement passwordInput;

	@FindBy(xpath = "//EditText[@id='inputName']")
	private WebElement nameInput;

	@FindBy(id = "input_preferedProgrammingLanguage")
	private WebElement preferedProgrammingLanguageDropdown;

	@FindBy(id = "alertTitle")
	private WebElement preferedProgrammingLanguageList;

	@FindBy(id = "input_adds")
	private WebElement addsCheckbox;

	@FindBy(id = "btnRegisterUser")
	private WebElement registerUserButton;

	public SelendroidTestRegistrationObjects(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getRegistrationPageWebElement(String webElement) {

		switch (webElement) {
		case "startUserRegistrationButton":
			return startUserRegistrationButton;
		case "usernameInput":
			return usernameInput;
		case "emailInput":
			return emailInput;
		case "passwordInput":
			return passwordInput;
		case "nameInput":
			return nameInput;
		case "preferedProgrammingLanguageDropdown":
			return preferedProgrammingLanguageDropdown;
		case "addsCheckbox":
			return addsCheckbox;
		case "registerUserButton":
			return registerUserButton;
		default:
			throw new RuntimeException("No such element on registration page! ");
		}
	}
}
