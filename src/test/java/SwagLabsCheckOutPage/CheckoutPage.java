package SwagLabsCheckOutPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import SwagLabsBaseClass.SwagBaseClass;
import SwagLabsPomPages.CartPomPage;
import SwagLabsPomPages.CheckoutPomPage;
import SwagLabsPomPages.LoginPomPage;
import SwagLabsPomPages.ProductPomPage;
import SwagLabsUtilities.ExcelUtilities;
import SwagLabsUtilities.PropertiesUtilities;
import SwagLabsUtilities.ScreenshotUtilities;

public class CheckoutPage extends SwagBaseClass {
	
	@BeforeMethod(groups= {"functional", "integration", "negative", "smoke","system"})
	public void loginToApplication() throws Exception {
	    PropertiesUtilities properties = new PropertiesUtilities();
	    String url = properties.getDataFromPropertiesFile("url");
	    driver.get(url);
	    driver.manage().deleteAllCookies();
	    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
	            "window.localStorage.clear(); window.sessionStorage.clear();");
	    driver.get(url);
	    Thread.sleep(1000);
	    ExcelUtilities excel = new ExcelUtilities();
	    String username = excel.readDataFromExcel("Login Positive Test Cases", 1, 0);
	    String password = excel.readDataFromExcel("Login Positive Test Cases", 1, 1);
	    LoginPomPage loginPage = new LoginPomPage(driver);
	    loginPage.login(username, password);
	    Thread.sleep(1000);
	}

    @Test(groups = {"functional"})
    public void verifyCheckoutInformationFieldsAndControls() throws Exception {

        // TC-CHK-01: Verify Checkout Information fields and controls
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Your Information","Checkout Information page was not displayed");
        Assert.assertTrue(checkoutPage.isFirstNameFieldDisplayed(),"First Name field is not displayed");
        Assert.assertTrue(checkoutPage.isLastNameFieldDisplayed(),"Last Name field is not displayed");
        Assert.assertTrue(checkoutPage.isZipFieldDisplayed(),"Zip field is not displayed");
        Assert.assertTrue(checkoutPage.isContinueButtonDisplayed(),"Continue button is not displayed");
        Assert.assertTrue(checkoutPage.isCancelButtonDisplayed(),"Cancel button is not displayed");
        Reporter.log("Checkout Information fields and controls are displayed successfully",true);
    }
    
    @Test(groups = {"functional"})
    public void verifyValidCheckoutInformationAccepted() throws Exception {

        // TC-CHK-02: Verify valid checkout information is accepted

        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        Thread.sleep(500);
        checkoutPage.enterLastName(lastName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Overview","Checkout Overview page was not displayed");
        Reporter.log("Valid checkout information was accepted and Checkout Overview was displayed successfully",true);
    }
    
    @Test(groups = {"functional"})
    public void verifyCheckoutOverviewProductVisibilityAndControls() throws Exception {

        // TC-CHK-03: Verify Checkout Overview product visibility and controls
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getProductName(0),productName,"Selected product is not displayed in Cart");
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        Thread.sleep(500);
        checkoutPage.enterLastName(lastName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Overview","Checkout Overview page was not displayed");
        Assert.assertTrue(checkoutPage.areProductsDisplayed(),"Selected product is not displayed in Checkout Overview");
        Assert.assertEquals(checkoutPage.getProductName(0),productName,"Selected product is not retained in Checkout Overview");
        Assert.assertTrue(checkoutPage.isFinishButtonDisplayed(),"Finish button is not displayed");
        Assert.assertTrue(checkoutPage.isCancelButtonDisplayed(),"Cancel button is not displayed");
        Reporter.log("Checkout Overview displays the selected product and required controls successfully",true);
    }
    
    @Test(groups = {"functional"})
    public void verifyCheckoutCompletionAndBackHome() throws Exception {

        // TC-CHK-04: Verify Checkout Completion and Back Home
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        Thread.sleep(500);
        checkoutPage.enterLastName(lastName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Overview","Checkout Overview page was not displayed");
        checkoutPage.clickFinish();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isOrderConfirmationDisplayed(),"Order confirmation message is not displayed");
        Assert.assertEquals(checkoutPage.getOrderConfirmationMessage(),"Thank you for your order!","Order confirmation message is incorrect");
        Assert.assertTrue(checkoutPage.isBackHomeButtonDisplayed(),"Back Home button is not displayed");
        checkoutPage.clickBackHome();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after clicking Back Home");
        Reporter.log("Checkout completed successfully and Back Home navigated to Product Page",true);
    }
    
    
    @Test(groups = {"integration"})
    public void verifyCartToCheckoutIntegration() throws Exception {

        // TC-CHK-05: Verify Cart to Checkout integration
        ExcelUtilities excel = new ExcelUtilities();
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getProductName(0),productName,"Selected product is not displayed in Cart Page");
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Your Information","Checkout Information page was not displayed");
        Reporter.log("Cart Page successfully navigated to Checkout Information Page",true);
    }
    
    @Test(groups = {"integration"})
    public void verifyCheckoutOverviewToCheckoutCompleteIntegration() throws Exception {

        // TC-CHK-06: Verify Checkout Overview to Checkout Complete integration
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        checkoutPage.enterLastName(lastName);
        checkoutPage.enterZip(zip);
        checkoutPage.clickContinue();
        Thread.sleep(1000);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Overview","Checkout Overview page was not displayed");
        checkoutPage.clickFinish();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isOrderConfirmationDisplayed(),"Checkout Complete confirmation is not displayed");
        System.out.println(checkoutPage.getOrderConfirmationMessage());
        Assert.assertEquals(checkoutPage.getOrderConfirmationMessage(),"Thank you for your order!","Order confirmation message is incorrect");
        Reporter.log("Checkout Overview successfully completed the order and displayed confirmation",true);
    }
    
    @Test(groups = {"integration"})
    public void verifyCheckoutCompleteToHomeIntegration() throws Exception {

        // TC-CHK-07: Verify Checkout Complete to Home integration
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        Thread.sleep(500);
        checkoutPage.enterLastName(lastName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(500);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Overview","Checkout Overview page was not displayed");
        checkoutPage.clickFinish();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isOrderConfirmationDisplayed(),"Checkout Complete page was not displayed");
        Assert.assertEquals(checkoutPage.getOrderConfirmationMessage(),"Thank you for your order!","Order confirmation message is incorrect");
        System.out.println(checkoutPage.getOrderConfirmationMessage());
        checkoutPage.clickBackHome();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after clicking Back Home");
        Reporter.log("Checkout Complete successfully navigated back to Product Page",true);
    }
    
    @Test(groups = {"negative"})
    public void verifyBlankFirstNameIsRejected() throws Exception {

        // TC-CHK-08: Verify blank First Name is rejected
        ExcelUtilities excel = new ExcelUtilities();
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterLastName(lastName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(500);
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),"Validation error message was not displayed");
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Your Information","Checkout proceeded even though First Name was blank");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-CHECKOUT-04_BlankFirstName");
        Reporter.log("Blank First Name was rejected and validation was displayed successfully",true);
    }
    
    @Test(groups = {"negative"})
    public void verifyBlankLastNameIsRejected() throws Exception {

        // TC-CHK-09: Verify blank Last Name is rejected
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),"Validation error message was not displayed");
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Your Information","Checkout proceeded even though Last Name was blank");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-CHECKOUT-05_BlankLastName");
        Reporter.log("Blank Last Name was rejected and validation was displayed successfully",true);
    }
    
    @Test(groups = {"negative"})
    public void verifyInvalidOrBlankZipIsRejected() throws Exception {

        // TC-CHK-10: Verify invalid or blank Zip is rejected
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        cartPage.clickCheckout();
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        checkoutPage.enterFirstName(firstName);
        checkoutPage.enterLastName(lastName);
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),"Validation error message was not displayed");
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Your Information","Checkout proceeded with invalid or blank Zip");
//        ScreenshotUtilities.captureScreenshot(driver, "TC-CHECKOUT-06_BlankPostalCode");
        Reporter.log("Invalid or blank Zip was rejected and validation was displayed successfully",true);
    }
    
    @Test(groups = {"system"})
    public void verifyCompleteSystemPurchaseFlow() throws Exception {

        // TC-CHK-12: Verify complete system purchase flow
        ExcelUtilities excel = new ExcelUtilities();
        String firstName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 0);
        String lastName = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 1);
        String zip = excel.readDataFromExcel("Checkout Positive Test Cases", 1, 2);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        Assert.assertTrue(cartPage.areCartItemsDisplayed(),"Product was not displayed in Cart");
        cartPage.clickCheckout();
        Thread.sleep(1000);
        CheckoutPomPage checkoutPage = new CheckoutPomPage(driver);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Your Information","Checkout Information page was not displayed");
        checkoutPage.enterFirstName(firstName);
        Thread.sleep(500);
        checkoutPage.enterLastName(lastName);
        Thread.sleep(500);
        checkoutPage.enterZip(zip);
        Thread.sleep(500);
        checkoutPage.clickContinue();
        Thread.sleep(500);
        Assert.assertEquals(checkoutPage.getCheckoutPageTitle(),"Checkout: Overview", "Checkout Overview page was not displayed");
        checkoutPage.clickFinish();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isOrderConfirmationDisplayed(),"Order confirmation was not displayed");
        Assert.assertEquals(checkoutPage.getOrderConfirmationMessage(),"Thank you for your order!","Order was not completed successfully");
        Reporter.log("Complete system purchase flow executed successfully",true);
    }
}