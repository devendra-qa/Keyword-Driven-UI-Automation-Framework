package com.qa.sf.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qa.sf.keyword.base.Base;

public class KeyWordEngine {

	public WebDriver driver;
	public Properties prop;

	public static Workbook book;
	public static Sheet sheet;

	public Base base;
	public WebElement element;

	public final String SCENARIO_SHEET_PATH = ".\\src\\main\\java\\com\\qa\\sf\\keyword\\scenarios\\sf_keyword_scenarios.xlsx";

	public void startExecution(String sheetName) {

		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {

			try {
				String locatorType = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
				String locatorValue = sheet.getRow(i + 1).getCell(k + 4).toString().trim();
				String action = sheet.getRow(i + 1).getCell(k + 5).toString().trim();
				String value = sheet.getRow(i + 1).getCell(k + 6).toString().trim();

				switch (action) {
				case "launchBrowser":
					base = new Base();
					prop = base.init_properties();
					if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
						driver = base.init_driver(prop.getProperty("browser"));
					} else {
						driver = base.init_driver(value);
					}
					break;

				case "enterUrl":
					if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
						driver.get(prop.getProperty("url"));
					} else {
						driver.get(value);
					}
					break;

				case "quitBrowser":
					driver.close();
					driver.quit();
					break;

				case "implicitlyWait":
					try {
						driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case "getTitle":
					try {
						WebDriverWait wait = new WebDriverWait(driver, 10);
						wait.until(ExpectedConditions.titleContains(value));
						//assert page title
						Assert.assertTrue(driver.getTitle().contains(value));
						System.out.println("page title matched: " + value);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case "getCurrentUrl":
					try {
						//assert current url
						Assert.assertEquals(driver.getCurrentUrl(), value);
						System.out.println("Url matched: " + value);
					} catch (AssertionError e) {
						e.printStackTrace();
					}
					break;

				default:
					break;
				}

				switch (locatorType) {

				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						try {
							element.clear();
							element.sendKeys(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("clickTab")) {
						try {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", element);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.visibilityOf(element));
							Assert.assertTrue(element.isDisplayed());
							//element.isDisplayed();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("getText")) {
						try {
							String elementText = element.getText();
							System.out.println("text from element : " + elementText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("assertText")) {
						try {
							Assert.assertEquals(element.getText(), value);
							System.out.println("text " + value + " matched");
						} catch (AssertionError e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("select")) {
						try {
							element.click();
							WebElement selectValue = driver
									.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));
							selectValue.click();
							System.out.println("value selected: " + value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;// to use locatorName in other cases
					break;

				case "name":
					element = driver.findElement(By.name(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						try {
							element.clear();
							element.sendKeys(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("clickTab")) {
						try {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", element);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.visibilityOf(element));
							Assert.assertTrue(element.isDisplayed());
							//element.isDisplayed();						
							} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("getText")) {
						try {
							String elementText = element.getText();
							System.out.println("text from element : " + elementText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("assertText")) {
						try {
							Assert.assertEquals(element.getText(), value);
							System.out.println("text " + value + " matched");
						} catch (AssertionError e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("select")) {
						try {
							element.click();
							WebElement selectValue = driver
									.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));
							selectValue.click();
							System.out.println("value selected: " + value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;// to use locatorName in other cases
					break;

				case "className":
					element = driver.findElement(By.className(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						try {
							element.clear();
							element.sendKeys(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("clickTab")) {
						try {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", element);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.visibilityOf(element));
							Assert.assertTrue(element.isDisplayed());
							//element.isDisplayed();
							} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("getText")) {
						try {
							String elementText = element.getText();
							System.out.println("text from element : " + elementText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("assertText")) {
						try {
							Assert.assertEquals(element.getText(), value);
							System.out.println("text " + value + " matched");
						} catch (AssertionError e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("select")) {
						try {
							element.click();
							WebElement selectValue = driver
									.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));
							selectValue.click();
							System.out.println("value selected: " + value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;// to use locatorName in other cases
					break;

				case "cssSelector":
					element = driver.findElement(By.cssSelector(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						try {
							element.clear();
							element.sendKeys(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("clickTab")) {
						try {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", element);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.visibilityOf(element));
							Assert.assertTrue(element.isDisplayed());
							//element.isDisplayed();
							} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("getText")) {
						try {
							String elementText = element.getText();
							System.out.println("text from element : " + elementText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("assertText")) {
						try {
							Assert.assertEquals(element.getText(), value);
							System.out.println("text " + value + " matched");
						} catch (AssertionError e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("select")) {
						try {
							element.click();
							WebElement selectValue = driver
									.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));
							selectValue.click();
							System.out.println("value selected: " + value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;// to use locatorName in other cases
					break;

				case "xpath":
					element = driver.findElement(By.xpath(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						try {
							element.clear();
							element.sendKeys(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("clickTab")) {
						try {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", element);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.visibilityOf(element));
							Assert.assertTrue(element.isDisplayed());
							//element.isDisplayed();
							} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("getText")) {
						try {
							String elementText = element.getText();
							System.out.println("text from element : " + elementText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("assertText")) {
						try {
							Assert.assertEquals(element.getText(), value);
							System.out.println("text " + value + " matched");
						} catch (AssertionError e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("select")) {
						try {
							element.click();
							WebElement selectValue = driver
									.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));
							selectValue.click();
							System.out.println("value selected: " + value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;// to use locatorName in other cases
					break;

				case "linkText":
					element = driver.findElement(By.linkText(locatorValue));
					if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;
					break;

				case "partialLinkText":
					element = driver.findElement(By.partialLinkText(locatorValue));
					if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;
					break;

				case "tagName":
					element = driver.findElement(By.tagName(locatorValue));
					if (action.equalsIgnoreCase("sendKeys")) {
						try {
							element.clear();
							element.sendKeys(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("click")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(element));
							element.click();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("clickTab")) {
						try {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", element);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.visibilityOf(element));
							Assert.assertTrue(element.isDisplayed());
							//element.isDisplayed();
							} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("getText")) {
						try {
							String elementText = element.getText();
							System.out.println("text from element : " + elementText);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("assertText")) {
						try {
							Assert.assertEquals(element.getText(), value);
							System.out.println("text " + value + " matched");
						} catch (AssertionError e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("verifyElement")) {
						try {
							Assert.assertTrue(element.isDisplayed());
							System.out.println("element " + value + " exists");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (action.equalsIgnoreCase("select")) {
						try {
							element.click();
							WebElement selectValue = driver
									.findElement(By.xpath("//a[contains(text(),'" + value + "')]"));
							selectValue.click();
							System.out.println("value selected: " + value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					locatorType = null;// to use locatorName in other cases
					break;

				default:
					break;
				}
			} catch (Exception e) {
			}

		}

	}

}
