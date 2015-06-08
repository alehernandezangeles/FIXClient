package cencor.meif.fix.client.db;

import cencor.meif.fix.client.jpa.entities.*;

import java.util.List;

/**
 * Created by alejandro on 5/31/15.
 */
public interface DBController {

    List<NosEntity> getNewNos();

    List<OcrEntity> getNewOcr();

    void editNos(NosEntity nosEntity) throws Exception;
    NosEntity findNosByClOrdId(String clOrdId);

    void editOcr(OcrEntity ocrEntity) throws Exception;
    OcrEntity findOcrByClOrdId(String clOrdId);

    void createOtrosMsjFix(OtrosMsjFixEntity otrosMsjFixEntity) throws Exception;

    void createAck1(Ack1Entity ack1Entity) throws Exception;

    void createAck2(Ack2Entity ack2Entity) throws Exception;

    void createEr(ErEntity erEntity);

    void editStatus(String clOrdId, int estatus) throws Exception;

    void editStatus(String clOrdId, int estatus, int estatusAck2, String descrAck2) throws Exception;
}
