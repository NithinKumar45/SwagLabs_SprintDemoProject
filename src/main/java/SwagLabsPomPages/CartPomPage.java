package SwagLabsPomPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPomPage {
	 WebDriver driver;

	 public CartPomPage(WebDriver driver) {
		 this.driver = driver;
		 PageFactory.initElements(driver, this);
	    }
	    
	 @FindBy(xpath = "//div[@class='header_secondary_container']/descendant::span[text()]")
	 private WebElement cartPageTitle;

	 public String getCartPageTitle() {
	     return cartPageTitle.getText();
	 }
	 public boolean isCartPageTitleDisplayed() {
		    return cartPageTitle.isDisplayed();
	}
	 
	 @FindBy(xpath = "//div[@class='cart_item']")
	 private List<WebElement> cartItems;
	 public boolean areCartItemsDisplayed() {
		    return cartItems.size() > 0;
		}
	 
	 public int getCartItemCount() {
		 return cartItems.size();
		}

	 @FindBy(className = "inventory_item_name")
	 private List<WebElement> productNames;
	 public String getProductName(int index) {
		    return productNames.get(index).getText();
		}

	 @FindBy(xpath = "//button[contains(@id,'remove')]")
	 private List<WebElement> removeButtons;
	 public void removeProduct(int index) {
		    removeButtons.get(index).click();
		}
	 public boolean areRemoveButtonsDisplayed() {
		    return removeButtons.size() > 0;
		}

	 @FindBy(id = "continue-shopping")
	 private WebElement continueShoppingButton;
	 public void clickContinueShopping() {
		    continueShoppingButton.click();
		}

	 @FindBy(id = "checkout")
	 private WebElement checkoutButton;
	 public void clickCheckout() {
		 checkoutButton.click();
		}
}
