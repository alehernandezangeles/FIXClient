package cencor.meif.fix.client.jpa.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mhernandez on 5/29/15.
 */
@Entity
@javax.persistence.Table(name = "OCR", schema = "", catalog = "FixClientDB")
public class OcrEntity implements Serializable {
    private Long id;

    @Id
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

    private Integer estatusAck2;

    @Basic
    @Column(name = "EstatusAck2")
    public Integer getEstatusAck2() {
        return estatusAck2;
    }

    public void setEstatusAck2(Integer estatusAck2) {
        this.estatusAck2 = estatusAck2;
    }

    private String mensajeEstatusAck2;

    @Basic
    @Column(name = "MensajeEstatusAck2")
    public String getMensajeEstatusAck2() {
        return mensajeEstatusAck2;
    }

    public void setMensajeEstatusAck2(String mensajeEstatus) {
        this.mensajeEstatusAck2 = mensajeEstatus;
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

    private String orderId;

    @Basic
    @javax.persistence.Column(name = "OrderID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    private Timestamp transactTime;

    @Basic
    @javax.persistence.Column(name = "TransactTime")
    public Timestamp getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(Timestamp transactTime) {
        this.transactTime = transactTime;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OcrEntity ocrEntity = (OcrEntity) o;

        if (id != null ? !id.equals(ocrEntity.id) : ocrEntity.id != null) return false;
        if (estatus != null ? !estatus.equals(ocrEntity.estatus) : ocrEntity.estatus != null) return false;
        if (clOrdId != null ? !clOrdId.equals(ocrEntity.clOrdId) : ocrEntity.clOrdId != null) return false;
        if (orderId != null ? !orderId.equals(ocrEntity.orderId) : ocrEntity.orderId != null) return false;
        if (origClOrdId != null ? !origClOrdId.equals(ocrEntity.origClOrdId) : ocrEntity.origClOrdId != null)
            return false;
        if (side != null ? !side.equals(ocrEntity.side) : ocrEntity.side != null) return false;
        if (transactTime != null ? !transactTime.equals(ocrEntity.transactTime) : ocrEntity.transactTime != null)
            return false;
        if (cveOperadora != null ? !cveOperadora.equals(ocrEntity.cveOperadora) : ocrEntity.cveOperadora != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (estatus != null ? estatus.hashCode() : 0);
        result = 31 * result + (clOrdId != null ? clOrdId.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (origClOrdId != null ? origClOrdId.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (transactTime != null ? transactTime.hashCode() : 0);
        result = 31 * result + (cveOperadora != null ? cveOperadora.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "OcrEntity{" +
                "id=" + id +
                ", estatus=" + estatus +
                ", clOrdId='" + clOrdId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", origClOrdId='" + origClOrdId + '\'' +
                ", side='" + side + '\'' +
                ", transactTime=" + transactTime +
                ", cveOperadora='" + cveOperadora + '\'' +
                '}';
    }
}
