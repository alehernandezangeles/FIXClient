package cencor.meif.fix.client.jpa.entities.test;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by mhernandez on 5/29/15.
 */
public class CatEstatus {

    private static EntityManagerFactory emf;

    @BeforeClass
    public void init() {
         emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
    }

    @Test
    public void selectAll() {
        System.out.println(emf);
    }

}
