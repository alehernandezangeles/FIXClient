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

    public static int getNosEntity() {
        return NOS_ENTITY;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public static int getOcrEntity() {
        return OCR_ENTITY;
    }

    public int getEntityType() {
        return entityType;
    }

    public String getClOrdId() {
        return clOrdId;
    }

    public int getEstatus() {
        return estatus;
    }

    public int getEstatusAck2() {
        return estatusAck2;
    }

    public String getDescrEstatusAck2() {
        return descrEstatusAck2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstatusInfo that = (EstatusInfo) o;

        return clOrdId.equals(that.clOrdId);

    }

    @Override
    public int hashCode() {
        return clOrdId.hashCode();
    }

    @Override
    public String toString() {
        return "EstatusInfo{" +
                "entityType=" + entityType +
                ", clOrdId='" + clOrdId + '\'' +
                ", estatus=" + estatus +
                ", estatusAck2=" + estatusAck2 +
                ", descrEstatusAck2='" + descrEstatusAck2 + '\'' +
                '}';
    }
}
