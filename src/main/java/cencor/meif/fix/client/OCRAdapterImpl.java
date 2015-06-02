package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.OcrEntity;
import quickfix.StringField;
import quickfix.field.*;
import quickfix.fix44.OrderCancelRequest;

/**
 * Created by mhernandez on 6/2/15.
 */
public class OCRAdapterImpl implements OCRAdapter {
    @Override
    public OrderCancelRequest adapt(OcrEntity ocrEntity) {
        ClOrdID clOrdID = new ClOrdID(ocrEntity.getClOrdId());
        OrigClOrdID origClOrdID = new OrigClOrdID(ocrEntity.getOrigClOrdId());
        Side side = new Side(ocrEntity.getSide().charAt(0));
        TransactTime transactTime = new TransactTime(ocrEntity.getTransactTime());
        OrderCancelRequest ocr = new OrderCancelRequest(origClOrdID, clOrdID, side, transactTime);
        ocr.setField(new OrderID(ocrEntity.getOrderId()));
        ocr.setField(new StringField(CustomTags.CVE_CONTRAPARTE, ocrEntity.getCveOperadora()));

        return ocr;
    }
}
