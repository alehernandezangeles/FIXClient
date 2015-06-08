package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.Ack2Entity;
import quickfix.CharField;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.field.OrdStatus;
import quickfix.fix44.ExecutionReport;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mhernandez on 6/3/15.
 */
public class ACK2AdapterImpl implements ACK2Adapter {


    private FixUtils fixUtils;

    public ACK2AdapterImpl() {
        this.fixUtils = new FixUtilsImpl();
    }

    @Override
    public Ack2Entity adapt(ExecutionReport er) {
        Ack2Entity ack2Entity = new Ack2Entity();
        ack2Entity.setFechaInsercion(new Timestamp(new Date().getTime()));
        ack2Entity.setFixMsg(er.toString());

        StringField clOrdId = fixUtils.get(er, new ClOrdID());
        ack2Entity.setClOrdId(clOrdId.getValue());

        StringField motivoRechazo = fixUtils.get(er, new StringField(CustomTags.MOTIVOS_RECHAZO));
        if (motivoRechazo != null) {
            ack2Entity.setMensajeError(motivoRechazo.getValue());
        }

        Long valido = -1l;
        CharField ordStatus = fixUtils.get(er, new OrdStatus());
        if (ordStatus.getValue() == OrdStatus.FILLED) {
            valido = 1l;
        } else if (ordStatus.getValue() == OrdStatus.REJECTED) {
            valido = 0l;
        }
        ack2Entity.setValido(valido);

        return ack2Entity;
    }
}
