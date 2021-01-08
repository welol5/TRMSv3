package dev.elliman.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
	private WebDriver driver;
	
	@FindBy(id="logout")
	private WebElement logoutButton;
	@FindBy(id="home")
	private WebElement homeButton;
	@FindBy(id="mainContent")
	private WebElement mainContentDiv;
	
	private WebElement makeAClaimButton;
	
	private WebElement newClaimEventNameInput;
	private Select newClaimEventTypeInput;
	private WebElement newClaimEventDateInput;
	private WebElement newClaimEventTimeInput;
	private WebElement newClaimEventlocationInput;
	private WebElement newClaimPassingPercentageInput;
	private Select newClaimPassingLetterInput;
	private WebElement newClaimHasPresentationInput;
	private WebElement newClaimDescriptionInput;
	private WebElement newClaimJustificationInput;
	private WebElement newClaimHoursMissedInput;
	private WebElement newClaimCostInput;
	private WebElement newClaimSubmitInput;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
		makeAClaimButton = null;
		
		newClaimEventNameInput = null;
		newClaimEventTypeInput = null;
		newClaimEventDateInput = null;
		newClaimEventTimeInput = null;
		newClaimEventlocationInput = null;
		newClaimPassingPercentageInput = null;
		newClaimPassingLetterInput = null;
		newClaimHasPresentationInput = null;
		newClaimDescriptionInput = null;
		newClaimJustificationInput = null;
		newClaimHoursMissedInput = null;
		newClaimCostInput = null;
		newClaimSubmitInput = null;
	}
	
	public WebElement getHomeButton() {
		return homeButton;
	}
	
	public WebElement getLogoutButton() {
		return logoutButton;
	}
	
	public void makeAClaim() {
		makeAClaimButton = driver.findElement(By.id("makeAClaim"));
		makeAClaimButton.click();
	}
	
	public void fillNewClaimForm(String eventName, String eventOptions, String eventDate, String eventTime, String eventLocation, String passingPerventage, String passingLetter, String hasPresentation, String description, String justification, String hoursMissed, String cost) {
		newClaimEventNameInput = driver.findElement(By.id("eventName"));
		newClaimEventTypeInput = new Select(driver.findElement(By.id("eventOptions")));
		newClaimEventDateInput = driver.findElement(By.id("eventDate"));
		newClaimEventTimeInput = driver.findElement(By.id("eventTime"));
		newClaimEventlocationInput = driver.findElement(By.id("eventLocation"));
		newClaimPassingPercentageInput = driver.findElement(By.id("passingPercentage"));
		newClaimPassingLetterInput = new Select(driver.findElement(By.id("passingLetter")));
		newClaimHasPresentationInput = driver.findElement(By.id("hasPresentation"));
		newClaimDescriptionInput = driver.findElement(By.id("description"));
		newClaimJustificationInput = driver.findElement(By.id("justification"));
		newClaimHoursMissedInput = driver.findElement(By.id("hoursMissed"));
		newClaimCostInput = driver.findElement(By.id("price"));
		newClaimSubmitInput = driver.findElement(By.id("submitButton"));
		
		newClaimEventNameInput.sendKeys(eventName);
		
		newClaimEventTypeInput.selectByIndex(Integer.valueOf(eventOptions));
		
		newClaimEventDateInput.click();
		newClaimEventDateInput.sendKeys(Keys.LEFT);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newClaimEventDateInput.sendKeys(eventDate);
		newClaimEventTimeInput.click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newClaimEventTimeInput.sendKeys(eventTime);
		newClaimEventlocationInput.sendKeys(eventLocation);
		
		newClaimPassingPercentageInput.clear();
		newClaimPassingPercentageInput.sendKeys(passingPerventage);
		
		newClaimPassingLetterInput.selectByIndex(Integer.valueOf(passingLetter));
		if(hasPresentation.equals("Y")) {
			newClaimHasPresentationInput.click();
		}
		newClaimDescriptionInput.sendKeys(description);
		newClaimJustificationInput.sendKeys(justification);
		newClaimHoursMissedInput.sendKeys(hoursMissed);
		newClaimCostInput.sendKeys(cost);
	}
	
	public void submitNewClaim() {
		newClaimSubmitInput.click();
	}
}
