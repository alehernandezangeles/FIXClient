package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.OcrEntity;
import quickfix.fix44.OrderCancelRequest;

/**
 * Created by mhernandez on 6/2/15.
 */
public interface OCRAdapter {

    String DATE_FORMAT = "yyyy-MM-dd";

    OrderCancelRequest adapt(OcrEntity ocrEntity);

}
