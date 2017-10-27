package org.exam.jee.tasks.mycantina.backend.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@NamedQuery(name = Menu.GET_MENU, query = "SELECT m FROM Menu m WHERE m.date = :mdate")
public class Menu {

    public static final String GET_MENU = "MENU_GET_ALL";

    @Id @GeneratedValue
    private long id;

    @Column(unique=true)
    private LocalDate date;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Dish> dishes;

    public Menu(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

}
