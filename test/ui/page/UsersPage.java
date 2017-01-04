package ui.page;

import common.page.LogoutFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import play.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by mirceac on 12/31/16.
 */
public class UsersPage extends LogoutFunction {
    public static final String USERS_TABLE_ID = "tableUser";
    public static final String USERS_TABLE_ADD_BUTTON_ID = "tableUseradd";
    public static final String USERS_TABLE_DELETE_BUTTON_ID = "tableUserdelete";
    public static final String USERS_FORM_ID = "formUser";



    @Override
    protected void applyMap(@Nullable WebDriver driver, HashMap<String, WebElement> map) {
        WebElement usersForm = driver.findElement(By.id(USERS_FORM_ID));
        WebElement usersTable = driver.findElement(By.id(USERS_TABLE_ID));
        WebElement addButton = driver.findElement(By.id(USERS_TABLE_ADD_BUTTON_ID));
        WebElement deleteButton = driver.findElement(By.id(USERS_TABLE_DELETE_BUTTON_ID));
        map.put(USERS_FORM_ID, usersForm);
        map.put(USERS_TABLE_ID, usersTable);
        map.put(USERS_TABLE_ADD_BUTTON_ID, addButton);
        map.put(USERS_TABLE_DELETE_BUTTON_ID, deleteButton);
    }
}
