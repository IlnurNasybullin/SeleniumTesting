package pages;

import org.openqa.selenium.WebDriver;

public class HomePage implements HTMLPage {

    public static final String URL = "http://localhost:9001/email/main";
    public static final String TITLE = "Main";
    private WebDriver driver;

    public HomePage() { }

    @Override
    public void init(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void open() {
        driver.get(URL);
    }

    @Override
    public boolean atPage() {
        return driver.getTitle().equals(TITLE);
    }
}
