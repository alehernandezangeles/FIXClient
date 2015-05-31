package cencor.meif.fix.client;

import javax.jms.JMSException;

/**
 * Created by alejandro on 5/31/15.
 */
public interface DBClient {

    String BROKER_URL = "vm://localhost";
    String REQ_QUEUE_NAME = "FIX.IN";

    void start() throws JMSException;

}
