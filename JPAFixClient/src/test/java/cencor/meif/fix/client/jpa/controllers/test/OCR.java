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

        OcrEntity ocrEntity = new OcrEntity();
        ocrEntity.setAvgPrice(120.67);
        ocrEntity.setClOrdId(date.getTime() + "");
        ocrEntity.setComision(1.25);
        ocrEntity.setCumQty(212000000);
        ocrEntity.setCveDistribuidoraOrig("Dist1");
        ocrEntity.setEstatus(CatEstatusEntity.ER);
        ocrEntity.setExecType("0");
        ocrEntity.setFechaHoraCapturaOper(new Timestamp(date.getTime()));
        ocrEntity.setGrossTradeAmt(176.98);
        ocrEntity.setIva(16.0);
        ocrEntity.setMotivosRechazo("Motivo rechazo");
        ocrEntity.setNetMoney(700000000.00);
        ocrEntity.setOrderId("MEI" + date.getTime());
        ocrEntity.setOrdStatus("0");
        ocrEntity.setOrigClOrdId(date.getTime() + "");
        ocrEntity.setOrigFolioMei("MEI" + date.getTime());
        ocrEntity.setPorcentajeComisionDist(1.34);
        ocrEntity.setSide("1");
        ocrEntity.setSymbol("EMI|SERIE");
        ocrEntity.setTransactTime(new Timestamp(date.getTime()));

        OcrEntityJpaController controller = new OcrEntityJpaController(emf);
        try {
            controller.create(ocrEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
