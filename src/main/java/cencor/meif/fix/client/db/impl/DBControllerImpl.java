package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.ErroresAdapter;
import cencor.meif.fix.client.ErroresAdapterImpl;
import cencor.meif.fix.client.FixUtils;
import cencor.meif.fix.client.FixUtilsImpl;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.UpdateNosStatus;
import cencor.meif.fix.client.db.UpdateOcrStatus;
import cencor.meif.fix.client.jdbc.JdbcController;
import cencor.meif.fix.client.jdbc.JdbcControllerImpl;
import cencor.meif.fix.client.jpa.controllers.*;
import cencor.meif.fix.client.jpa.entities.*;
import org.apache.log4j.Logger;
import quickfix.Message;
import quickfix.StringField;
import quickfix.field.ClOrdID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;
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
    private ErroresEntityJpaController erroresController;

    private JdbcController jdbcController;

    private static Logger logger = Logger.getLogger(DBControllerImpl.class);

    public DBControllerImpl() {
        emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
        nosController = new NosEntityJpaController(emf);
        ocrControlleer = new OcrEntityJpaController(emf);
        otrosController = new OtrosMsjFixEntityJpaController(emf);
        ack1Controller = new Ack1EntityJpaController(emf);
        ack2Controller = new Ack2EntityJpaController(emf);
        erController = new ErEntityJpaController(emf);
        erroresController = new ErroresEntityJpaController(emf);

        try {
            jdbcController = new JdbcControllerImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        int rows = jdbcController.updateStatusNos(clOrdId, estatus);
        // Si no está en la tabla NOS busca en OCR
        if (rows <= 0) {
            rows = jdbcController.updateStatusOcr(clOrdId, estatus);
        }

        if (rows <= 0) {
            throw new NoResultException("El clOrdId " + clOrdId + " no se encontró en la BD, tablas NOS y OCR.");
        }
    }

    @Override
    public void editStatusNos(String clOrdId, int estatus) throws Exception {
        int rows = jdbcController.updateStatusNos(clOrdId, estatus);

        if (rows <= 0) {
            throw new NoResultException("El clOrdId " + clOrdId + " no se encontró en la BD, tabla NOS");
        }
    }

    @Override
    public int editStatusNos(List<String> clOrdIdList, int estatus) throws SQLException {
        int rows = jdbcController.updateStatusNos(clOrdIdList, estatus);

        return rows;
    }

    @Override
    public void editStatusOcr(String clOrdId, int estatus) throws SQLException {
        int rows = jdbcController.updateStatusOcr(clOrdId, estatus);

        if (rows <= 0) {
            throw new NoResultException("El clOrdId " + clOrdId + " no se encontró en la BD, tabla OCR");
        }
    }

    @Override
    public int editStatusOcr(List<String> clOrdIdList, int estatus) throws SQLException {
        int rows = jdbcController.updateStatusOcr(clOrdIdList, estatus);

        return rows;
    }

    @Override
    public void updateStatus(List<String> clOrdIdListNos, List<String> clOrdIdListOcr, int estatus) {
        Thread threadNos = new Thread(new UpdateNosStatus(this, clOrdIdListNos, estatus));
        Thread threadOcr = new Thread(new UpdateOcrStatus(this, clOrdIdListOcr, estatus));
        threadNos.start();
        threadOcr.start();

        try {
            threadNos.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            threadOcr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateStatusSync(List<String> clOrdIdList, int estatus) throws SQLException {
        int rows = editStatusNos(clOrdIdList, estatus);
        if (rows != clOrdIdList.size()) {
            rows += editStatusOcr(clOrdIdList, estatus);
        }
        return rows;
    }

    @Override
    public void editStatus(String clOrdId, int estatus, int estatusAck2, String descrAck2) throws Exception {
        NosEntity nosEntity = null;
        OcrEntity ocrEntity = null;

        // Busca en la tabla NOS
        try {
            nosEntity = nosController.findNosByClOrdId(clOrdId);
            nosEntity.setEstatus(estatus);
            nosEntity.setEstatusAck2(estatusAck2);
            nosEntity.setMensajeEstatusAck2(descrAck2);
        } catch (NoResultException e) {
        }

        // Si no está en la tabla NOS busca en OCR
        if (nosEntity == null) {
            try {
                ocrEntity = ocrControlleer.findOcrByClOrdId(clOrdId);
                ocrEntity.setEstatus(estatus);
                ocrEntity.setEstatusAck2(estatusAck2);
                ocrEntity.setMensajeEstatusAck2(descrAck2);
            } catch (NoResultException e) {
            }
        }

        if (nosEntity != null) {
            editNos(nosEntity);
        } else if (ocrEntity != null) {
            editOcr(ocrEntity);
        } else {
            throw new NoResultException("El clOrdId " + clOrdId + " no se encontró en la BD, tablas NOS y OCR.");
        }

    }

    @Override
    public void createError(ErroresEntity erroresEntity) throws Exception {
        erroresController.create(erroresEntity);
    }

    @Override
    public void createErrorUpdateEstatus(String errorMsg, Throwable t, Object fixMsg) {
        FixUtils fixUtils = new FixUtilsImpl();
        ErroresAdapter erroresAdapter = new ErroresAdapterImpl();
        ErroresEntity erroresEntity = erroresAdapter.adapt(errorMsg, t);
        try {
            createError(erroresEntity);
        } catch (Exception e) {
            logger.error("Error al persistir error " + erroresEntity.getMensajeError(), e);
        }
        StringField clOrdId = null;
        if (fixMsg != null && fixMsg instanceof Message) {
            Message message = ((Message) fixMsg);
            clOrdId = fixUtils.get(message, new ClOrdID());
        }
        if (clOrdId != null) {
            try {
                String strClOrdId = clOrdId.getValue();
                editStatus(strClOrdId, CatEstatusEntity.ERROR);
            } catch (Exception e) {
                logger.error("Error al cambiar el estatus de la orden " + clOrdId + " al estatus de ERRRO", e);
            }
        }
    }
}
