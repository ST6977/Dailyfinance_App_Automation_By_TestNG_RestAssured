package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AdminLoginPage {

    @FindBy(id="email")
    public WebElement txtEmail;
    @FindBy(id="password")
    public WebElement txtPassword;

    @FindBy(css = "[type=submit]")
    public WebElement btnLogin;

    @FindBy(css = "[data-testid=AccountCircleIcon]")
    public static WebElement btnProfileIcon;
    @FindBy(css = "[role=menuitem]")
    public static List<WebElement> btnProfileMenuItems;






    public AdminLoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }



    public void doLogin(String email, String password){
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        btnLogin.click();

    }


    public static  void doLogout(){
        btnProfileIcon.click();
        btnProfileMenuItems.get(1).click();
    }


}
