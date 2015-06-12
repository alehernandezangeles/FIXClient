package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.EstatusInfo;
import cencor.meif.fix.client.db.UpdateStatusDemon;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mhernandez on 6/12/15.
 */
public class UpdateStatusDemonImpl implements UpdateStatusDemon {

    public static final int SLEEP_TIME = 1000;
    private static Logger logger = Logger.getLogger(UpdateStatusDemonImpl.class);
    private ConcurrentHashMap<String, EstatusInfo> estatusInfoMap;
    private boolean stop;
    private DBController dbController;

    public UpdateStatusDemonImpl(DBController dbController) {
        this.dbController = dbController;
        estatusInfoMap = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void add(EstatusInfo estatusInfo) {
        String clOrdId = estatusInfo.getClOrdId();
        estatusInfoMap.put(clOrdId, estatusInfo);
    }

    private synchronized void deleteIfExists(String clOrdId, int estatus) {
        EstatusInfo estatusInfoOld = estatusInfoMap.get(clOrdId);
        if (estatusInfoOld != null && estatusInfoOld.getEstatus() == estatus) {
            estatusInfoMap.remove(clOrdId);
        }
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!estatusInfoMap.isEmpty()) {
                            Collection<EstatusInfo> estatusInfos = estatusInfoMap.values();
                            for (final EstatusInfo estatusInfo : estatusInfos) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String clOrdId = estatusInfo.getClOrdId();
                                        int entityType = estatusInfo.getEntityType();
                                        int estatus = estatusInfo.getEstatus();
                                        try {
                                            if (entityType == EstatusInfo.NOS_ENTITY) {
                                                dbController.editStatusNos(clOrdId, estatus);
                                            } else if (entityType == EstatusInfo.OCR_ENTITY) {
                                                dbController.editStatusOcr(clOrdId, estatus);
                                            } else {
                                                dbController.editStatus(clOrdId, estatus);
                                            }
                                            deleteIfExists(clOrdId, estatus);
                                        } catch (Exception e) {
                                            logger.error("Error al modificar estatus de la orden " + clOrdId + " en la tabla NOS. Nuevo estatus " + estatus, e);
                                        }
                                    }
                                }).start();
                            }
                        }
                        Thread.sleep(SLEEP_TIME);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
