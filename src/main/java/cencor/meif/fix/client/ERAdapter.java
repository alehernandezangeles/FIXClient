package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.ErEntity;
import quickfix.fix44.ExecutionReport;

/**
 * Created by mhernandez on 6/2/15.
 */
public interface ERAdapter {

    String DATE_FORMAT = "yyyy-MM-dd";

    ErEntity adapt(ExecutionReport er);

}
