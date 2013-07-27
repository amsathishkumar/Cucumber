

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;


import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.awt.event.KeyEvent;




public class Cucumber_sel {
	public enum Browsers		{ FF, IE,CH };
	public WebDriver driver;
	@Given("^EC is open in (.*)$")
	public void openbrowser(Browsers browser) throws Exception
	{
		String baseUrl = "http://10.78.203.118";
		switch ( browser ) {
		case FF:				
		      { 
		  FirefoxProfile profile = new ProfilesIni().getProfile("sat");
		  profile.setPreference("network.http.phishy-userpass-length", 255);
		  File pluginAutoAuth = new File("src/autoauth-2.1-fx+fn.xpi");
		  profile.addExtension(pluginAutoAuth);	  
		  driver = new FirefoxDriver(profile);	  
		  break; 
		     }

		case IE:
		      { 
		    	  driver = new InternetExplorerDriver(); 	
		    	  break;
		      }
		case CH:				
		      { 
		    	  DesiredCapabilities  capabilities = DesiredCapabilities.chrome();
		          capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
		          HashMap<String, String> chromePreferences = new HashMap<String, String>();
		          chromePreferences.put("profile.password_manager_enabled", "true");
		          capabilities.setCapability("chrome.prefs", chromePreferences);
		    	  	    			  
		    	  driver = new ChromeDriver();			
		    	  break; 
		      }
		
		default:				{ throw new Exception();	}
		
		}
		  
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  driver.get(baseUrl+"/dncs/console/home.do");			
		
			
			
		   String actualTitle = driver.getTitle();
		   String expectedTitle="Process Status (10.78.203.118:80)";
			
			Assert.assertEquals(expectedTitle,actualTitle);
			
	}
	
	@When("^I Navigate to Source Page and enter valid values$")
	public void Navigate() throws IOException
	{
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(By.xpath("//*[text()='EC']"))).click().build().perform();
			driver.findElement(By.xpath("//*[text()='Source']")).click();
			driver.findElement(By.name("actionRequested")).click();
			driver.findElement(By.name("currentEntry.sourceName")).sendKeys("sample");
			driver.findElement(By.name("currentEntry.sourceId")).sendKeys("4141");
	}
	
	@And("^I click on Save button$")
	public void click_save() throws IOException
	{
			driver.findElement(By.name("actionRequested")).click();
	}
	
	@Then("^I See success message$")
	public void valid_save() throws IOException
	{
		String actualsavemsg = driver.findElement(By.className("xwtBody")).getText();
		Assert.assertEquals("Source created successfully.",actualsavemsg);
		driver.close();
	}
	
   @After
   public void source_delete() throws IOException, InterruptedException {
          driver.quit();
  }
  
}
