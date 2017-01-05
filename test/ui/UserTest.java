package ui;


import common.BaseUiTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.page.AddUserPage;
import ui.page.UsersAdminConsolePage;
import ui.page.UsersPage;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserTest extends BaseUiTest {
    public static final String USERS_TABLE_MODIFY_BUTTON_ID = "id0operationUser";

    @Test
    public void checkUsersCard() throws Exception {
        assertTrue(browser.pageSource().contains("&quot;cardId&quot;:&quot;Users&quot;"));
        Map<String, WebElement> elementMap = browser.waitUntil(wait, new UsersAdminConsolePage());
        WebElement usersButton = elementMap.get(UsersAdminConsolePage.PAPER_ICON_BUTTON);
        assertNotNull(usersButton);

        usersButton.click();

        elementMap = browser.waitUntil(wait, new UsersPage());
        WebElement table = elementMap.get(UsersPage.USERS_TABLE_ID);
        assertNotNull(table);
        List<WebElement> rows = table.findElements(By.cssSelector(TABLE_CSS_ROWS_SELECTOR));
        assertEquals(3, rows.size());
        WebElement addButton = elementMap.get(UsersPage.USERS_TABLE_ADD_BUTTON_ID);
        assertNotNull(addButton);
        WebElement deleteButton = elementMap.get(UsersPage.USERS_TABLE_DELETE_BUTTON_ID);
        assertNotNull(deleteButton);

        browser.executeScript("var form = document.getElementById(\"formUser\");" +
                "form.setAttribute(\"add\",true);" +
                "form.setAttribute(\"key\",'');" +
                "form.setAttribute(\"list\",false);");

        //add user
        addButton.click();
        elementMap = browser.waitUntil(wait, new AddUserPage());
        WebElement closeButton = elementMap.get(AddUserPage.CLOSE_BUTTON_ID);
        assertNotNull(closeButton);

        WebElement username = elementMap.get(AddUserPage.USERNAME_FIELD_NAME);
        assertNotNull(username);
        WebElement password = elementMap.get(AddUserPage.PASSWORD_FIELD_NAME);
        assertNotNull(password);
        WebElement birthDate = elementMap.get(AddUserPage.BIRTH_DATE_FIELD_NAME);
        assertNotNull(birthDate);

        WebElement saveIdentificationButton = elementMap.get(AddUserPage.SAVE_IDENTIFICATION_ID);
        assertNotNull(saveIdentificationButton);

        setText(username, "user1");
        setText(password, "password1");
        setText(birthDate, "10/28/1944");

        saveIdentificationButton.click();

        assertAlertEquals("Item added");
        closeButton.click();

        //retrieve new table rows and test user table size
        elementMap = browser.waitUntil(wait, new UsersPage());
        table = elementMap.get(UsersPage.USERS_TABLE_ID);
        assertNotNull(table);
        rows = table.findElements(By.cssSelector(TABLE_CSS_ROWS_SELECTOR));
        assertEquals(4, rows.size());

        WebElement lastUserColumn = rows.get(rows.size() - 1);

        //retrieve modify user button element from more menu
        WebElement lastUserMoreButton = lastUserColumn.findElement(By.tagName("iron-icon"));
        lastUserMoreButton.click();
        WebElement modifyUserButton = table.findElement(By.id(USERS_TABLE_MODIFY_BUTTON_ID));
        assertNotNull(modifyUserButton);
        modifyUserButton = wait.until(ExpectedConditions.visibilityOf(modifyUserButton));
        assertTrue(modifyUserButton.isDisplayed());

        //modify user
        modifyUserButton.click();
        elementMap = browser.waitUntil(wait, new AddUserPage());
        closeButton = elementMap.get(AddUserPage.CLOSE_BUTTON_ID);
        assertNotNull(closeButton);

        username = elementMap.get(AddUserPage.USERNAME_FIELD_NAME);
        assertNotNull(username);
        password = elementMap.get(AddUserPage.PASSWORD_FIELD_NAME);
        assertNotNull(password);
        birthDate = elementMap.get(AddUserPage.BIRTH_DATE_FIELD_NAME);
        assertNotNull(birthDate);

        saveIdentificationButton = elementMap.get(AddUserPage.SAVE_IDENTIFICATION_ID);
        assertNotNull(saveIdentificationButton);

        setText(username, "user2");
        setText(password, "password2");
        setText(birthDate, "12/28/2010");

        saveIdentificationButton.click();

        assertAlertEquals("Item modified");
        closeButton.click();

        //retrieve new table rows and test again user table size
        elementMap = browser.waitUntil(wait, new UsersPage());
        table = elementMap.get(UsersPage.USERS_TABLE_ID);
        assertNotNull(table);
        rows = table.findElements(By.cssSelector(TABLE_CSS_ROWS_SELECTOR));
        assertEquals(4, rows.size());

        //click on table checkbox and delete added user
        lastUserColumn = rows.get(rows.size() - 1);
        WebElement lastUserCheckbox = lastUserColumn.findElement(By.tagName("input"));
        lastUserCheckbox.click();
        deleteButton.click();
        assertAlertEquals("deleted");

        //test again new user table size
        rows = table.findElements(By.cssSelector(TABLE_CSS_ROWS_SELECTOR));
        assertEquals(3, rows.size());
    }
}
