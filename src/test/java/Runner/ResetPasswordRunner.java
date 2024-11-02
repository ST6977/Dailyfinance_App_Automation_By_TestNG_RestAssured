package Runner;

import Config.Setup;
import Pages.ResetPasswordPage;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ResetPasswordRunner extends Setup {


    public String newPass;
    public String userEmail;
    ResetPasswordPage validEmail;



    @BeforeClass
    public void init() {
        // Initialize the ResetPasswordPage after driver is set up
        validEmail = new ResetPasswordPage(driver);
    }




  @Test(priority = 1, description = " Give valid gmail account in Forgot Password Field")
    public void RegisteredGmail() throws IOException, ParseException {

        validEmail = new ResetPasswordPage(driver);
       validEmail.linkResetPassword.click();

        //validEmail.linkResetPassword.get(0).click();
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size() - 1);
        userEmail = (String) jsonObject.get("email");
        System.out.println(userEmail);
        newPass = validEmail.getValidEmail(userEmail);

    }


    //Negative Case

    @Test(priority = 2, description = "Reset New Password With Unregistered Email")
    public void resetPassUnregisteredEmail() {
        driver.get("https://dailyfinance.roadtocareer.net/login");
        validEmail.linkResetPassword.click();
        validEmail.UnregisteredEmail("sumaiyat610+901@gmail.com");


    }






//Negative Case
@Test
    public void testForgotPasswordLinkNotVisibleWhenLoggedIn() throws InterruptedException {
      validEmail =new ResetPasswordPage(driver);
        boolean forgotPasswordVisibility=  validEmail.testForgotPasswordLinkNotVisibleWhenLoggedIn();
        Assert.assertTrue(forgotPasswordVisibility);
        Assert.assertTrue(validEmail.linkResetPassword.isEnabled());
       Allure.description("Checking whether forgot password option is available to the users or not");
    }



    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

}

