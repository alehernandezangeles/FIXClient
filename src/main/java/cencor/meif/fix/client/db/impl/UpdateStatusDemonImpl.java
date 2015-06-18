package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.db.*;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by mhernandez on 6/12/15.
 */
public class UpdateStatusDemonImpl implements UpdateStatusDemon {

    public static final int SLEEP_TIME = 1000;
    private static Logger logger = Logger.getLogger(UpdateStatusDemonImpl.class);
    private ConcurrentHashMap<String, EstatusInfo> estatusInfoMap;
    private ConcurrentLinkedQueue<EstatusAck2Info> estatusAck2InfoQueue;
    private DBController dbController;

    public UpdateStatusDemonImpl(DBController dbController) {
        this.dbController = dbController;
        estatusInfoMap = new ConcurrentHashMap<>();
        estatusAck2InfoQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public synchronized void add(EstatusInfo estatusInfo) {
        String clOrdId = estatusInfo.getClOrdId();
        EstatusInfo estatusInfoOld = estatusInfoMap.get(clOrdId);
        int newStatus = estatusInfo.getEstatus();
        if (estatusInfoOld == null) {
            estatusInfoMap.put(clOrdId, estatusInfo);
        } else if (estatusInfoOld != null && newStatus > estatusInfoOld.getEstatus()) {
            estatusInfoOld.setEstatus(newStatus);
            estatusInfoOld.setPersisted(false);
        }

        if (newStatus == CatEstatusEntity.ACK2) {
            int estatusAck2 = estatusInfo.getEstatusAck2();
            String descrEstatusAck2 = estatusInfo.getDescrEstatusAck2();

            EstatusAck2Info estatusAck2Info = new EstatusAck2Info(clOrdId, estatusAck2, descrEstatusAck2);
            estatusAck2InfoQueue.add(estatusAck2Info);
        }
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!estatusInfoMap.isEmpty()) {
                        EstatusInfoClassifier estatusClassifier = new EstatusInfoClassifierImpl();
                        Collection<EstatusInfo> estatusInfos = estatusInfoMap.values();
                        for (final EstatusInfo estatusInfo : estatusInfos) {
                            synchronized (estatusInfo) {
                                if (!estatusInfo.isPersisted()) {
                                    estatusClassifier.classify(estatusInfo);
                                    estatusInfo.setPersisted(true);
                                    if (estatusInfo.getEstatus() == CatEstatusEntity.ER || (estatusInfo.getEstatus() == CatEstatusEntity.ACK2 && estatusInfo.getEstatusAck2() == 0) || estatusInfo.getEstatus() == CatEstatusEntity.ERROR) {
                                        UpdateStatusDemonImpl.this.estatusInfoMap.remove(estatusInfo.getClOrdId());
                                    }
                                }
                            }
                        }
                        updateEstatus(estatusClassifier);
                    }
                    logger.info("Update Status Demon Queue size: " + UpdateStatusDemonImpl.this.estatusInfoMap.size());
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            private void updateEstatus(EstatusInfoClassifier estatusClassifier) {
                int rows = 0;
                try {
                    rows += dbController.updateStatusSync(estatusClassifier.getAck1(), CatEstatusEntity.ACK1);
                } catch (SQLException e) {
                    logger.error("Error al actualizar los folios " + estatusClassifier.getAck1() + " al estatus " + CatEstatusEntity.ACK1, e);
                }
                try {
                    rows += dbController.updateStatusSync(estatusClassifier.getAck2(), CatEstatusEntity.ACK2);
                } catch (SQLException e) {
                    logger.error("Error al actualizar los folios " + estatusClassifier.getAck2() + " al estatus " + CatEstatusEntity.ACK2, e);
                }
                try {
                    rows += dbController.updateStatusSync(estatusClassifier.getEr(), CatEstatusEntity.ER);
                } catch (SQLException e) {
                    logger.error("Error al actualizar los folios " + estatusClassifier.getEr() + " al estatus " + CatEstatusEntity.ER, e);
                }
                try {
                    rows += dbController.updateStatusSync(estatusClassifier.getError(), CatEstatusEntity.ERROR);
                } catch (SQLException e) {
                    logger.error("Error al actualizar los folios " + estatusClassifier.getError() + " al estatus " + CatEstatusEntity.ERROR, e);
                }
                logger.info("Updated estatus from classifier: " + rows + " / " + estatusClassifier.size());
            }
        }, "UpdateStatusDemon").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    EstatusAck2InfoClassifier classifier = new EstatusAck2InfoClassifierImpl();
                    EstatusAck2Info estatusAck2Info = null;
                    while ((estatusAck2Info = UpdateStatusDemonImpl.this.estatusAck2InfoQueue.poll()) != null) {
                        classifier.add(estatusAck2Info);
                    }
                    if (classifier.size() > 0) {
                        List<EstatusAck2InfoBatch> estatusAck2InfoBatches = classifier.classify();
                        for (EstatusAck2InfoBatch estatusAck2InfoBatch : estatusAck2InfoBatches) {
                            try {
                                dbController.updateStatusAck2Sync(estatusAck2InfoBatch);
                            } catch (SQLException e) {
                                logger.error("Error al actualizar los folios " + estatusAck2InfoBatch, e);
                            }
                        }
                    }
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "UpdateStatusAck2Demon").start();
    }
}
