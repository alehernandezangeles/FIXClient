package cencor.meif.fix.client.jdbc.test;

import cencor.meif.fix.client.jdbc.JdbcController;
import cencor.meif.fix.client.jdbc.JdbcControllerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by mhernandez on 6/11/15.
 */
public class Nos {

    private static JdbcController controller;

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
    public void changeStatus() {
        String clOrdId = "Id1433873123464";
        int estatus = 1, rows = 0;
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < 100; i++) {
                rows += controller.updateStatusNos(clOrdId, estatus);
            }
            long t2 = new Date().getTime();
            System.out.println(t2 - t1);
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeStatusBatch() {
        String clOrdId = "Id1433873123464";
        List<String> clOrdIdList = Arrays.asList("Id1433873021906", "Id1433873029801", "Id1433873030610", "Id1433873031553", "Id1433873032407", "Id1433873033197", "Id1433873034477", "Id1433873035263", "Id1433873036085", "Id1433873036963", "Id1433873037791", "Id1433873038660", "Id1433873039491", "Id1433873040347", "Id1433873041196", "Id1433873042367", "Id1433873043223", "Id1433873044177", "Id1433873045142", "Id1433873045969", "Id1433873046826", "Id1433873047773", "Id1433873048637", "Id1433873049477", "Id1433873050509", "Id1433873051313", "Id1433873052087", "Id1433873052919", "Id1433873053748", "Id1433873054538", "Id1433873055344", "Id1433873056199", "Id1433873056988", "Id1433873057918", "Id1433873058962", "Id1433873059812", "Id1433873060656", "Id1433873061578", "Id1433873062378", "Id1433873063210", "Id1433873064084", "Id1433873064974", "Id1433873066017", "Id1433873066803", "Id1433873067631", "Id1433873068739", "Id1433873069599", "Id1433873070412", "Id1433873071274", "Id1433873072092", "Id1433873072900", "Id1433873073766", "Id1433873074931", "Id1433873075843", "Id1433873076812", "Id1433873077662", "Id1433873078490", "Id1433873079332", "Id1433873080190", "Id1433873081014", "Id1433873081876", "Id1433873082870", "Id1433873083693", "Id1433873084554", "Id1433873085396", "Id1433873086243", "Id1433873087055", "Id1433873087899", "Id1433873088747", "Id1433873089540", "Id1433873090490", "Id1433873091312", "Id1433873092199", "Id1433873093416", "Id1433873094249", "Id1433873095274", "Id1433873096105", "Id1433873096943", "Id1433873097831", "Id1433873098991", "Id1433873099814", "Id1433873101312", "Id1433873102790", "Id1433873104062", "Id1433873105106", "Id1433873106525", "Id1433873107744", "Id1433873108876", "Id1433873110014", "Id1433873111313", "Id1433873112580", "Id1433873114402", "Id1433873115715", "Id1433873117437", "Id1433873118626", "Id1433873119472", "Id1433873120818", "Id1433873121664", "Id1433873122597", "Id1433873123464");
        int estatus = 1;
        try {
            long t1 = new Date().getTime();
            int rows = controller.updateStatusNos(clOrdIdList, estatus);
            long t2 = new Date().getTime();
            System.out.println(t2 - t1);
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
