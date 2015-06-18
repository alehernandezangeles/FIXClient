package cencor.meif.fix.client.db;

/**
 * Created by mhernandez on 6/17/15.
 */
public class EstatusAck2Info {

    private String clOrdId;
    private int estatus;
    private String descr;

    public EstatusAck2Info(String clOrdId, int estatus, String descr) {
        this.clOrdId = clOrdId;
        this.estatus = estatus;
        this.descr = descr;
    }

    public String getClOrdId() {
        return clOrdId;
    }

    public int getEstatus() {
        return estatus;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return "EstatusAck2Info{" +
                "clOrdId='" + clOrdId + '\'' +
                ", estatus=" + estatus +
                ", descr='" + descr + '\'' +
                '}';
    }
}
