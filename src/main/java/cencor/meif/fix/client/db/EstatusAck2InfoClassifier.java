package cencor.meif.fix.client.db;

import java.util.List;

/**
 * Created by mhernandez on 6/15/15.
 */
public interface EstatusAck2InfoClassifier {

    void add(EstatusAck2Info estatusAck2Info);
    List<EstatusAck2InfoBatch> classify();

    int size();

}
