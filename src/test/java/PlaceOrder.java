import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.google.gson.JsonObject;

import net.datafaker.Faker;

public class PlaceOrder {

    JsonObject details;
    Faker faker = new Faker();

    By email = By.id("input-email");
    By password = By.id("input-password");
    By loginButton = By.xpath("//*[@id=\"content\"]/div/div[2]/div/div/form/input");
    By cart = By.xpath("//*[@id=\"entry_217825\"]/a/div[1]/span");
    By editCartButton = By.xpath("//*[@id=\"entry_217850\"]/a");
    By clearCartItemButton = By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/div/button[2]");
    By searchbox = By.cssSelector("[name=\"search\"]");
    By searchButton = By.cssSelector(".type-text");
    By searchResults = By.xpath("//*[@id=\"entry_212469\"]/div");
    By agreePolicy = By.xpath("//*[@id=\"form-checkout\"]/div/div[2]/div/div[5]/label");
    By existingPaymentDetails = By.id("payment-existing");
    By newAddress = By.xpath("//*[@id=\"payment-address\"]/div[2]/div/label");
    By paymentFirstName = By.id("input-payment-firstname");
    By paymentLastName = By.id("input-payment-lastname");
    By paymentAddress1 = By.id("input-payment-address-1");
    By city = By.id("input-payment-city");
    By postcode = By.id("input-payment-postcode");
    By country = By.id("input-payment-country");
    By zone = By.id("input-payment-zone");

    /*
     * Using an existing account that was created manually
     */
    public void login(WebDriver driver) {
        driver.findElement(email).sendKeys("sofake@test2.com");
        driver.findElement(password).sendKeys("Sofakepass123");
        driver.findElement(loginButton).click();
    }

    /**
     * This is used in case of test failure,
     * if a test fails, it will leave the product in cart,
     * causing an assert on the number of items to fail
     */
    public void clearCart(WebDriver driver) {
        String items = driver.findElement(cart).getText();

        if (!items.equals("0")) {
            driver.findElement(cart).click();
            driver.findElement(editCartButton).click();
            driver.findElement(clearCartItemButton).click();
        }
    }

    public void searchItem(String item, WebDriver driver) {
        driver.findElement(searchbox).sendKeys(item);
        driver.findElement(searchButton).click();
    }

    public void clickElement(By element, WebDriver driver) {
        driver.findElement(element).click();
    }

    public void saveProductDetails(WebDriver driver) {
        String productName = driver.findElement(By.xpath("//*[@id=\"entry_216816\"]")).getText();
        String productCode = driver.findElement(By.xpath("//*[@id=\"entry_216820\"]/ul/li/span[2]")).getText();
        String productPrice = driver.findElement(By.xpath("//*[@id=\"entry_216831\"]/div/div/h3")).getText();
        String productQuantity = driver.findElement(By.xpath("//*[@id=\"entry_216841\"]/div/input"))
                .getAttribute("value");
        JsonObject productDetails = new JsonObject();
        productDetails.addProperty("productName", productName);
        productDetails.addProperty("productCode", productCode);
        productDetails.addProperty("productPrice", productPrice);
        productDetails.addProperty("productQuantity", productQuantity);
        details = productDetails;
    }

    public void fillBillingDetails(String addressType, WebDriver driver) {
        if (addressType == "existing") {
            driver.findElement(existingPaymentDetails).isDisplayed();
            driver.findElement(agreePolicy).click();
        } else {
            driver.findElement(newAddress).click();
            driver.findElement(paymentFirstName).sendKeys(faker.name().firstName());
            driver.findElement(paymentLastName).sendKeys(faker.name().lastName());
            driver.findElement(paymentAddress1).sendKeys(faker.address().streetAddress());
            driver.findElement(city).sendKeys(faker.address().city());
            driver.findElement(postcode).sendKeys(faker.address().zipCode());
            Select dropdownCountry = new Select(driver.findElement(country));
            dropdownCountry.selectByVisibleText("United Kingdom");
            Select zoneDropdown = new Select(driver.findElement(zone));
            zoneDropdown.selectByVisibleText("Worcestershire");
            driver.findElement(agreePolicy).click();
        }
    }

    public void getFirstProduct(WebDriver driver) {
        WebElement numberOfResults = driver.findElement(searchResults);
        List<WebElement> allElements = numberOfResults.findElements(By.xpath("*"));

        if (allElements.size() >= 1) {
            allElements.get(0).click();
        }
    }

    public void compareProductDetails(WebDriver driver) {

        String actualProdName = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[1]"))
                .getText();
        String actualProdCode = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[2]"))
                .getText();
        String actualProdPrice = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[4]"))
                .getText();
        String actualProdQuantity = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[3]"))
                .getText();

        Assert.assertEquals(actualProdName, details.get("productName").getAsString());
        Assert.assertEquals(actualProdCode, details.get("productCode").getAsString());
        Assert.assertEquals(actualProdPrice, details.get("productPrice").getAsString());
        Assert.assertEquals(actualProdQuantity, details.get("productQuantity").getAsString());
    }
}
