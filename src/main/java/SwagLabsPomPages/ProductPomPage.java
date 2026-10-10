package SwagLabsPomPages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPomPage {

    WebDriver driver;

    public ProductPomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[data-test='inventory-item']")
    private List<WebElement> products;

    public boolean areProductsDisplayed() {
        return products.size() > 0;
    }

    public int getProductCount() {
        return products.size();
    }

    public void selectProductByName(String productName) {
        for (WebElement product : productNames) {
            if (product.getText().equals(productName)) {
                product.click();
                return;
            }
        }
        throw new IllegalArgumentException("Product not found: " + productName);
    }
    

    @FindBy(xpath = "//span[text()='Products']")
    private WebElement pageTitle;

    public boolean isPageTitleDisplayed() {
        return pageTitle.isDisplayed();
    }
    
    public String getpageTitle() {
    	return pageTitle.getText();
    }

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    public boolean areProductNamesDisplayed() {
        return productNames.size() > 0;
    }

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public boolean areProductPricesDisplayed() {
        return productPrices.size() > 0;
    }
    
    public void clickAddToCart() {
        addToCartButtons.get(0).click();
    }

    @FindBy(id= "add-to-cart")
    private List<WebElement> addToCartButtons;

    public boolean areAddToCartButtonsDisplayed() {
        return addToCartButtons.size() > 0;
    }

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    public boolean isCartIconDisplayed() {
        return cartIcon.isDisplayed();
    }

    public void clickCartIcon() {
        cartIcon.click();
    }

    @FindBy(css = "[class='shopping_cart_badge']")
    private WebElement cartBadge;

    public boolean isCartBadgeDisplayed() {
        return cartBadge.isDisplayed();
    }

    public String getCartItemCount() {
        return cartBadge.getText();
    }

    @FindBy(xpath = "//div[@class='right_component']/descendant::select")
    private WebElement filterDropdown;

    public boolean isFilterDropdownDisplayed() {
        return filterDropdown.isDisplayed();
    }

    public List<WebElement> getFilterOptions() {
        Select select = new Select(filterDropdown);
        return select.getOptions();
    }

    public void selectFilterByVisibleText(String option) {
        Select select = new Select(filterDropdown);
        select.selectByVisibleText(option);
    }
    
    public String selectRandomFilterOption() {
        Select select = new Select(filterDropdown);
        List<WebElement> options = select.getOptions();
        Random random = new Random();
        int index = random.nextInt(options.size());
        String selectedOption = options.get(index).getText();
        select.selectByIndex(index);
        return selectedOption;
    }

    public String getSelectedFilterOption() {
        Select select = new Select(filterDropdown);
        return select.getFirstSelectedOption().getText();
    }
}