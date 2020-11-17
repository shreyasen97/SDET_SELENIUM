/*
 * 
 * Author: Mayur Kumar
 * Emp Id: MA20081559
 * Last Modified: 21/06/2020
 * 
 * Topic: Topgear Selenium Hands-on
 * 
 * Features used: Selenium, Data driven(Excel), Maven-TestNG framework
 * 
 */

package SampleAutomation.com.webapps.automate;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
//import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentAventReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Configuration.PropertiesFile;
import Utils.ExcelUtils;

public class Mayur_Assignment_02 {

	WebDriver driver = null;
	String browserName = null;
	static ExcelUtils excel;
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static String projectPath = System.getProperty("user.dir");

	@BeforeSuite
	void setUpMethod() {

		System.out.println(projectPath);
		htmlReporter = new ExtentHtmlReporter(projectPath + "/Extent Report/extentReports_02.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest configTest = extent.createTest("ConfigTest", "Browser");

		browserName = PropertiesFile.getProperties("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "/Resources/chromedriver.exe");
			driver = new ChromeDriver();
			configTest.log(Status.INFO, "Starting Chrome");
		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/Resources/geckodriver.exe");
			driver = new FirefoxDriver();
			configTest.log(Status.INFO, "Starting Firefox");
		}

	}

	@Test
	void testLanguageLogIn() {
		ExtentTest test1 = extent.createTest("Language Test", "Olay site invalid login message");
		int i = 0;
		String lang[] = { "en", "de", "es" };
		String xpathSubmit;
		while (i < lang.length) {
			if (i == 0) {

				LoginFeature("https://www.olay.co.uk/en-gb", "phdesktopbody_0_SIGN IN");
				test1.log(Status.INFO, "English UK");
			}

			if (i == 1) {
				LoginFeature("https://www.olaz.de/de-de", "phdesktopbody_0_ANMELDEN");
				test1.log(Status.INFO, "German");
			}
			if (i == 2) {
				LoginFeature("https://www.olay.es/es-es", "phdesktopbody_0_INICIAR SESIÓN");
				test1.log(Status.INFO, "Espaneol");
			}
			i++;
		}

	}

	void LoginFeature(String url, String idName) {
		ExtentTest test1 = extent.createTest("Login Test", "Olay site login ");
		excel = new ExcelUtils(projectPath + "/excel/testdata.xlsx", "Sheet1");
		driver.get(url);
		test1.pass("Navigated to Olay");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"phdesktopheader_0_phdesktopheadertop_2_pnlCRMHeaderLink\"]/div/a[1]"))
				.click();
		test1.pass("Navigated to Login");
		// driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.id("phdesktopbody_0_username")).sendKeys(ExcelUtils.getCellDataString(2, 0));
		driver.findElement(By.id("phdesktopbody_0_password")).sendKeys(ExcelUtils.getCellDataString(2, 1));
		test1.pass("Enetered Data successfully");
		driver.findElement(By.id(idName)).submit();
		test1.pass("Logged in successfully");
	}

	@Test
	void testInvalidLogIn() throws Exception {
		ExtentTest test1 = extent.createTest("Invalid password Test", "Olay site invalid login message");
		excel = new ExcelUtils(projectPath + "/excel/testdata.xlsx", "Sheet1");
		driver.get("https://www.olay.com/");
		test1.pass("Navigated to Olay");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/header/nav/ul/li[4]/a")).click();
		test1.pass("Navigated to Login");
		// driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.id("login_email_popup")).sendKeys(ExcelUtils.getCellDataString(1, 0));
		driver.findElement(By.id("login_pass_popup")).sendKeys(ExcelUtils.getCellDataString(1, 1));
		test1.pass("Enetered Data successfully");
		driver.findElement(By.xpath("//*[@id=\"loginModal\"]/div[1]/div/div/form/div[3]/input")).click();
		Thread.sleep(6000);

		String errorMsg = driver.findElement(By.id("message")).getText();

		System.out.println(errorMsg);
		Assert.assertEquals(errorMsg, "Invalid credentials. Please try again");
		test1.pass("Error verified");
		Thread.sleep(3000);

	}

	@AfterSuite
	void tearDownMethod() {
		ExtentTest tear = extent.createTest("tearDownTest", "Browser");
		driver.close();
		// driver.quit();

		tear.pass("Closed the Browser");
		tear.info("Test Completed");

		extent.flush();

	}

}
