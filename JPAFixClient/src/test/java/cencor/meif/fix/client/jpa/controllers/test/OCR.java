package cencor.meif.fix.client.jpa.controllers.test;

import cencor.meif.fix.client.jpa.controllers.OcrEntityJpaController;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by alejandro on 5/30/15.
 */
public class OCR {

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
        Date date = new Date();
        String orderId = "MEI1723164541519219389691194";

        OcrEntity ocrEntity = new OcrEntity();
        ocrEntity.setClOrdId(date.getTime() + "");
        ocrEntity.setEstatus(CatEstatusEntity.NUEVO);
        ocrEntity.setCveOperadora("Oper2");
        ocrEntity.setOrderId(orderId);
        ocrEntity.setOrigClOrdId(date.getTime() + "");
        ocrEntity.setSide("1");
        ocrEntity.setTransactTime(new Timestamp(date.getTime()));

        OcrEntityJpaController controller = new OcrEntityJpaController(emf);
        try {
            controller.create(ocrEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
