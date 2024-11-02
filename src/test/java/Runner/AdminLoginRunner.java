package Runner;

import Config.Setup;
import Pages.AdminLoginPage;
import Pages.SearchUpdatedGmail;
import Pages.UserLogin;
import Utils.utils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class AdminLoginRunner extends Setup {
    private AdminLoginPage adminLoginPage;
    private SearchUpdatedGmail searchUpdatedGmail;
   // private WebDriver driver;
   @Test(priority = 1, description = "Admin Login")
  public void adminLogin() throws IOException {

 AdminLoginPage adminLoginPage = new   AdminLoginPage(driver);
    if(System.getProperty("username")!=null && System.getProperty("password")!=null){
        adminLoginPage.doLogin(System.getProperty("username"),System.getProperty("password"));
    }
    else{
        adminLoginPage.doLogin("admin@test.com","admin123");
    }
    // Wait for the header element to be visible
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
    String headerActual = headerElement.getText();
    String headerExpected = "Admin Dashboard";

    Assert.assertTrue(headerActual.contains(headerExpected));





    }

    @Test(priority = 2, description = "Searching updated gmail in row")
    public void searchUpdatedGmail() throws IOException, ParseException {
        String email= utils.getUpdatedEmailPassProperty("email");
        searchUpdatedGmail = new SearchUpdatedGmail(driver);
        searchUpdatedGmail.EmailSearching(email);
        AdminLoginPage.doLogout();

    }




    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

}
