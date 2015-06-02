package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.FixClientSvcImpl;
import cencor.meif.fix.client.queue.ProducerController;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by alejandro on 5/31/15.
 */
public class ProducerControllerImpl implements ProducerController {

    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Destination destination;

    public ProducerControllerImpl(Connection brokerConn) throws JMSException {
        this.connection = brokerConn;
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.destination = session.createQueue(FixClientSvcImpl.REQ_QUEUE_NAME);
        this.producer = session.createProducer(destination);

    }

    @Override
    public void put(Serializable obj) throws JMSException {
        Message message = session.createObjectMessage(obj);
        producer.send(destination, message);
    }
}
