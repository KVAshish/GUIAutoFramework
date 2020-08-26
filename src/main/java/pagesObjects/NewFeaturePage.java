package pagesObjects;

import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stepsDefinitions.NewFeatureStepDefinition;
import utils.SeleniumWrapper;

public class NewFeaturePage extends SeleniumWrapper{

	public NewFeaturePage() throws Exception {
		PageFactory.initElements(getDriver(), this);
	}
	
	private final Logger logger = LoggerFactory.getLogger(NewFeatureStepDefinition.class);
	public void loginApplication(){
		this.logger.debug("Login Started");
		this.logger.info("Login Started");
		//Enter UserName
	}
}
