package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.Ack1Entity;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.fix44.ExecutionReport;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mhernandez on 6/3/15.
 */
public class ACK1AdapterImpl implements ACK1Adapter {

    private FixUtils fixUtils;

    public ACK1AdapterImpl() {
        this.fixUtils = new FixUtilsImpl();
    }

    @Override
    public Ack1Entity adapt(ExecutionReport er) {
        Ack1Entity ack1Entity = new Ack1Entity();
        ack1Entity.setFechaInsercion(new Timestamp(new Date().getTime()));
        ack1Entity.setFixMsg(er.toString());

        StringField clOrdId = fixUtils.get(er, new ClOrdID());
        ack1Entity.setClOrdId(clOrdId.getValue());

        return ack1Entity;
    }
}
