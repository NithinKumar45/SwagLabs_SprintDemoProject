package SwagLabsHamburgerMenuPage;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import SwagLabsBaseClass.SwagBaseClass;
import SwagLabsPomPages.CartPomPage;
import SwagLabsPomPages.HamburgerMenuPomPage;
import SwagLabsPomPages.LoginPomPage;
import SwagLabsPomPages.ProductPomPage;
import SwagLabsUtilities.ExcelUtilities;
import SwagLabsUtilities.PropertiesUtilities;

public class HamburgerMenuPage extends SwagBaseClass {
	
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
    public void verifyHamburgerMenuOptionsDisplayed() throws Exception {

        // TC-HAM-01: Verify Hamburger menu options are displayed
        ExcelUtilities excel = new ExcelUtilities();
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        Assert.assertTrue(hamburgerMenu.isAllItemsDisplayed(),"All Items option is not displayed");
        Assert.assertTrue(hamburgerMenu.isLogoutDisplayed(),"Logout option is not displayed");
        Assert.assertTrue(hamburgerMenu.isResetAppStateDisplayed(),"Reset App State option is not displayed");
        Reporter.log("All Items, Logout and Reset App State options are displayed successfully",true);
    }
    
    @Test(groups = {"functional"})
    public void verifyResetAppStateRemovesAddedProduct() throws Exception {

        // TC-HAM-02: Verify Reset App State removes added product
        ExcelUtilities excel = new ExcelUtilities();
        ProductPomPage productPage = new ProductPomPage(driver);
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Cart indicator is not displayed after adding product");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        hamburgerMenu.clickResetAppState();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        Assert.assertTrue(hamburgerMenu.isCartEmpty(),"Cart is not empty after Reset App State");
        Reporter.log("Reset App State removed the added product and cleared the Cart successfully",true);
    }
    
    @Test(groups = {"integration"})
    public void verifyLogoutNavigatesToLoginPage() throws Exception {

        // TC-HAM-03: Verify Logout navigates to Login Page
        ExcelUtilities excel = new ExcelUtilities();
        LoginPomPage loginPage = new LoginPomPage(driver);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        hamburgerMenu.clickLogout();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),"Username field is not displayed after Logout");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),"Password field is not displayed after Logout");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),"Login button is not displayed after Logout");
        Reporter.log("Logout successfully navigated the user to Login Page",true);
    }
    
    @Test(groups = {"negative"})
    public void verifyResetAppStateWithNoCartItem() throws Exception {

        // TC-HAM-04: Verify Reset App State with no cart item
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        Assert.assertTrue(hamburgerMenu.isCartEmpty(),"Cart is not empty before Reset App State");
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        hamburgerMenu.clickResetAppState();
        Thread.sleep(1000);
        Assert.assertTrue(hamburgerMenu.isCartEmpty(),"Cart is not empty after Reset App State");
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        Assert.assertFalse(cartPage.areCartItemsDisplayed(),"Cart contains a product after Reset App State");
        Reporter.log("Reset App State handled the empty cart successfully",true);
    }
    @Test(groups = {"negative"})
    public void verifyMenuNotAccessibleAfterLogout() throws Exception {

        // TC-HAM-05: Verify logged-in menu functions are not accessible after logout
        ExcelUtilities excel = new ExcelUtilities();
        LoginPomPage loginPage = new LoginPomPage(driver);
        Thread.sleep(1000);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        Assert.assertTrue(hamburgerMenu.isLogoutDisplayed(),"Logout option is not displayed");
        hamburgerMenu.clickLogout();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),"Login Page is not displayed after Logout");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),"Password field is not displayed after Logout");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),"Login button is not displayed after Logout");
        Reporter.log("Logged-out state verified successfully; Hamburger menu is no longer accessible",true);
    }
    
    @Test(groups = {"smoke"})
    public void verifyHamburgerMenuSmokeTest() throws Exception {

        // TC-HAM-06: Verify Hamburger menu smoke test
        ExcelUtilities excel = new ExcelUtilities();
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        Assert.assertTrue(hamburgerMenu.isAllItemsDisplayed(),"All Items option is not displayed");
        Assert.assertTrue(hamburgerMenu.isLogoutDisplayed(),"Logout option is not displayed");
        Assert.assertTrue(hamburgerMenu.isResetAppStateDisplayed(),"Reset App State option is not displayed");
        hamburgerMenu.closeHamburgerMenu();
        Thread.sleep(1000);
        Reporter.log("Hamburger Menu smoke test passed successfully",true);
    }
    
    @Test(groups = {"smoke"})
    public void verifyResetAppStateSmokeTest() throws Exception {

        // TC-HAM-07: Verify Reset App State smoke test
        ExcelUtilities excel = new ExcelUtilities();
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Cart indicator is not displayed after adding product");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        hamburgerMenu.clickResetAppState();
        Thread.sleep(1000);
        Assert.assertFalse(driver.findElements(By.cssSelector(".shopping_cart_badge")).size() > 0,"Cart indicator is still displayed after Reset App State");
        Reporter.log("Reset App State smoke test passed successfully",true);
    }
    
    @Test(groups = {"system"})
    public void verifyCompleteHamburgerMenuWorkflow() throws Exception {

        // TC-HAM-11: Complete Login to Hamburger Menu Logout system flow
        ExcelUtilities excel = new ExcelUtilities();
        LoginPomPage loginPage = new LoginPomPage(driver);
        Thread.sleep(1000);
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after login");
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Cart indicator is not displayed after adding product");
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        Assert.assertTrue(cartPage.areCartItemsDisplayed(),"Added product is not displayed in Cart");
        Assert.assertEquals(cartPage.getProductName(0),productName,"Added product is not displayed correctly in Cart");
        HamburgerMenuPomPage hamburgerMenu1 =new HamburgerMenuPomPage(driver);
        hamburgerMenu1.clickHamburgerMenu();
        Thread.sleep(2000);
        hamburgerMenu1.clickAllItems();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after Continue Shopping");
        HamburgerMenuPomPage hamburgerMenu =new HamburgerMenuPomPage(driver);
        hamburgerMenu.clickHamburgerMenu();
        Thread.sleep(1000);
        Assert.assertTrue(hamburgerMenu.isAllItemsDisplayed(),"All Items option is not displayed");
        Assert.assertTrue(hamburgerMenu.isLogoutDisplayed(),"Logout option is not displayed");
        Assert.assertTrue(hamburgerMenu.isResetAppStateDisplayed(),"Reset App State option is not displayed");
        hamburgerMenu.clickResetAppState();
        Thread.sleep(1000);
        Assert.assertTrue(hamburgerMenu.isCartEmpty(),"Cart is not empty after Reset App State");
        Assert.assertFalse(cartPage.areCartItemsDisplayed(),"Cart still contains a product after Reset App State");
        hamburgerMenu.clickLogout();
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),"Login Page was not displayed after Logout");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),"Password field is not displayed after Logout");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),"Login button is not displayed after Logout");
        Reporter.log("Complete Hamburger Menu system workflow from Login to Logout executed successfully",true);
    }
}