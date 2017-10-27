package org.exam.jee.tasks.mycantina.backend.ejb;


import org.exam.jee.tasks.mycantina.backend.entity.Dish;
import org.exam.jee.tasks.mycantina.backend.entity.Menu;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Stateless
public class MenuEJB {

    @PersistenceContext
    private EntityManager em;

    public MenuEJB(){

    }


    public Boolean CreateMenu(List<Dish> dishes, LocalDate date)
    {
        if(dishes.isEmpty())
        {
            throw new IllegalArgumentException("You need at least one dish in the menu");
        }
        Menu menu = getTodayMenu(date);
        if(menu != null)
        {
            return false;
        }

        menu = new Menu();
        menu.setDishes(dishes);
        menu.setDate(date);
        em.persist(menu);
        return true;

    }

    public Menu getCurrentMenu()
    {
        LocalDate localDate = LocalDate.now();
        if ((getTodayMenu(localDate) == null) && (getMenuInFuture() == null))
        {
            return getMenuInPast();
        }
        else if (getTodayMenu(localDate) == null)
        {
            return getMenuInFuture();
        }
        else {
            return getTodayMenu(localDate);
        }

    }
    public Menu getTodayMenu(LocalDate localDate)
    {
        Menu menu = null;
        try {
            TypedQuery<Menu> query = em.createQuery(
                    "select distinct m from Menu m where date=?1", Menu.class);
            query.setParameter(1, localDate);
            menu = query.getSingleResult();
        }
        catch (NoResultException nre)
        {

        }
        return menu;
    }

    public Menu getMenuInFuture()
    {
        Menu menu = null;
        try {
            LocalDate localDate = LocalDate.now().plusDays(1);
            TypedQuery<Menu> query = em.createQuery(
                    "select distinct m from Menu m where date >= :start and date <= :end order by date ASC", Menu.class);
            query.setMaxResults(1);
            query.setParameter("start", localDate);
            query.setParameter("end", localDate.plusYears(2));

            menu = query.getSingleResult();
        }
        catch (NoResultException nre)
        {

        }
        return menu;
    }

    public Menu getMenuInPast()
    {
        Menu menu = null;
        try {
            LocalDate localDate = LocalDate.now().minusDays(1);
            TypedQuery<Menu> query = em.createQuery(
                    "select distinct m from Menu m where date >= :start and date <= :end order by date DESC", Menu.class);
            query.setMaxResults(1);
            query.setParameter("start", localDate.minusYears(2));
            query.setParameter("end", localDate);
            menu = query.getSingleResult();
        }
        catch (NoResultException nre)
        {

        }
        return menu;
    }



}
