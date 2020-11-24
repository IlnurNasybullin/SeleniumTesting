package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage implements HTMLPage {

    public static final String URL = "http://localhost:9001/email/registration_form";
    public static final String TITLE = "Registration";
    private WebDriver driver;

    @FindBy(css = "input[type='password']")
    private WebElement passwordElement;

    public RegistrationPage() { }

    @Override
    public void init(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        driver.get(URL);
    }

    @Override
    public boolean atPage() {
        return driver.getTitle().equals(TITLE);
    }

    public void enterName(String userName) {
        driver.findElement(By.xpath("//input[@type='username']")).sendKeys(userName);
    }

    public void enterEmail(String email) {
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordElement.sendKeys(password);
    }

    public void clickSignUp() {
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }
}
