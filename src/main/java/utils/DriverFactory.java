package utils;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {
	
	private final static Logger logger = LoggerFactory.getLogger(DriverFactory.class);
	private static WebDriver REAL_DRIVER = null;
	public static DataSource datasource;
	public static WebDriver driver;

	public static WebDriver getDriver() throws Exception {
		try {
			datasource = new DataSource();
			System.out.println("Starting to load browser! - Inside: ");
			String browser = datasource.readPropertiesFileForCMD("browser");
			logger.debug("Browser Invoked is :::" +browser);
			
			switch (browser) {
			//firefox setup
			case "firefox":
				if (REAL_DRIVER == null) {
					System.setProperty("webdriver.gecko.driver", Constant.GECKO_DRIVER_DIRECTORY);
					DesiredCapabilities capabilities=DesiredCapabilities.firefox();
				    capabilities.setCapability("marionette", true);
				    driver = new FirefoxDriver();
				}
				break;
		   //chrome setup
			case "chrome":
				if (REAL_DRIVER == null) {
					System.setProperty("webdriver.chrome.driver", Constant.CHROME_DRIVER_DIRECTORY);
					//CHROME OPTIONS
					ChromeOptions options = new ChromeOptions();
					driver = new ChromeDriver(options);
					driver.manage().window().maximize();
				}
				break;
		   //IE setup	
			case "ie":
				if (REAL_DRIVER == null) {
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					System.setProperty("webdriver.ie.driver", Constant.IE_DRIVER_DIRECTORY);
					caps.setCapability("ignoreZoomSetting", true);
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
				}
				break;
			}
		} catch (Exception e) {
			System.out.println("Unable to load browser! - Exception: " + e.getMessage());
		} finally {
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		}
		return driver;
	}
}
