package com.selendroidtest.tasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	private WebDriver driver;

	@FindBy(id = "user-name")
	private WebElement usernameInput;

	@FindBy(id = "login-pass")
	private WebElement passwordInput;

	@FindBy(id = "saveBtn")
	private WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//li[contains(text(),'¬ход в системата')]")
	private WebElement loginPageLabel;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public HomePage login(String user, String pass) {
		usernameInput.sendKeys(user);
		passwordInput.sendKeys(pass);
		submitButton.click();
		HomePage homePage = new HomePage(driver);
		// if (!homePage.getInstructionHeaderButton().isDisplayed()) {
		// throw new
		// IncorrectPageContentException("Home Page has not been opened after Login.");
		// }
		return homePage;
	}

	public boolean verifyLoginScreenDisplayed() {
		return loginPageLabel.isDisplayed();
	}

	public WebElement getLoginPageLabel() {
		return loginPageLabel;
	}
}
