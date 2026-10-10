package SwagLabsProductPage;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import SwagLabsBaseClass.SwagBaseClass;
import SwagLabsPomPages.CartPomPage;
import SwagLabsPomPages.LoginPomPage;
import SwagLabsPomPages.ProductPomPage;
import SwagLabsUtilities.ExcelUtilities;
import SwagLabsUtilities.PropertiesUtilities;

public class ProductPage extends SwagBaseClass {

	@BeforeMethod(groups= {"functional", "integration", "negative", "smoke"})
	public void loginToApplication() throws Exception {

	    PropertiesUtilities properties = new PropertiesUtilities();
	    String url = properties.getDataFromPropertiesFile("url");
	    driver.get(url);
	    driver.manage().deleteAllCookies();
	    ((JavascriptExecutor) driver).executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
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
    public void verifyProductPageTitleAndProductsDisplayed()  {

        // TC-PROD-01: Verify Product title, products, names and prices are visible
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Products page title is not displayed");
        Assert.assertTrue(productPage.areProductsDisplayed(),"Product cards are not displayed");
        Assert.assertTrue(productPage.areProductNamesDisplayed(),"Product names are not displayed");
        Assert.assertTrue(productPage.areProductPricesDisplayed(),"Product prices are not displayed");
        Reporter.log("Product title, product cards, names and prices are displayed successfully",true);
    }

    @Test(groups = {"functional"})
    public void verifyAddToCartAndCartLogoBehavior() throws Exception  {

        // TC-PROD-02: Verify Add to Cart and Cart Logo behavior
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Cart Logo badge is not displayed after adding product");
        Assert.assertEquals(productPage.getCartItemCount(),"1","Cart count is not updated correctly");
        Reporter.log(productName + " was added to Cart successfully",true);
    }

    @Test(groups = {"integration"})
    public void verifyProductToCartNavigation() throws Exception {

        // TC-PROD-03: Verify Product Page to Cart Page using selected product
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        productPage.clickCartIcon();
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not opened");
        Assert.assertTrue(cartPage.areCartItemsDisplayed(),"Selected product is not displayed in Cart");
        Assert.assertEquals(cartPage.getProductName(0),productName,"Selected product is not displayed correctly in Cart");
        Reporter.log("Product Page successfully navigated to Cart Page with selected product",true);
    }
    
    
    @Test(groups = {"negative"})
    public void verifyProductCanBeAddedAgainAfterRemoval() throws Exception {

        // TC-PROD-04: Verify product can be removed and added again
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Product was not added to Cart");
        productPage.clickCartIcon();
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertTrue(cartPage.areCartItemsDisplayed(),"Product is not displayed in Cart");
        Assert.assertEquals(cartPage.getProductName(0),productName,"Incorrect product is displayed in Cart");
        cartPage.removeProduct(0);
        Assert.assertFalse(cartPage.areCartItemsDisplayed(),"Product is still displayed after removal");
        cartPage.clickContinueShopping();
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page was not displayed after Continue Shopping");
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Product could not be added again after removal");
        Reporter.log(productName + " was removed and successfully added again",true);
    }
    
    @Test(groups = {"negative"})
    public void verifyCartNavigationWithEmptyCart() {

        // TC-PROD-05: Verify Cart navigation with an empty Cart
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page is not displayed");
        Assert.assertTrue(productPage.isCartIconDisplayed(),"Cart Logo is not displayed");
        productPage.clickCartIcon();
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not opened");
        Assert.assertFalse(cartPage.areCartItemsDisplayed(),"Cart contains a product even though no product was selected");
        Reporter.log("Empty Cart navigation handled correctly",true);
    }


    @Test(groups = {"smoke"})
    public void verifyProductPageSmokeTest() throws Exception {

        // TC-PROD-06: Verify Product Page smoke test
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isPageTitleDisplayed(),"Product Page title is not displayed");
        Assert.assertTrue(productPage.areProductsDisplayed(),"Products are not displayed");
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Assert.assertTrue(productPage.isCartBadgeDisplayed(),"Cart Logo was not updated after adding product");
        Reporter.log("Product Page smoke test passed successfully",true);
    }

    @Test(groups = {"smoke"})
    public void verifyFilterDropDownSmokeTest() throws Exception {

        // TC-PROD-07: Verify filter drop-down smoke test
        ProductPomPage productPage = new ProductPomPage(driver);
        Assert.assertTrue(productPage.isFilterDropdownDisplayed(),"Filter drop-down is not displayed");
        Assert.assertEquals(productPage.getFilterOptions().size(),4,"Expected 4 filter options are not available");
        String selectedOption = productPage.selectRandomFilterOption();
        Assert.assertEquals(productPage.getSelectedFilterOption(),selectedOption,"Random filter option was not selected correctly");
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Reporter.log("Random filter option selected and product added succesfully: " + selectedOption,true);
    }
}