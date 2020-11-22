import handlers.CachedHandler;
import handlers.TestMemCachedHandler;
import models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;
import repository.TestUserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.testng.Assert.assertTrue;

public class RegistrationTest {

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private HomePage homePage;

    private Connection connection;
    private TestUserRepository repository;

    private CachedHandler cachedHandler;

    @DataProvider
    public static Object[][] successful_registration() {
        return new Object[][] {
                {"Kate", "kate@yandex.ru", "Kate Password"},
                {"Bob", "martin_bob@yandex.ru", "MartinBob!"},
                {"Clinton", "clinton@gmail.com", "ClintonUSA"}
        };
    }

    @DataProvider
    public static Object[][] wrong_user_name() {
        return new Object[][] {
                {"", "kate@yandex.ru", "Kate Password"}
        };
    }

    @DataProvider
    public static Object[][] wrong_email() {
        return new Object[][] {
                {"Kate", "kateyandex.ru", "Kate Password"},
                {"Bob", "@yandex.ru", "MartinBob!"},
                {"Clinton", "clinton@", "ClintonUSA"},
                {"Eva", "", "Eva&Adam"}
        };
    }

    @DataProvider
    public static Object[][] existed_email() {
        return new Object[][] {
                {"Kate", "alice@yandex.ru", "Kate Password"},
                {"Bob", "bob@gmail.com", "MartinBob!"},
                {"Clinton", "jack@gmail.com", "ClintonUSA"}
        };
    }

    @DataProvider
    public static Object[][] wrong_password() {
        return new Object[][]{
                {"Kate", "kate@yandex.ru", "Kate"},
                {"Sid", "sid@kpfu.stud.ru", "Sid Idis"},
                {"Harry", "harry@hogwarts.en", ""}
        };
    }

    @BeforeMethod
    public void initPages() {
        driver = new ChromeDriver();
        registrationPage = new RegistrationPage(driver);
        homePage = new HomePage(driver);
        cachedHandler = new TestMemCachedHandler();
    }

    @BeforeMethod(onlyForGroups = "database_remove")
    public void initDatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Email", "postgres", "password");
        this.repository = new TestUserRepository(connection, "user", "email");
    }

    private void test_action(String userName, String email, String password) {
        registrationPage.open();
        assertTrue(registrationPage.atPage());
        registrationPage.enterName(userName);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.clickSignUp();
        assertTrue(homePage.atPage());
    }

    @Test(groups = "database_remove", dataProvider = "successful_registration")
    public void test_successful_registration(String userName, String email, String password) {
        test_action(userName, email, password);
    }

    @Test(dataProvider = "wrong_user_name")
    public void test_wrong_user_name(String userName, String email, String password) {
        test_action(userName, email, password);
    }

    @Test(dataProvider = "wrong_email")
    public void test_wrong_email(String userName, String email, String password) {
        test_action(userName, email, password);
    }

    @Test(dataProvider = "existed_email")
    public void test_existed_email(String userName, String email, String password) {
        test_action(userName, email, password);
    }

    @Test(dataProvider = "wrong_password")
    public void test_wrong_password(String userName, String email, String password) {
        test_action(userName, email, password);
    }

    @AfterMethod(onlyForGroups = "database_remove")
    public void removeUsers() throws SQLException {
        User user = new User();
        for (Object[] objects: successful_registration()) {
            user.setUserName((String) objects[0]);
            user.setEmail((String) objects[1]);
            user.setPassword((String) objects[2]);
            if (repository.remove(user)) {
                System.out.printf("Removed user %s", user);
            }
        }
        connection.close();
    }

    @AfterMethod
    public void closePages() {
        driver.quit();
        if (cachedHandler.flushAll()) {
            System.out.println("Memcached is empty!");
        }
    }

}
