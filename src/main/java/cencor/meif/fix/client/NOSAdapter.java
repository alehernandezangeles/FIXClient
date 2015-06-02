package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.NosEntity;
import quickfix.fix44.NewOrderSingle;

/**
 * Created by mhernandez on 6/2/15.
 */
public interface NOSAdapter {

    String DATE_FORMAT = "yyyy-MM-dd";

    NewOrderSingle adapt(NosEntity nosEntity);

}
