package dev.elliman.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.WebElement;

import dev.elliman.pages.HomePage;
import dev.elliman.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TRMSCucumberTestingImpl {
	static {
		File file = new File("src/test/resources/geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
	}
	
	private static WebDriver driver = new FirefoxDriver();
	private static LoginPage login;
	private static HomePage home;
	
	private static String url = "http://localhost:8080";
	
	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
	    driver.get(url);
	    login = new LoginPage(driver);
	}

	@When("I enter my {string} and {string}")
	public void i_enter_my_and(String string, String string2) {
	    login.enterUserAndPass(string, string2);
	}

	@When("I click the login button")
	public void i_click_the_login_button() {
	    login.login();
	}

	@Then("the navbar should appear and I should be able to make new claims")
	public void the_navbar_should_appear_and_I_should_be_able_to_make_new_claims() {
	    
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("home")));

	    home = new HomePage(driver);
		assertEquals("Home", home.getHomeButton().getText());
		assertEquals("showClaims()", home.getHomeButton().getAttribute("onclick"));
		assertEquals("Logout", home.getLogoutButton().getText());
	}

	@Given("I am logged in as an employee")
	public void i_am_logged_in_as_an_employee() {
	    driver.get(url);
	    login = new LoginPage(driver);
	    login.enterUserAndPass("testE", "password");
	    login.login();
	    Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("home")));
		home = new HomePage(driver);
	}

	@When("I click make a claim")
	public void i_click_make_a_claim() {
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("makeAClaim")));
	    home.makeAClaim();
	}

	@When("I complete the form with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
	public void i_complete_the_form_with(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12) {
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newClaimForm")));
	    home.fillNewClaimForm(string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12);
	}

	@When("I click submit")
	public void i_click_submit() {
	    home.submitNewClaim();
	}

	@Then("The claim should exist")
	public void the_claim_should_exist() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


	@When("I select an urgent claim")
	public void i_select_an_urgent_claim() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I click accept")
	public void i_click_accept() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("the claim should no longer be there")
	public void the_claim_should_no_longer_be_there() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}


}
