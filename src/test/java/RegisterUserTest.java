import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterUserTest extends Setup {
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String phone;
    private String pass;
    private String confirmPass;
    RegisterUser registerUser = new RegisterUser();

    public RegisterUserTest(String firstname, String lastname, String emailAddress, String phone, String pass) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.pass = pass;
        this.confirmPass = this.pass;
    }

    @Test
    public void registerUser() throws InterruptedException {
        driver.get(driver.getCurrentUrl() + "/index.php?route=account/register");
        registerUser.fillRegistrationDetails(driver, firstname, lastname, emailAddress, phone, pass, confirmPass);
        wait.until(ExpectedConditions.urlContains("route=account/success"));
        Assert.assertEquals(driver.getTitle(), "Your Account Has Been Created!");
    }
}