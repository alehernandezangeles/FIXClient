package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.OtrosMsjFixEntity;
import org.apache.log4j.Logger;
import quickfix.Message;
import quickfix.StringField;
import quickfix.field.ClOrdID;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mhernandez on 6/3/15.
 */
public class OtrosAdapterImpl implements OtrosAdapter {

    private static Logger logger = Logger.getLogger(OtrosAdapterImpl.class);
    private FixUtils fixUtils;

    public OtrosAdapterImpl() {
        fixUtils = new FixUtilsImpl();
    }

    @Override
    public OtrosMsjFixEntity adapt(Message message) {
        OtrosMsjFixEntity otrosMsjFixEntity = new OtrosMsjFixEntity();
        otrosMsjFixEntity.setFixMsg(message.toString());
        otrosMsjFixEntity.setFechaInsercion(new Timestamp(new Date().getTime()));

        StringField clOrdId = fixUtils.get(message, new ClOrdID());
        if (clOrdId != null) {
            otrosMsjFixEntity.setClOrdId(clOrdId.getValue());
        }

        return otrosMsjFixEntity;
    }
}
