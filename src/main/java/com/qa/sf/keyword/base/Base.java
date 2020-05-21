package com.qa.sf.keyword.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {

	public WebDriver driver;
	public Properties prop;

	public WebDriver init_driver(String browserName) {
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\drivers\\chromedriver_win32 (1)\\chromedriver.exe");

			if (prop.getProperty("headless").equals("yes")) {
				// headless mode
				ChromeOptions options = new ChromeOptions();
				//to wait for the DOMContentLoaded event (html content downloaded and parsed only)
				options.setPageLoadStrategy(PageLoadStrategy.EAGER);
				options.addArguments("--headless");
				driver = new ChromeDriver(options);
			} else {
				driver = new ChromeDriver();
			}
		} else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\drivers\\chromedriver_win32 (1)\\geckodriver.exe");
			driver = new FirefoxDriver();

		}
			
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


		return driver;
	}

	public Properties init_properties() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(
					".\\src\\main\\java\\com\\qa\\sf\\keyword\\config\\config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
