package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.HomePage;
import pages.Login;
import utils.GenericFunctions;
import utils.UrlProvider;


public class TraditionalTests extends BaseTest {

	Login login;
	HomePage homePage;
	WebDriverWait wait;
	SoftAssert softAssert;

	@BeforeMethod
	public void initialize() {
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
	}

	@Test
	public void LoginPageUIElementsTest() {
		generic.openUrl(urlProvider.getV2Url());
		//Is Logo visible
		softAssert.assertTrue(generic.isVisible(login.logo),"Logo not visible");
		//Is login form Label visible
		softAssert.assertTrue(generic.isVisible(login.loginFormLabel),"login form Label not visible");
		//Is login form Label correct
		softAssert.assertEquals(generic.getText(login.loginFormLabel),"Login Form","login form Label not correct");
		//Is Username Label visible
		softAssert.assertTrue(generic.isVisible(login.usernameLabel),"Username Label not visible");
		//Is Username Label correct
		softAssert.assertEquals(generic.getText(login.usernameLabel),"Username","Username Label not correct");
		//Is Username icon visible
		softAssert.assertTrue(generic.isVisible(login.usernameIcon),"Username icon not visible");
		//Is Username Textbox visible
		softAssert.assertTrue(generic.isVisible(login.usernameTextBox),"Username field not visible");
		//Is Username Placeholder correct
		softAssert.assertEquals(generic.getAttribute(login.usernameTextBox,"placeholder"),"Enter your username","Username placeholder not correct");
		//Is Password Label visible
		softAssert.assertTrue(generic.isVisible(login.passwordLabel),"Password Label not visible");
		//Is Password Label correct
		softAssert.assertEquals(generic.getText(login.passwordLabel),"Password","Password Label not correct");
		//Is Password icon visible
		softAssert.assertTrue(generic.isVisible(login.passwordIcon),"Password icon not visible");
		//Is Password Textbox visible
		softAssert.assertTrue(generic.isVisible(login.passwordTextBox),"Password field not visible");
		//Is Password Placeholder correct
		softAssert.assertEquals(generic.getAttribute(login.passwordTextBox,"placeholder"),"Enter your password","Password placeholder not correct");
		//Is Login button visible
		softAssert.assertTrue(generic.isVisible(login.loginBtn),"Login button not visible");
		//Is Login button text correct
		softAssert.assertEquals(generic.getText(login.loginBtn),"Log In","Login button text not correct");
		//Is Remember Me checkbox visible
		softAssert.assertTrue(generic.isVisible(login.rememberMeCheckbox),"Remember Me checkbox not visible");
		//Is Remember Me Label visible
		softAssert.assertTrue(generic.isVisible(login.rememberMeLabel),"Remember Me Label not visible");
		//Is Twitter logo visible
		softAssert.assertTrue(generic.isVisible(login.twitterLogo),"Twitter logo not visible");
		//Is Facebook logo visible
		softAssert.assertTrue(generic.isVisible(login.facebookLogo),"Facebook logo not visible");
		//Is Linkedin logo visible
		softAssert.assertTrue(generic.isVisible(login.linkedinLogo),"Linkedin logo not visible");
		//Error text should not be visible by default
		softAssert.assertTrue(!generic.isVisible(login.errorLabel),"Error text is visible on page load");
		softAssert.assertAll();
	}

	@DataProvider(name = "DataDrivenTest") 
	  public Object[][] testDataforDataDrivenTest()
	  { 
		login = new Login(driver);
		homePage = new HomePage(driver);
		return new Object[][]{
			{"","","Both Username and Password must be present",login.errorLabelXPath,"Test for Both username & password empty"},
			{"testuser","","Password must be present",login.errorLabelXPath,"Test for Password field empty"},
			{"","testpassword","Username must be present",login.errorLabelXPath,"Test for Username field empty"},
			{"testuser","testpassword","Jack Gomez",homePage.usernameLabelXPath,"Test for Valid username & password"}
	  } ;
	  }

	@Test(dataProvider = "DataDrivenTest") 
	public void DataDrivenTest(String username,String password, String expectedString, String locator, String testName) {
		generic.openUrl(urlProvider.getV2Url());

		generic.fill(login.usernameTextBox, username);
		generic.fill(login.passwordTextBox, password);
		generic.click(login.loginBtn);
		WebElement elementToCheck = driver.findElement(By.xpath(locator));
		softAssert.assertTrue(generic.isVisible(elementToCheck),testName+" Failed. Element not visible");
		softAssert.assertEquals(generic.getText(elementToCheck),expectedString,testName+" Failed. Text does not match");
		softAssert.assertAll();
	}

	@Test
	public void TableSortTest() {
		generic.openUrl(urlProvider.getV2Url());
		generic.fill(login.usernameTextBox, "testUser");
		generic.fill(login.passwordTextBox, "testPassword");
		generic.click(login.loginBtn);
			
		//Store table in a linkedHashMap with Time column as the key 
		//so that row remains unique in case multiple rows are of same amount/category/Description
		LinkedHashMap<String,LinkedHashMap<Integer,String>> tableBeforeRefactor = generic.getTableRows(homePage.transactionsTable,1);
		
		//Store amount in a list
		List<WebElement> elementsList = homePage.transactionsTable.findElements(By.xpath("./tr/td[5]"));
		List<Float> originalList = new ArrayList<Float>();
		for(WebElement element: elementsList) {
			originalList.add(Float.parseFloat(element.getText().trim().split(" USD")[0].replace(" ", "").replace(",", "")));
		}
		
		System.out.println("originalList"+originalList);

		//Click Amount Column header
		generic.click(homePage.amountHeader);
	
		//Check If each row stays intact
		LinkedHashMap<String,LinkedHashMap<Integer,String>> tableAfterRefactor = generic.getTableRows(homePage.transactionsTable,1);
		Iterator<Entry<String, LinkedHashMap<Integer, String>>> iterate = tableBeforeRefactor.entrySet().iterator();
		while(iterate.hasNext()) {
			LinkedHashMap<Integer, String> row = iterate.next().getValue();
			System.out.println("Checking for row: "+row);
			softAssert.assertTrue(tableAfterRefactor.containsValue(row),"Row data is not same in sorted table for row "+row);
		}
		
		//Store sorted amount in a list
		List<WebElement> sortedElementsList = homePage.transactionsTable.findElements(By.xpath("./tr/td[5]"));
		List<Float> sortedList = new ArrayList<Float>();
		for(WebElement element: sortedElementsList) {
			sortedList.add(Float.parseFloat(element.getText().trim().split(" USD")[0].replace(" ", "").replace(",", "")));
		}
		
		//Sort original list & compare with actual list
		Collections.sort(originalList);
		softAssert.assertEquals(sortedList, originalList,"Sorting on Amount not working correctly");
	
		System.out.println("OriginalSortedList"+originalList);
		System.out.println("ActualSortedList"+sortedList);
		softAssert.assertAll();
	}
	
	@Test
	public void CanvasChartTest() {
		//Chart data & representation cannot be tested using Selenium as chart elements not visible in DOM
	}

	@Test
	public void DynamicContentTest() {
		generic.openUrl(urlProvider.getV2Url()+"?showAd=true");
		generic.fill(login.usernameTextBox, "testUser");
		generic.fill(login.passwordTextBox, "testPassword");
		generic.click(login.loginBtn);
		
		softAssert.assertTrue(generic.isVisible(homePage.flashSaleImage1),"Failed. First flash sale image not visible");
		softAssert.assertTrue(generic.isVisible(homePage.flashSaleImage2),"Failed. Second flash sale image not visible");
		softAssert.assertAll();
	}
}
