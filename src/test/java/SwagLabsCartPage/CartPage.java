package SwagLabsCartPage;

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

public class CartPage extends SwagBaseClass {
	
	@BeforeMethod(groups= {"functional", "integration", "negative", "smoke"})
	public void loginToApplication() throws Exception {

	    PropertiesUtilities properties = new PropertiesUtilities();

	    String url = properties.getDataFromPropertiesFile("url");
	    driver.get(url);
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
	public void verifyAddedProductVisibleInCart() throws Exception {

	    // TC-CART-01: Verify added product is visible in Cart Page
	    ProductPomPage productPage = new ProductPomPage(driver);
	    ExcelUtilities excel = new ExcelUtilities();
	    String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
	    productPage.selectProductByName(productName);
	    productPage.clickAddToCart();
	    System.out.println("Product selected on Product Page: " + productName);
	    productPage.clickCartIcon();
	    CartPomPage cartPage = new CartPomPage(driver);
	    System.out.println("Number of products in Cart: "+ cartPage.getCartItemCount());
	    boolean productFound = false;
	    for (int i = 0; i < cartPage.getCartItemCount(); i++) {
	        String cartProductName = cartPage.getProductName(i);
	        System.out.println(
	                "Cart Product " + i + ": " + cartProductName);
	        if (cartProductName.equals(productName)) {
	            productFound = true;
	            break;
	        }
	    }

	    Assert.assertTrue(productFound,productName + " is not displayed in Cart Page");
	    Reporter.log(productName + " is displayed in Cart Page successfully",true);
	}
	
	@Test(groups = {"functional"})
	public void verifyCartPageControls() throws Exception {

	    // TC-CART-02: Verify Remove, Continue Shopping and Checkout controls
	    ProductPomPage productPage = new ProductPomPage(driver);
	    ExcelUtilities excel = new ExcelUtilities();
	    String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
	    productPage.selectProductByName(productName);
	    productPage.clickAddToCart();
	    Thread.sleep(1000);
	    productPage.clickCartIcon();
	    CartPomPage cartPage = new CartPomPage(driver);
	    Assert.assertTrue(cartPage.areRemoveButtonsDisplayed(),"Remove button is not displayed");
	    cartPage.clickContinueShopping();
	    Thread.sleep(1000);
	    Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),"Product Page was not displayed after Continue Shopping");
	    productPage.clickCartIcon();
	    Thread.sleep(1000);
	    cartPage.clickCheckout();
	    Thread.sleep(1000);
	    Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),"Checkout Page was not displayed");
	    Reporter.log("Cart Page controls are working successfully",true);
	}
    
    
    @Test(groups = {"integration"})
    public void verifyCartToCheckoutIntegration() throws Exception {

        // TC-CART-03: Verify Cart Page to Checkout Page navigation
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        cartPage.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),"Checkout Page was not displayed");
        Reporter.log("Cart Page navigated to Checkout Page successfully", true);
    }
    
    @Test(groups = {"negative"})
    public void verifyEmptyCart() {

        // TC-CART-04: Verify Cart Page when no product is added
        ProductPomPage productPage = new ProductPomPage(driver);
        productPage.clickCartIcon();
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        Assert.assertFalse(cartPage.areCartItemsDisplayed(),"Cart is not empty");
        Reporter.log("Empty Cart is displayed successfully", true);
    }
    
    @Test(groups = {"negative"})
    public void verifyRemovedProductNotInCart() throws Exception {

        // TC-CART-05: Verify removed product is not present in Cart
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getProductName(0),productName,"Added product is not displayed in Cart");
        cartPage.removeProduct(0);
        Thread.sleep(1000);
        Assert.assertFalse(cartPage.areCartItemsDisplayed(),"Removed product is still displayed in Cart");
        Reporter.log("Product was removed from Cart successfully", true);
    }
    
    @Test(groups = {"smoke"})
    public void verifyCartPageSmokeTest() throws Exception {

        // TC-CART-06: Verify basic Cart Page functionality
        ProductPomPage productPage = new ProductPomPage(driver);
        ExcelUtilities excel = new ExcelUtilities();
        String productName = excel.readDataFromExcel("Product Test Cases", 1, 0);
        productPage.selectProductByName(productName);
        productPage.clickAddToCart();
        Thread.sleep(1000);
        productPage.clickCartIcon();
        Thread.sleep(1000);
        CartPomPage cartPage = new CartPomPage(driver);
        Assert.assertEquals(cartPage.getCartPageTitle(),"Your Cart","Cart Page was not displayed");
        Assert.assertTrue(cartPage.areCartItemsDisplayed(),"Product is not displayed in Cart Page");
        Reporter.log("Cart Page smoke test passed successfully", true);
    }
}