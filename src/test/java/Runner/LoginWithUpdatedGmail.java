package Runner;

import Config.Setup;
import Pages.UserLogin;
import Utils.utils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class LoginWithUpdatedGmail extends Setup {
    UserLogin userLogin;

   @Test(priority = 1, description = "User can login with Updated Email" )
    public void userLoginUpdatedEmail() throws ParseException, IOException {

        String email= utils.getUpdatedEmailPassProperty("email");
        String password= utils.getUpdatedEmailPassProperty("password");
        driver.get("https://dailyfinance.roadtocareer.net/login");
        userLogin= new UserLogin(driver);
        userLogin.doLogin(email,password);
        String expectedMsg = "User Daily Costs";

        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOf(  userLogin.dashboardMsg));

        String actualMsg =  userLogin.dashboardMsg.getText();
        Assert.assertTrue(actualMsg.contains(expectedMsg));
        userLogin.doLogout();


    }


     @Test(priority = 2, description = "User can not login with Older email" )
    public void userLoginFail() throws ParseException, IOException, InterruptedException {
        this.userLogin = new UserLogin(driver);
        Thread.sleep(5000);
        String email = "sumaiyat610+271@gmail.com " ;//old email
        String password= utils.getUpdatedEmailPassProperty("password");

        userLogin.doLogin(email ,password);

        Thread.sleep(2000);
        String errorMessageActual= driver.findElement(By.tagName("p")).getText();
        String errorMessageExpected="Invalid";
        Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));


    }


    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

}
