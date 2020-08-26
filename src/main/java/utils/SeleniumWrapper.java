package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import utils.DriverFactory;

public class SeleniumWrapper extends DriverFactory {

	public SeleniumWrapper() throws Exception {
		    WebDriver driver = getDriver();
			this.wait = new WebDriverWait(driver, 60);
			this.jsExecuter = ((JavascriptExecutor) driver);
		    this.action = new Actions(driver);
			tempList = new ArrayList<String>();
	}
	
	private final Logger logger = LoggerFactory.getLogger(SeleniumWrapper.class);
	protected WebDriverWait wait;
	protected JavascriptExecutor jsExecuter;
	protected Actions action;
	static HashMap<String, String> session;
	public List<String> tempList;
	private static String screenshotName;

	

	protected HashMap<String, String> getMapSession() {
		if (session == null)
			session = new HashMap<>();
		return session;
	}

	/**
	 * This method is used as saving the key values in local working memory
	 * 
	 * @param key
	 * @param value
	 */
	public final void setSession(String key, String value) {
		getMapSession().put(key, value);
	}

	/**
	 * This method is used as retrieving the value from local working memory
	 * 
	 * @param key
	 */
	public final String getSession(String key) {
		return getMapSession().get(key);
	}
	
	/**
	 * This method is used to get the Session Size.
	 * 
	 * @return
	 */
	public final int getSessionSize() {
		return getMapSession().size();
	}
	
	/**
	 * This method is used to get the session Map
	 * 
	 * @return
	 */
	public final HashMap<String, String> getSessionMap() {
		return getMapSession();
	}

