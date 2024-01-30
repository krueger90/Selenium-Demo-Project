import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class Setup {

    protected WebDriver driver = new ChromeDriver();
    ChromeOptions options = new ChromeOptions();

    Wait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(5))
            .pollingEvery(Duration.ofMillis(500));

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        System.out.println(System.getProperty("GITHUB_RUN_ID") + " AICI...");
        if (System.getProperty("ENV") == "PIPELINE") {
            System.out.println(System.getProperty("GITHUB_RUN_ID") + " AICI...");

            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.manage().window().maximize();
        }
    }

    @BeforeMethod
    public void openBrowser() {
        driver.get("https://ecommerce-playground.lambdatest.io");
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
