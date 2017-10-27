package org.exam.jee.tasks.mycantina.frontend;

import org.exam.jee.tasks.mycantina.backend.ejb.DishEJB;
import org.exam.jee.tasks.mycantina.backend.ejb.MenuEJB;
import org.exam.jee.tasks.mycantina.backend.entity.Dish;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Named
@RequestScoped
public class CreateMenuController {
    private String formDate;
    private Map<Long, Boolean> checked = new HashMap<>();
    private List<Dish> dishes;

    @EJB
    private DishEJB dishEJB;

    @EJB
    private MenuEJB menuEJB;

    public String createMenu(){

        try{
            menuEJB.CreateMenu(dishes,toLocalDate(formDate));
        } catch (Exception e) {
            return "menu.jsf";
        }
        return "home.jsf";
    }


    public String submit(){
        List<Dish> checkedDishes = new ArrayList<>();
        for(Dish dish: dishEJB.getAllDishes()) {
            if(checked.get(dish.getId())){
                checkedDishes.add(dish);
            }
        }
        setDishes(checkedDishes);
        return createMenu();
    }


    public LocalDate toLocalDate(String sDate)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(sDate,dtf);
        return dt;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Map<Long, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, Boolean> checked) {
        this.checked = checked;
    }
}
