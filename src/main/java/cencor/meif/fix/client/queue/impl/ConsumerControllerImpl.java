package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.*;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.EstatusInfo;
import cencor.meif.fix.client.db.UpdateStatusDemon;
import cencor.meif.fix.client.jpa.entities.Ack2Entity;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
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

    private FixUtils fixUtils;

    public ConsumerControllerImpl(Connection brokerConn, FixApp fixApp, DBController dbController, UpdateStatusDemon updateStatusDemon) throws JMSException {
        this.fixApp = fixApp;
        this.dbController = dbController;
        this.updateStatusDemon = updateStatusDemon;
        this.nosAdapter = new NOSAdapterImpl();
        this.ocrAdapter = new OCRAdapterImpl();
        this.otrosAdapter = new OtrosAdapterImpl();
        this.ack1Adapter = new ACK1AdapterImpl();
        this.ack2Adapter = new ACK2AdapterImpl();
        this.erAdapter = new ERAdapterImpl();

        this.connection = brokerConn;
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

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
            Serializable msgObj = getObject((ActiveMQObjectMessage) message);
            if (msgObj != null) {
                if (msgObj instanceof quickfix.fix44.Message) {
                    fixMessage = (quickfix.Message) msgObj;

                    final quickfix.Message finalFixMessage = fixMessage;
/*
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OtrosMsjFixEntity otrosMsjFixEntity = otrosAdapter.adapt(finalFixMessage);
                            try {
                                dbController.createOtrosMsjFix(otrosMsjFixEntity);
                            } catch (Exception e) {
                                logger.error("Error al pesistir en BD: " + otrosMsjFixEntity, e);
                            }
                        }
                    }).start();
*/

                    if (fixMessage instanceof ExecutionReport) {
                        final ExecutionReport er = (ExecutionReport) fixMessage;
                        CharField ordStatus = fixUtils.get(fixMessage, new OrdStatus());
                        String clOrdId = fixUtils.get(fixMessage, new ClOrdID()).getValue();
                        if (ordStatus != null) {
                            if (ordStatus.getValue() == OrdStatus.PENDING_NEW || ordStatus.getValue() == OrdStatus.PENDING_CANCEL) { // ACK1
                                final quickfix.Message finalFixMessage2 = fixMessage;
/*
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Ack1Entity ack1Entity = ConsumerControllerImpl.this.ack1Adapter.adapt(er);
                                        try {
                                            dbController.createAck1(ack1Entity);
                                        } catch (Exception e) {
                                            String errorMsg = "Error al pesistir en BD: " + ack1Entity;
                                            logger.error(errorMsg, e);
                                            dbController.createErrorUpdateEstatus(errorMsg, e, finalFixMessage2);
                                        }
                                    }
                                }).start();
*/
                                ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(clOrdId, CatEstatusEntity.ACK1));
                            } else if (ordStatus.getValue() == OrdStatus.FILLED || ordStatus.getValue() == OrdStatus.REJECTED) { // ACK2
                                final Ack2Entity ack2Entity = ConsumerControllerImpl.this.ack2Adapter.adapt(er);
                                final quickfix.Message finalFixMessage3 = fixMessage;
/*
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            dbController.createAck2(ack2Entity);
                                        } catch (Exception e) {
                                            String errorMsg = "Error al pesistir en BD: " + ack2Entity;
                                            logger.error(errorMsg, e);
                                            dbController.createErrorUpdateEstatus(errorMsg, e, finalFixMessage3);
                                        }
                                    }
                                }).start();
*/
                                int estatusAck2 = ack2Entity.getValido().intValue();
                                String descrAck2 = ack2Entity.getMensajeError();
                                ConsumerControllerImpl.this.updateStatusDemon.add(new EstatusInfo(clOrdId, CatEstatusEntity.ACK2, estatusAck2, descrAck2));
                            } else if (ordStatus.getValue() == OrdStatus.NEW || ordStatus.getValue() == OrdStatus.CANCELED) { // ER
                                final quickfix.Message finalFixMessage1 = fixMessage;
/*
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ErEntity erEntity = erAdapter.adapt(er);
                                        try {
                                            dbController.createEr(erEntity);
                                        } catch (Exception e) {
                                            String errorMsg = "Error al pesistir en BD: " + erEntity;
                                            logger.error(errorMsg, e);
                                            dbController.createErrorUpdateEstatus(errorMsg, e, finalFixMessage1);
                                        }
                                    }
                                }).start();
*/
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
            Serializable entity = getObject((ActiveMQObjectMessage) message);
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

    private Serializable getObject(ActiveMQObjectMessage message) {
        Serializable entity = null;
        try {
            entity = message.getObject();
        } catch (JMSException e) {
            logger.error("Error retrieving message from queue " + FixClientSvcImpl.REQ_QUEUE_NAME, e);
        }
        return entity;
    }
}