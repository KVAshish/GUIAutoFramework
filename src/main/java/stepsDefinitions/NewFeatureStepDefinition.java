package stepsDefinitions;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Given;
import pagesObjects.NewFeaturePage;
import utils.DataSource;
import utils.DriverFactory;
import utils.SeleniumWrapper;

public class NewFeatureStepDefinition {

	NewFeaturePage newfeature_Page;
	private final Logger logger = LoggerFactory.getLogger(NewFeatureStepDefinition.class);
	
	public NewFeatureStepDefinition() throws Exception {
		newfeature_Page = new NewFeaturePage();
	}

	@Given("User Login to the Appilcation {string}")
	public void user_Login_to_the_Appilcation(String data_Source) throws Throwable {
		try {
			System.out.println("Data Source Name is :::" +data_Source);
			String username = DataSource.getDataCSV(data_Source, "name");
			System.out.println("User Name is  :::" +username);
			
			String url = DriverFactory.datasource.readPropertiesFileForCMD("application_url");
			String userName = DriverFactory.datasource.readPropertiesFileForCMD("username");
			String password = DriverFactory.datasource.readPropertiesFileForCMD("password");
			String clientId = DriverFactory.datasource.readPropertiesFileForCMD("clientid");
			DriverFactory.getDriver().get(url);
			newfeature_Page.loginApplication();
			logger.debug("user Name is:: "+userName);
			logger.debug("user Passowrd is:: "+password);
			logger.debug("user clientId is:: "+clientId);
			SeleniumWrapper.driver.quit();
			
		} catch (Exception e) {
			Assert.fail("####Login Failed :::");
		}
	}
}
