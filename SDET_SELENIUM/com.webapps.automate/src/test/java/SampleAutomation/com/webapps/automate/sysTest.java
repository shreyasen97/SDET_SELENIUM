package SampleAutomation.com.webapps.automate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class sysTest {

	public static void main(String args[]) {

		String projectPath = System.getProperty("user-dir");
		System.out.println(projectPath);
		System.setProperty("webdriver.chrome.driver", "D:\\Mayur\\com.webapps.automate\\Resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.olay.com/");

	}

}
