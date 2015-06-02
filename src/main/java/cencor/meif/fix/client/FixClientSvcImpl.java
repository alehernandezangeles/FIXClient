package cencor.meif.fix.client;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.NewMsgObserver;
import cencor.meif.fix.client.db.impl.DBControllerImpl;
import cencor.meif.fix.client.queue.ProducerController;
import cencor.meif.fix.client.queue.impl.ConsumerControllerImpl;
import cencor.meif.fix.client.queue.impl.ProducerControllerImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import quickfix.*;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by alejandro on 5/30/15.
 */
public class FixClientSvcImpl implements Service {

    public static final String BROKER_URL = "vm://localhost";
    public static final String REQ_QUEUE_NAME = "FIX.IN";
    public static final String RESP_QUEUE_NAME = "FIX.OUT";
    public static final String FIX_CFG_FILE = "DistFixConn.cfg";
    public static final String PROPS_FILE = "fixclient.properties";

    private static Logger loggger = Logger.getLogger(FixClientSvcImpl.class);

    public static Properties fixClientProps = new Properties();

    // DB related
    private DBController dbController;
    private NewMsgObserver newMsgObserver;

    // Broker related
    private ProducerController producerController;
    private Service consumerController;
    private Connection brokerConn;

    // FIX related
    private SocketInitiator socketInitiator;
    private FixApp fixApp;

    public FixClientSvcImpl() throws JMSException, IOException, ConfigError {
        initResources();
    }

    private void initResources() throws JMSException, IOException, ConfigError {
        initFixClient();
        dbController = new DBControllerImpl();

        ConnectionFactory brokerCF = new ActiveMQConnectionFactory(BROKER_URL);
        brokerConn = brokerCF.createConnection();
        brokerConn.start();
        producerController = new ProducerControllerImpl(brokerConn);
        consumerController = new ConsumerControllerImpl(brokerConn);

        newMsgObserver = new NewMsgObserver(dbController, producerController);
    }

    private void initFixClient() throws IOException, ConfigError {
        fixApp = new FixApp();
        InputStream is = new FileInputStream(FIX_CFG_FILE);
        SessionSettings sessionSettings = new SessionSettings(is);
        is.close();

        socketInitiator = new SocketInitiator(fixApp, new MemoryStoreFactory(), sessionSettings, new ScreenLogFactory(), new DefaultMessageFactory());
    }

    /**
     * Starts fix client service
     */
    public void start() {
        try {
            socketInitiator.start();
            while (!fixApp.isConnected()) {
                try { Thread.sleep(100); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        } catch (ConfigError configError) {
            loggger.error("Error al conectarse con el servidor FIX", configError);
        }
        // start observing for new orders
        newMsgObserver.start();

        // start listening for new messages in queue
        consumerController.start();
    }

    public static void main(String[] args) throws IOException, JMSException, ConfigError {
        fixClientProps.load(new FileInputStream(PROPS_FILE));

        Service fixClientSvc = new FixClientSvcImpl();
        fixClientSvc.start();
    }

}