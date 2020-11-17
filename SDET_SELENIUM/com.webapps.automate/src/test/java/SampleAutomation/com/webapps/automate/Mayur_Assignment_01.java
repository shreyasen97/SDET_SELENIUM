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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Configuration.PropertiesFile;
import Utils.ExcelUtils;

public class Mayur_Assignment_01 {

	WebDriver driver = null;
	String browserName = null;
	static ExcelUtils excel;
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static String projectPath = System.getProperty("user.dir");

	@BeforeSuite
	void setUpMethod() {

		System.out.println(projectPath);
		htmlReporter = new ExtentHtmlReporter(projectPath + "/Extent Report/extentReports_01.html");
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
	void demoQAtest() throws Exception {

		ExtentTest test1 = extent.createTest("DemoQA Test", "DemoQA all tests");

		excel = new ExcelUtils(projectPath + "/excel/testdata.xlsx", "Sheet2");
		driver.get("http://www.demoqa.com/selectable");
		test1.pass("Navigated to DemoQA selectable");
		driver.findElement(By.xpath("//*[@id=\"verticalListContainer\"]/li[1]")).click();
		driver.findElement(By.xpath("//*[@id=\"verticalListContainer\"]/li[3]")).click();
		test1.pass("Selectable tests done");
		//
		//

		driver.get("http://www.demoqa.com/automation-practice-form");
		test1.pass("Navigated to DemoQA automation-practice-form");
		driver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys(ExcelUtils.getCellDataString(1, 0));
		driver.findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys(ExcelUtils.getCellDataString(1, 1));
		driver.findElement(By.xpath("//*[@id=\"userEmail\"]")).sendKeys(ExcelUtils.getCellDataString(1, 2));
		driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[1]/label")).click();
		driver.findElement(By.xpath("//*[@id=\"userNumber\"]")).sendKeys(ExcelUtils.getCellDataNumber(1, 3));
		test1.pass("Data entered successfully");
		Thread.sleep(2000);
		// driver.findElement(By.xpath("//*[@id=\"subjectsContainer\"]/div/div[1]")).sendKeys("English");
		// driver.findElement(By.xpath("//*[@id=\"currentAddress\"]")).sendKeys("Northwest
		// Street");
		((JavascriptExecutor) driver).executeScript("scroll(0,700)");
		driver.findElement(By.id("submit")).click();
		String success = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
		Assert.assertEquals(success, "Thanks for submitting the form");
		test1.pass("Submitted successfully");

		//// *[@id="genterWrapper"]/div[2]/div[1]/label
		//

		driver.get("http://www.demoqa.com/droppable");
		test1.pass("Navigated to DemoQA droppable");
		WebElement from = driver.findElement(By.xpath("//*[@id=\"draggable\"]"));
		WebElement to = driver.findElement(By.xpath("//*[@id=\"droppable\"]"));
		Actions act = new Actions(driver);

		act.dragAndDrop(from, to).build().perform();
		test1.pass("Done successfully");

		//
		//

		driver.get("http://www.demoqa.com/select-menu");
		test1.pass("Navigated to DemoQA select-menu");

		// driver.findElement(By.xpath("")).click();
		// driver.findElement(By.xpath("//*[@id=\"selectOne\"]/div/div[1]")).click();
		Select drp2 = new Select(driver.findElement(By.xpath("//*[@id=\"oldSelectMenu\"]")));
		drp2.selectByIndex(2);
		Thread.sleep(3000);
		test1.pass("Done successfully");

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
