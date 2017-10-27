package org.exam.jee.tasks.mycantina.frontend;


import org.exam.jee.tasks.mycantina.backend.ejb.DishEJB;
import org.exam.jee.tasks.mycantina.backend.entity.Dish;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class DishController implements Serializable{

    @EJB
    private DishEJB dishEJB;

    public List<Dish> getDishes(){
        return dishEJB.getAllDishes();
    }


}
