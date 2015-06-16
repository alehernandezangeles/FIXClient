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
public class HistDelete {

    private static JdbcController controller;
    private static final String DB_NAME ="FixClientDB";

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
    public void deleteNos() {
        try {
            int rows = controller.deleteData(DB_NAME, "NOS");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteOcr() {
        try {
            int rows = controller.deleteData(DB_NAME, "OCR");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteEr() {
        try {
            int rows = controller.deleteData(DB_NAME, "ER");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAck1() {
        try {
            int rows = controller.deleteData(DB_NAME, "ACK1");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAck2() {
        try {
            int rows = controller.deleteData(DB_NAME, "ACK2");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteErrores() {
        try {
            int rows = controller.deleteData(DB_NAME, "Errores");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteOtrosMsjFix() {
        try {
            int rows = controller.deleteData(DB_NAME, "OtrosMsjFix");
            System.out.println("Rows: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
