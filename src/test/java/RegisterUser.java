import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterUser {
    By firstName = By.id("input-firstname");
    By lastName = By.id("input-lastname");
    By email = By.id("input-email");
    By telephone = By.id("input-telephone");
    By password = By.id("input-password");
    By confirmPassword = By.id("input-confirm");
    By agreePolicy = By.xpath("//*[@id=\"content\"]/form/div/div/div");
    By continueButton = By.xpath("//*[@id=\"content\"]/form/div/div/input");

    public void fillRegistrationDetails(WebDriver driver, String firstname, String lastname, String emailAddress,
            String phone, String pass, String confirmPass) {

        driver.findElement(this.firstName).sendKeys(firstname);
        driver.findElement(this.lastName).sendKeys(lastname);
        driver.findElement(this.email).sendKeys(emailAddress);
        driver.findElement(this.telephone).sendKeys(phone);
        driver.findElement(this.password).sendKeys(pass);
        driver.findElement(this.confirmPassword).sendKeys(confirmPass);
        driver.findElement(this.agreePolicy).click();
        driver.findElement(this.continueButton).click();
    }
}
