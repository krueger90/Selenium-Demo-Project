import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class Setup {

    private String driverPath = "F:/Selenium-Demo-Project/selenium-java-demo/chromedriver.exe";
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openBrowser(){
        driver.get("https://ecommerce-playground.lambdatest.io");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
