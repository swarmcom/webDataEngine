package ui.page;

import common.page.LogoutFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import play.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by mirceac on 12/30/16.
 */
public class UsersAdminConsolePage extends LogoutFunction {
    public static final String USERS_CARD_ID = "Users";
    public static final String PAPER_ICON_BUTTON = "paper-icon-button";

    @Override
    protected void applyMap(@Nullable WebDriver driver, HashMap<String, WebElement> map) {
        WebElement usersButton = driver.findElement(By.id(USERS_CARD_ID)).findElement(By.tagName(PAPER_ICON_BUTTON));
        map.put(PAPER_ICON_BUTTON, usersButton);
    }
}