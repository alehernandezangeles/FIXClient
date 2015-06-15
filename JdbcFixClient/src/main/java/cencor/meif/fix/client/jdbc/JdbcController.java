package cencor.meif.fix.client.jdbc;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mhernandez on 6/11/15.
 */
public interface JdbcController {

    int updateStatusNos(String clOrdId, int estatus) throws SQLException;
    int updateStatusNos(List<String> clOrdIdList, int estatus) throws SQLException;
    int updateStatusOcr(String clOrdId, int estatus) throws SQLException;
    int updateStatusOcr(List<String> clOrdIdList, int estatus) throws SQLException;

    void moveToHist();
    int copyHist(String dbSource, String dbtarget, String tblName) throws SQLException;
    int deleteHist(String dbName, String tblName) throws SQLException;
}
