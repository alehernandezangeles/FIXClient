package cencor.meif.fix.client.db;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mhernandez on 6/12/15.
 */
public class UpdateNosStatus implements Runnable {
    private static Logger logger = Logger.getLogger(UpdateNosStatus.class);

    private List<String> clOrdIdList;
    private int estatus;
    private DBController dbController;

    public UpdateNosStatus(DBController dbController, List<String> clOrdIdList, final int estatus) {
        this.dbController = dbController;
        this.clOrdIdList = clOrdIdList;
        this.estatus = estatus;
    }

    @Override
    public void run() {
        try {
            int rows = dbController.editStatusNos(clOrdIdList, estatus);
            logger.info("Se actualizó el estatus de " + rows + " registros de la tabla NOS. Nuevo estatus: " + estatus);
        } catch (SQLException e) {
            logger.error("Error al cambiar el estatus de la tabla NOS para los clOrdId's " + this.clOrdIdList + "- Nuevo estatus " + estatus, e);
        }
    }
}
