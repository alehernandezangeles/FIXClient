package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.jpa.controllers.*;
import cencor.meif.fix.client.jpa.entities.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by alejandro on 5/31/15.
 */
public class DBControllerImpl implements DBController {

    private EntityManagerFactory emf;
    private NosEntityJpaController nosController;
    private OtrosMsjFixEntityJpaController otrosController;
    private Ack1EntityJpaController ack1Controller;
    private Ack2EntityJpaController ack2Controller;
    private ErEntityJpaController erController;

    public DBControllerImpl() {
        emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
        nosController = new NosEntityJpaController(emf);
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
    public void editNos(NosEntity nosEntity) throws Exception {
        nosController.edit(nosEntity);
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
}
