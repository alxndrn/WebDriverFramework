package com.selendroidtest.core;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class TestUtil {

	protected static long WAIT_TIMEOUT;
	protected static long PAGE_LOAD_TIMEOUT;

	private static String APPLICATION_TYPE;
	private static String SCREENSHOTS_DIR;
	private static boolean USE_SELENIUM_GRID;
	private static String XLS_DATAPOOL_FILE_PATH;

	private static Properties config;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "configFile" })
	public void init(@Optional("resources/config.properties") String configFile) {
		try {
			config = new Properties();

			FileInputStream in = new FileInputStream(configFile);

			config.load(in);
			in.close();
			APPLICATION_TYPE = config.getProperty("applicationType");
			USE_SELENIUM_GRID = Boolean.parseBoolean(config.getProperty("useSeleniumGrid"));
			SCREENSHOTS_DIR = config.getProperty("screenshotDir");
			XLS_DATAPOOL_FILE_PATH = config.getProperty("dataPoolPath");
			WAIT_TIMEOUT = Long.parseLong(config.getProperty("elementWaitTimeout"));
			PAGE_LOAD_TIMEOUT = Long.parseLong(config.getProperty("pageLoadTimeout"));

			clearScreenshotDir();
		} catch (Exception e) {
			Reporter.log(e.getMessage());
			Assert.assertTrue(false, e.getMessage());
			System.exit(1);
		}
	}

	protected BaseTest initTest() throws Exception {
		BaseTest test = null;
		if (USE_SELENIUM_GRID) {
			test = new GridTest(config, APPLICATION_TYPE);
		} else {
			test = new SingleTest(config, APPLICATION_TYPE);
		}
		setDriverTimeouts(test);
		return test;
	}

	private void setDriverTimeouts(BaseTest test) {
		test.getDriver().manage().timeouts().implicitlyWait(WAIT_TIMEOUT, TimeUnit.SECONDS);
	}

	private void clearScreenshotDir() {
		File outputDir = new File(SCREENSHOTS_DIR);
		if (outputDir.exists()) {
			for (String fileName : outputDir.list()) {
				File file = new File(outputDir, fileName);
				file.delete();
			}
			outputDir.delete();
		}
	}

	public String[][] readTestDataFromXls(String sheetName, String tableName) {
		String[][] tableArray = null;
		final int COLUMNS_TO_RETREIVE_COUNT = 100;
		final int ROWS_TO_RETREIVE_COUNT = 64000;
		Workbook workbook = null;
		File file = null;

		try {

			file = new File(XLS_DATAPOOL_FILE_PATH);

			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow, startCol, endRow, endCol, ci, cj;
			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, COLUMNS_TO_RETREIVE_COUNT,
					ROWS_TO_RETREIVE_COUNT, false);

			endRow = tableEnd.getRow();
			endCol = tableEnd.getColumn();

			tableArray = new String[endRow - startRow - 1][endCol - startCol - 1];
			ci = 0;

			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tableArray[ci][cj] = sheet.getCell(j, i).getContents();
				}
			}

		} catch (Exception e) {
			System.out.println(" 1 Error while reading from the xls file!");
			e.printStackTrace();
			Reporter.log(e.getMessage());
		} finally {
			workbook.close();
		}
		return tableArray;
	}

	private CellCoordinates getCellCoordinates(String value, String sheetName, String tableName) {
		File file = null;
		CellCoordinates cellCoords = null;
		final int COLUMNS_TO_RETREIVE_COUNT = 100;
		final int ROWS_TO_RETREIVE_COUNT = 64000;
		Workbook workbook = null;
		try {

			file = new File(URLDecoder.decode(getClass().getResource(XLS_DATAPOOL_FILE_PATH).getFile(), "UTF-8"));

			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow, startCol, endCol;

			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, COLUMNS_TO_RETREIVE_COUNT,
					ROWS_TO_RETREIVE_COUNT, false);

			endCol = tableEnd.getColumn();

			for (int j = startCol + 1; j < endCol; j++) {
				if (sheet.getCell(j, startRow).getContents().equals(value)) {
					cellCoords = new CellCoordinates(j, startRow);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("2 Error while reading from the xls file!");
			e.printStackTrace();
			Reporter.log(e.getMessage());
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return cellCoords;
	}

	private class CellCoordinates {
		protected int rowNum;
		protected int columnNum;

		protected CellCoordinates(int colNum, int rowNum) {
			this.rowNum = rowNum;
			this.columnNum = colNum;
		}
	}

	public void setDatapoolCellValue(String value, String property, String sheetName, String tableName) {
		CellCoordinates cellCoords = getCellCoordinates(property, sheetName, tableName);
		File file = null;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		if (cellCoords != null) {
			try {
				file = new File(URLDecoder.decode(getClass().getResource(XLS_DATAPOOL_FILE_PATH).getFile(), "UTF-8"));
				fileInputStream = new FileInputStream(file);

				HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
				HSSFSheet sheet = workbook.getSheet(sheetName);
				HSSFCell cell;

				cell = sheet.getRow(cellCoords.rowNum + 1).getCell((short) cellCoords.columnNum);
				cell.setCellValue(value);

				fileOutputStream = new FileOutputStream(file);
				workbook.write(fileOutputStream);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Reporter.log(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				Reporter.log(e.getMessage());
			} finally {
				closeStream(fileInputStream);
				closeStream(fileOutputStream);
			}
		}
	}

	private void closeStream(Closeable streamToClose) {
		try {
			if (streamToClose != null)
				streamToClose.close();
		} catch (IOException e) {
			e.printStackTrace();
			Reporter.log(e.getMessage());
		}
	}
}
