package cencor.meif.fix.client.db;

import cencor.meif.fix.client.jpa.entities.NosEntity;

import java.util.List;

/**
 * Created by alejandro on 5/31/15.
 */
public interface DBController {

    List<NosEntity> getNewNos();

    void editNos(NosEntity nosEntity) throws Exception;

}
