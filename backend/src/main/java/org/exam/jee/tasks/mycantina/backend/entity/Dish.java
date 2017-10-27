package org.exam.jee.tasks.mycantina.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@NamedQuery(name = Dish.GET_ALL, query = "SELECT d FROM Dish d")
public class Dish {

    public static final String GET_ALL = "DISH_GET_ALL";

    @Id @GeneratedValue @NotNull
    private long id;

    @NotNull @Size(min =1, max = 50 )
    private String name;

    @NotNull @Size(min = 1, max = 500)
    private String description;

    public Dish(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
