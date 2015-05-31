package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.DBClient;
import cencor.meif.fix.client.queue.ConsumerController;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;

/**
 * Created by alejandro on 5/31/15.
 */
public class ConsumerControllerImpl implements ConsumerController {

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private Destination destination;

    private MessageListener messageListener;

    public ConsumerControllerImpl(Connection brokerConn) throws JMSException {
        this.connection = brokerConn;
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.destination = session.createQueue(DBClient.REQ_QUEUE_NAME);
        this.consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("onMessage: " + ((ActiveMQObjectMessage) message).getObject());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
