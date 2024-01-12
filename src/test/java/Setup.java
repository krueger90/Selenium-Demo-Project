import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class Setup {

    private String driverPath = "chromedriver.exe";
    protected WebDriver driver = new ChromeDriver();

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openBrowser() {
        driver.get("https://ecommerce-playground.lambdatest.io");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
