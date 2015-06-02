package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.*;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
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

    public ConsumerControllerImpl(Connection brokerConn) throws JMSException {
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
                    System.out.println("onMessage: " + entity);
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
                            quickfix.Session.sendToTarget(fixMessage);
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
