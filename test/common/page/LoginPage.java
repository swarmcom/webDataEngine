package common.page;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import scala.concurrent.java8.FuturesConvertersImpl;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by mirceac on 12/30/16.
 */
public class LoginPage implements Function<WebDriver, HashMap<String, WebElement>> {
    public static final String ACCOUNT_INPUT_NAME = "accountid";
    public static final String USERNAME_INPUT_NAME = "username";
    public static final String PASSWORD_INPUT_NAME = "password";
    public static final String LOGIN_BUTTON_ID = "login";

    @Nullable
    @Override
    public HashMap<String, WebElement> apply(@Nullable WebDriver driver) {
        HashMap<String, WebElement> map = new HashMap<String, WebElement>();
        WebElement accountId = driver.findElement(By.name(ACCOUNT_INPUT_NAME)).findElement(By.name(ACCOUNT_INPUT_NAME));
        WebElement username = driver.findElement(By.name(USERNAME_INPUT_NAME)).findElement(By.name(USERNAME_INPUT_NAME));
        WebElement password = driver.findElement(By.name(PASSWORD_INPUT_NAME)).findElement(By.name(PASSWORD_INPUT_NAME));
        WebElement login = driver.findElement(By.id(LOGIN_BUTTON_ID)).findElement(By.id(LOGIN_BUTTON_ID));
        if (accountId != null) {
            map.put(ACCOUNT_INPUT_NAME, accountId);
        }
        if (username != null) {
            map.put(USERNAME_INPUT_NAME, username);
        }
        if (password != null) {
            map.put(PASSWORD_INPUT_NAME, password);
        }
        if (login != null) {
            map.put(LOGIN_BUTTON_ID, login);
        }
        return map;
    }
}
