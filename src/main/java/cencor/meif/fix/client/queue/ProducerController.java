package cencor.meif.fix.client.queue;

import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Created by alejandro on 5/31/15.
 */
public interface ProducerController {

    String BROKER_URL = "vm://FIX_BROKER";

    void put(Serializable obj) throws JMSException;

}
