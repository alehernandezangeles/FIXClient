package cencor.meif.fix.client;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.impl.DBControllerImpl;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.queue.ConsumerController;
import cencor.meif.fix.client.queue.ProducerController;
import cencor.meif.fix.client.queue.impl.ConsumerControllerImpl;
import cencor.meif.fix.client.queue.impl.ProducerControllerImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by alejandro on 5/30/15.
 */
public class DBClientImpl implements DBClient {


    private static Logger log = Logger.getLogger(DBClientImpl.class);

    public static Properties fixClientProps = new Properties();

    private DBController dbController;
    private ProducerController producerController;
    private ConsumerController consumerController;
    private Connection brokerConn;

    public DBClientImpl() throws JMSException {
        dbController = new DBControllerImpl();

        ConnectionFactory brokerCF = new ActiveMQConnectionFactory(BROKER_URL);
        brokerConn = brokerCF.createConnection();
        brokerConn.start();
        producerController = new ProducerControllerImpl(brokerConn);
        consumerController = new ConsumerControllerImpl(brokerConn);
    }

    public void start() throws JMSException {
        while (true) {
            List<NosEntity> nosEntities = dbController.getNewNos();
            for (NosEntity nosEntity : nosEntities) {
                producerController.put(nosEntity);
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) throws IOException, JMSException {
        fixClientProps.load(new FileInputStream("fixclient.properties"));

        DBClient dbClient = new DBClientImpl();
        dbClient.start();
    }

}