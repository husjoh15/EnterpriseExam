package org.exam.jee.tasks.mycantina.frontend;

import org.exam.jee.tasks.mycantina.backend.ejb.DishEJB;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CreateDishController {
    private String formName;
    private String formDescription;

    @EJB
    private DishEJB dishEJB;

    public String createDish(){
        try{
            dishEJB.createDish(formName,formDescription);
        } catch (Exception e)
        {
            return "dishes.jsf";
        }
        return "home.jsf";
    }


    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }
}
