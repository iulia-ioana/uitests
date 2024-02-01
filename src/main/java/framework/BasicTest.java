package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicTest {
    private WebDriver driver;
    protected SoftAssert softAssert;

    protected WebDriver getDriver() {
        if (driver == null) {
            initWebDriver();
        }

        return driver;
    }

    private void initWebDriver() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void createAssertions() {
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void tearDown() {
        if (null != driver) {
            getDriver().quit();
        }
        driver = null;
    }
}
