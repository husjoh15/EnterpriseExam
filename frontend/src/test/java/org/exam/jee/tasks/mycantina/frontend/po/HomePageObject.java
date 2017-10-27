package org.exam.jee.tasks.mycantina.frontend.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Johan on 09.06.2017.
 */
public class HomePageObject extends PageObject{
    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    public HomePageObject toStartingPage() {
        String context = "/my_cantina"; // see jboss-web.xml
        driver.get("localhost:8080" + context + "/home.jsf");
        waitForPageToLoad();

        return this;
    }

    public boolean isOnPage() {
        return driver.getTitle().equals("MyCantina Home Page");
    }

    public CreateDishObject toCreateDish() {
        driver.findElement(By.id("dishLink")).click();
        waitForPageToLoad();
        return new CreateDishObject(driver);
    }

    public CreateMenuObject toCreateMenu(){
        driver.findElement(By.id("menuLink")).click();
        waitForPageToLoad();
        return new CreateMenuObject(driver);
    }

}
