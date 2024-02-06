import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Setup {

    protected WebDriver driver = new ChromeDriver();

    Wait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(5))
            .pollingEvery(Duration.ofMillis(500));

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();

            options.setBinary("/opt/hostedtoolcache/chromium/latest/x64");
            options.addArguments("--no-sandbox");
            options.addArguments("--headless=new");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.get("https://ecommerce-playground.lambdatest.io");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
    }

    @AfterMethod
    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
