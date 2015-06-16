package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.*;
import cencor.meif.fix.client.db.*;
import cencor.meif.fix.client.jpa.entities.Ack2Entity;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import cencor.meif.fix.client.queue.QueueUtils;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import quickfix.CharField;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.field.ClOrdID;
import quickfix.field.OrdStatus;
import quickfix.fix44.ExecutionReport;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by alejandro on 5/31/15.
 */
public class ConsumerControllerImpl implements Service {
    private static Logger logger = Logger.getLogger(ConsumerControllerImpl.class);

    private Connection connection;
    private Session session;
    private QueueUtils queueUtils;

    private MessageConsumer reqConsumer;
    private Destination reqDestination;
    private MessageConsumer respConsumer;
    private Destination respDestination;

    private NOSAdapter nosAdapter;
    private OCRAdapter ocrAdapter;
    private OtrosAdapter otrosAdapter;
    private ACK1Adapter ack1Adapter;
    private ACK2Adapter ack2Adapter;
    private ERAdapter erAdapter;

    private MessageListener messageListener;
    private FixApp fixApp;
    private DBController dbController;
    private UpdateStatusDemon updateStatusDemon;
    private InsertErThread erInsertThread;
    private InsertErThread ack1InsertThread;
    private InsertErThread ack2InsertThread;
    private InsertFixMsgThread otrosMsgFixThread;

    private FixUtils fixUtils;

    public ConsumerControllerImpl(Connection brokerConn, FixApp fixApp, DBController dbController, UpdateStatusDemon updateStatusDemon, InsertErThread erInsertThread, InsertErThread ack1InsertThread, InsertErThread ack2InsertThread, InsertFixMsgThread otrosMsgFixThread) throws JMSException {
        this.fixApp = fixApp;
        this.dbController = dbController;
        this.updateStatusDemon = updateStatusDemon;
        this.erInsertThread = erInsertThread;
        this.ack1InsertThread = ack1InsertThread;
        this.ack2InsertThread = ack2InsertThread;
        this.otrosMsgFixThread = otrosMsgFixThread;
        this.nosAdapter = new NOSAdapterImpl();
        this.ocrAdapter = new OCRAdapterImpl();
        this.otrosAdapter = new OtrosAdapterImpl();
        this.ack1Adapter = new ACK1AdapterImpl();
        this.ack2Adapter = new ACK2AdapterImpl();
        this.erAdapter = new ERAdapterImpl();

        this.connection = brokerConn;
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.queueUtils = new QueueUtilsImpl();

        this.reqDestination = session.createQueue(FixClientSvcImpl.REQ_QUEUE_NAME);
        this.reqConsumer = session.createConsumer(reqDestination);

        this.respDestination = session.createQueue(FixClientSvcImpl.RESP_QUEUE_NAME);
        this.respConsumer = session.createConsumer(respDestination);

        this.fixUtils = new FixUtilsImpl();
    }

    @Override
    public void start() {
        try {
            reqConsumer.setMessageListener(new ReqListener());
            respConsumer.setMessageListener(new RespListener());
        } catch (JMSException e) {
            logger.error("Error attaching message listener to queue " + FixClientSvcImpl.REQ_QUEUE_NAME, e);
        }
    }

    private class RespListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            quickfix.Message fixMessage = null;
            Serializable msgObj = ConsumerControllerImpl.this.queueUtils.getObject((ActiveMQObjectMessage) message);
            if (msgObj != null) {
                if (msgObj instanceof quickfix.fix44.Message) {
                    fixMessage = (quickfix.Message) msgObj;
                    otrosMsgFixThread.put(fixMessage);

                    if (fixMessage instanceof ExecutionReport) {
                        final ExecutionReport er = (ExecutionReport) fixMessage;
                        CharField ordStatus = fixUtils.get(fixMessage, new OrdStatus());
                        String clOrdId = fixUtils.get(fixMessage, new ClOrdID()).getValue();
                        if (ordStatus != null) {
                            if (ordStatus.getValue() == OrdStatus.PENDING_NEW || ordStatus.getValue() == OrdStatus.PENDING_CANCEL) { // ACK1
                                ack1InsertThread.put(er);
                                ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(clOrdId, CatEstatusEntity.ACK1));
                            } else if (ordStatus.getValue() == OrdStatus.FILLED || ordStatus.getValue() == OrdStatus.REJECTED) { // ACK2
                                ack2InsertThread.put(er);

                                final Ack2Entity ack2Entity = ConsumerControllerImpl.this.ack2Adapter.adapt(er);
                                int estatusAck2 = ack2Entity.getValido().intValue();
                                String descrAck2 = ack2Entity.getMensajeError();
                                ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(clOrdId, CatEstatusEntity.ACK2, estatusAck2, descrAck2));
                            } else if (ordStatus.getValue() == OrdStatus.NEW || ordStatus.getValue() == OrdStatus.CANCELED) { // ER
                                erInsertThread.put(er);
                                ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(clOrdId, CatEstatusEntity.ER));
                            }
                        }
                    }
                }
            }
        }
    }

    private class ReqListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            quickfix.fix44.Message fixMessage = null;
            Serializable entity = ConsumerControllerImpl.this.queueUtils.getObject((ActiveMQObjectMessage) message);
            if (entity != null) {
                if (entity instanceof NosEntity) {
                    NosEntity nosEntity = (NosEntity) entity;
                    fixMessage = nosAdapter.adapt(nosEntity);
                } else if (entity instanceof OcrEntity) {
                    OcrEntity ocrEntity = (OcrEntity) entity;
                    fixMessage = ocrAdapter.adapt(ocrEntity);
                }
            }
            if (fixMessage != null) {
                try {
                    SessionID sessionID = fixApp.getSessionID();
                    quickfix.Session.sendToTarget(fixMessage, sessionID);
                    logger.info("Sent to fix: " + fixMessage);

                    if (entity instanceof NosEntity) {
                        NosEntity nosEntity = (NosEntity) entity;
                        String clOrdId = nosEntity.getClOrdId();
                        ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(EstatusInfo.NOS_ENTITY, clOrdId, CatEstatusEntity.ENVIADO_A_MEIF));
                    } else if (entity instanceof OcrEntity) {
                        OcrEntity ocrEntity = (OcrEntity) entity;
                        String clOrdId = ocrEntity.getClOrdId();
                        ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(EstatusInfo.OCR_ENTITY, clOrdId, CatEstatusEntity.ENVIADO_A_MEIF));
                    }
                } catch (SessionNotFound sessionNotFound) {
                    String errorMsg = "Error al enviar mensaje fix: " + fixMessage;
                    logger.error(errorMsg, sessionNotFound);
                    dbController.createErrorUpdateEstatus(errorMsg, sessionNotFound, fixMessage);
                }
            }
        }
    }
}