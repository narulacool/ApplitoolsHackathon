package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;

	@FindBy(id = "logged-user-name")
	public WebElement usernameLabel;
	public String usernameLabelXPath="//div[contains(@id,'logged-user-name')]";
	
	@FindBy(xpath = "//img[@src='img/flashSale.gif']")
	public WebElement flashSaleImage1;
	
	@FindBy(xpath = "//img[@src='img/flashSale2.gif']")
	public WebElement flashSaleImage2;
	
	@FindBy(id = "amount")
	public WebElement amountHeader;
	
	@FindBy(xpath = "//table[@id='transactionsTable']/tbody")
	public WebElement transactionsTable;

	@FindBy(id = "showExpensesChart")
	public WebElement compareExpenses;

	@FindBy(id = "addDataset")
	public WebElement showDataForNextYear;
	
	public HomePage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
}
