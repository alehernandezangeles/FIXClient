package cencor.meif.fix.client.db;

import cencor.meif.fix.client.Service;
import quickfix.fix44.ExecutionReport;

/**
 * Created by mhernandez on 6/16/15.
 */
public interface InsertThread extends Service {
    void put(ExecutionReport er);
}
