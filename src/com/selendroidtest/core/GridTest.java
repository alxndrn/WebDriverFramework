package com.selendroidtest.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GridTest extends BaseTest {

	public GridTest(Properties config, String app_type) throws Exception {
		super(config, app_type);

		// get appropriate driver instance
		try {
			this.driver = new RemoteWebDriver(new URL(config.getProperty("seleniumGridHubUrl")), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String captureScreenshot() {
		String path;
		WebDriver scDriver = new Augmenter().augment(this.driver);

		File outputDir = new File(this.screenshotDir);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}
		this.screenshotDir = outputDir.getAbsolutePath();

		try {
			File source = ((TakesScreenshot) scDriver).getScreenshotAs(OutputType.FILE);
			path = this.screenshotDir + System.getProperty("file.separator") + source.getName();
			FileUtils.moveFile(source, new File(path));
		} catch (IOException e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}
		return path;
	}

}