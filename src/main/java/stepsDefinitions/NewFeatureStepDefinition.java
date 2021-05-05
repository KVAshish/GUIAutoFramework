package stepsDefinitions;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Given;
import pagesObjects.NewFeaturePage;
import utils.DriverFactory;

public class NewFeatureStepDefinition {

	NewFeaturePage newfeature_Page;
	private final Logger logger = LoggerFactory.getLogger(NewFeatureStepDefinition.class);
	
	public NewFeatureStepDefinition() throws Exception {
		newfeature_Page = new NewFeaturePage();
	}

	@Given("^User Login to the Appilcation$")
	public void user_Login_to_the_Appilcation() throws Throwable {
		try {
			String url = DriverFactory.datasource.readPropertiesFileForCMD("application_url");
			String userName = DriverFactory.datasource.readPropertiesFileForCMD("username");
			String password = DriverFactory.datasource.readPropertiesFileForCMD("password");
			String clientId = DriverFactory.datasource.readPropertiesFileForCMD("clientid");
			DriverFactory.getDriver().get(url);
			newfeature_Page.loginApplication();
			logger.debug("user Name is:: "+userName);
			logger.debug("user Passowrd is:: "+password);
			logger.debug("user clientId is:: "+clientId);
			
			DriverFactory.datasource.getDataInstance("testData.csv");
		} catch (Exception e) {
			Assert.fail("####Login Failed :::");
		}
	}
}
