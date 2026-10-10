package SwagLabsPomPages;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HamburgerMenuPomPage {

    WebDriver driver;

    public HamburgerMenuPomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    private WebElement hamburgerMenuButton;
    public void clickHamburgerMenu() {
        hamburgerMenuButton.click();
    }


    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;
    public void closeHamburgerMenu() {
        closeMenuButton.click();
    }

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;
    public boolean isAllItemsDisplayed() {
        return allItemsLink.isDisplayed();
    }
    public void clickAllItems() {
        allItemsLink.click();
    }

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;
    public boolean isLogoutDisplayed() {
        return logoutLink.isDisplayed();
    }
    public void clickLogout() {
        logoutLink.click();
    }

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;
    public boolean isResetAppStateDisplayed() {
        return resetAppStateLink.isDisplayed();
    }

    public void clickResetAppState() {
        resetAppStateLink.click();
    }
    
    @FindBy(className = "shopping_cart_badge")
    private List<WebElement> cartBadge;

    public boolean isCartEmpty() {
        return cartBadge.isEmpty();
    }
}