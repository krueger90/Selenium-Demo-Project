import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.datafaker.Faker;

public class PlaceOrderTest extends Setup {

    Faker faker = new Faker();
    Gson gson = new Gson();
    JsonObject details;

    By email = By.id("input-email");
    By password = By.id("input-password");
    By loginButton = By.xpath("//*[@id=\"content\"]/div/div[2]/div/div/form/input");
    By cart = By.xpath("//*[@id=\"entry_217825\"]/a/div[1]/span");
    By editCartButton = By.xpath("//*[@id=\"entry_217850\"]/a");
    By clearCartItemButton = By.xpath("//*[@id=\"content\"]/form/div/table/tbody/tr/td[4]/div/div/button[2]");
    By searchbox = By.cssSelector("[name=\"search\"]");
    By searchButton = By.cssSelector(".type-text");
    By searchResults = By.xpath("//*[@id=\"entry_212469\"]/div");
    By buyButton = By.cssSelector("[title=\"Buy now\"]");
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
    By continueButton = By.xpath("//*[@id=\"button-save\"]");
    By confirmOrderButton = By.id("button-confirm");

    @Test(dataProvider = "addressType")
    public void placeOrder(String addressType, String value) {
        driver.get(driver.getCurrentUrl() + "/index.php?route=account/login");
        login();
        Assert.assertEquals(driver.getTitle(), "My Account");
        clearCart();
        try {
            searchItem("HTC");

        } catch (StaleElementReferenceException e) {
            searchItem("HTC");
        }
        getFirstProduct();
        saveProductDetails();
        driver.findElement(buyButton).click();
        wait.until(ExpectedConditions.urlContains("route=checkout/checkout"));
        Assert.assertEquals(driver.getTitle(), "Checkout");
        fillBillingDetails(value);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        driver.findElement(continueButton).click();
        wait.until(ExpectedConditions.titleIs("Confirm Order"));
        Assert.assertEquals(driver.getTitle(), "Confirm Order");
        compareProductDetails();
        driver.findElement(confirmOrderButton).click();
        wait.until(ExpectedConditions.titleIs("Your order has been placed!"));
        Assert.assertEquals(driver.getTitle(), "Your order has been placed!");
    }

    /*
     * Using an existing account that was created manually
     */
    private void login() {
        driver.findElement(email).sendKeys("sofake@test2.com");
        driver.findElement(password).sendKeys("Sofakepass123");
        driver.findElement(loginButton).click();
    }

    /**
     * This is used in case of test failure,
     * if a test fails, it will leave the product in cart,
     * causing an assert on the number of items to fail
     */
    private void clearCart() {
        String items = driver.findElement(cart).getText();

        if (!items.equals("0")) {
            driver.findElement(cart).click();
            driver.findElement(editCartButton).click();
            driver.findElement(clearCartItemButton).click();
        }
    }

    private void searchItem(String item) {
        driver.findElement(searchbox).sendKeys(item);
        driver.findElement(searchButton).click();
    }

    private void getFirstProduct() {
        WebElement numberOfResults = driver.findElement(searchResults);
        List<WebElement> allElements = numberOfResults.findElements(By.xpath("*"));

        if (allElements.size() >= 1) {
            allElements.get(0).click();
        }
    }

    private void saveProductDetails() {
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
        this.details = productDetails;
    }

    private void fillBillingDetails(String addressType) {
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

    @DataProvider(name = "addressType")
    private Object[][] addressType() {
        return new Object[][] {
                { "addressType", "existing" },
                { "addressType", "new" }
        };
    }

    private void compareProductDetails() {

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
