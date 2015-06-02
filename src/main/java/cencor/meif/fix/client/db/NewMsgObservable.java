package cencor.meif.fix.client.db;

import cencor.meif.fix.client.Service;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import cencor.meif.fix.client.jpa.entities.OcrEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by mhernandez on 6/1/15.
 */
public class NewMsgObservable extends Observable implements Service {

    /**
     * Especifica el tiempo en milisegundos entre consultas a la BD
     */
    public static final int POLLING_TIME = 1000;

    private DBController dbController;

    public NewMsgObservable(DBController dbController) {
        this.dbController = dbController;
    }

    /**
     * Inicia el servicio de polling a la BD
     */
    @Override
    public void start() {
        while (true) {
            // Busca órdenes nuevas en la BD
            List<NosEntity> nosEntities = dbController.getNewNos();
            // TODO Consultar OCR
            List<OcrEntity> ocrEntities = null;

            List<Serializable> entities = new ArrayList<>();
            if (nosEntities != null) { entities.addAll(nosEntities); }
            if (ocrEntities != null) { entities.addAll(ocrEntities); }

            // Si hay nuevas ordenes, notificar
            if (entities.size() > 0) {
                setChanged();
                notifyObservers(entities);
            }

            try { Thread.sleep(POLLING_TIME); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }


}
