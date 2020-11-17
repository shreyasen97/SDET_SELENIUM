/*
 * 
 * Author: Mayur Kumar
 * Emp Id: MA20081559
 * Last Modified: 08/06/2020
 * 
 * Topic: Topgear Selenium Hands-on
 * 
 * Features used: Selenium, Data driven(Excel), Maven-TestNG framework
 * 
 */

package Configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesFile {

	static Properties prop = new Properties();
	static String projectPath = System.getProperty("user.dir");

	// public static void main(String[] args) {

	// }

	public static String getProperties(String key) {

		String browserName = null;
		String testDataPath = null;
		String sheetName = null;

		try {

			InputStream input = new FileInputStream(projectPath + "/src/test/java/Configuration/config.properties");
			prop.load(input);
			// System.out.println(prop.getProperty("browser"));

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		if (key.equalsIgnoreCase("browser")) {
			browserName = prop.getProperty("Browser");
			return browserName;
		} else if (key.equalsIgnoreCase("testDataPath")) {

			testDataPath = prop.getProperty("TestDataPath");
			return testDataPath;
		} else if (key.equalsIgnoreCase("sheetName")) {

			sheetName = prop.getProperty("TestDataPath");
			return sheetName;
		}

		else {
			return null;
		}

	}

	public static void setProperties() {

		try {
			OutputStream output = new FileOutputStream(projectPath + "/src/test/java/Configuration/config.properties");
			prop.setProperty("browser", "firefox");
			prop.store(output, null);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
