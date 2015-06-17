package cencor.meif.fix.client;

import cencor.meif.fix.client.queue.ProducerController;
import org.apache.log4j.Logger;
import quickfix.*;
import quickfix.field.Password;
import quickfix.field.Username;
import quickfix.fix44.Logon;

import javax.jms.JMSException;

/**
 * Created by mhernandez on 6/1/15.
 */
public class FixApp implements Application {

    private static Logger logger = Logger.getLogger(FixApp.class);
    private boolean connected;
    private SessionID sessionID;
    private ProducerController producerController;
    private String fixUser;
    private String fixPwd;

    public FixApp(String fixUser, String fixPwd) {

        this.fixUser = fixUser;
        this.fixPwd = fixPwd;
    }

    @Override
    public void onCreate(SessionID sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public void onLogon(SessionID sessionID) {
        connected = true;
    }

    @Override
    public void onLogout(SessionID sessionID) {
        connected = false;
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        sendToQueue(message);
        if (message instanceof Logon) {
            String user = this.fixUser;
            String pwd = this.fixPwd;
            Logon logon = (Logon) message;
            logon.set(new Username(user));
            logon.set(new Password(pwd));
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        sendToQueue(message);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        sendToQueue(message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        sendToQueue(message);
    }

    private void sendToQueue(Message message) {
        try {
            producerController.putResp(message);
        } catch (JMSException e) {
            logger.error("Error al poner mensaje en la cola " + FixClientSvcImpl.RESP_QUEUE_NAME + ". Mensaje: " + message, e);
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public SessionID getSessionID() {
        return sessionID;
    }

    public ProducerController getProducerController() {
        return producerController;
    }

    public void setProducerController(ProducerController producerController) {
        this.producerController = producerController;
    }
}
