package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage implements HTMLPage {

    public static final String URL = "http://localhost:9001/email";
    public static final String TITLE = "Sign In";

    private WebDriver driver;

    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    public boolean atPage() {
        return driver.getTitle().equals(TITLE);
    }

    @Override
    public void close() {
        driver.quit();
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
