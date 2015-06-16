package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.EstatusInfo;
import cencor.meif.fix.client.db.EstatusInfoClassifier;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhernandez on 6/15/15.
 */
public class EstatusInfoClassifierImpl implements EstatusInfoClassifier {

    private List<String> ack1s;
    private List<String> ack2s;
    private List<String> ers;
    private List<String> errors;

    public EstatusInfoClassifierImpl() {
        ack1s = new ArrayList<>();
        ack2s = new ArrayList<>();
        ers = new ArrayList<>();
        errors = new ArrayList<>();
    }

    @Override
    public void classify(EstatusInfo estatusInfo) {
        switch (estatusInfo.getEstatus()) {
            case CatEstatusEntity.ACK1: ack1s.add(estatusInfo.getClOrdId()); break;
            case CatEstatusEntity.ACK2: ack2s.add(estatusInfo.getClOrdId()); break;
            case CatEstatusEntity.ER: ers.add(estatusInfo.getClOrdId()); break;
            case CatEstatusEntity.ERROR: errors.add(estatusInfo.getClOrdId()); break;
        }
    }

    @Override
    public int size() {
        int size = ack1s.size() + ack2s.size() + ers.size() + errors.size();

        return size;
    }

    @Override
    public List<String> getAck1() {
        return ack1s;
    }

    @Override
    public List<String> getAck2() {
        return ack2s;
    }

    @Override
    public List<String> getEr() {
        return ers;
    }

    @Override
    public List<String> getError() {
        return errors;
    }
}
