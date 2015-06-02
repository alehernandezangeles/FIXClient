package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.*;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import quickfix.SessionID;
import quickfix.SessionNotFound;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by alejandro on 5/31/15.
 */
public class ConsumerControllerImpl implements Service {
    private static Logger logger = Logger.getLogger(ConsumerControllerImpl.class);

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private Destination destination;
    private NOSAdapter nosAdapter;
    private OCRAdapter ocrAdapter;

    private MessageListener messageListener;
    private FixApp fixApp;
    private DBController dbController;

    public ConsumerControllerImpl(Connection brokerConn, FixApp fixApp, DBController dbController) throws JMSException {
        this.fixApp = fixApp;
        this.dbController = dbController;
        this.nosAdapter = new NOSAdapterImpl();
        this.ocrAdapter = new OCRAdapterImpl();

        this.connection = brokerConn;
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.destination = session.createQueue(FixClientSvcImpl.REQ_QUEUE_NAME);
        this.consumer = session.createConsumer(destination);
    }

    @Override
    public void start() {
        try {
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    Serializable entity = null;
                    quickfix.fix44.Message fixMessage = null;
                    try {
                        entity = ((ActiveMQObjectMessage) message).getObject();
                    } catch (JMSException e) {
                        logger.error("Error retrieving message from queue " + FixClientSvcImpl.REQ_QUEUE_NAME, e);
                    }
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

                            if (entity instanceof NosEntity) {
                                NosEntity nosEntity = (NosEntity) entity;
                                nosEntity.setEstatus(CatEstatusEntity.ENVIADO_A_MEIF);
                                try {
                                    dbController.editNos(nosEntity);
                                } catch (Exception e) {
                                    logger.error("Error al pesistir en BD: " + entity, e);
                                }
                            } else if (entity instanceof OcrEntity) {
                                // TODO Implementar
                            }
                        } catch (SessionNotFound sessionNotFound) {
                            logger.error("Error al enviar mensaje fix: " + fixMessage, sessionNotFound);
                        }
                    }
                }
            });
        } catch (JMSException e) {
            logger.error("Error attaching message listener to queue " + FixClientSvcImpl.REQ_QUEUE_NAME, e);
        }
    }
}
