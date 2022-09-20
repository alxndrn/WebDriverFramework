package com.selendroidtest.tasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selendroidtest.core.IncorrectPageContentException;

public class VehicleCheckPage {

	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//h1[text()='Проверка на МПС']")
	private WebElement vehicleCheckHeader;

	@FindBy(how = How.XPATH, using = "//div[@id='vehicle_header']//a")
	private WebElement backButton;

	@FindBy(id = "vc-dkn")
	private WebElement dknInput;

	@FindBy(name = "67")
	private WebElement cUiKeyboardButton;

	@FindBy(name = "65")
	private WebElement aUiKeyboardButton;

	@FindBy(name = "56")
	private WebElement eightUiKeyboardButton;

	@FindBy(name = "52")
	private WebElement fourUiKeyboardButton;

	@FindBy(name = "88")
	private WebElement xUiKeyboardButton;

	@FindBy(name = "84")
	private WebElement tUiKeyboardButton;

	@FindBy(id = "regNumberKeyboard")
	private WebElement regNumberUiKeyboard;

	@FindBy(xpath = "//button[@name='accept' and @aria-disabled='false']")
	private WebElement acceptUiKeyboardButton;

	@FindBy(id = "vc-downloadVehicleData")
	private WebElement downloadVehicleDataButton;

	@FindBy(id = "saveCheckVBtn")
	private WebElement saveVehicleCheckButton;

	public VehicleCheckPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public WebElement getVehicleCheckHeader() {
		return vehicleCheckHeader;
	}

	public ActivityPage goBackToVehicleActivityPage() {
		backButton.click();
		ActivityPage activityPage = new ActivityPage(driver);
		if (!activityPage.getActivityDataButton().isDisplayed()) {
			throw new IncorrectPageContentException("Vehicle Activity Page has not been opened.");
		}
		return activityPage;
	}

	public ActivityPage fillVehicleDataAndSubmit(String dkn) {
		dknInput.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		regNumberUiKeyboard = wait.until(ExpectedConditions.visibilityOf(regNumberUiKeyboard));

		cUiKeyboardButton.click();
		aUiKeyboardButton.click();
		eightUiKeyboardButton.click();
		fourUiKeyboardButton.click();
		fourUiKeyboardButton.click();
		eightUiKeyboardButton.click();
		xUiKeyboardButton.click();
		tUiKeyboardButton.click();

		acceptUiKeyboardButton = wait.until(ExpectedConditions.visibilityOf(acceptUiKeyboardButton));
		acceptUiKeyboardButton.click();
		// WebDriverWait wait = new WebDriverWait(driver, 10);
		downloadVehicleDataButton = wait.until(ExpectedConditions.elementToBeClickable(downloadVehicleDataButton));
		downloadVehicleDataButton.click();
		saveVehicleCheckButton.click();

		ActivityPage activityPage = new ActivityPage(driver);
		if (!activityPage.getActivityDataButton().isDisplayed()) {
			throw new IncorrectPageContentException("Vehicle Activity Page has not been opened.");
		}
		return activityPage;
	}
}
