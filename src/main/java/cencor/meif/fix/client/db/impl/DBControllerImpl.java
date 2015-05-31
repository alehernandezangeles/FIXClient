package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.jpa.controllers.NosEntityJpaController;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.NosEntity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by alejandro on 5/31/15.
 */
public class DBControllerImpl implements DBController {

    private EntityManagerFactory emf;
    private NosEntityJpaController nosController;

    public DBControllerImpl() {
        emf = Persistence.createEntityManagerFactory("jpaFixClientPU");
        nosController = new NosEntityJpaController(emf);
    }

    @Override
    public List<NosEntity> getNewNos() {
        List<NosEntity> nosNewList = nosController.findNosByEstatus(CatEstatusEntity.NUEVO);
        return nosNewList;
    }
}
