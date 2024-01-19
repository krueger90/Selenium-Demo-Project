import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PlaceOrderTest extends Setup {

    By buyButton = By.cssSelector("[title=\"Buy now\"]");
    By continueButton = By.xpath("//*[@id=\"button-save\"]");
    By confirmOrderButton = By.id("button-confirm");

    PlaceOrder placeOrder = new PlaceOrder();

    @Test(dataProvider = "addressType")
    public void placeOrder(String addressType, String value) {
        driver.get(driver.getCurrentUrl() + "/index.php?route=account/login");
        placeOrder.login(driver);
        Assert.assertEquals(driver.getTitle(), "My Account");
        placeOrder.clearCart(driver);
        try {
            placeOrder.searchItem("HTC", driver);

        } catch (StaleElementReferenceException e) {
            placeOrder.searchItem("HTC", driver);
        }
        placeOrder.getFirstProduct(driver);
        placeOrder.saveProductDetails(driver);
        placeOrder.clickElement(buyButton, driver);
        wait.until(ExpectedConditions.urlContains("route=checkout/checkout"));
        Assert.assertEquals(driver.getTitle(), "Checkout");
        placeOrder.fillBillingDetails(value, driver);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        placeOrder.clickElement(continueButton, driver);
        wait.until(ExpectedConditions.titleIs("Confirm Order"));
        Assert.assertEquals(driver.getTitle(), "Confirm Order");
        placeOrder.compareProductDetails(driver);
        placeOrder.clickElement(confirmOrderButton, driver);
        wait.until(ExpectedConditions.titleIs("Your order has been placed!"));
        Assert.assertEquals(driver.getTitle(), "Your order has been placed!");
    }

    @DataProvider(name = "addressType")
    private Object[][] addressType() {
        return new Object[][] {
                { "addressType", "existing" },
                { "addressType", "new" }
        };
    }
}
