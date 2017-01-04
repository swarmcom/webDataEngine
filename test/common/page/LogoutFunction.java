package common.page;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by mirceac on 12/30/16.
 */
public class LogoutFunction implements Function<WebDriver, HashMap<String, WebElement>> {
    public static final String LOGOUT_BUTTON_ID = "exit";

    @Nullable
    @Override
    public HashMap<String, WebElement> apply(@Nullable WebDriver driver) {
        HashMap<String, WebElement> map = new HashMap<String, WebElement>();
        WebElement logoutButton = driver.findElement(By.id(LOGOUT_BUTTON_ID)).findElement(By.id(LOGOUT_BUTTON_ID));
        map.put(LOGOUT_BUTTON_ID, logoutButton);
        applyMap(driver, map);
        return map;
    }

    protected void applyMap(@Nullable WebDriver driver, HashMap<String, WebElement> map) {

    }
}
