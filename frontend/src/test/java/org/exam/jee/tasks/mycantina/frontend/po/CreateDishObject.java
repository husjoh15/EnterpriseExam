package org.exam.jee.tasks.mycantina.frontend.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

/**
 * Created by Johan on 09.06.2017.
 */
public class CreateDishObject extends PageObject{
    public CreateDishObject(WebDriver driver) {
        super(driver);

        assertEquals("Dishes", driver.getTitle());
    }

    public boolean isOnPage(){
        return driver.getTitle().equals("Dishes");


    }
    public HomePageObject createDish(String name, String description){

        setText("createDishForm:name",name);
        setText("createDishForm:description", description);

        driver.findElement(By.id("createDishForm:createButton")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(driver);
        }
    }
}
