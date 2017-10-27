package org.exam.jee.tasks.mycantina.backend.ejb;

import org.exam.jee.tasks.mycantina.backend.ejb.DishEJB;
import org.exam.jee.tasks.mycantina.backend.entity.Dish;
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

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class DishEJBTest {

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
    private DeleterEJB deleterEJB;

    @Before
    @After
    public void emptyDatabase(){ deleterEJB.deleteEntities(Dish.class);
    }


    @Test
    public void testCreateDish(){

        String name = "Pasta Carbonara";
        String description = "Pasta with bacon and a sauce of egg and parmesan";
        Dish dish = dishEJB.getDish(dishEJB.createDish(name,description));
        assertEquals(name,dish.getName());
        assertEquals(description,dish.getDescription());
    }

    @Test
    public void testCreateTwoDishes(){
        String pastaName = "Pasta Bolognese";
        String pastaDes = "Pasta with tomato sauce";
        String steakName = "Streak";
        String steakDes = "Steak with potato and béarnaisesauce";
        Dish pasta = dishEJB.getDish(dishEJB.createDish(pastaName, pastaDes));
        Dish steak = dishEJB.getDish(dishEJB.createDish(steakName, steakDes));

        assertEquals(pastaName, pasta.getName());
        assertEquals(pastaDes,pasta.getDescription());
        assertEquals(steakName, steak.getName());
        assertEquals(steakDes,steak.getDescription());
    }


}