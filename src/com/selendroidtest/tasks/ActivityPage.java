package com.selendroidtest.tasks;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selendroidtest.core.IncorrectPageContentException;

public class ActivityPage {

	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//span[text()='Данни за дейността']//..")
	private WebElement activityDataButton;

	@FindBy(id = "time-2")
	private WebElement endTimeInput;

	@FindBy(how = How.XPATH, using = "//div[@id='vehicle']//div[12]//span//fieldset[1]//div[2]//span")
	private WebElement plusEndTimeButton;

	@FindBy(how = How.XPATH, using = "//div[@id='vehicle']//div[12]//span//div[2]//a//span")
	private WebElement saveEndTimeButton;

	@FindBy(id = "point")
	private WebElement routingPointDropdown;

	@FindBy(how = How.XPATH, using = "//span[text()='Описание и адрес']//..")
	private WebElement descAndAddressRadioButton;

	@FindBy(id = "addressText")
	private WebElement addressInput;

	@FindBy(id = "save_vehicle_button")
	private WebElement saveButton;

	@FindBy(id = "up-Popup-cancel-btn")
	private WebElement resetButton;

	// @FindBy(xpath = "//div[@id='check-list']//h4//a//span")
	@FindBy(id = "check-list")
	private WebElement openCheckListButton;

	@FindBy(how = How.XPATH, using = "//a[@id='newVehicleCheck']//span")
	private WebElement newVehicleCheckButton;

	@FindBy(id = "newVehicleActivity")
	private WebElement newVehicleActivityButton;

	public ActivityPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public WebElement getActivityDataButton() {
		return activityDataButton;
	}

	public boolean setActivityDataAndSave(String routingPoint) throws Exception {
		// choose End time later than Start time
		endTimeInput.click();
		plusEndTimeButton.click();
		saveEndTimeButton.click();

		// choose routing point from dropdown menu - not working due to changes
		// on page
		// Select preferedPoint = new Select(routingPointDropdown);
		// preferedPoint.selectByVisibleText(routingPoint);

		// enter description address
		WebElementHelper.swipeScreen(driver);
		descAndAddressRadioButton.click();
		addressInput.sendKeys(routingPoint);
		WebElementHelper.verifyWebElementValueIsSameAs(addressInput, routingPoint);
		// save activity
		WebElementHelper.swipeScreen(driver);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		saveButton = wait.until(ExpectedConditions.elementToBeClickable(saveButton));

		saveButton.sendKeys(Keys.ENTER);
		return openCheckListButton.isEnabled();
	}

	public boolean openCheckListTab() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		openCheckListButton = wait.until(ExpectedConditions.elementToBeClickable(openCheckListButton));
		// WebElementHelper.swipeScreen(driver);
		openCheckListButton.sendKeys(Keys.ENTER);

		// WebDriverWait wait = new WebDriverWait(driver, 10);
		newVehicleCheckButton = wait.until(ExpectedConditions.visibilityOf(newVehicleCheckButton));
		if (newVehicleCheckButton.isDisplayed()) {
			return true;
		}
		return false;
	}

	public VehicleCheckPage openVehiclePage() throws InterruptedException {
		newVehicleCheckButton.click();

		VehicleCheckPage vehicleCheckPage = new VehicleCheckPage(driver);
		if (!vehicleCheckPage.getVehicleCheckHeader().isDisplayed()) {
			throw new IncorrectPageContentException("Vehicle Check page has not been opened.");
		}
		return vehicleCheckPage;
	}
}
