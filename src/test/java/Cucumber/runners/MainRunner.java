package Cucumber.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

	@RunWith(Cucumber.class)
	@CucumberOptions(features = { "src/test/java/Cucumber/featureFiles/" }, 
	glue = {"stepsDefinitions"},
	plugin = { "pretty", "html:target/index.html","json:target/cucumber.json",
			"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
	publish = false)
	public class MainRunner {

		   
}
