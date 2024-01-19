import org.openqa.selenium.By;
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

    By firstName = By.id("input-firstname");
    By lastName = By.id("input-lastname");
    By email = By.id("input-email");
    By telephone = By.id("input-telephone");
    By password = By.id("input-password");
    By confirmPassword = By.id("input-confirm");
    By agreePolicy = By.xpath("//*[@id=\"content\"]/form/div/div/div");
    By continueButton = By.xpath("//*[@id=\"content\"]/form/div/div/input");

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

        driver.findElement(this.firstName).sendKeys(firstname);
        driver.findElement(this.lastName).sendKeys(lastname);
        driver.findElement(this.email).sendKeys(emailAddress);
        driver.findElement(this.telephone).sendKeys(phone);
        driver.findElement(this.password).sendKeys(pass);
        driver.findElement(this.confirmPassword).sendKeys(confirmPass);
        driver.findElement(this.agreePolicy).click();
        driver.findElement(this.continueButton).click();

        wait.until(ExpectedConditions.urlContains("route=account/success"));
        Assert.assertEquals(driver.getTitle(), "Your Account Has Been Created!");
    }
}