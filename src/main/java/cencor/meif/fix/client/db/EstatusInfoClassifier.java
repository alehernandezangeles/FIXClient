package cencor.meif.fix.client.db;

import java.util.List;

/**
 * Created by mhernandez on 6/15/15.
 */
public interface EstatusInfoClassifier {

    void classify(EstatusInfo estatusInfo);

    int size();

    List<String> getAck1();
    List<String> getAck2();
    List<String> getEr();
    List<String> getError();

}
