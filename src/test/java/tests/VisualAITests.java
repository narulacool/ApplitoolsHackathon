package tests;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

import base.BaseTest;
import pages.HomePage;
import pages.Login;
import utils.Config;
import utils.GenericFunctions;
import utils.UrlProvider;

public class VisualAITests  extends BaseTest  {

	Login login;
	HomePage homePage;
	WebDriverWait wait;
	SoftAssert softAssert;
	private EyesRunner runner;
	private Eyes eyes;
	private static BatchInfo batch;
	Config config;
	@BeforeClass
	public static void setBatch() {
		// Must be before ALL tests (at Class-level)
		batch = new BatchInfo("Hackathon");
	}

	@BeforeMethod
	public void initialize() {
		config = new Config();

		// Initialize the Runner for your test.
		runner = new ClassicRunner();

		// Initialize the eyes SDK
		eyes = new Eyes(runner);

		// Set your personal Applitols API Key from your environment variables.
		eyes.setApiKey(config.apiKey);

		// set batch name
		eyes.setBatch(batch);
		eyes.setForceFullPageScreenshot(true);
		eyes.setHideScrollbars(true);
		
		generic = new GenericFunctions();
		driver = generic.startDriver("Chrome");
		urlProvider = new UrlProvider();
		login = new Login(driver);
		homePage = new HomePage(driver);
		wait = new WebDriverWait(driver, 10);
		softAssert = new SoftAssert();
	}

	@AfterMethod
	public void cleanUp(){
		// Close the browser.
		driver.quit();
		
		// If the test was aborted before eyes.close was called, ends the test as
		// aborted.
		eyes.abortIfNotClosed();

	}

	@Test
	public void LoginPageUIElementsTest() {
		eyes.open(driver, "Hackathon App", "LoginPageUIElementsTest");
		generic.openUrl(urlProvider.getV2Url());
		// Visual checkpoint #1 - Check the login page UI.
		eyes.checkWindow("Login Window");
		eyes.closeAsync();
	}

	@DataProvider(name = "DataDrivenTest") 
	  public Object[][] testDataforDataDrivenTest()
	  { 
		login = new Login(driver);
		homePage = new HomePage(driver);
		return new Object[][]{
			{"","","Both username & password empty"},
			{"testuser","","Password field empty"},
			{"","testpassword","Username field empty"},
			{"testuser","testpassword","Valid username & password"}
	  } ;
	  }

	@Test(dataProvider = "DataDrivenTest") 
	public void DataDrivenTest(String username,String password, String testName) {
		eyes.open(driver, "Hackathon App", testName);

		generic.openUrl(urlProvider.getV2Url());

		generic.fill(login.usernameTextBox, username);
		generic.fill(login.passwordTextBox, password);
		generic.click(login.loginBtn);
		// Visual checkpoint #1 - Check the page according to test.
		eyes.checkWindow(testName);
		eyes.closeAsync();
	}

	@Test
	public void TableSortTest() {
		eyes.open(driver, "Hackathon App", "TableSortTest");
		generic.openUrl(urlProvider.getV2Url());
		generic.fill(login.usernameTextBox, "testUser");
		generic.fill(login.passwordTextBox, "testPassword");
		generic.click(login.loginBtn);
		
		// Visual checkpoint #1 - Check before sorting.
		eyes.checkWindow("BeforeSortingOnAmount");

		generic.click(homePage.amountHeader);
	
		// Visual checkpoint #2 - Check after sorting.
		eyes.checkWindow("AfterSortingOnAmount");

		eyes.closeAsync();

	}
	
	

	@Test
	public void CanvasChartTest() {
		eyes.open(driver, "Hackathon App", "CanvasChartTest");
		generic.openUrl(urlProvider.getV2Url());
		generic.fill(login.usernameTextBox, "testUser");
		generic.fill(login.passwordTextBox, "testPassword");
		generic.click(login.loginBtn);
		generic.click(homePage.compareExpenses);
		// Visual checkpoint #1 - Check for year 2017,2018
		eyes.checkWindow("CompareExpenses2017,2018");
		generic.click(homePage.showDataForNextYear);
		// Visual checkpoint #2 - Check for year 2017,2018,2019
		eyes.checkWindow("CompareExpenses2017,2018,2019");
		eyes.closeAsync();
	}

	@Test
	public void DynamicContentTest() {
		eyes.open(driver, "Hackathon App", "DynamicContentTest");
		generic.openUrl(urlProvider.getV2Url()+"?showAd=true");
		generic.fill(login.usernameTextBox, "testUser");
		generic.fill(login.passwordTextBox, "testPassword");
		generic.click(login.loginBtn);
		// Visual checkpoint #1 - Check for flash images
		eyes.checkWindow("FlashImagesOnHomePage");
		eyes.closeAsync();

	}
}
