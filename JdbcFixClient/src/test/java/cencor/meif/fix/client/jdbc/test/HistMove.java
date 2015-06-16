package cencor.meif.fix.client.jdbc.test;

import cencor.meif.fix.client.jdbc.JdbcController;
import cencor.meif.fix.client.jdbc.JdbcControllerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mhernandez on 6/11/15.
 */
public class HistMove {

    private static JdbcController controller;
    private static final String DB_SOURCE ="FixClientDB", DB_TARGET="FixClientHistDB";
    private static List<String> TBL_NAMES = Arrays.asList("ACK1", "ACK2", "ER", "Errores", "NOS", "OCR", "OtrosMsjFix");

    @BeforeClass
    public static void init() {
        try {
            controller = new JdbcControllerImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveHist() {
        controller.moveData(DB_SOURCE, DB_TARGET, TBL_NAMES);
    }
}
