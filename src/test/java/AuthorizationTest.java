import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.HomePage;
import pages.SignInPage;

import static org.testng.Assert.*;

public class AuthorizationTest {

    private SignInPage signInPage;
    private HomePage homePage;

    @DataProvider
    public static Object[][] successful_authentication() {
        return new Object[][] {
                {"alice@yandex.ru", "AlicePassword"},
                {"bob@gmail.com", "BobPassword"},
                {"jack@gmail.com", "JackPassword"}
        };
    }

    @DataProvider
    public static Object[][] wrong_email() {
        return new Object[][] {
                {"alice@gmail.com", "AlicePassword"},
                {"bobgmail.com", "BobPassword"},
                {"", "JackPassword"}
        };
    }

    @DataProvider
    public static Object[][] wrong_password() {
        return new Object[][] {
                {"alice@yandex.ru", "Alice"},
                {"bob@gmail.com", "password"},
                {"jack@gmail.com", ""}
        };
    }

    @BeforeMethod
    public void init() {
        ChromeDriver driver = new ChromeDriver();
        signInPage = new SignInPage(driver);
        homePage = new HomePage(driver);
    }

    @Test(dataProvider = "successful_authentication")
    public void test_successful_authentication(String email, String password) {
        signInPage.open();
        assertTrue(signInPage.atPage());
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
        signInPage.clickLogin();
        assertTrue(homePage.atPage());
    }

    @Test(dataProvider = "wrong_email")
    public void test_wrong_email(String email, String password) {
        signInPage.open();
        assertTrue(signInPage.atPage());
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
        signInPage.clickLogin();
        assertFalse(homePage.atPage());
    }

    @Test(dataProvider = "wrong_password")
    public void test_wrong_password(String email, String password) {
        signInPage.open();
        assertTrue(signInPage.atPage());
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
        signInPage.clickLogin();
        assertFalse(homePage.atPage());
    }

    @AfterMethod
    public void close() {
        signInPage.close();
        homePage.close();
    }
}
