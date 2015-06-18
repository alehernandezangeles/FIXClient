package cencor.meif.fix.client.db;

import java.util.List;

/**
 * Created by mhernandez on 6/17/15.
 */
public class EstatusAck2InfoBatch {

    private List<String> clOrdIds;
    private int estatus;
    private String descr;

    public EstatusAck2InfoBatch(List<String> clOrdIds, int estatus, String descr) {
        this.clOrdIds = clOrdIds;
        this.estatus = estatus;
        this.descr = descr;
    }

    public List<String> getClOrdIds() {
        return clOrdIds;
    }

    public int getEstatus() {
        return estatus;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return "EstatusAck2InfoBatch{" +
                "clOrdIds='" + clOrdIds + '\'' +
                ", estatus=" + estatus +
                ", descr='" + descr + '\'' +
                '}';
    }
}
