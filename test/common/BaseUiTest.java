package common;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import play.api.test.WebDriverFactory;
import play.libs.F.Callback;
import play.test.Helpers;
import play.test.TestBrowser;
import common.page.LogoutFunction;
import common.page.LoginPage;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static play.test.Helpers.*;


public class BaseUiTest extends BaseRestTest {
    protected TestBrowser browser;
    protected FluentWait<WebDriver> wait;
    protected static final String TABLE_CSS_ROWS_SELECTOR = "table[class='style-scope vaadin-grid'] tr";

    @Before
    public void setUpUi() throws Exception {
        FirefoxDriver driver = (FirefoxDriver) WebDriverFactory.apply(FIREFOX);
        browser = Helpers.testBrowser(driver);
        wait = browser.fluentWait().withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        Callback<TestBrowser> callback = tenantLogin();
        try {
            callback.invoke(browser);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

    }

    private Callback<TestBrowser> tenantLogin() {
        Callback<TestBrowser> callback = new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("/loginform");
                assertTrue(browser.pageSource().contains("<login-form"));

                Map<String, WebElement> elementMap = browser.waitUntil(wait, new LoginPage());

                WebElement accountId = elementMap.get(LoginPage.ACCOUNT_INPUT_NAME);
                setText(accountId, TEST_ACCOUNT);
                WebElement username = elementMap.get(LoginPage.USERNAME_INPUT_NAME);
                setText(username, TEST_SUPERADMIN);
                WebElement password = elementMap.get(LoginPage.PASSWORD_INPUT_NAME);
                setText(password, TEST_SUPERADMIN_PASSWORD);
                WebElement login = elementMap.get(LoginPage.LOGIN_BUTTON_ID);

                login.click();
                assertTrue(browser.pageSource().contains("Users Admin Console"));
            }
        };
        return callback;
    }

    private void tenantLogout() {
        Map<String, WebElement> elementMap = browser.waitUntil(wait, new LogoutFunction());
        WebElement logout = elementMap.get(LogoutFunction.LOGOUT_BUTTON_ID);

        logout.click();
        assertTrue(browser.pageSource().contains("<login-form"));

    }

    protected void setText(WebElement element, String text) {
        element.clear();
        element.click();
        element.sendKeys(text);
    }

    protected void assertAlertEquals(String text) {
        Alert alert = browser.getDriver().switchTo().alert();
        String alertText = alert.getText();
        assertEquals(text, alertText);
        alert.accept();
    }

    @After
    public void tearDownUi() throws Exception {
        tenantLogout();
        if (browser != null) {
            browser.quit();
        }
    }
}
