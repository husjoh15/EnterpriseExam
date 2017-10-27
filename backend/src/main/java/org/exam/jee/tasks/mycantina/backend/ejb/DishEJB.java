package org.exam.jee.tasks.mycantina.backend.ejb;

import org.exam.jee.tasks.mycantina.backend.entity.Dish;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.List;

@Stateless
public class DishEJB {

    @PersistenceContext
    private EntityManager em;

    public DishEJB(){}


    public long createDish(@NotNull String name, @NotNull String description)
    {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);

        em.persist(dish);

        return dish.getId();
    }

    public Dish getDish(long dishId)
    {
        return em.find(Dish.class,dishId);
    }

    public List<Dish> getAllDishes()
    {
        return em.createNamedQuery(Dish.GET_ALL).getResultList();
    }


}
