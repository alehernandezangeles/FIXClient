package cencor.meif.fix.client.queue;

import org.apache.activemq.command.ActiveMQObjectMessage;

import java.io.Serializable;

/**
 * Created by mhernandez on 6/16/15.
 */
public interface QueueUtils {

    Serializable getObject(ActiveMQObjectMessage message);

}
