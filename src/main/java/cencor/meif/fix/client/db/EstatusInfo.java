package cencor.meif.fix.client.db;

/**
 * Created by mhernandez on 6/12/15.
 */
public class EstatusInfo {

    public static int NOS_ENTITY = 1, OCR_ENTITY = 2;

    private int entityType = -1;
    private String clOrdId;
    private int estatus, estatusAck2;
    private String descrEstatusAck2;
    private boolean persisted;

    public EstatusInfo(String clOrdId, int estatus) {
        this.clOrdId = clOrdId;
        this.estatus = estatus;
    }

    public EstatusInfo(int entityType, String clOrdId, int estatus) {
        this.entityType = entityType;
        this.clOrdId = clOrdId;
        this.estatus = estatus;
    }

    public EstatusInfo(int entityType, String clOrdId, int estatus, int estatusAck2, String descrEstatusAck2) {
        this.entityType = entityType;
        this.clOrdId = clOrdId;
        this.estatus = estatus;
        this.estatusAck2 = estatusAck2;
        this.descrEstatusAck2 = descrEstatusAck2;
    }

    public EstatusInfo(String clOrdId, int estatus, int estatusAck2, String descrEstatusAck2) {
        this.entityType = entityType;
        this.clOrdId = clOrdId;
        this.estatus = estatus;
        this.estatusAck2 = estatusAck2;
        this.descrEstatusAck2 = descrEstatusAck2;
    }

    public synchronized int getEntityType() {
        return entityType;
    }

    public synchronized void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public synchronized String getClOrdId() {
        return clOrdId;
    }

    public synchronized void setClOrdId(String clOrdId) {
        this.clOrdId = clOrdId;
    }

    public synchronized int getEstatus() {
        return estatus;
    }

    public synchronized void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public synchronized int getEstatusAck2() {
        return estatusAck2;
    }

    public synchronized void setEstatusAck2(int estatusAck2) {
        this.estatusAck2 = estatusAck2;
    }

    public synchronized String getDescrEstatusAck2() {
        return descrEstatusAck2;
    }

    public synchronized void setDescrEstatusAck2(String descrEstatusAck2) {
        this.descrEstatusAck2 = descrEstatusAck2;
    }

    public synchronized boolean isPersisted() {
        return persisted;
    }

    public synchronized void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstatusInfo that = (EstatusInfo) o;

        return clOrdId.equals(that.clOrdId);

    }

    @Override
    public synchronized int hashCode() {
        return clOrdId.hashCode();
    }


    @Override
    public synchronized String toString() {
        return "EstatusInfo{" +
                "entityType=" + entityType +
                ", clOrdId='" + clOrdId + '\'' +
                ", estatus=" + estatus +
                ", estatusAck2=" + estatusAck2 +
                ", descrEstatusAck2='" + descrEstatusAck2 + '\'' +
                ", persisted=" + persisted +
                '}';
    }
}
