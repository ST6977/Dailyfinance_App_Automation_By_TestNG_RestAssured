package Runner;

import Config.Setup;
import Config.UserModel;
import Pages.RegistrationPage;
import Utils.utils;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RegistrationTestRunner extends Setup {

    public   Properties props;
    FileInputStream fs;

@Test(description = "User Can Register by providing all info")
    public void UserRegByAllFields() throws IOException, ParseException, InterruptedException {
        RegistrationPage userReg = new RegistrationPage(driver);
        Faker faker  = new Faker();
    userReg.btnRegister.get(1).click();
        String firstname=faker.name().firstName();
        String lastname=faker.name().lastName();
        String email = "sumaiyat610+" + (int)(Math.random() * 1000) + "@gmail.com";
        String password = "6666";
        String phonenumber = "01756"+ utils.generateRandomNumber(100000,999999);
        String address=faker.address().fullAddress();

        UserModel userModel = new UserModel();
        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phonenumber);
        userModel.setAddress(address);
        userReg.doRegistration(userModel);
    Thread.sleep(4000);
       doAssertion();
     assertRegistrationEmail(firstname);
    JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("lastName",lastname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phonenumber);
        userObj.put("address",address);

        utils.SaveAllInfo("./src/test/resources/users.json", userObj);

    }


    public void doAssertion() throws InterruptedException {

        Thread.sleep(2000);

        String successMessageActual = driver.findElement(By.className("Toastify__toast")).getText();
        String successMessageExpected = "successfully";
        System.out.println(successMessageActual);
        Assert.assertTrue(successMessageActual.contains(successMessageExpected));


    }

    public  void assertRegistrationEmail(String firstname) throws IOException  {

        String confirmationEmailActual = utils.readLatestMail();
        String confirmationEmailExpected = "Dear "+ firstname  + ", Welcome to our platform!";
        System.out.println(confirmationEmailActual);
        Assert.assertTrue( confirmationEmailActual.contains(confirmationEmailExpected) );

    }


    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}

