package com.selendroidtest.core;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;

public abstract class BaseTest {
	private String result;
	private String stepDescription;
	private String testLog;
	private boolean captureScreenshot;

	SelendroidLauncher selendroidServer;

	protected enum TargetApplication {
		native_app, hybrid_app;
	}

	protected TargetApplication app_type;
	protected WebDriver driver;
	protected DesiredCapabilities capabilities;
	protected String screenshotDir;

	private String username;
	private String password;

	protected abstract String captureScreenshot();

	protected void logGridOption() {
		this.logMessage("<p><font size=1 color='black'><b>Using Selenium Grid: "
				+ Boolean.toString(this instanceof GridTest).toUpperCase() + " </b></font></p>");
	}

	protected BaseTest(Properties config, String app_type) throws Exception {
		try {
			this.app_type = TargetApplication.valueOf(app_type);
		} catch (IllegalArgumentException e) {
			System.out.println("Not supported Target Application: " + app_type + "\nPlease check config file!\n");
			System.exit(1);
		}
		this.result = "";
		this.testLog = "";
		this.captureScreenshot = Boolean.parseBoolean(config.getProperty("captureScreenshot"));
		this.screenshotDir = config.getProperty("screenshotDir");
		this.username = config.getProperty("username");
		this.password = config.getProperty("password");
		this.setDriverCapabilities();
	}

	protected void setDriverCapabilities() throws Exception {
		SelendroidConfiguration config = new SelendroidConfiguration();
		// config.addSupportedApp("selendroid-test-app-0.15.0.apk");
		SelendroidCapabilities capabilities = SelendroidCapabilities.device("io.selendroid.testapp:0.15.0");
		capabilities.setVersion("17");
		capabilities.setModel("HUAWEI P6-U06");
		capabilities.setEmulator(false);
		capabilities.setSerial("022AQQ7N37105587");
		if (selendroidServer == null) {
			selendroidServer = new SelendroidLauncher(config);
			selendroidServer.launchSelendroid();
		}
		this.driver = new SelendroidDriver(capabilities);
	}

	public void startTest() {
		try {
			switch (this.app_type) {
			case native_app:
				this.driver.switchTo().window("NATIVE_APP");
				break;
			case hybrid_app:
				this.driver.switchTo().window("WEBVIEW");
				break;
			default:
				System.out.println("Error: Not supported application type is used!");
				System.exit(1);
				break;
			}
		} catch (Exception e) {
			this.logMessage(e.getMessage());
			this.fluchLog();
			this.driver.quit();
			return;
		}
	}

	public void endTest() {
		this.logMessage("</ol>");
		try {
			Assert.assertTrue(this.result.equalsIgnoreCase(""), this.result);
		} finally {
			this.fluchLog();
			this.driver.quit();
		}
	}

	public void startStep(String description) {
		this.stepDescription = description;
	}

	public void endStep(boolean condition) {
		endStep(condition, null);
	}

	public void endStep() {
		endStep(true, null);
	}

	public void endStep(boolean condition, String errorMessage) {

		this.logMessage("<li>" + stepDescription);

		if (errorMessage == null) {
			errorMessage = "No error message was added for that assertion";
		}

		if (condition) {
			this.logMessage("...<b><font size=4 color='green'>&#x2713</font></b>");
		} else {
			this.logMessage("...<b><font size=5 color='red'>&#x2717</font></a></b>");
			this.result = errorMessage;
			if (this.captureScreenshot) {
				String screenShotLink = this.captureScreenshot();
				this.logMessage("...<a href=" + screenShotLink + " target='_blank'>view screenshot</a>");
			}
		}
		this.logMessage("</li>");
	}

	public void logMessage(String message) {
		this.testLog += message;
	}

	public void fluchLog() {
		Reporter.log(this.testLog);
	}

	public String getStepDescription() {
		return this.stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public TargetApplication getApplicationType() {
		return this.app_type;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}