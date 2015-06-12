package cencor.meif.fix.client.db;

import cencor.meif.fix.client.ErroresAdapter;
import cencor.meif.fix.client.ErroresAdapterImpl;
import cencor.meif.fix.client.FixClientSvcImpl;
import cencor.meif.fix.client.Service;
import cencor.meif.fix.client.queue.ProducerController;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mhernandez on 6/1/15.
 */
public class NewMsgObserver implements Observer, Service {

    private static Logger logger = Logger.getLogger(NewMsgObserver.class);

    private NewMsgObservable newMsgObservable;
    private DBController dbController;
    private ProducerController producerController;
    private ErroresAdapter erroresAdapter;

    /**
     * Crea un observador de nuevos mensajes
     * @param dbController Objeto para hacer consultas a la BD
     * @param producerController
     */
    public NewMsgObserver(DBController dbController, ProducerController producerController) {
        this.dbController = dbController;
        this.producerController = producerController;
        this.newMsgObservable = new NewMsgObservable(dbController);
        this.newMsgObservable.addObserver(this);
        this.erroresAdapter = new ErroresAdapterImpl();
    }

    /**
     * Callback when new order arrives in DB
     * @param o Object that polls DB
     * @param newOrders List of new orders
     */
    @Override
    public void update(Observable o, Object newOrders) {
        List<Serializable> entities = (List<Serializable>) newOrders;
        for (final Serializable entity : entities) {
            logger.info("Starting with " + entity);
            try {
                // enviar a la cola de nuevas peticiones
                producerController.putReq(entity);
            } catch (JMSException e) {
                String errorMsg = "Error al poner mensaje en la cola " + FixClientSvcImpl.REQ_QUEUE_NAME + ". Mensaje: " + entity;
                logger.error(errorMsg, e);
                dbController.createErrorUpdateEstatus(errorMsg, e, entity);
            } catch (Exception e) {
                String errorMsg = "Error al pesistir en BD: " + entity;
                logger.error(errorMsg, e);
                dbController.createErrorUpdateEstatus(errorMsg, e, entity);
            }
        }
    }

    /**
     * Start polling DB
     */
    @Override
    public void start() {
        newMsgObservable.start();
    }
}
