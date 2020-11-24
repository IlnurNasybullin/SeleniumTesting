package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage implements HTMLPage {

    public static final String URL = "http://localhost:9001/email/registration_form";
    public static final String TITLE = "Registration";
    public static final int MAX_TIME_OUT = 5;
    private WebDriver driver;

    @FindBy(css = "input[type='password']")
    private WebElement passwordElement;

    private WebDriverWait wait;

    public RegistrationPage() { }

    @Override
    public void init(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, MAX_TIME_OUT);
    }

    @Override
    public void open() {
        driver.get(URL);
        if (!wait.until(ExpectedConditions.urlMatches(URL))) {
            throw new TimeoutException(String.format("URL %s is not founded!", URL));
        }
    }

    @Override
    public boolean atPage() {
        return wait.until(ExpectedConditions.titleIs(TITLE));
    }

    public void enterName(String userName) {
        WebElement userNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='username']")));
        userNameInput.sendKeys(userName);
    }

    public void enterEmail(String email) {
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordElement.sendKeys(password);
    }

    public void clickSignUp() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']")));
        button.click();
    }
}
