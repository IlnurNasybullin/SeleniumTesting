package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class SignInPage implements HTMLPage {

    public static final String URL = "http://localhost:9001/email";
    public static final String TITLE = "Sign In";
    public static final int MAX_TIME_OUT = 5;

    private WebDriver driver;

    public SignInPage() { }

    @Override
    public void init(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(MAX_TIME_OUT, TimeUnit.SECONDS);
    }

    public void open() {
        driver.get(URL);
    }

    public boolean atPage() {
        return driver.getTitle().equals(TITLE);
    }

    public void enterEmail(String email) {
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(By.cssSelector("input[type=submit]")).click();
    }
}
