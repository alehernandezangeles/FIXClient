package cencor.meif.fix.client.db;

import cencor.meif.fix.client.jpa.entities.*;

import java.util.List;

/**
 * Created by alejandro on 5/31/15.
 */
public interface DBController {

    List<NosEntity> getNewNos();

    void editNos(NosEntity nosEntity) throws Exception;

    void createOtrosMsjFix(OtrosMsjFixEntity otrosMsjFixEntity) throws Exception;

    void createAck1(Ack1Entity ack1Entity) throws Exception;

    void createAck2(Ack2Entity ack2Entity) throws Exception;

    void createEr(ErEntity erEntity);
}
