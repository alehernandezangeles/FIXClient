package cencor.meif.fix.client.jpa.controllers.test;

import cencor.meif.fix.client.jpa.controllers.ErroresEntityJpaController;
import cencor.meif.fix.client.jpa.entities.ErroresEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mhernandez on 6/9/15.
 */
public class Errores {
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
    public void insert() {
        ErroresEntity entity = new ErroresEntity();
        entity.setClOrdId("ClOrdId");
        entity.setMensajeError("Mensaje error");
        entity.setFechaInsercion(new Timestamp(new Date().getTime()));

        ErroresEntityJpaController controller = new ErroresEntityJpaController(emf);
        try {
            controller.create(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
