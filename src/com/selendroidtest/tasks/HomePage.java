package com.selendroidtest.tasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selendroidtest.core.IncorrectPageContentException;

public class HomePage {

	private WebDriver driver;

	@FindBy(how = How.XPATH, using = "//h4[@id='instruction_header']//a//span")
	private WebElement instructionHeaderButton;

	@FindBy(how = How.XPATH, using = "//h4[@id='bulletin_header']//a//span")
	private WebElement bulletinHeaderButton;

	@FindBy(how = How.XPATH, using = "//div[@id='instruction-confirm-btn']//a//span")
	private WebElement instructionConfirmButton;

	@FindBy(id = "instruction-confirm")
	private WebElement instructionConfirmDialogButton;

	@FindBy(id = "instruction-confirm-popup")
	private WebElement instructionConfirmPopup;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public WebElement getInstructionHeaderButton() {
		return instructionHeaderButton;
	}

	public void confirmInstructionPlan() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		instructionConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(instructionConfirmButton));
		instructionConfirmButton.click();
		if (!instructionConfirmPopup.isDisplayed()) {
			throw new IncorrectPageContentException("The Confirm Instruction Pop-up has not opened.");
		}
		instructionConfirmDialogButton.click();
	}

}
