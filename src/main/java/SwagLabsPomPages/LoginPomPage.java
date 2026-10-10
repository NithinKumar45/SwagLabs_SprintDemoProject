package SwagLabsPomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPomPage {

    WebDriver driver;

    public LoginPomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='error-message-container error']")
    private WebElement errorMessage;
    
    @FindBy(css = "[data-icon='xmark']")
    private WebElement errorCloseButton;
    public void clickErrorCloseButton() {
        errorCloseButton.click();
    }
    
    public boolean isUsernameFieldDisplayed() {
        return usernameField.isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return passwordField.isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }

    public void enterUsername(String username) throws InterruptedException {

        usernameField.clear();
        Thread.sleep(1000);

        if (username != null && !username.trim().isEmpty()) {
            usernameField.sendKeys(username);
        }
    }

    public void enterPassword(String password) throws InterruptedException {

        passwordField.clear();
        Thread.sleep(1000);

        if (password != null && !password.trim().isEmpty()) {
            passwordField.sendKeys(password);
        }
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public void login(String username, String password) throws InterruptedException {
        enterUsername(username);
        Thread.sleep(1000);
        enterPassword(password);
        Thread.sleep(1000);
        clickLogin();
        Thread.sleep(1000);
    }
}