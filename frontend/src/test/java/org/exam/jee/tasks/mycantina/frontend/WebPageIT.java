package org.exam.jee.tasks.mycantina.frontend;

import org.exam.jee.tasks.mycantina.frontend.po.CreateDishObject;
import org.exam.jee.tasks.mycantina.frontend.po.HomePageObject;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * Created by Johan on 09.06.2017.
 */
public class WebPageIT extends  WebTestBase {

    @Test
    public void testHomePage(){
        assumeTrue(JBossUtil.isJBossUpAndRunning());
        home = new HomePageObject(getDriver());
        home.toStartingPage();
        assertTrue(home.isOnPage());
    }

    @Test
    public void testCreateDish(){
        assumeTrue(JBossUtil.isJBossUpAndRunning());
        home = new HomePageObject(getDriver());
        CreateDishObject createDish = home.toCreateDish();
        assertTrue(createDish.isOnPage());

        String name = "Carbonara";
        String description = "pasta with bacon and sauce of egg and parmesan";

        assertFalse(getPageSource().contains(name));
        createDish.createDish(name,description);
        home.toCreateDish();
        assertTrue(createDish.isOnPage());
        assertTrue(getPageSource().contains(name));

    }


    //this test is not complete
    @Test
    public void testCreateMenu(){
        assumeTrue(JBossUtil.isJBossUpAndRunning());
        home = new HomePageObject(getDriver());
        home.toStartingPage();
        CreateDishObject createDish = home.toCreateDish();
        String name = "Bolognese";
        String description = "pasta with meat and tomato sauce";
        createDish.createDish(name,description);
        home.toCreateDish();
        String name2 = "Tandoori Chicken";
        String description2 = "indian food";
        createDish.createDish(name2,description2);
        home.toCreateDish();
        String name3 = "Pizza Margeritha";
        String description3 = "pizza with tomato sauce and mozzarella cheese";
        createDish.createDish(name3,description3);
        //ends on the menu
        home.toCreateMenu();
        assertTrue(getPageSource().contains(name));
        assertTrue(getPageSource().contains(name2));
        assertTrue(getPageSource().contains(name3));

    }

}
