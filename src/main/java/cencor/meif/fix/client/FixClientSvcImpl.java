package cencor.meif.fix.client;

import cencor.meif.fix.client.db.*;
import cencor.meif.fix.client.db.impl.*;
import cencor.meif.fix.client.queue.ProducerController;
import cencor.meif.fix.client.queue.impl.ConsumerControllerImpl;
import cencor.meif.fix.client.queue.impl.ProducerControllerImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import quickfix.*;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alejandro on 5/30/15.
 */
public class FixClientSvcImpl implements Service {

    public static final String BROKER_URL = "vm://localhost";
    public static final String REQ_QUEUE_NAME = "FIX.IN";
    public static final String RESP_QUEUE_NAME = "FIX.OUT";
    public static final String FIX_CFG_FILE = "Meif_fix.cfg";
    public static final String FIX_SESSION_USER = "FixSessionUser";
    private static final String FIX_SESSION_PWD = "FixSessionPwd";

    private static Logger logger = Logger.getLogger(FixClientSvcImpl.class);


    // DB related
    private DBController dbController;
    private NewMsgObserver newMsgObserver;
    private UpdateStatusDemon updateStatusDemon;
    private MoveToHistServiceImpl moveToHistService;

    // Broker related
    private ProducerController producerController;
    private Service consumerController;
    private InsertErThread erInsertThread, ack1InsertThread, ack2InsertThread;
    private InsertFixMsgThread otrosMsgFixThread;
    private Connection brokerConn;

    // FIX related
    private SocketInitiator socketInitiator;
    private FixApp fixApp;

    public FixClientSvcImpl(String fixCfgFile) throws JMSException, IOException, ConfigError {
        initResources(fixCfgFile);
    }

    private void initResources(String fixCfgFile) throws JMSException, IOException, ConfigError {
        initFixClient(fixCfgFile);
        dbController = new DBControllerImpl();
        updateStatusDemon = new UpdateStatusDemonImpl(dbController);
        moveToHistService = new MoveToHistServiceImpl(dbController);

        ConnectionFactory brokerCF = new ActiveMQConnectionFactory(BROKER_URL);
        brokerConn = brokerCF.createConnection();
        brokerConn.start();
        producerController = new ProducerControllerImpl(brokerConn);
        erInsertThread = new ErInsertThreadImpl(dbController);
        ack1InsertThread = new Ack1InsertThreadImpl(dbController);
        ack2InsertThread = new Ack2InsertThreadImpl(dbController);
        otrosMsgFixThread = new InsertFixMsgThreadImpl(dbController);

        fixApp.setProducerController(producerController);
        consumerController = new ConsumerControllerImpl(brokerConn, fixApp, dbController, updateStatusDemon, erInsertThread, ack1InsertThread, ack2InsertThread, otrosMsgFixThread);

        newMsgObserver = new NewMsgObserver(dbController, producerController);
    }

    private void initFixClient(String fixCfgFile) throws IOException, ConfigError {
        File fixCfgFileObj = new File(fixCfgFile);
        logger.info("Using fix cfg file " + fixCfgFileObj.getAbsolutePath());
        InputStream is = new FileInputStream(fixCfgFileObj);
        SessionSettings sessionSettings = new SessionSettings(is);
        is.close();

        FixUtils fixUtils = new FixUtilsImpl();
        String fixUser = fixUtils.getCfgFileParam(sessionSettings, FIX_SESSION_USER);
        String fixPwd = fixUtils.getCfgFileParam(sessionSettings, FIX_SESSION_PWD);
        fixApp = new FixApp(fixUser, fixPwd);

        socketInitiator = new SocketInitiator(fixApp, new MemoryStoreFactory(), sessionSettings, new ScreenLogFactory(), new DefaultMessageFactory());
    }

    /**
     * Starts fix client service
     */
    public void start() {
        // Connect to MEIF
        startFixConn();

        updateStatusDemon.start();
        moveToHistService.start();

        // start listening for new messages in queue
        consumerController.start();
        erInsertThread.start();
        ack1InsertThread.start();
        ack2InsertThread.start();
        otrosMsgFixThread.start();

        // start observing for new orders
        newMsgObserver.start();

    }

    private void startFixConn() {
        try {
            socketInitiator.start();
            while (!fixApp.isConnected()) {
                try { Thread.sleep(100); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        } catch (ConfigError configError) {
            logger.error("Error al conectarse con el servidor FIX", configError);
        }
    }

    public static void main(String[] args) throws IOException, JMSException, ConfigError {
        String fixCfgFile = FIX_CFG_FILE;
        if (args.length > 1) {
            System.out.println("Usage: FixClientSvcImpl [Meif_fix.cfg]");
            System.exit(0);
        } else if (args.length == 1) {
            fixCfgFile = args[0];
        }

        Service fixClientSvc = new FixClientSvcImpl(fixCfgFile);
        fixClientSvc.start();
    }

}