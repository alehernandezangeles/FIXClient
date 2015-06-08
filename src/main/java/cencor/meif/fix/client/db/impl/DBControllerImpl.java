package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.jpa.controllers.*;
import cencor.meif.fix.client.jpa.entities.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by alejandro on 5/31/15.
 */
public class DBControllerImpl implements DBController {

    private EntityManagerFactory emf;
    private NosEntityJpaController nosController;
    private OcrEntityJpaController ocrControlleer;
    private OtrosMsjFixEntityJpaController otrosController;
    private Ack1EntityJpaController ack1Controller;
    private Ack2EntityJpaController ack2Controller;
    private ErEntityJpaController erController;

    public DBControllerImpl() {
        emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
        nosController = new NosEntityJpaController(emf);
        ocrControlleer = new OcrEntityJpaController(emf);
        otrosController = new OtrosMsjFixEntityJpaController(emf);
        ack1Controller = new Ack1EntityJpaController(emf);
        ack2Controller = new Ack2EntityJpaController(emf);
        erController = new ErEntityJpaController(emf);
    }

    @Override
    public List<NosEntity> getNewNos() {
        List<NosEntity> nosNewList = nosController.findNosByEstatus(CatEstatusEntity.NUEVO);
        return nosNewList;
    }

    @Override
    public List<OcrEntity> getNewOcr() {
        List<OcrEntity> ocrNewList = ocrControlleer.findOcrByEstatus(CatEstatusEntity.NUEVO);
        return ocrNewList;
    }

    @Override
    public void editNos(NosEntity nosEntity) throws Exception {
        nosController.edit(nosEntity);
    }

    @Override
    public NosEntity findNosByClOrdId(String clOrdId) {
        NosEntity nosEntity = nosController.findNosByClOrdId(clOrdId);
        return nosEntity;
    }

    @Override
    public void editOcr(OcrEntity ocrEntity) throws Exception {
        ocrControlleer.edit(ocrEntity);
    }

    @Override
    public OcrEntity findOcrByClOrdId(String clOrdId) {
        OcrEntity ocrEntity = ocrControlleer.findOcrByClOrdId(clOrdId);
        return ocrEntity;
    }

    @Override
    public void createOtrosMsjFix(OtrosMsjFixEntity otrosMsjFixEntity) throws Exception {
        otrosController.create(otrosMsjFixEntity);
    }

    @Override
    public void createAck1(Ack1Entity ack1Entity) throws Exception {
        ack1Controller.create(ack1Entity);
    }

    @Override
    public void createAck2(Ack2Entity ack2Entity) throws Exception {
        ack2Controller.create(ack2Entity);
    }

    @Override
    public void createEr(ErEntity erEntity) {
        erController.create(erEntity);
    }

    @Override
    public void editStatus(String clOrdId, int estatus) throws Exception {
        Object entity = findOrderByClOrdId(clOrdId);
        if (entity instanceof NosEntity) {
            NosEntity nosEntity = (NosEntity) entity;
            nosEntity.setEstatus(estatus);
            editNos(nosEntity);
        } else if (entity instanceof OcrEntity) {
            OcrEntity ocrEntity = (OcrEntity) entity;
            ocrEntity.setEstatus(estatus);
            editOcr(ocrEntity);
        }
    }

    @Override
    public void editStatus(String clOrdId, int estatus, Integer estatusAck2, String mensajeEstatusAck2) throws Exception {
        Object entity = findOrderByClOrdId(clOrdId);
        if (entity instanceof NosEntity) {
            NosEntity nosEntity = (NosEntity) entity;
            nosEntity.setEstatus(estatus);
            nosEntity.setEstatusAck2(estatusAck2);
            nosEntity.setMensajeEstatusAck2(mensajeEstatusAck2);

            editNos(nosEntity);
        } else if (entity instanceof OcrEntity) {
            OcrEntity ocrEntity = (OcrEntity) entity;
            ocrEntity.setEstatus(estatus);
            ocrEntity.setEstatusAck2(estatusAck2);
            ocrEntity.setMensajeEstatusAck2(mensajeEstatusAck2);

            editOcr(ocrEntity);
        }
    }

    @Override
    public Object findOrderByClOrdId(String clOrdId) {
        Object entity = null;

        // Busca en la tabla NOS
        try {
            entity = nosController.findNosByClOrdId(clOrdId);
        } catch (NoResultException e) {
        }

        // Si no está en la tabla NOS busca en OCR
        if (entity == null) {
            try {
                entity = ocrControlleer.findOcrByClOrdId(clOrdId);
            } catch (NoResultException e) {
            }
        }

        if (entity == null) {
            throw new NoResultException("El clOrdId " + clOrdId + " no se encontró en la BD, tablas NOS y OCR.");
        }

        return entity;
    }
}
