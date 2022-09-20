package com.selendroidtest.core;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class SingleTest extends BaseTest {

	public SingleTest(Properties config, String app_type) throws Exception {
		super(config, app_type);

		try {
			setDriverCapabilities();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String captureScreenshot() {
		String path;
		File outputDir = new File(this.screenshotDir);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}
		this.screenshotDir = outputDir.getAbsolutePath();

		try {
			File source = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
			path = this.screenshotDir + System.getProperty("file.separator") + source.getName();
			FileUtils.moveFile(source, new File(path));
		} catch (IOException e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}
		return path;
	}

}