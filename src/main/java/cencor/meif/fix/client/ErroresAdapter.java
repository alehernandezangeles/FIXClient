package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.ErroresEntity;
import quickfix.Message;

/**
 * Created by mhernandez on 6/2/15.
 */
public interface ErroresAdapter {

    ErroresEntity adapt(Throwable t);
    ErroresEntity adapt(Message fixMsg, Throwable t);
    ErroresEntity adapt(String errorMsg, Throwable t);
    ErroresEntity adapt(String errorMsg, Message fixMsg, Throwable t);

}
