package org.exam.jee.tasks.mycantina.frontend;

import org.exam.jee.tasks.mycantina.backend.ejb.DishEJB;
import org.exam.jee.tasks.mycantina.backend.ejb.MenuEJB;
import org.exam.jee.tasks.mycantina.backend.entity.Dish;
import org.exam.jee.tasks.mycantina.backend.entity.Menu;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Johan on 09.06.2017.
 */
@Named
@SessionScoped
public class MenuController implements Serializable {

    Menu menu = new Menu();

    @EJB
    private MenuEJB menuEJB;


    public Menu getMenu(){
        return menu = menuEJB.getCurrentMenu();
    }
    public Menu getNextMenu()
    {
        return menu = menuEJB.getMenuInFuture();
    }
    public Menu getPreviousMenu()
    {
        return menu = menuEJB.getMenuInPast();
    }

    public List<Dish> getDishes(){
        return menu.getDishes();
    }
}
