package cencor.meif.fix.client.jpa.controllers.test;

import cencor.meif.fix.client.jpa.controllers.ErEntityJpaController;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.ErEntity;
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
public class ER {

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

        ErEntity erEntity = new ErEntity();
        erEntity.setAvgPrice(123.45);
        erEntity.setClOrdId("Id" + date.getTime());
        erEntity.setComision(1.43);
        erEntity.setCumQty(12000000);
        erEntity.setCveDistribuidoraOrig("Dist1");
        erEntity.setEstatus(CatEstatusEntity.ACK2);
        erEntity.setExecType("1");
        erEntity.setFechaHoraCapturaOper(new Timestamp(date.getTime()));
        erEntity.setGrossTradeAmt(127.48);
        erEntity.setIva(16.0);
        erEntity.setMotivosRechazo("MotivosRechazo");
        erEntity.setNetMoney(165.78);
        erEntity.setOrderId("MEI" + date.getTime());
        erEntity.setOrdStatus("A");
        erEntity.setOrigClOrdId("B");
        erEntity.setOrigFolioMei("C");
        erEntity.setPorcentajeComisionDist(1.32);
        erEntity.setSide("1");
        erEntity.setSymbol("EH|SER");
        erEntity.setTransactTime(new Timestamp(date.getTime()));

        ErEntityJpaController controller = new ErEntityJpaController(emf);
        controller.create(erEntity);

    }

}
