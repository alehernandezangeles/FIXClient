package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.EstatusAck2Info;
import cencor.meif.fix.client.db.EstatusAck2InfoBatch;
import cencor.meif.fix.client.db.EstatusAck2InfoClassifier;

import java.util.*;

/**
 * Created by mhernandez on 6/17/15.
 */
public class EstatusAck2InfoClassifierImpl implements EstatusAck2InfoClassifier {

    private Map<Key, EstatusAck2InfoBatch> map;
    private int size;

    public EstatusAck2InfoClassifierImpl() {
        map = new HashMap<>();
    }

    @Override
    public void add(EstatusAck2Info estatusAck2Info) {
        String clOrdId = estatusAck2Info.getClOrdId();
        int estatus = estatusAck2Info.getEstatus();
        String descr = estatusAck2Info.getDescr();

        Key key = new Key(estatus, descr);
        EstatusAck2InfoBatch estatusAck2InfoBatch = map.get(key);
        if (estatusAck2InfoBatch == null) {
            List<String> clOrdIds = new ArrayList<>();
            estatusAck2InfoBatch = new EstatusAck2InfoBatch(clOrdIds, estatus, descr);
            map.put(key, estatusAck2InfoBatch);
        }
        estatusAck2InfoBatch.getClOrdIds().add(clOrdId);
        size++;
    }

    @Override
    public List<EstatusAck2InfoBatch> classify() {
        Collection<EstatusAck2InfoBatch> values = map.values();
        List<EstatusAck2InfoBatch> estatusAck2InfoBatches = new ArrayList<>(values);

        return estatusAck2InfoBatches;
    }

    @Override
    public int size() {
        return size;
    }

    private class Key {
        private int estatus;
        private String descr;

        public Key() {
        }

        public Key(int estatus, String descr) {
            this.estatus = estatus;
            this.descr = descr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (estatus != key.estatus) return false;
            return !(descr != null ? !descr.equals(key.descr) : key.descr != null);

        }

        @Override
        public int hashCode() {
            int result = estatus;
            result = 31 * result + (descr != null ? descr.hashCode() : 0);
            return result;
        }
    }

}
