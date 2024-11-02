package Runner;

import Config.AddCostDataSet;
import Config.Setup;
import Pages.AddCostPage;
import Pages.UserLogin;
import Utils.utils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserLoginRunner extends Setup {
    UserLogin userLogin;


   @Test(priority = 1, description = "User can Login With New Password")
    public void userLogin() throws IOException, ParseException, InterruptedException {

        userLogin = new UserLogin(driver);
        String email = utils.getUpdatedEmailPassProperty("email");

       // String password = "SH77";
        String password= "6666";
        userLogin.doLogin(email, password);
        String expectedMsg = "User Daily Costs";


         Thread.sleep(7000);

    }

   @Test(priority = 2,dataProvider = "AddCostData", dataProviderClass = AddCostDataSet.class, description="User Can Add Item" )
    public void addCost(String itemName, String amount, String quantity, String purchaseDate, String
            month, String remarks) throws InterruptedException {


        System.out.println("Item Name: " + itemName);
        System.out.println("Amount: " + amount);
        System.out.println("Quantity: " + quantity);
        System.out.println("Purchase Date: " + purchaseDate);
        System.out.println("Month: " + month);
        System.out.println("Remarks: " + remarks);


        AddCostPage addCostPage = new AddCostPage(driver);
        addCostPage.addExpenditure(itemName, amount, quantity, purchaseDate, month, remarks);


        Thread.sleep(5000);
        driver.switchTo().alert().accept();


        ItemAssert();


    }


    public void ItemAssert() throws InterruptedException {
        // Get the text from the rows
        String firstItem = driver.findElements(By.tagName("tr")).get(1).getText();
        String secondItem = driver.findElements(By.tagName("tr")).get(2).getText();
        System.out.println("First Item: " + firstItem);
        System.out.println("Second Item: " + secondItem );


        String expectedFirstItem = "Pen";
        String expectedSecondItem = "Apple";


        String actualFirstItem = firstItem.split(" ")[0];
        String actualSecondItem = secondItem.split(" ")[0];

        Thread.sleep(2000);

        Assert.assertEquals(actualFirstItem, expectedFirstItem, "First item is not 'pen'");
        Assert.assertEquals(actualSecondItem, expectedSecondItem, "Second item is not 'apple'");



    }


    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }


}
