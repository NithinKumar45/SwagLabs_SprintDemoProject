package SwagLabsPomPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPomPage {

    WebDriver driver;

    public CheckoutPomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='header_secondary_container']/descendant::span[text()]")
    private WebElement checkoutPageTitle;

    public String getCheckoutPageTitle() {
        return checkoutPageTitle.getText();
    }

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    public boolean isFirstNameFieldDisplayed() {
        return firstNameField.isDisplayed();
    }

    public void enterFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    public boolean isLastNameFieldDisplayed() {
        return lastNameField.isDisplayed();
    }

    public void enterLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    @FindBy(id = "postal-code")
    private WebElement zipField;

    public boolean isZipFieldDisplayed() {
        return zipField.isDisplayed();
    }

    public void enterZip(String zip) {
        zipField.sendKeys(zip);
    }

    @FindBy(id = "continue")
    private WebElement continueButton;

    public boolean isContinueButtonDisplayed() {
        return continueButton.isDisplayed();
    }

    public void clickContinue() {
        continueButton.click();
    }

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    public boolean isCancelButtonDisplayed() {
        return cancelButton.isDisplayed();
    }

    public void clickCancel() {
        cancelButton.click();
    }

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    public boolean areProductsDisplayed() {
        return productNames.size() > 0;
    }

    public String getProductName(int index) {
        return productNames.get(index).getText();
    }

    @FindBy(id = "finish")
    private WebElement finishButton;

    public boolean isFinishButtonDisplayed() {
        return finishButton.isDisplayed();
    }

    public void clickFinish() {
        finishButton.click();
    }

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public boolean isBackHomeButtonDisplayed() {
        return backHomeButton.isDisplayed();
    }

    public void clickBackHome() {
        backHomeButton.click();
    }

    @FindBy(css = "[data-test='complete-header']")
    private WebElement orderConfirmationMessage;

    public String getOrderConfirmationMessage() {
        return orderConfirmationMessage.getText();
    }

    public boolean isOrderConfirmationDisplayed() {
        return orderConfirmationMessage.isDisplayed();
    }

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}