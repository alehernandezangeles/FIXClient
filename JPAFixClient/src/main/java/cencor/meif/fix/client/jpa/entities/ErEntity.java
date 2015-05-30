package cencor.meif.fix.client.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by alejandro on 5/30/15.
 */
@Entity
@javax.persistence.Table(name = "ER", schema = "", catalog = "FixClientDB")
public class ErEntity implements Serializable {
    private Long id;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Integer estatus;

    @Basic
    @javax.persistence.Column(name = "Estatus")
    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    private Double avgPrice;

    @Basic
    @javax.persistence.Column(name = "AvgPrice")
    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    private String clOrdId;

    @Basic
    @javax.persistence.Column(name = "ClOrdID")
    public String getClOrdId() {
        return clOrdId;
    }

    public void setClOrdId(String clOrdId) {
        this.clOrdId = clOrdId;
    }

    private Integer cumQty;

    @Basic
    @javax.persistence.Column(name = "CumQty")
    public Integer getCumQty() {
        return cumQty;
    }

    public void setCumQty(Integer cumQty) {
        this.cumQty = cumQty;
    }

    private String orderId;

    @Basic
    @javax.persistence.Column(name = "OrderID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String ordStatus;

    @Basic
    @javax.persistence.Column(name = "OrdStatus")
    public String getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus = ordStatus;
    }

    private String origClOrdId;

    @Basic
    @javax.persistence.Column(name = "OrigClOrdID")
    public String getOrigClOrdId() {
        return origClOrdId;
    }

    public void setOrigClOrdId(String origClOrdId) {
        this.origClOrdId = origClOrdId;
    }

    private String side;

    @Basic
    @javax.persistence.Column(name = "Side")
    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    private String symbol;

