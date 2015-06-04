package cencor.meif.fix.client;

import quickfix.*;

/**
 * Created by mhernandez on 6/3/15.
 */
public interface FixUtils {

    StringField get(Message message, StringField stringField);
    CharField get(Message message, CharField stringField);
    DoubleField get(Message message, DoubleField doubleField);
    UtcTimeStampField get(Message message, UtcTimeStampField utcTimeStampField);

}