	/**
	 * This method is used for checking the WebElement visibility
	 * 
	 * @param by
	 */
	public void waitUntilElementIsVisible(WebElement elem) {

		try {
			this.waitForPageLoaded();
			this.wait.until(ExpectedConditions.visibilityOf(elem));
		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + elem.toString() + ">");
			Assert.fail("Unable to Find the Web Element" + e.getMessage());
		}

	}
	
	/**
	 * This method is used for checking the WebElement is clickable
	 * 
	 * @param by
	 */
	public void waitUntilElementIsClickable(WebElement elem) {

		try {
			this.wait.until(ExpectedConditions.elementToBeClickable(elem));
		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + elem.toString() + ">");
			Assert.fail("Unable to Find the Web Element" + e.getMessage());
		}

	}
	
	
	/**
	 * This method is used for checking the WebElement as Selected.
	 * 
	 * @param elem
	 * @param state
	 */
	public void waitUntilElementIsSelected(WebElement elem, boolean state) {

		try {
			this.wait.until(ExpectedConditions.elementSelectionStateToBe(elem, state));
		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + elem.toString() + ">");
			Assert.fail("Unable to Find the Web Element" + e.getMessage());
		}

	}
	
	/**
	 * This method is used for checking the WebElement is Present.
	 * 
	 * @param elem
	 * @param state
	 */
	public void waitUntilElementIsPresent(By by){

		try {
			this.wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + by.toString() + ">");
			Assert.fail("Unable to Find the Web Element" + e.getMessage());
		}

	}

	/**
	 * This method is used for clicking on Element using Action Class
	 * 
	 * @param Locator
	 */
	public void clickByAction(WebElement Locator) {
		try {

			Actions builder = new Actions(driver);
			builder.click(Locator).build().perform();

		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + Locator.toString() + ">");
			Assert.fail("Unable to click on the Web Element" + e.getMessage());
		}
	}
	
	/**
	 * This method is used for clicking on Element using Action Class With Focus on Element
	 * 
	 * @param Locator
	 */
	public void clickByActionFocus(WebElement Locator) {
		try {

			Actions builder = new Actions(driver);
			builder.moveToElement(Locator);
			builder.click(Locator).build().perform();

		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + Locator.toString() + ">");
			Assert.fail("Unable to click on the Web Element" + e.getMessage());
		}
	}

	/**
	 * This method is used for Send keys on web Element using Action Class; First it
	 * focus on that element then type text
	 * 
	 * @param Locator
	 */
	public void typeByActionFocus(WebElement Locator, String text) {
		try {

			Actions builder = new Actions(driver);
			builder.moveToElement(Locator);
			builder.click();
			builder.sendKeys(text);
			builder.build().perform();

		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + Locator.toString() + ">");
			Assert.fail("Unable to click on the Web Element" + e.getMessage());
		}
	}
	
	/**
	 * This method is used for MouseHover on Element
	 * 
	 * @param Locator
	 */
	public void mouseHover(WebElement Locator) {
		try {

			Actions builder = new Actions(driver);
			builder.moveToElement(Locator);
			
		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + Locator.toString() + ">");
			Assert.fail("Unable to Mouse Hover on the Web Element" + e.getMessage());
		}
	}
	
	/**
	 * This method is used for MouseHover on Element
	 * 
	 * @param Locator
	 */
	public WebElement getElement(String Locator) {

		WebElement elem = driver.findElement(By.xpath(Locator));
		return elem;
	}

	/**
	 * This method is used for Send keys on web Element using Action Class; First it
	 * focus on that element then type text
	 * 
	 * @param Locator
	 */
	public void typeByActionFocus(WebElement Locator, Keys key) {
		try {

			Actions builder = new Actions(driver);
			builder.moveToElement(Locator);
			builder.click();
			builder.sendKeys(key);
			builder.build().perform();

		} catch (Exception e) {
			System.out.println("WebElement is not visible" + "<" + Locator.toString() + ">");
			Assert.fail("Unable to click on the Web Element" + e.getMessage());
		}
	}

	/**
	 * This method is used for the page Load wait with time
	 * 
	 * @param loadTime
	 */
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return jsExecuter.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			this.wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	/**
	 * This method is used for the page Load wait with time
	 * 
	 * @param loadTime
	 */
	public void waitForLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return jsExecuter.executeScript("return document.readyState").equals("complete");
			}
		};
		this.wait.until(pageLoadCondition);
	}

	/**
	 * This method is used for the page Load wait with time
	 * 
	 * @param loadTime
	 */
	public void waitPageLoad(int loadTime) {
		new WebDriverWait(driver, loadTime)
				.until(Webdriver -> jsExecuter.executeScript("return document.readyState").equals("complete"));
	}

	public void scrollPageBottom() {
		try {
			long lastHeight = (long) jsExecuter.executeScript("return document.documentElement.scrollHeight");
			while (true) {
				// jsExecuter.executeScript("window.scrollTo(0,
				// document.documentElement.scrollHeight);");
				jsExecuter.executeScript("window.scrollBy(0,999999999999999999999)", "");
				Thread.sleep(5000);

				long newHeight = (long) jsExecuter.executeScript("return document.documentElement.scrollHeight");
				if (newHeight == lastHeight) {
					break;
				}
				lastHeight = newHeight;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used for clicking the WebElement With Custom expected time out
	 * 
	 * @param by
	 * @param driver
	 * @param timeout
	 */
	public void clickOnElementUsingCustomTimeout(By by, WebDriver driver, int timeout) {
		try {
			final WebDriverWait customWait = new WebDriverWait(driver, timeout);
			customWait.until(ExpectedConditions.elementToBeClickable(by)).click();
			System.out.println("Successfully clicked on the WebElement, using locator: " + "<" + by.toString() + ">"
					+ ", using a custom Timeout of: " + timeout);
		} catch (Exception e) {
			System.out.println("Unable to click on the WebElement, using locator: " + "<" + by.toString() + ">"
					+ ", using a custom Timeout of: " + timeout);
			Assert.fail("Unable to click on the WebElement, Exception: " + e.getMessage());
		}
	}

	/**
	 * This method Click On Text from Drop Down
	 * 
	 * @param list
	 * @param textToSearchFor
	 * @throws Exception
	 */
	public void clickOnTextFromDropdownList(WebElement list, String textToSearchFor) throws Exception {
		Wait<WebDriver> tempWait = new WebDriverWait(driver, 30);
		try {
			tempWait.until(ExpectedConditions.elementToBeClickable(list)).click();
			list.sendKeys(textToSearchFor);
			list.sendKeys(Keys.ENTER);
			System.out.println("Successfully sent the following keys: " + textToSearchFor
					+ ", to the following WebElement: " + "<" + list.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to send the following keys: " + textToSearchFor
					+ ", to the following WebElement: " + "<" + list.toString() + ">");
			Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
		}
	}

	/**
	 * This method is used for the Clicking the WebElement using the ACtion Class
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void actionMoveAndClickByLocator(By element) throws Exception {
		Actions ob = new Actions(driver);
		try {
			Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
			if (elementPresent == true) {
				WebElement elementToClick = driver.findElement(element);
				ob.moveToElement(elementToClick).click().build().perform();
				System.out.println("Action moved and clicked on the following element, using By locator: " + "<"
						+ element.toString() + ">");
			}
		} catch (StaleElementReferenceException elementUpdated) {
			WebElement elementToClick = driver.findElement(element);
			ob.moveToElement(elementToClick).click().build().perform();
			System.out
					.println("(Stale Exception) - Action moved and clicked on the following element, using By locator: "
							+ "<" + element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to Action Move and Click on the WebElement using by locator: " + "<"
					+ element.toString() + ">");
			Assert.fail(
					"Unable to Action Move and Click on the WebElement using by locator, Exception: " + e.getMessage());
		}
	}

	// return ByType of WebElement
	public By getByElem(WebElement we) {
		// By format = "[foundFrom] -> locator: term"
		// see RemoteWebElement toString() implementation
		String[] data = we.toString().split(" -> ")[1].replace("]", "").split(": ");
		String locator = data[0];
		String term = data[1];

		switch (locator) {
		case "xpath":
			return By.xpath(term);
		case "css selector":
			return By.cssSelector(term);
		case "id":
			return By.id(term);
		case "tag name":
			return By.tagName(term);
		case "name":
			return By.name(term);
		case "link text":
			return By.linkText(term);
		case "class name":
			return By.className(term);
		}
		return (By) we;
	}
	
	/**
	 * This method is used for type in the Text Field
	 * 
	 * @param by
	 * @param textToSend
	 * @throws Exception
	 */
	public void typeToWebElement(By by, String textToSend) throws Exception {
		try {
			this.WaitUntilWebElementIsVisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(textToSend);
			System.out.println("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<"
					+ by.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to locate WebElement: " + "<" + by.toString() + "> and send the following keys: "
					+ textToSend);
			Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
		}
	}

	public boolean WaitUntilWebElementIsVisible(By by) {
		try {
			this.wait.until(ExpectedConditions.visibilityOf((WebElement) by));
			System.out.println("WebElement is visible using locator: " + "<" + by.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using locator: " + "<" + by.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}

	public void WaitUntillTextToBePresent(By by) {
		
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		
	}

	public boolean WaitUntilWebElementIsVisibleUsingByLocator(By by) {
		try {
			this.jsExecuter.executeScript("return document.readyState").equals("complete");
			this.wait.until(ExpectedConditions.presenceOfElementLocated(by));
			System.out.println("Element is visible using By locator: " + "<" + by.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT visible, using By locator: " + "<" + by.toString() + ">");
			Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * This method
	 * 
	 * @param by
	 * @return
	 */
	public WebElement waitForWebElement(By by) {
		
		boolean elementClickableState = false;
		boolean elementVisibleState = false;
		boolean elementFound = false;
		int intCounter = 0;
		WebElement webWaitForPresent = null;
		try {
			
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.presenceOfElementLocated(by));
			webWaitForPresent = driver.findElement(by);
			
			elementClickableState = isElementClickable(by);
			elementVisibleState = isElementVisable(by);
			logger.debug("###Document State is :::" +this.jsExecuter.executeScript("return document.readyState"));
			if(this.jsExecuter.executeScript("return document.readyState").equals("Complete"))		
			{
				while(elementClickableState==true && elementVisibleState==true && intCounter < 20){
					logger.debug("### WebElement Found :::" +by.toString());
					elementFound = true;
					intCounter = 0;
					return webWaitForPresent;
				}
			}
		} catch (Exception e) {
			if(elementFound) {
				logger.debug("### WebElement Found :::");
				return webWaitForPresent;
			}else if(intCounter < 15) {
				logger.debug("### Element not found :::" +by.toString());
				logger.debug(e.getMessage());
				waitForWebElement(by);
			}else {
				logger.debug(e.getMessage());
			}
			intCounter = 0;
		}
		
		return webWaitForPresent;
		
	}

	/**
	 * This method is Wait method used to check if element is ready to Click
	 * 
	 * @param by
	 * @return
	 */
	public boolean isElementClickable(By by) {
		try {
			this.wait.until(ExpectedConditions.elementToBeClickable(by));
			System.out.println("WebElement is clickable using locator: " + "<" + by.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT clickable using locator: " + "<" + by.toString() + ">");
			return false;
		}
	}
	
	/**
	 * This method is Wait method used to check if element is ready to Click
	 * 
	 * @param by
	 * @return
	 */
	public boolean isElementVisable(By by) {
		try {
			this.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			System.out.println("WebElement is clickable using locator: " + "<" + by.toString() + ">");
			return true;
		} catch (Exception e) {
			System.out.println("WebElement is NOT clickable using locator: " + "<" + by.toString() + ">");
			return false;
		}
	}

	public boolean waitUntilPreLoadElementDissapears(By by) {
		return this.wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	
	/**
	 * This method ignores the stale element exception and wait till the wait time
	 * 
	 * @param by
	 */
	public void ignoreStaleElementWait(By by) {
		this.wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(by));
	}
	/**
	 * This method is used to get the Text from the Webelement
	 * 
	 * @param by
	 * @return
	 */
	public String getTextFromElement(By by) {
		String text = driver.findElement(by).getText();
		return text;
	}

	/**
	 * This method is used to click on WebElement using Selenium Click method.
	 * 
	 * @param by
	 */
	public void click(By by) {
		try {
			this.WaitUntilWebElementIsVisibleUsingByLocator(by);
			SeleniumWrapper.driver.findElement(by).click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("WebElement is NOT clickable using locator: " + "<" + by.toString() + ">");
			Assert.fail("Locator not clickable");
		}
	}

	/**
	 * This Method is used for clicking the WebElement using Java Script executer
	 * 
	 * @param element
	 */
	public void javaScriptClick(WebElement element) {
		try {
			this.jsExecuter.executeScript("return arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println("Click not successfully");
			Assert.fail("Locator not clickable");
		}
	}

	/**
	 * This Method is used for waiting the WebElement using Java Script executer
	 * 
	 * @param element
	 */
	public void javaScriptWait(WebElement element) {
		try {
			this.jsExecuter.executeScript("arguments[0].scrollIntoView(false);", element);
		} catch (Exception e) {
			Assert.fail("Locator not found");
		}
	}

	/**
	 * This method is used for setting the implicit wait.
	 * 
	 * @param timeout
	 * @param webElement
	 */
	public void setImplicitwait(int timeout, WebElement webElement) {
		try {
			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			Assert.fail("Implicit Wait not working for Webelement Action >>" +webElement.toString());
		}

	}
	
	/**
	 * This method is used for setting the implicit wait.
	 * 
	 * @param timeout
	 * @param webElement
	 */
	public void setImplicitwait(int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			Assert.fail("Implicit Wait not working >>");
		}

	}
	
	/**
	 * Fluent Wait Condition
	 * 
	 * @param element
	 * @param timeoutSec
	 * @param pollingeSec
	 * @return
	 */
	public WebElement fluentWaitforElementToVisible(WebElement element, long timeoutSec, long pollingeSec) {
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver).withTimeout(timeoutSec, TimeUnit.SECONDS)
				.pollingEvery(pollingeSec, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, TimeoutException.class)
				.ignoring(StaleElementReferenceException.class);
				fWait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}
	
	
//	/**
//	 * Fluent Wait Condition
//	 * 
//	 * @param element
//	 * @param timeoutSec
//	 * @param pollingeSec
//	 * @return
//	 */
//	public By fluentWaitforElementToVisible(By locator, long timeoutSec, long pollingeSec) {
//		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver).withTimeout(timeoutSec, TimeUnit.SECONDS)
//				.pollingEvery(pollingeSec, TimeUnit.SECONDS)
//				.ignoring(NoSuchElementException.class, TimeoutException.class)
//				.ignoring(StaleElementReferenceException.class);
//				fWait.until(ExpectedConditions.visibilityOf(By.xpath(locator)));
//
//		return locator;
//	}
	
	public WebElement getWebElement(String Locator) {
		WebElement elem =driver.findElement(By.xpath(Locator));
		return elem;
	}
	
	
}