    @Basic
    @javax.persistence.Column(name = "Symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private Timestamp transactTime;

    @Basic
    @javax.persistence.Column(name = "TransactTime")
    public Timestamp getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(Timestamp transactTime) {
        this.transactTime = transactTime;
    }

    private String execType;

    @Basic
    @javax.persistence.Column(name = "ExecType")
    public String getExecType() {
        return execType;
    }

    public void setExecType(String execType) {
        this.execType = execType;
    }

    private Double netMoney;

    @Basic
    @javax.persistence.Column(name = "NetMoney")
    public Double getNetMoney() {
        return netMoney;
    }

    public void setNetMoney(Double netMoney) {
        this.netMoney = netMoney;
    }

    private Double grossTradeAmt;

    @Basic
    @javax.persistence.Column(name = "GrossTradeAmt")
    public Double getGrossTradeAmt() {
        return grossTradeAmt;
    }

    public void setGrossTradeAmt(Double grossTradeAmt) {
        this.grossTradeAmt = grossTradeAmt;
    }

    private Double porcentajeComisionDist;

    @Basic
    @javax.persistence.Column(name = "PorcentajeComisionDist")
    public Double getPorcentajeComisionDist() {
        return porcentajeComisionDist;
    }

    public void setPorcentajeComisionDist(Double porcentajeComisionDist) {
        this.porcentajeComisionDist = porcentajeComisionDist;
    }

    private String cveDistribuidoraOrig;

    @Basic
    @javax.persistence.Column(name = "CveDistribuidoraOrig")
    public String getCveDistribuidoraOrig() {
        return cveDistribuidoraOrig;
    }

    public void setCveDistribuidoraOrig(String cveDistribuidoraOrig) {
        this.cveDistribuidoraOrig = cveDistribuidoraOrig;
    }

    private Double comision;

    @Basic
    @javax.persistence.Column(name = "Comision")
    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    private Double iva;

    @Basic
    @javax.persistence.Column(name = "IVA")
    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    private String origFolioMei;

    @Basic
    @javax.persistence.Column(name = "OrigFolioMEI")
    public String getOrigFolioMei() {
        return origFolioMei;
    }

    public void setOrigFolioMei(String origFolioMei) {
        this.origFolioMei = origFolioMei;
    }

    private String motivosRechazo;

    @Basic
    @javax.persistence.Column(name = "MotivosRechazo")
    public String getMotivosRechazo() {
        return motivosRechazo;
    }

    public void setMotivosRechazo(String motivosRechazo) {
        this.motivosRechazo = motivosRechazo;
    }

    private Timestamp fechaHoraCapturaOper;

    @Basic
    @javax.persistence.Column(name = "FechaHoraCapturaOper")
    public Timestamp getFechaHoraCapturaOper() {
        return fechaHoraCapturaOper;
    }

    public void setFechaHoraCapturaOper(Timestamp fechaHoraCapturaOper) {
        this.fechaHoraCapturaOper = fechaHoraCapturaOper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErEntity erEntity = (ErEntity) o;

        if (id != null ? !id.equals(erEntity.id) : erEntity.id != null) return false;
        if (estatus != null ? !estatus.equals(erEntity.estatus) : erEntity.estatus != null) return false;
        if (avgPrice != null ? !avgPrice.equals(erEntity.avgPrice) : erEntity.avgPrice != null) return false;
        if (clOrdId != null ? !clOrdId.equals(erEntity.clOrdId) : erEntity.clOrdId != null) return false;
        if (cumQty != null ? !cumQty.equals(erEntity.cumQty) : erEntity.cumQty != null) return false;
        if (orderId != null ? !orderId.equals(erEntity.orderId) : erEntity.orderId != null) return false;
        if (ordStatus != null ? !ordStatus.equals(erEntity.ordStatus) : erEntity.ordStatus != null) return false;
        if (origClOrdId != null ? !origClOrdId.equals(erEntity.origClOrdId) : erEntity.origClOrdId != null)
            return false;
        if (side != null ? !side.equals(erEntity.side) : erEntity.side != null) return false;
        if (symbol != null ? !symbol.equals(erEntity.symbol) : erEntity.symbol != null) return false;
        if (transactTime != null ? !transactTime.equals(erEntity.transactTime) : erEntity.transactTime != null)
            return false;
        if (execType != null ? !execType.equals(erEntity.execType) : erEntity.execType != null) return false;
        if (netMoney != null ? !netMoney.equals(erEntity.netMoney) : erEntity.netMoney != null) return false;
        if (grossTradeAmt != null ? !grossTradeAmt.equals(erEntity.grossTradeAmt) : erEntity.grossTradeAmt != null)
            return false;
        if (porcentajeComisionDist != null ? !porcentajeComisionDist.equals(erEntity.porcentajeComisionDist) : erEntity.porcentajeComisionDist != null)
            return false;
        if (cveDistribuidoraOrig != null ? !cveDistribuidoraOrig.equals(erEntity.cveDistribuidoraOrig) : erEntity.cveDistribuidoraOrig != null)
            return false;
        if (comision != null ? !comision.equals(erEntity.comision) : erEntity.comision != null) return false;
        if (iva != null ? !iva.equals(erEntity.iva) : erEntity.iva != null) return false;
        if (origFolioMei != null ? !origFolioMei.equals(erEntity.origFolioMei) : erEntity.origFolioMei != null)
            return false;
        if (motivosRechazo != null ? !motivosRechazo.equals(erEntity.motivosRechazo) : erEntity.motivosRechazo != null)
            return false;
        if (fechaHoraCapturaOper != null ? !fechaHoraCapturaOper.equals(erEntity.fechaHoraCapturaOper) : erEntity.fechaHoraCapturaOper != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (estatus != null ? estatus.hashCode() : 0);
        result = 31 * result + (avgPrice != null ? avgPrice.hashCode() : 0);
        result = 31 * result + (clOrdId != null ? clOrdId.hashCode() : 0);
        result = 31 * result + (cumQty != null ? cumQty.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (ordStatus != null ? ordStatus.hashCode() : 0);
        result = 31 * result + (origClOrdId != null ? origClOrdId.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (transactTime != null ? transactTime.hashCode() : 0);
        result = 31 * result + (execType != null ? execType.hashCode() : 0);
        result = 31 * result + (netMoney != null ? netMoney.hashCode() : 0);
        result = 31 * result + (grossTradeAmt != null ? grossTradeAmt.hashCode() : 0);
        result = 31 * result + (porcentajeComisionDist != null ? porcentajeComisionDist.hashCode() : 0);
        result = 31 * result + (cveDistribuidoraOrig != null ? cveDistribuidoraOrig.hashCode() : 0);
        result = 31 * result + (comision != null ? comision.hashCode() : 0);
        result = 31 * result + (iva != null ? iva.hashCode() : 0);
        result = 31 * result + (origFolioMei != null ? origFolioMei.hashCode() : 0);
        result = 31 * result + (motivosRechazo != null ? motivosRechazo.hashCode() : 0);
        result = 31 * result + (fechaHoraCapturaOper != null ? fechaHoraCapturaOper.hashCode() : 0);
        return result;
    }
}
