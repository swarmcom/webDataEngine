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
public class AddUserPage extends LogoutFunction {
    public static final String CUSTOM_TEMPLATE_TAG = "custom-alpaca-template";
    public static final String PAPER_CARD_TAG = "paper-card";
    public static final String IRON_PAGES_TAG = "iron-pages";
    public static final String IDENTIFICATION_PAGE = "page_Identification";
    public static final String CLOSE_BUTTON_ID = "close";
    public static final String USERNAME_FIELD_NAME = "userName";
    public static final String PASSWORD_FIELD_NAME = "password";
    public static final String BIRTH_DATE_FIELD_NAME = "birthDate";
    public static final String SAVE_IDENTIFICATION_ID = "create_identification";

    @Override
    protected void applyMap(@Nullable WebDriver driver, HashMap<String, WebElement> map) {
        WebElement paperCard = driver.findElement(By.tagName(CUSTOM_TEMPLATE_TAG)).
                findElement(By.tagName(PAPER_CARD_TAG));

        WebElement closeButton = paperCard.
                findElement(By.id(CLOSE_BUTTON_ID));

        WebElement ironPages = paperCard.
                findElement(By.tagName(IRON_PAGES_TAG));

        WebElement identificationPage = ironPages.
                findElement(By.id(IDENTIFICATION_PAGE));

        WebElement usernameField = identificationPage.
                findElement(By.name(USERNAME_FIELD_NAME));
        WebElement passwordField = identificationPage.
                findElement(By.name(PASSWORD_FIELD_NAME));
        WebElement birthDateField = identificationPage.
                findElement(By.name(BIRTH_DATE_FIELD_NAME));

        WebElement saveIdentificationButton = ironPages.findElement(By.id(SAVE_IDENTIFICATION_ID));

        map.put(CLOSE_BUTTON_ID, closeButton);
        map.put(USERNAME_FIELD_NAME, usernameField);
        map.put(PASSWORD_FIELD_NAME, passwordField);
        map.put(BIRTH_DATE_FIELD_NAME, birthDateField);
        map.put(SAVE_IDENTIFICATION_ID, saveIdentificationButton);
    }
}
