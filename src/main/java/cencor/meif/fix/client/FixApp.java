package cencor.meif.fix.client;

import org.apache.log4j.Logger;
import quickfix.*;
import quickfix.field.Password;
import quickfix.field.Username;
import quickfix.fix44.Logon;

/**
 * Created by mhernandez on 6/1/15.
 */
public class FixApp implements Application {

    private static Logger logger = Logger.getLogger(FixApp.class);
    private boolean connected;
    private SessionID sessionID;

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
        if (message instanceof Logon) {
            String user = FixClientSvcImpl.fixClientProps.getProperty("fix.user");
            String pwd = FixClientSvcImpl.fixClientProps.getProperty("fix.pwd");
            Logon logon = (Logon) message;
            logon.set(new Username(user));
            logon.set(new Password(pwd));
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {

    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

    }

    public boolean isConnected() {
        return connected;
    }

    public SessionID getSessionID() {
        return sessionID;
    }
}
