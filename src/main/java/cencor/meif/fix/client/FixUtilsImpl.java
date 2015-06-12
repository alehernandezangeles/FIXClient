package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import org.apache.log4j.Logger;
import quickfix.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhernandez on 6/3/15.
 */
public class FixUtilsImpl implements FixUtils {

    private static Logger logger = Logger.getLogger(FixUtilsImpl.class);

    @Override
    public StringField get(Message message, StringField stringField) {
        boolean existsField = message.isSetField(stringField);
        StringField valStrField = null;
        if (existsField) {
            try {
                valStrField = message.getField(stringField);
            } catch (FieldNotFound fieldNotFound) {
                logger.error("Error al obtener campo " + stringField + " del mensaje " + message, fieldNotFound);
            }
        }

        return valStrField;
    }

    @Override
    public CharField get(Message message, CharField charField) {
        boolean existsField = message.isSetField(charField);
        CharField valCharField = null;
        if (existsField) {
            try {
                valCharField = message.getField(charField);
            } catch (FieldNotFound fieldNotFound) {
                logger.error("Error al obtener campo " + charField + " del mensaje " + message, fieldNotFound);
            }
        }

        return valCharField;
    }

    @Override
    public DoubleField get(Message message, DoubleField doubleField) {
        boolean existsField = message.isSetField(doubleField);
        DoubleField valDoubleField = null;
        if (existsField) {
            try {
                valDoubleField = message.getField(doubleField);
            } catch (FieldNotFound fieldNotFound) {
                logger.error("Error al obtener campo " + doubleField + " del mensaje " + message, fieldNotFound);
            }
        }
        return valDoubleField;

    }

    @Override
    public UtcTimeStampField get(Message message, UtcTimeStampField utcTimeStampField) {
        boolean existsField = message.isSetField(utcTimeStampField);
        UtcTimeStampField valUtcTimestampField = null;
        if (existsField) {
            try {
                valUtcTimestampField = message.getField(utcTimeStampField);
            } catch (FieldNotFound fieldNotFound) {
                logger.error("Error al obtener campo " + utcTimeStampField + " del mensaje " + message, fieldNotFound);
            }
        }
        return valUtcTimestampField;

    }

    @Override
    public List<String> getClOrdIdListNos(List<NosEntity> nosEntities) {
        List<String> clOrdIdListNos = new ArrayList<>();
        for (NosEntity nosEntity : nosEntities) {
            clOrdIdListNos.add(nosEntity.getClOrdId());
        }
        return clOrdIdListNos;
    }

    @Override
    public List<String> getClOrdIdListOcr(List<OcrEntity> ocrEntities) {
        List<String> clOrdIdListOcr = new ArrayList<>();
        for (OcrEntity ocrEntity : ocrEntities) {
            clOrdIdListOcr.add(ocrEntity.getClOrdId());
        }
        return clOrdIdListOcr;
    }
}
