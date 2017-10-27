package org.exam.jee.tasks.mycantina.frontend.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 * Created by Johan on 09.06.2017.
 */
public class CreateMenuObject extends PageObject{
    public CreateMenuObject(WebDriver driver) {
        super(driver);

        assertEquals("Menu", driver.getTitle());
    }

    public boolean isOnPage(){
        return driver.getTitle().equals("Menu");


    }
    public HomePageObject createMenu(String date){

        setText("createMenuForm:date",date);
        driver.findElement(By.id("createMenuForm:includeDish")).click();

        driver.findElement(By.id("createMenuForm:createButton")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(driver);
        }
    }
}
