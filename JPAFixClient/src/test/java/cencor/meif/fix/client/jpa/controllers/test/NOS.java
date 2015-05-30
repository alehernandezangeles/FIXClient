package cencor.meif.fix.client.jpa.controllers.test;

import cencor.meif.fix.client.jpa.controllers.NosEntityJpaController;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Created by mhernandez on 5/29/15.
 */
public class NOS {
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

        NosEntityJpaController controller = new NosEntityJpaController(emf);
        NosEntity nosEntity = new NosEntity();
        nosEntity.setEstatus(CatEstatusEntity.ACK1);
        nosEntity.setAccount("Account");
        nosEntity.setAllocQty(12000);
        nosEntity.setClOrdId("Id" + date.getTime());
        nosEntity.setComision(1.5);
        nosEntity.setContratoOper("ContratoOper");
        nosEntity.setCurrency("MXN");
        nosEntity.setCveOperadora("Oper1");
        nosEntity.setCveOrigen(0);
        nosEntity.setFechaSolicitud(new java.sql.Date(date.getTime()));
        nosEntity.setGrossTradeAmt(1212.12);
        nosEntity.setImporteSolicitado(12121212.25);
        nosEntity.setIva(16.0);
        nosEntity.setNumAsesorDist("AsesorDist");
        nosEntity.setOrdType("1");
        nosEntity.setPorcentajeComisionDist(2.44);
        nosEntity.setPrice(1200.21);
        nosEntity.setQuantity(77778);
        nosEntity.setSecurityId("SecurityId");
        nosEntity.setSecurityIdSource("4");
        nosEntity.setSecurityType("TV");
        nosEntity.setSettlDate(new java.sql.Date(date.getTime()));
        nosEntity.setSettlType("0");
        nosEntity.setSide(1l);
        nosEntity.setSymbol("EMI|SERIE");
        nosEntity.setTradeDate(new java.sql.Date(date.getTime()));
        nosEntity.setTransactTime(new java.sql.Timestamp(date.getTime()));

        try {
            controller.create(nosEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
