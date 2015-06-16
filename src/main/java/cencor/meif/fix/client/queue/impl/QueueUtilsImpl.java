package cencor.meif.fix.client.queue.impl;

import cencor.meif.fix.client.queue.QueueUtils;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Created by mhernandez on 6/16/15.
 */
public class QueueUtilsImpl implements QueueUtils {

    private static Logger logger = Logger.getLogger(QueueUtilsImpl.class);

    @Override
    public Serializable getObject(ActiveMQObjectMessage message) {
        Serializable entity = null;
        try {
            entity = message.getObject();
        } catch (JMSException e) {
            logger.error("Error converting message to object. Message: " + message, e);
        }
        return entity;
    }
}
