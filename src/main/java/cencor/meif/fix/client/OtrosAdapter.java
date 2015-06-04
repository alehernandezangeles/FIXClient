package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.OtrosMsjFixEntity;
import quickfix.Message;

/**
 * Created by mhernandez on 6/2/15.
 */
public interface OtrosAdapter {

    String DATE_FORMAT = "yyyy-MM-dd";

    OtrosMsjFixEntity adapt(Message message);

}
