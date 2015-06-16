package cencor.meif.fix.client.db;

import cencor.meif.fix.client.Service;
import quickfix.Message;

/**
 * Created by mhernandez on 6/16/15.
 */
public interface InsertFixMsgThread extends Service {
    void put(Message msg);
}
