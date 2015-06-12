package cencor.meif.fix.client.db;

import cencor.meif.fix.client.FixUtils;
import cencor.meif.fix.client.FixUtilsImpl;
import cencor.meif.fix.client.Service;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by mhernandez on 6/1/15.
 */
public class NewMsgObservable extends Observable implements Service {

    private static Logger logger = Logger.getLogger(NewMsgObservable.class);

    /**
     * Especifica el tiempo en milisegundos entre consultas a la BD
     */
    public static final int POLLING_TIME = 1000;

    private DBController dbController;
    private FixUtils fixUtils;

    public NewMsgObservable(DBController dbController) {
        this.dbController = dbController;
        this.fixUtils = new FixUtilsImpl();
    }

    /**
     * Inicia el servicio de polling a la BD
     */
    @Override
    public void start() {
        while (true) {
            // Busca órdenes nuevas en la BD
            List<NosEntity> nosEntities = dbController.getNewNos();
            List<OcrEntity> ocrEntities = dbController.getNewOcr();

            List<Serializable> entities = new ArrayList<>();
            if (nosEntities != null) { entities.addAll(nosEntities); }
            if (ocrEntities != null) { entities.addAll(ocrEntities); }

            // Si hay nuevas ordenes cambiar estatus y notificar
            if (entities.size() > 0) {
                // cambiar el estatus de las ordenes a "Por enviar a MEIF"
                List<String> clOrdIdListNos = this.fixUtils.getClOrdIdListNos(nosEntities);
                List<String> clOrdIdListOcr = this.fixUtils.getClOrdIdListOcr(ocrEntities);
                this.dbController.updateStatus(clOrdIdListNos, clOrdIdListOcr, CatEstatusEntity.POR_ENVIAR_A_MEIF);

                // Notificar a los observers
                setChanged();
                notifyObservers(entities);
            }

            try { Thread.sleep(POLLING_TIME); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

}
