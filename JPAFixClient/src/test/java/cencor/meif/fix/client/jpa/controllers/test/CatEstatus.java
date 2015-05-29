package cencor.meif.fix.client.jpa.controllers.test;

import cencor.meif.fix.client.jpa.controllers.CatEstatusEntityJpaController;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by mhernandez on 5/29/15.
 */
public class CatEstatus {

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
    }

    @AfterClass
    public static void free() {
        emf.close();
    }

    @Test
    public void findAll() {
        CatEstatusEntityJpaController controller = new CatEstatusEntityJpaController(emf);
        List<CatEstatusEntity> entityList = controller.findCatEstatusEntityEntities();
        for (CatEstatusEntity catEstatusEntity : entityList) {
            System.out.println(catEstatusEntity);
        }
    }

    @Test
    public void findById() {
        CatEstatusEntityJpaController controller = new CatEstatusEntityJpaController(emf);
        CatEstatusEntity catEstatusEntity = controller.findCatEstatusEntity(1);
        System.out.println(catEstatusEntity);
    }

}
