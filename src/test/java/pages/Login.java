package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.GenericFunctions;

public class Login {
	
	WebDriver driver;
	GenericFunctions generic;

	@FindBy(xpath = "//img[contains(@src,'img/logo-big.png')]")
	public WebElement logo;
	
	@FindBy(xpath = "//h4[@class='auth-header']")
	public WebElement loginFormLabel;
	
	@FindBy(xpath = "//div[contains(@id,'random_id_')]")
	public WebElement errorLabel;
	public String errorLabelXPath="//div[contains(@id,'random_id_')]";
	
	@FindBy(xpath = "//input[@id='username']/preceding-sibling::label[1]")
	public WebElement usernameLabel;
	
	@FindBy(xpath = "//div[@class='pre-icon os-icon os-icon-user-male-circle']")
	public WebElement usernameIcon;
	
	@FindBy(id = "username")
	public WebElement usernameTextBox;
	
	@FindBy(xpath = "//input[@id='password']/preceding-sibling::label[1]")
	public WebElement passwordLabel;
	
	@FindBy(xpath = "//div[@class='pre-icon os-icon os-icon-fingerprint']")
	public WebElement passwordIcon;
	
	@FindBy(id = "password")
	public WebElement passwordTextBox;
	
	@FindBy(id = "log-in")
	public WebElement loginBtn;
	
	@FindBy(xpath = "//input[@class='form-check-input']")
	public WebElement rememberMeCheckbox;
	
	@FindBy(xpath = "//label[@class='form-check-label']")
	public WebElement rememberMeLabel;
	
	@FindBy(xpath = "//img[contains(@src,'twitter')]")
	public WebElement twitterLogo;
	
	@FindBy(xpath = "//img[contains(@src,'facebook')]")
	public WebElement facebookLogo;
	
	@FindBy(xpath = "//img[contains(@src,'linkedin')]")
	public WebElement linkedinLogo;
	
	public Login(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginUser(String username, String Password) {
		generic = new GenericFunctions(driver);
		generic.fill(usernameTextBox, username);
		generic.fill(passwordTextBox, Password);
		generic.click(loginBtn);
	}
}
