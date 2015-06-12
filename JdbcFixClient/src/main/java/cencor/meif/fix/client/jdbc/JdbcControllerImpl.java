package cencor.meif.fix.client.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by mhernandez on 6/11/15.
 */
public class JdbcControllerImpl implements JdbcController {
    public static final String UPDATE_NOS_SET_ESTATUS = "UPDATE NOS SET Estatus = ? WHERE ClOrdID = ?";
    public static final String UPDATE_OCR_SET_ESTATUS = "UPDATE OCR SET Estatus = ? WHERE ClOrdID = ?";

    private Connection conn;
    private PreparedStatement pstmtUpdateStatusNos;
    private PreparedStatement pstmtUpdateStatusOcr;

    public JdbcControllerImpl() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException, SQLException {
        Properties props = new Properties();
        props.load(new FileInputStream("jdbc.properties"));
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pwd = props.getProperty("db.pwd");

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        this.conn = DriverManager.getConnection(url, user, pwd);
        this.pstmtUpdateStatusNos = conn.prepareStatement(UPDATE_NOS_SET_ESTATUS);
        this.pstmtUpdateStatusOcr = conn.prepareStatement(UPDATE_OCR_SET_ESTATUS);
    }

    @Override
    public int updateStatusNos(String clOrdId, int estatus) throws SQLException {
        this.pstmtUpdateStatusNos.setInt(1, estatus);
        this.pstmtUpdateStatusNos.setString(2, clOrdId);
        int rowsUpdated = this.pstmtUpdateStatusNos.executeUpdate();

        return rowsUpdated;
    }

    @Override
    public int updateStatusNos(List<String> clOrdIdList, int estatus) throws SQLException {
        final String NOS_TABLE = "NOS";
        int rows = updateStatus(NOS_TABLE, clOrdIdList, estatus);

        return rows;
    }

    @Override
    public int updateStatusOcr(String clOrdId, int estatus) throws SQLException {
        this.pstmtUpdateStatusOcr.setInt(1, estatus);
        this.pstmtUpdateStatusOcr.setString(2, clOrdId);
        int rowsUpdated = this.pstmtUpdateStatusOcr.executeUpdate();

        return rowsUpdated;
    }

    @Override
    public int updateStatusOcr(List<String> clOrdIdList, int estatus) throws SQLException {
        final String OCR_TABLE = "OCR";
        int rows = updateStatus(OCR_TABLE, clOrdIdList, estatus);

        return rows;
    }

    private int updateStatus(String nomTable, List<String> clOrdIdList, int estatus) throws SQLException {
        if (clOrdIdList.size() <= 0) {
            return 0;
        }

        StringBuilder sb = new StringBuilder("UPDATE " + nomTable + " SET Estatus = ? WHERE ClOrdID IN ( ");
        for (int i = 0; i < clOrdIdList.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(")");
        String updateStr = sb.toString();
        PreparedStatement pstmt = this.conn.prepareStatement(updateStr);
        pstmt.setInt(1, estatus);
        int count = 2;
        for (String clOrdId : clOrdIdList) {
            pstmt.setString(count, clOrdId);
            count++;
        }
        int rows = pstmt.executeUpdate();

        return rows;
    }
}
