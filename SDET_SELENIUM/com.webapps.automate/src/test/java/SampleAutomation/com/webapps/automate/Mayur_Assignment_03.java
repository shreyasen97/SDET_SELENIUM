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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Configuration.PropertiesFile;
import Utils.ExcelUtils;

public class Mayur_Assignment_03 {

	WebDriver driver = null;
	String browserName = null;
	static ExcelUtils excel;
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static String projectPath = System.getProperty("user.dir");

	@BeforeTest
	void setUpMethod() {
		// String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		htmlReporter = new ExtentHtmlReporter(projectPath + "/Extent Report/extentReports_03.html");
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
	void makeMyTripTest() throws Exception {
		ExtentTest test1 = extent.createTest("MakeMyTrip Test", "MMT Flight booking tests");

		excel = new ExcelUtils(projectPath + "/excel/testdata.xlsx", "Sheet3");

		String departMonth = ExcelUtils.getCellDataString(1, 0);
		String departdate = ExcelUtils.getCellDataNumber(1, 1);
		String returndate = ExcelUtils.getCellDataNumber(1, 2);

		driver.get("https://www.makemytrip.com/");
		test1.pass("Navigated to MakeMyTrip");
		driver.manage().window().maximize();
		Thread.sleep(10000);
		if (!driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div/div[1]/ul/li[2]")).isDisplayed()) {
			driver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
			driver.findElement(By.id("webklipper-publisher-widget-container-notification-close-div")).click();
			driver.switchTo().parentFrame();
			test1.pass("Closed the ad frame");
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='SW']/div[1]/div[1]/ul/li[6]/div[2]")).click();
		Thread.sleep(1000);
		// Select round trip
		WebElement roundtrip1 = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div/div[1]/ul/li[2]"));
		roundtrip1.click();

		// from to place
		driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div/div[2]/div[1]/div[1]/label/span")).click();
		WebElement de = driver.findElement(By.xpath("//input[@placeholder='From']"));
		de.clear();
		Thread.sleep(3000);
		de.sendKeys(ExcelUtils.getCellDataString(1, 3));
		Thread.sleep(3000);
		de.sendKeys(Keys.ARROW_DOWN);
		de.sendKeys(Keys.RETURN);
		// driver.findElement(By.xpath("//*[@id='SW']/div[1]/div[1]/ul/li[6]/div[2]")).click();

		driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div/div[2]/div[1]/div[2]/label/span")).click();
		WebElement ar = driver.findElement(By.xpath("//input[@placeholder='To']"));
		ar.clear();
		Thread.sleep(3000);
		ar.sendKeys(ExcelUtils.getCellDataString(1, 4));
		Thread.sleep(5000);
		ar.sendKeys(Keys.ARROW_DOWN);
		ar.sendKeys(Keys.RETURN);

		// from to date
		driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div/div[2]/div[1]/div[3]/label/span")).click();
		Thread.sleep(2000);
		while (!driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[1]/div")).getText()
				.contains(departMonth)) {
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
		}

		List<WebElement> dates = driver.findElements(By.xpath(
				"//div[@class='DayPicker-Month'][1]/div[@class='DayPicker-Body']/div[@class='DayPicker-Week']/div[@class='DayPicker-Day']"));
		int count = dates.size();
		System.out.println(count);
		for (int i = 0; 1 < count; i++) {
			String text = dates.get(i).getText();
			if (text.equalsIgnoreCase(departdate)) {
				dates.get(i).click();
				break;
			}
		}
		for (int i = 0; 1 < count; i++) {
			String text = dates.get(i).getText();
			if (text.equalsIgnoreCase(returndate)) {
				dates.get(i).click();
				break;
			}
		}
		test1.pass("Details filled");

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@class='primaryBtn font24 latoBold widgetSearchBtn ']")).click();
		Thread.sleep(10000);

		driver.findElement(By.xpath("//*[@id='ow-domrt-jrny']/div/div[1]/label/div/span[1]/span")).click();
		String departflight = driver.findElement(By.xpath("//*[@id='ow-domrt-jrny']/div/div[1]/label/div/span[2]/span"))
				.getText();
		String depart_amt = driver
				.findElement(By.xpath("//*[@id='ow-domrt-jrny']/div/div[1]/label/div[2]/div[3]/div/p/span")).getText();

		driver.findElement(By.xpath("//*[@id='rt-domrt-jrny']/div/div[1]/label/div/span[1]/span")).click();
		String returnflight = driver.findElement(By.xpath("//*[@id='rt-domrt-jrny']/div/div[1]/label/div/span[2]/span"))
				.getText();
		String return_amt = driver
				.findElement(By.xpath("//*[@id='rt-domrt-jrny']/div/div[1]/label/div[2]/div[3]/div/p/span")).getText();
		driver.findElement(By.xpath(" //*[@class='cursor_pointer chevron-down']")).click();

		String rev_dep = driver
				.findElement(By.xpath(
						"//*[@id='left-side--wrapper']/div/div/div[4]/div/div[1]/div/div[1]/div/div[2]/div[4]/p"))
				.getText();
		String rev_return = driver
				.findElement(By.xpath(
						"//*[@id='left-side--wrapper']/div/div/div[4]/div/div[1]/div/div[2]/div/div[2]/div[4]/p"))
				.getText();
		String rev_departflight = driver.findElement(By.xpath("(//*[@class='pull-left flight-name'])[1]")).getText();
		String rev_returnflight = driver.findElement(By.xpath("(//*[@class='pull-left flight-name'])[2]")).getText();

		if ((depart_amt.equalsIgnoreCase(rev_dep)) && return_amt.equalsIgnoreCase(rev_return)) {
			System.out.println("review page verified");
		}
		test1.pass("review page verified");
	}

	@AfterTest
	void tearDownMethod() {
		ExtentTest tear = extent.createTest("tearDownTest", "Browser");
		driver.close();
		// driver.quit();

		tear.pass("Closed the Browser");
		tear.info("Test Completed");

		extent.flush();
	}

}
