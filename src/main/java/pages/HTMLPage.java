package pages;

import org.openqa.selenium.WebDriver;

public interface HTMLPage {

    void init(WebDriver driver);
    void open();
    boolean atPage();

}
