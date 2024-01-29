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
        System.out.println(System.getenv("GITHUB_RUN_ID"));
        // if (System.getenv("GITHUB_RUN_ID") != null) {
        //     options.addArguments("--headless");
        //     options.addArguments("--no-sandbox");
        //     options.addArguments("--disable-dev-shm-usage");
        //     options.addArguments("--disable-gpu");
        //     driver = new ChromeDriver(options);
        // } else {
        //     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        //     driver.manage().window().maximize();
        // }
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
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
