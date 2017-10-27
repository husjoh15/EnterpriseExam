package org.exam.jee.tasks.mycantina.backend.ejb;

import org.exam.jee.tasks.mycantina.backend.entity.Dish;
import org.exam.jee.tasks.mycantina.backend.entity.Menu;
import org.exam.jee.tasks.mycantina.backend.util.DeleterEJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Johan on 08.06.2017.
 */
@RunWith(Arquillian.class)
public class MenuEJBTest {
    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.exam.jee.tasks.mycantina.backend")
                .addClass(DeleterEJB.class)
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private DishEJB dishEJB;
    @EJB
    private MenuEJB menuEJB;
    @EJB
    private DeleterEJB deleterEJB;

    @Before
    @After
    public void emptyDatabase(){ deleterEJB.deleteEntities(Menu.class);
    }


    @Test(expected = javax.ejb.EJBException.class)
    public void testCreateMenuWithNoDish(){
        Menu menu = new Menu();
        LocalDate currentDate = LocalDate.now();
        List<Dish> dishes = menu.getDishes(); //this will be empty
        menuEJB.CreateMenu(dishes,currentDate);
    }

    @Test
    public void testGetCurrentMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");

        //Creates a menu five days ago
        LocalDate currentDate = LocalDate.now().minusDays(5);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        Menu menu = menuEJB.getCurrentMenu();
        assertEquals(currentDate,menu.getDate());

        //Creates a menu five days in the future, this should now be the current menu. The other one should not
        LocalDate currentDate2 = LocalDate.now().plusDays(5);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate2);
        menu = menuEJB.getCurrentMenu();
        assertNotEquals(currentDate,menu.getDate());
        assertEquals(currentDate2,menu.getDate());

        //Creates a menu today, this should now be the current menu. The two previous should not
        LocalDate currentDate3 = LocalDate.now();
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate3);
        menu = menuEJB.getCurrentMenu();
        assertNotEquals(currentDate,menu.getDate());
        assertNotEquals(currentDate2,menu.getDate());
        assertEquals(currentDate3,menu.getDate());
    }

    @Test
    public void testGetAbsentPreviousMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate currentDate = LocalDate.now();
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        assertNull(menuEJB.getMenuInPast());
    }

    @Test
    public void testGetAbsentNextMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate currentDate = LocalDate.now();
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        assertNull(menuEJB.getMenuInFuture());
    }

    @Test
    public void testThreeMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate currentDate = LocalDate.now();
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate.minusDays(1));
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate.plusDays(1));
        Menu menuYesterday=menuEJB.getMenuInPast();
        Menu menuToday=menuEJB.getTodayMenu(currentDate);
        Menu menuTomorrow=menuEJB.getMenuInFuture();
        //today
        assertEquals(menuToday.getDate().plusDays(1),menuTomorrow.getDate());
        assertEquals(menuToday.getDate().minusDays(1),menuYesterday.getDate());
        //tomorrow
        assertNotEquals(menuTomorrow.getDate().plusDays(1),currentDate.plusDays(1));
        assertEquals(menuTomorrow.getDate().minusDays(1),menuToday.getDate());
        //yesterday
        assertEquals(menuYesterday.getDate().plusDays(1),menuToday.getDate());
        assertNotEquals(menuYesterday.getDate().minusDays(1),currentDate.minusDays(1));

    }


    @Test
    public void testCanCreateAMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate currentDate = LocalDate.now();
        List<Dish> dishes = dishEJB.getAllDishes();
        //Test creating a menu
        assertTrue(menuEJB.CreateMenu(dishes,currentDate));
        //Test creating a menu with same date. This should fail.
        assertFalse(menuEJB.CreateMenu(dishes,currentDate));

    }

    @Test
    public void testGetTodayMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate currentDate = LocalDate.now();
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        Menu menu = menuEJB.getTodayMenu(currentDate);
        assertEquals(currentDate,menu.getDate());
    }

    @Test
    public void testGetFutureMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate currentDate = LocalDate.now();
        LocalDate futureTenDaysDate = LocalDate.now().plusDays(10);
        LocalDate futureMonthDate = LocalDate.now().plusMonths(1);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),futureTenDaysDate);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),futureMonthDate);
        //Checks for menu 5 days in future
        Menu menu = menuEJB.getMenuInFuture();
        //Should find this date
        assertEquals(futureTenDaysDate,menu.getDate());

    }

    @Test
    public void testGetPreviousMenu(){
        dishEJB.createDish("aName","aDes");
        dishEJB.createDish("bName","bDes");
        LocalDate previousMonthDate = LocalDate.now().minusMonths(1);
        LocalDate previousTenDayDate = LocalDate.now().minusDays(10);
        LocalDate currentDate = LocalDate.now();
        menuEJB.CreateMenu(dishEJB.getAllDishes(),previousTenDayDate);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),previousMonthDate);
        menuEJB.CreateMenu(dishEJB.getAllDishes(),currentDate);
        //Checks for menu 5 days in past
        Menu menu = menuEJB.getMenuInPast();
        assertEquals(previousTenDayDate,menu.getDate());

    }

}

