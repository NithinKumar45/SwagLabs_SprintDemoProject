package SwagLabsBaseClass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;


import SwagLabsUtilities.PropertiesUtilities;

public class SwagBaseClass {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeSuite(groups= {"functional", "integration", "negative", "smoke","system"})
    public void beforeSuite() {

        // Runs once before the complete test suite
        Reporter.log("Swag Labs Test Suite Started", true);
    }

    @BeforeTest(groups= {"functional", "integration", "negative", "smoke","system"})
    public void beforeTest() {

        // Runs before the <test> tag in TestNG XML
        System.out.println("Executing Test Preconditions");
    }
    
    @Parameters("browser")
    @BeforeClass(groups= {"functional", "integration", "negative", "smoke","system"})
    public void launchBrowser(@Optional("chrome") String xmlBrowser) throws Exception {

        // Create properties utility object
        PropertiesUtilities prop = new PropertiesUtilities();
        String browser = xmlBrowser;
        String url = prop.getDataFromPropertiesFile("url");

        // Chrome settings
        ChromeOptions settings = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        settings.setExperimentalOption("prefs", prefs);

        // Launch browser based on properties file
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver(settings);
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get(url);
        Reporter.log("Browser Launched Successfully", true);
    }

    @AfterClass(groups= {"functional", "integration", "negative", "smoke","system"})
    public void closeBrowser() throws InterruptedException {
    		Thread.sleep(2000);
        // Close browser after all tests in the class
        if (driver != null) {
            driver.quit();
        }

        Reporter.log("Browser Closed Successfully", true);
    }
    
    @AfterSuite(groups= {"functional", "integration", "negative", "smoke","system"})
    public void AfterSuite() {

        // Runs before the <test> tag in TestNG XML
        System.out.println("Swag Labs Test Suite Completed ");
    }
    
    
}