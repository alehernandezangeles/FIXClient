package cencor.meif.fix.client;

import cencor.meif.fix.client.jpa.entities.NosEntity;
import quickfix.DecimalField;
import quickfix.DoubleField;
import quickfix.StringField;
import quickfix.UtcDateOnlyField;
import quickfix.field.*;
import quickfix.fix44.NewOrderSingle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by mhernandez on 6/2/15.
 */
public class NOSAdapterImpl implements NOSAdapter {
    @Override
    public NewOrderSingle adapt(NosEntity nosEntity) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        ClOrdID clOrdID = new ClOrdID(nosEntity.getClOrdId());
        TransactTime transactTime = new TransactTime(nosEntity.getTransactTime());
        OrdType ordType = new OrdType(nosEntity.getOrdType().charAt(0));
        Side side = new Side(nosEntity.getSide().charAt(0));
        NewOrderSingle nos = new NewOrderSingle(clOrdID, side, transactTime, ordType);

        // Número de contrato
        nos.setField(new Account(nosEntity.getAccount()));
        nos.setField(new Currency(nosEntity.getCurrency()));
        nos.setField(new Price(nosEntity.getPrice()));
        nos.setField(new Quantity(nosEntity.getQuantity()));
        nos.setField(new Symbol(nosEntity.getSymbol()));
        nos.setField(new SettlType(nosEntity.getSettlType()));
        nos.setField(new SettlDate(df.format(nosEntity.getSettlDate())));
        nos.setField(new TradeDate(df.format(nosEntity.getTradeDate())));
        nos.setField(new AllocQty(nosEntity.getAllocQty()));
        nos.setField(new SecurityType(nosEntity.getSecurityType()));
        nos.setField(new GrossTradeAmt(nosEntity.getGrossTradeAmt()));

        // Custom fields
        nos.setField(new StringField(CustomTags.NUM_ASESOR_DIST, nosEntity.getNumAsesorDist()));
        nos.setField(new DecimalField(CustomTags.PORCENTAJE_COMISION_DIST, nosEntity.getPorcentajeComisionDist()));
        nos.setField(new StringField(CustomTags.CVE_CONTRAPARTE, nosEntity.getCveOperadora()));
        nos.setField(new StringField(CustomTags.CVE_ORIGEN, nosEntity.getCveOrigen().toString()));
        nos.setField(new DoubleField(CustomTags.IMPORTE_SOLICITADO, nosEntity.getImporteSolicitado()));

        // Uso futuro
        // ISIN
        nos.setField(new SecurityIDSource(nosEntity.getSecurityIdSource()));
        nos.setField(new SecurityID(nosEntity.getSecurityId()));
        nos.setField(new DoubleField(CustomTags.COMISION, nosEntity.getComision()));
        nos.setField(new DoubleField(CustomTags.IVA, nosEntity.getIva()));
        nos.setField(new StringField(CustomTags.NUM_CONTRATO_OPER, nosEntity.getContratoOper()));

        UtcDateOnlyField fecSolicitud = new UtcDateOnlyField(CustomTags.FEC_SOLICITUD);
        fecSolicitud.setValue(nosEntity.getFechaSolicitud());
        nos.setField(fecSolicitud);

        return nos;
    }

}
