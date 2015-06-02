package cencor.meif.fix.client.jpa.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by mhernandez on 5/29/15.
 */
@Entity
@javax.persistence.Table(name = "NOS", schema = "", catalog = "FixClientDB")
public class NosEntity implements Serializable {
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

    private String account;

    @Basic
    @javax.persistence.Column(name = "Account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    private String currency;

    @Basic
    @javax.persistence.Column(name = "Currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String securityIdSource;

    @Basic
    @javax.persistence.Column(name = "SecurityIDSource")
    public String getSecurityIdSource() {
        return securityIdSource;
    }

    public void setSecurityIdSource(String securityIdSource) {
        this.securityIdSource = securityIdSource;
    }

    private String ordType;

    @Basic
    @javax.persistence.Column(name = "OrdType")
    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    private Double price;

    @Basic
    @javax.persistence.Column(name = "Price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private String securityId;

    @Basic
    @javax.persistence.Column(name = "SecurityID")
    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    private Integer quantity;

    @Basic
    @javax.persistence.Column(name = "Quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    private String settlType;

    @Basic
    @javax.persistence.Column(name = "SettlType")
    public String getSettlType() {
        return settlType;
    }

    public void setSettlType(String settlType) {
        this.settlType = settlType;
    }

    private Date settlDate;

    @Basic
    @javax.persistence.Column(name = "SettlDate")
    public Date getSettlDate() {
        return settlDate;
    }

    public void setSettlDate(Date settlDate) {
        this.settlDate = settlDate;
    }

    private Date tradeDate;

    @Basic
    @javax.persistence.Column(name = "TradeDate")
    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    private Integer allocQty;

    @Basic
    @javax.persistence.Column(name = "AllocQty")
    public Integer getAllocQty() {
        return allocQty;
    }

    public void setAllocQty(Integer allocQty) {
        this.allocQty = allocQty;
    }

    private String securityType;

    @Basic
    @javax.persistence.Column(name = "SecurityType")
    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
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

    private String numAsesorDist;

    @Basic
    @javax.persistence.Column(name = "NumAsesorDist")
    public String getNumAsesorDist() {
        return numAsesorDist;
    }

    public void setNumAsesorDist(String numAsesorDist) {
        this.numAsesorDist = numAsesorDist;
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

    private String cveOperadora;

    @Basic
    @javax.persistence.Column(name = "CveOperadora")
    public String getCveOperadora() {
        return cveOperadora;
    }

    public void setCveOperadora(String cveOperadora) {
        this.cveOperadora = cveOperadora;
    }

    private Integer cveOrigen;

    @Basic
    @javax.persistence.Column(name = "CveOrigen")
    public Integer getCveOrigen() {
        return cveOrigen;
    }

    public void setCveOrigen(Integer cveOrigen) {
        this.cveOrigen = cveOrigen;
    }

    private Double importeSolicitado;

    @Basic
    @javax.persistence.Column(name = "ImporteSolicitado")
    public Double getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImporteSolicitado(Double importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
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

    private Date fechaSolicitud;

    @Basic
    @javax.persistence.Column(name = "FechaSolicitud")
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    private String contratoOper;

    @Basic
    @javax.persistence.Column(name = "ContratoOper")
    public String getContratoOper() {
        return contratoOper;
    }

    public void setContratoOper(String contratoOper) {
        this.contratoOper = contratoOper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NosEntity nosEntity = (NosEntity) o;

        if (id != null ? !id.equals(nosEntity.id) : nosEntity.id != null) return false;
        if (estatus != null ? !estatus.equals(nosEntity.estatus) : nosEntity.estatus != null) return false;
        if (account != null ? !account.equals(nosEntity.account) : nosEntity.account != null) return false;
        if (clOrdId != null ? !clOrdId.equals(nosEntity.clOrdId) : nosEntity.clOrdId != null) return false;
        if (currency != null ? !currency.equals(nosEntity.currency) : nosEntity.currency != null) return false;
        if (securityIdSource != null ? !securityIdSource.equals(nosEntity.securityIdSource) : nosEntity.securityIdSource != null)
            return false;
        if (ordType != null ? !ordType.equals(nosEntity.ordType) : nosEntity.ordType != null) return false;
        if (price != null ? !price.equals(nosEntity.price) : nosEntity.price != null) return false;
        if (securityId != null ? !securityId.equals(nosEntity.securityId) : nosEntity.securityId != null) return false;
        if (quantity != null ? !quantity.equals(nosEntity.quantity) : nosEntity.quantity != null) return false;
        if (side != null ? !side.equals(nosEntity.side) : nosEntity.side != null) return false;
        if (symbol != null ? !symbol.equals(nosEntity.symbol) : nosEntity.symbol != null) return false;
        if (transactTime != null ? !transactTime.equals(nosEntity.transactTime) : nosEntity.transactTime != null)
            return false;
        if (settlType != null ? !settlType.equals(nosEntity.settlType) : nosEntity.settlType != null) return false;
        if (settlDate != null ? !settlDate.equals(nosEntity.settlDate) : nosEntity.settlDate != null) return false;
        if (tradeDate != null ? !tradeDate.equals(nosEntity.tradeDate) : nosEntity.tradeDate != null) return false;
        if (allocQty != null ? !allocQty.equals(nosEntity.allocQty) : nosEntity.allocQty != null) return false;
        if (securityType != null ? !securityType.equals(nosEntity.securityType) : nosEntity.securityType != null)
            return false;
        if (grossTradeAmt != null ? !grossTradeAmt.equals(nosEntity.grossTradeAmt) : nosEntity.grossTradeAmt != null)
            return false;
        if (numAsesorDist != null ? !numAsesorDist.equals(nosEntity.numAsesorDist) : nosEntity.numAsesorDist != null)
            return false;
        if (porcentajeComisionDist != null ? !porcentajeComisionDist.equals(nosEntity.porcentajeComisionDist) : nosEntity.porcentajeComisionDist != null)
            return false;
        if (cveOperadora != null ? !cveOperadora.equals(nosEntity.cveOperadora) : nosEntity.cveOperadora != null)
            return false;
        if (cveOrigen != null ? !cveOrigen.equals(nosEntity.cveOrigen) : nosEntity.cveOrigen != null) return false;
        if (importeSolicitado != null ? !importeSolicitado.equals(nosEntity.importeSolicitado) : nosEntity.importeSolicitado != null)
            return false;
        if (comision != null ? !comision.equals(nosEntity.comision) : nosEntity.comision != null) return false;
        if (iva != null ? !iva.equals(nosEntity.iva) : nosEntity.iva != null) return false;
        if (fechaSolicitud != null ? !fechaSolicitud.equals(nosEntity.fechaSolicitud) : nosEntity.fechaSolicitud != null)
            return false;
        if (contratoOper != null ? !contratoOper.equals(nosEntity.contratoOper) : nosEntity.contratoOper != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (estatus != null ? estatus.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (clOrdId != null ? clOrdId.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (securityIdSource != null ? securityIdSource.hashCode() : 0);
        result = 31 * result + (ordType != null ? ordType.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (securityId != null ? securityId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (transactTime != null ? transactTime.hashCode() : 0);
        result = 31 * result + (settlType != null ? settlType.hashCode() : 0);
        result = 31 * result + (settlDate != null ? settlDate.hashCode() : 0);
        result = 31 * result + (tradeDate != null ? tradeDate.hashCode() : 0);
        result = 31 * result + (allocQty != null ? allocQty.hashCode() : 0);
        result = 31 * result + (securityType != null ? securityType.hashCode() : 0);
        result = 31 * result + (grossTradeAmt != null ? grossTradeAmt.hashCode() : 0);
        result = 31 * result + (numAsesorDist != null ? numAsesorDist.hashCode() : 0);
        result = 31 * result + (porcentajeComisionDist != null ? porcentajeComisionDist.hashCode() : 0);
        result = 31 * result + (cveOperadora != null ? cveOperadora.hashCode() : 0);
        result = 31 * result + (cveOrigen != null ? cveOrigen.hashCode() : 0);
        result = 31 * result + (importeSolicitado != null ? importeSolicitado.hashCode() : 0);
        result = 31 * result + (comision != null ? comision.hashCode() : 0);
        result = 31 * result + (iva != null ? iva.hashCode() : 0);
        result = 31 * result + (fechaSolicitud != null ? fechaSolicitud.hashCode() : 0);
        result = 31 * result + (contratoOper != null ? contratoOper.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NosEntity{" +
                "id=" + id +
                ", estatus=" + estatus +
                ", account='" + account + '\'' +
                ", clOrdId='" + clOrdId + '\'' +
                ", currency='" + currency + '\'' +
                ", securityIdSource='" + securityIdSource + '\'' +
                ", ordType='" + ordType + '\'' +
                ", price=" + price +
                ", securityId='" + securityId + '\'' +
                ", quantity=" + quantity +
                ", side=" + side +
                ", symbol='" + symbol + '\'' +
                ", transactTime=" + transactTime +
                ", settlType='" + settlType + '\'' +
                ", settlDate=" + settlDate +
                ", tradeDate=" + tradeDate +
                ", allocQty=" + allocQty +
                ", securityType='" + securityType + '\'' +
                ", grossTradeAmt=" + grossTradeAmt +
                ", numAsesorDist='" + numAsesorDist + '\'' +
                ", porcentajeComisionDist=" + porcentajeComisionDist +
                ", cveOperadora='" + cveOperadora + '\'' +
                ", cveOrigen=" + cveOrigen +
                ", importeSolicitado=" + importeSolicitado +
                ", comision=" + comision +
                ", iva=" + iva +
                ", fechaSolicitud=" + fechaSolicitud +
                ", contratoOper='" + contratoOper + '\'' +
                '}';
    }
}
