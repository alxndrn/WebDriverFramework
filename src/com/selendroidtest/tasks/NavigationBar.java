package com.selendroidtest.tasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selendroidtest.core.IncorrectPageContentException;

public class NavigationBar {

	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Текущ потребител')]")
	private WebElement currentUser;

	@FindBy(xpath = "//a[contains(@class, 'logout')]")
	private WebElement logoutButton;

	@FindBy(how = How.XPATH, using = "//a[@id='v-a-button']//span")
	private WebElement vehicleActivityButton;

	public NavigationBar(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public boolean isCurrentUserDisplayed() {
		return currentUser.isDisplayed();
	}

	public LoginPage logout() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

		logoutButton.click();
		driver.switchTo().alert().accept();
		LoginPage loginPage = new LoginPage(driver);
		if (!loginPage.getLoginPageLabel().isDisplayed()) {
			throw new IncorrectPageContentException("Login Page has not been opened after Logout.");
		}
		return loginPage;
	}

	public ActivityPage getActivityPage() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		vehicleActivityButton = wait.until(ExpectedConditions.elementToBeClickable(vehicleActivityButton));

		vehicleActivityButton.click();

		ActivityPage activityPage = new ActivityPage(driver);
		if (!activityPage.getActivityDataButton().isDisplayed()) {
			throw new IncorrectPageContentException("The Activity page has not opened.");
		}
		return activityPage;
	}
}
