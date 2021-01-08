package dev.elliman.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver driver;
	
	@FindBy(id="username")
	WebElement usernameBox;
	@FindBy(id="password")
	WebElement passwordBox;
	@FindBy(id="loginBtn")
	WebElement loginButton;
	
	
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterUserAndPass(String username, String password) {
		usernameBox.sendKeys(username);
		passwordBox.sendKeys(password);
	}
	
	public void login() {
		loginButton.click();
	}
}
