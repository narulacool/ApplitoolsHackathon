package utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericFunctions {

	WebDriver driver;
	WebDriverWait wait;
	ChromeDriverService service ;

	public WebDriver startDriver(String browser) {
		if(browser.equalsIgnoreCase("Chrome"))
		{	

			System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromeDriver/chromedriver.exe").getPath());
			driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 10);
		}
		else {
			System.out.println("Unsupported Browser!");
		}
		return driver;
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public Boolean isVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void click(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element)).click();
	}

	public void clear(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element)).clear();
	}

	public void fill(WebElement element, String text) {
		wait.until(ExpectedConditions.visibilityOf(element)).clear();
		element.sendKeys(text);
	}

	public String getText(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element)).getText();
	}

	public String getAttribute(WebElement element, String attributeName) {
		return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute(attributeName);
	}

	public LinkedHashMap<String,LinkedHashMap<Integer,String>> getTableRows(WebElement table,int keyColumnNumber){
		try{

			LinkedHashMap<Integer,String> rowData;
			LinkedHashMap<String,LinkedHashMap<Integer,String>> tableMap = new LinkedHashMap<String,LinkedHashMap<Integer,String>>();


			List<WebElement> rowsList = table.findElements(By.tagName("tr"));
			List<WebElement> columnsList;
			int rowCount = rowsList.size();
			int columnsCount;
			for(int i=0;i<rowCount;i++){
				columnsList = rowsList.get(i).findElements(By.tagName("td"));
				columnsCount = columnsList.size();
				rowData = new LinkedHashMap<Integer,String>();

				for(int j=0;j<columnsCount;j++){
					rowData.put(j,columnsList.get(j).getText());
				}//End Inner For

				tableMap.put(rowData.get(keyColumnNumber), rowData);
			}//End Outer For

			return tableMap;
		}catch(Exception e){
			System.out.println("Unable to fetch data from Html table." +   
					" Stack Trace: " +e.getMessage());
			return null;
		}
	}


}
