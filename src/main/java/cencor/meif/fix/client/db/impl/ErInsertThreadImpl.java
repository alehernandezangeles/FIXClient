package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.ERAdapter;
import cencor.meif.fix.client.ERAdapterImpl;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.InsertErThread;
import cencor.meif.fix.client.jpa.entities.ErEntity;
import org.apache.log4j.Logger;
import quickfix.fix44.ExecutionReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mhernandez on 6/16/15.
 */
public class ErInsertThreadImpl extends Thread implements InsertErThread {
    private static Logger logger = Logger.getLogger(ErInsertThreadImpl.class);

    private DBController dbController;

    private CopyOnWriteArrayList<ExecutionReport> erList;
    private ERAdapter erAdapter;

    public ErInsertThreadImpl(DBController dbController) {
        this.dbController = dbController;
        this.erList = new CopyOnWriteArrayList<>();
        this.erAdapter = new ERAdapterImpl();
    }

    @Override
    public synchronized void put(ExecutionReport er) {
        erList.add(er);
    }

    @Override
    public void start() {
        super.start(); // Starts this thread
    }

    @Override
    public void run() {
        ExecutionReport[] erArr = null;
        int acum = 0;
        while (true) {
            synchronized (this.erList) {
                erArr = this.erList.toArray(new ExecutionReport[]{});
                this.erList.clear();
            }
            if (erArr != null && erArr.length > 0) {
                long t1 = new Date().getTime();
                List<ErEntity> erEntities = getErEntities(erArr);
                dbController.createEr(erEntities);
                long t2 = new Date().getTime();
                acum += erArr.length;
                logger.info("Er Rows Inserted: " + erArr.length + ", " + (t2 - t1) + " ms");
            }
            logger.info("Er Insert Acum: " + acum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<ErEntity> getErEntities(ExecutionReport[] erArr) {
        List<ErEntity> erEntities = new ArrayList<>(erArr.length);
        for (ExecutionReport erMsg : erArr) {
            ErEntity erEntity = erAdapter.adapt(erMsg);
            erEntities.add(erEntity);
        }
        return erEntities;
    }

}
