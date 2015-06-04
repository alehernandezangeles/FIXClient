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

    private MessageProducer reqProducer;
    private Destination reqDestination;

    private MessageProducer respProducer;
    private Destination respDestination;

    public ProducerControllerImpl(Connection brokerConn) throws JMSException {
        this.connection = brokerConn;
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.reqDestination = session.createQueue(FixClientSvcImpl.REQ_QUEUE_NAME);
        this.reqProducer = session.createProducer(reqDestination);

        this.respDestination = session.createQueue(FixClientSvcImpl.RESP_QUEUE_NAME);
        this.respProducer = session.createProducer(respDestination);
    }

    @Override
    public void putReq(Serializable obj) throws JMSException {
        Message message = session.createObjectMessage(obj);
        reqProducer.send(reqDestination, message);
    }

    @Override
    public void putResp(quickfix.Message fixMsg) throws JMSException {
        Message message = session.createObjectMessage(fixMsg);
        respProducer.send(respDestination, message);
    }
}
