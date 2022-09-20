package com.selendroidtest.tasks;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class AlertHelper {

	private static Alert alert;

	public static void acceptAlert(WebDriver driver) {
		alert = driver.switchTo().alert();
		alert.accept();

	}

	public static void acceptAlertIfPresent(WebDriver driver) {
		if (isAlertPresent(driver)) {
			acceptAlert(driver);
		}
	}

	private static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
}
