package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.Ack2Entity;
import quickfix.fix44.ExecutionReport;

/**
 * Created by mhernandez on 6/2/15.
 */
public interface ACK2Adapter {

    String DATE_FORMAT = "yyyy-MM-dd";

    Ack2Entity adapt(ExecutionReport er);

}
