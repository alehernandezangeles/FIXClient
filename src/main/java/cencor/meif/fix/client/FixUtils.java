package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import quickfix.*;

import java.util.List;

/**
 * Created by mhernandez on 6/3/15.
 */
public interface FixUtils {

    StringField get(Message message, StringField stringField);
    CharField get(Message message, CharField stringField);
    DoubleField get(Message message, DoubleField doubleField);
    UtcTimeStampField get(Message message, UtcTimeStampField utcTimeStampField);

    String getCfgFileParam(SessionSettings sessionSettings, String paramName);

    List<String> getClOrdIdListNos(List<NosEntity> nosEntities);
    List<String> getClOrdIdListOcr(List<OcrEntity> ocrEntities);

}
