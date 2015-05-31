package cencor.meif.fix.client.queue.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;

/**
 * Created by alejandro on 5/31/15.
 */
public class VMTransport implements MessageListener {

    private static final String BROKER_URL = "vm://localhost";
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Destination destination;


    public VMTransport() throws JMSException {
        factory = new ActiveMQConnectionFactory(BROKER_URL);
        connection = factory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("FIX.IN");
        producer = session.createProducer(destination);
    }

    private void start() throws JMSException {
        for (int i = 0; i < 10; i++) {
            Message message = session.createObjectMessage(i);
            producer.send(destination, message);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    public static void main(String[] args) throws JMSException {
        VMTransport vmTransport = new VMTransport();
        vmTransport.start();
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("onMessage: " + ((ActiveMQObjectMessage) message).getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
