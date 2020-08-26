package utils;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class MasterHooks extends DriverFactory {

	public MasterHooks() throws Exception {
		
	}
	
	private final Logger logger = LoggerFactory.getLogger(MasterHooks.class);
	
	@Before
	public void setUp(Scenario scenario) throws Exception {
		logger.info("### Automation Execution Started  :::");
		logger.info("############Inside Before ::::::::::::");
		logger.info("########################::::::::::::::");
//		manage().deleteAllCookies();
	}  
	
	@After
    public void tearDownEmbedScreenshot(Scenario scenario) throws IOException, InterruptedException {
        try {
//            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
        	logger.info("### Failed: TearDownAndScreenshotFailed , Exception::: " + somePlatformsDontSupportScreenshots.getMessage());
        }
    }
}
