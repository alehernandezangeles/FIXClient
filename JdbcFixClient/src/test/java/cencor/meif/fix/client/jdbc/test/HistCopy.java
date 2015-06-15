package cencor.meif.fix.client.jdbc.test;

import cencor.meif.fix.client.jdbc.JdbcController;
import cencor.meif.fix.client.jdbc.JdbcControllerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by mhernandez on 6/11/15.
 */
public class HistCopy {

    private static JdbcController controller;
    private static final String DB_SOURCE="FixClientDB", DB_TARGET="FixClientHistDB";

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
    public void copyNos() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "NOS");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyOcr() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "OCR");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyEr() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "ER");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyAck1() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "ACK1");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyAck2() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "ACK2");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyErrores() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "Errores");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyOtrosMsjFix() {
        try {
            int rows = controller.copyHist(DB_SOURCE, DB_TARGET, "OtrosMsjFix");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
