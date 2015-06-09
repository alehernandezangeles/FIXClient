package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.ErroresEntity;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.StringField;
import quickfix.field.ClOrdID;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mhernandez on 6/9/15.
 */
public class ErroresAdapterImpl implements ErroresAdapter {
    @Override
    public ErroresEntity adapt(Throwable t) {
        ErroresEntity entity = new ErroresEntity();
        entity.setFechaInsercion(new Timestamp(new Date().getTime()));
        String stacktrace = getStacktrace(t);
        entity.setMensajeError(stacktrace);

        return entity;
    }

    @Override
    public ErroresEntity adapt(Message fixMsg, Throwable t) {
        ErroresEntity entity = adapt(t);
        StringField clOrdId = getClOrdId(fixMsg);
        if (clOrdId != null) {
            entity.setClOrdId(clOrdId.getValue());
        }

        return entity;
    }

    @Override
    public ErroresEntity adapt(String errorMsg, Throwable t) {
        ErroresEntity entity = new ErroresEntity();
        String stacktrace = getStacktrace(t);
        String msg = errorMsg + ": " + stacktrace;

        entity.setFechaInsercion(new Timestamp(new Date().getTime()));
        entity.setMensajeError(msg);

        return entity;
    }

    @Override
    public ErroresEntity adapt(String errorMsg, Message fixMsg, Throwable t) {
        ErroresEntity entity = adapt(errorMsg, t);
        StringField clOrdId = getClOrdId(fixMsg);
        if (clOrdId != null) {
            entity.setClOrdId(clOrdId.getValue());
        }
        return entity;
    }

    private String getStacktrace(Throwable t) {
        ByteOutputStream bos = new ByteOutputStream();
        PrintWriter pw = new PrintWriter(bos);
        t.printStackTrace(pw);

        String stacktrace = new String(bos.getBytes());
        return stacktrace;
    }

    private StringField getClOrdId(Message fixMsg) {
        StringField clOrdId = null;
        if (fixMsg.isSetField(ClOrdID.FIELD)) {
            try {
                clOrdId = fixMsg.getField(new StringField(ClOrdID.FIELD));
            } catch (FieldNotFound fieldNotFound) {
                fieldNotFound.printStackTrace();
            }
        }
        return clOrdId;
    }
}
