import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {

    protected WebDriver driver = new FirefoxDriver();
    FirefoxOptions options = new FirefoxOptions();

    Wait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(5))
            .pollingEvery(Duration.ofMillis(500));

    @BeforeMethod
    public void setup() {
          	 WebDriverManager.firefoxdriver().setup();
            options.addArguments("--no-sandbox");
            options.addArguments("--headless=new");
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--disable-dev-shm-usage");
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();

            driver.get("https://ecommerce-playground.lambdatest.io");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        
    }

    // @BeforeMethod
    // public void openBrowser() {
    // driver.get("https://ecommerce-playground.lambdatest.io");
    // }

    @AfterMethod
    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
