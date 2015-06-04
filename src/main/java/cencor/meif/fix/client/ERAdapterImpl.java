package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.ErEntity;
import quickfix.CharField;
import quickfix.DoubleField;
import quickfix.StringField;
import quickfix.UtcTimeStampField;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;

import java.sql.Timestamp;

/**
 * Created by mhernandez on 6/3/15.
 */
public class ERAdapterImpl implements ERAdapter {

    private FixUtils fixUtils;

    public ERAdapterImpl() {
        this.fixUtils = new FixUtilsImpl();
    }

    @Override
    public ErEntity adapt(ExecutionReport er) {
        DoubleField avgPx = fixUtils.get(er, new AvgPx());
        StringField clOrdId = fixUtils.get(er, new ClOrdID());
        DoubleField cumQty = fixUtils.get(er, new CumQty());
        StringField orderId = fixUtils.get(er, new OrderID());
        CharField ordStatus = fixUtils.get(er, new OrdStatus());
        StringField origClOrdID = fixUtils.get(er, new OrigClOrdID());
        CharField side = fixUtils.get(er, new Side());
        StringField symbol = fixUtils.get(er, new Symbol());
        UtcTimeStampField transactTime = fixUtils.get(er, new TransactTime());
        CharField execType = fixUtils.get(er, new ExecType());
        DoubleField netMoney = fixUtils.get(er, new NetMoney());
        DoubleField grossTradeAmt = fixUtils.get(er, new GrossTradeAmt());
        DoubleField pctComisDist = fixUtils.get(er, new DoubleField(CustomTags.PORCENTAJE_COMISION_DIST));
        StringField cveDistOrig = fixUtils.get(er, new StringField(CustomTags.CVE_CONTRAPARTE));
        DoubleField comision = fixUtils.get(er, new DoubleField(CustomTags.COMISION));
        DoubleField iva = fixUtils.get(er, new DoubleField(CustomTags.IVA));
        StringField origFolioMEI = fixUtils.get(er, new StringField(CustomTags.ORIG_FOLIO_MEI));
        StringField motivoRechazo = fixUtils.get(er, new StringField(CustomTags.MOTIVOS_RECHAZO));
        UtcTimeStampField fecHoraCaptOper = fixUtils.get(er, new UtcTimeStampField(CustomTags.FECHA_HORA_CAPTURA_OPER));

        ErEntity erEntity = new ErEntity();
        erEntity.setAvgPrice(avgPx.getValue());
        erEntity.setClOrdId(clOrdId.getValue());
        erEntity.setComision(comision.getValue());
        erEntity.setCumQty((int) cumQty.getValue());
        erEntity.setCveDistribuidoraOrig(cveDistOrig.getValue());
        erEntity.setExecType(execType.getValue() + "");
        if (fecHoraCaptOper != null) { erEntity.setFechaHoraCapturaOper(new Timestamp(fecHoraCaptOper.getValue().getTime())); }
        erEntity.setGrossTradeAmt(grossTradeAmt.getValue());
        if (iva != null) { erEntity.setIva(iva.getValue()); }
        if (motivoRechazo != null) { erEntity.setMotivosRechazo(motivoRechazo.getValue()); }
        erEntity.setNetMoney(netMoney.getValue());
        erEntity.setOrderId(orderId.getValue());
        erEntity.setOrdStatus(ordStatus.getValue() + "");
        if (origClOrdID != null) { erEntity.setOrigClOrdId(origClOrdID.getValue()); }
        if (origFolioMEI != null) { erEntity.setOrigFolioMei(origFolioMEI.getValue()); }
        erEntity.setPorcentajeComisionDist(pctComisDist.getValue());
        erEntity.setSide(side.getValue() + "");
        erEntity.setSymbol(symbol.getValue());
        erEntity.setTransactTime(new Timestamp(transactTime.getValue().getTime()));

        return erEntity;
    }
}
