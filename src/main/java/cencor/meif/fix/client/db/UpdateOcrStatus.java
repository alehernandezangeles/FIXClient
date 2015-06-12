package cencor.meif.fix.client.db;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mhernandez on 6/12/15.
 */
public class UpdateOcrStatus implements Runnable {
    private static Logger logger = Logger.getLogger(UpdateOcrStatus.class);
    private List<String> clOrdIdList;
    private int estatus;
    private DBController dbController;

    public UpdateOcrStatus(final DBController dbController, List<String> clOrdIdList, final int estatus) {
        this.clOrdIdList = clOrdIdList;
        this.estatus = estatus;
        this.dbController = dbController;
    }

    @Override
    public void run() {
        try {
            int rows = dbController.editStatusOcr(clOrdIdList, estatus);
            logger.info("Se actualizó el estatus de " + rows + " registros de la tabla OCR. Nuevo estatus: " + estatus);
        } catch (SQLException e) {
            logger.error("Error al cambiar el estatus de la tabla OCR para los clOrdId's " + this.clOrdIdList + "- Nuevo estatus " + estatus, e);
        }
    }
}
