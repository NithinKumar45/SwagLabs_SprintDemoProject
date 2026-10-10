package SwagLabsLoginPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import SwagLabsBaseClass.SwagBaseClass;
import SwagLabsPomPages.LoginPomPage;
import SwagLabsPomPages.ProductPomPage;
import SwagLabsUtilities.ExcelUtilities;
import SwagLabsUtilities.PropertiesUtilities;
import SwagLabsUtilities.ScreenshotUtilities;

public class LoginPage extends SwagBaseClass {

    @BeforeMethod(groups= {"functional", "integration", "negative", "smoke"})
    public void openLoginPage() throws Exception {
        PropertiesUtilities properties = new PropertiesUtilities();
        String url = properties.getDataFromPropertiesFile("url");
        driver.get(url);
        Thread.sleep(1000);
    }

    @Test(groups = {"functional"})
    public void verifyLoginControlsDisplayed() throws InterruptedException {

        // TC-LOGIN-01: Verify Username, Password and Login controls are displayed
        LoginPomPage loginPage = new LoginPomPage(driver);
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),"Username field is not displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),"Password field is not displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),"Login button is not displayed");
        Reporter.log("Username, Password and Login controls are displayed", true);
        Thread.sleep(1000);
    }

    @Test(groups = {"functional"})
    public void verifyValidLogin() throws Exception {

        // TC-LOGIN-02: Verify login with valid standard user credentials
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Positive Test Cases", 1, 0);
        String password = excel.readDataFromExcel("Login Positive Test Cases", 1, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        loginPage.login(username, password);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page is not displayed after valid login");
        Reporter.log("Valid login successful", true);
        Thread.sleep(1000);
    }

    @Test(groups = {"integration"})
    public void verifyLoginNavigatesToProductPage() throws Exception {

        // TC-LOGIN-03: Verify Login navigates to Product Page
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Positive Test Cases", 1, 0);
        String password = excel.readDataFromExcel("Login Positive Test Cases", 1, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        loginPage.login(username, password);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Login did not navigate to Product Page");
        Reporter.log("Login successfully navigated to Product Page", true);
        ProductPomPage pp=new ProductPomPage(driver);
        System.out.println("The Page Title is: "+pp.getpageTitle());
        Thread.sleep(1000);
    }

    @Test(priority=1,groups = {"negative"})
    public void verifyInvalidUsername() throws Exception {

        // TC-LOGIN-04: Verify login with invalid username
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Negative Test Cases", 1, 0);
        String password = excel.readDataFromExcel("Login Negative Test Cases", 1, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed for invalid username");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-LOGIN-04_InvalidUsername");
        loginPage.clickErrorCloseButton();
        Reporter.log("Invalid username validation verified", true);
        Thread.sleep(2000);
        
    }

    @Test(priority=3,groups = {"negative"})
    public void verifyInvalidPassword() throws Exception {

        // TC-LOGIN-05: Verify login with invalid password
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Negative Test Cases", 2, 0);
        String password = excel.readDataFromExcel("Login Negative Test Cases", 2, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Error message is not displayed for invalid password");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-LOGIN-05_InvalidPassword");
        loginPage.clickErrorCloseButton();
        Reporter.log("Invalid password validation verified", true);
        Thread.sleep(1000);
        
    }
    
   
    @Test(priority=2,groups = {"negative"})
    public void verifyBlankUsername() throws Exception {

        // TC-LOGIN-06: Verify login with blank username
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Negative Test Cases", 3, 0);
        String password = excel.readDataFromExcel("Login Negative Test Cases", 3, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        System.out.println("Blank Username Test - Username: [" + username + "]");
        System.out.println("Blank Username Test - Password: [" + password + "]");
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Username validation error is not displayed");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-LOGIN-06_BlankUsername");
        loginPage.clickErrorCloseButton();
        Reporter.log("Blank username validation verified", true);
        Thread.sleep(1000);
        
    }

    @Test(priority=4,groups = {"negative"})
    public void verifyBlankPassword() throws Exception {

        // TC-LOGIN-07: Verify login with blank password
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Negative Test Cases", 4, 0);
        String password = excel.readDataFromExcel("Login Negative Test Cases", 4, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Password validation error is not displayed");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-LOGIN-07_BlankPassword");
        loginPage.clickErrorCloseButton();
        Reporter.log("Blank password validation verified", true);
        Thread.sleep(1000);
    }

    @Test(groups = {"smoke"})
    public void verifyLoginSmokeTest() throws Exception {

        // TC-LOGIN-08: Verify login smoke test
        ExcelUtilities excel = new ExcelUtilities();
        String username = excel.readDataFromExcel("Login Positive Test Cases", 1, 0);
        String password = excel.readDataFromExcel("Login Positive Test Cases", 1, 1);
        LoginPomPage loginPage = new LoginPomPage(driver);
        loginPage.login(username, password);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Critical login path failed and Product Page is not displayed");
        Reporter.log("Login smoke test passed successfully", true);
        Thread.sleep(1000);
    }
}