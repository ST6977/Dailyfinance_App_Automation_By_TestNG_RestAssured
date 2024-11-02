package Runner;

import Config.Setup;
import Config.UserModel;
import Pages.RegistrationPage;
import Pages.UserLogin;
import Pages.UserProfileUpdated;
import Utils.utils;
import com.github.javafaker.Faker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class UserProfileUpdatedRunner extends Setup {


    UserLogin userLogin;
    UserProfileUpdated userProfileUpdated;


    @BeforeTest
    public void UserCreate() throws IOException, ParseException, InterruptedException {
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

        JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("lastName",lastname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phonenumber);
        userObj.put("address",address);

        utils.SaveAllInfo("./src/test/resources/users.json", userObj);

    }




    @Test(priority = 1, description = "User Login for update email")
    public void userLogin() throws IOException, ParseException, InterruptedException {
    Thread.sleep(7000);
        userLogin = new  UserLogin(driver);
        userLogin.btnsubmit.click();


        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email= (String) userObj.get("email");
      String password= (String) userObj.get("password");
      //String password = "SH77";
    Thread.sleep(2000);
        userLogin.doLogin(email,password);
        String expectedMsg = "User Daily Costs";
        String actualMsg = userLogin.dashboardMsg.getText();
        Assert.assertTrue(actualMsg.contains(expectedMsg));
    }


@Test(priority = 2, description = "Update Gmail")
    public void updateUserEmail() throws InterruptedException, IOException, ParseException {

        userProfileUpdated = new UserProfileUpdated(driver);
        userProfileUpdated.btnProfileIcon.click();
        userProfileUpdated.btnProfileMenuItems.get(0).click();
        userProfileUpdated.updateEmail();
        userLogin.doLogout();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.elementToBeClickable( userLogin.btnsubmit));




    }



    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }





}
