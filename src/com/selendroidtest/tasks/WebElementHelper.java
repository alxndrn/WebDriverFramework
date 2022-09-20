package com.selendroidtest.tasks;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selendroidtest.core.IncorrectPageContentException;

public class WebElementHelper {

	public static void waitForElementToAppear(WebDriver driver, int secondToWait, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, secondToWait);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * 
	 * @param webElement
	 * @param inputValue
	 * 
	 *            Checks if the inputValue is null, clears the webElement value
	 *            attribute if its set and types the inputValue in the
	 *            webElement(input, textarea etc.)
	 */
	public static void fillInput(WebElement webElement, String inputValue) {
		if (inputValue != null) {
			// clear the input if it has value
			if (!webElement.getAttribute("value").isEmpty()) {
				webElement.clear();
			}
			webElement.sendKeys(inputValue);
		}
	}

	/**
	 * 
	 * @param webElement
	 * @param expectedValue
	 * 
	 *            Throws IncorrectPageContentException exception to be handled
	 *            by the framework if the expected value differs from the actual
	 * 
	 *            webElement - webElement(input, textarea) which has attribute
	 *            value
	 * 
	 *            expectedValue - the value to check against
	 * 
	 */

	public static void verifyWebElementValueIsSameAs(WebElement webElement, String expectedValue) {
		if (!expectedValue.equals(webElement.getAttribute("value"))) {
			String message = "The value of the displayed " + webElement.toString()
					+ " is not correct. The expected value is " + expectedValue + " but found "
					+ webElement.getAttribute("value");
			throw new IncorrectPageContentException(message);
		}
	}

	/**
	 * @param WebDriver
	 * @param WebElement
	 * 
	 * 
	 *            Scroll the window down in order to view an element.
	 * 
	 *            driver - the used driver in the test
	 * 
	 *            element - the target element which must be shown
	 */
	public static void scrollDownWindow(WebDriver driver, WebElement element) {
		if (driver != null) {
			HashMap<String, String> scrollToObject = new HashMap<String, String>();
			scrollToObject.put("element", element.getAttribute("id"));
			((JavascriptExecutor) driver).executeScript("mobile: scrollTo", scrollToObject);
		} else {
			throw new IncorrectPageContentException("Couldn't scroll the window");
		}
	}

	/**
	 * @param WebDriver
	 * 
	 * 
	 *            Scroll The window to the bottom of page.
	 * 
	 * 
	 * 
	 */

	public static void scroolToTheBottom(WebDriver driver) {
		if (driver != null) {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		} else {
			throw new IncorrectPageContentException("Counld't scroll to the bottom");
		}
	}

	public static void swipeScreen(WebDriver driver) {
		if (driver != null) {
			TouchActions swipe = new TouchActions(driver).flick(0, 1000);
			swipe.perform();
		} else {
			throw new IncorrectPageContentException("Counld't swipe the screen");
		}
	}

}
