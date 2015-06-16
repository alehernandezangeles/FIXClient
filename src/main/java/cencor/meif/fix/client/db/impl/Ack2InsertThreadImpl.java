package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.ACK2Adapter;
import cencor.meif.fix.client.ACK2AdapterImpl;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.InsertErThread;
import cencor.meif.fix.client.jpa.entities.Ack2Entity;
import org.apache.log4j.Logger;
import quickfix.fix44.ExecutionReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mhernandez on 6/16/15.
 */
public class Ack2InsertThreadImpl extends Thread implements InsertErThread {
    private static Logger logger = Logger.getLogger(Ack2InsertThreadImpl.class);

    private DBController dbController;

    private CopyOnWriteArrayList<ExecutionReport> ack2List;
    private ACK2Adapter ack2Adapter;

    public Ack2InsertThreadImpl(DBController dbController) {
        this.dbController = dbController;
        this.ack2List = new CopyOnWriteArrayList<>();
        this.ack2Adapter = new ACK2AdapterImpl();

        setName("Ack2InsertThread");
    }

    @Override
    public synchronized void put(ExecutionReport er) {
        ack2List.add(er);
    }

    @Override
    public void start() {
        super.start(); // Starts this thread
    }

    @Override
    public void run() {
        ExecutionReport[] ack2Arr = null;
        int acum = 0;
        while (true) {
            synchronized (this.ack2List) {
                ack2Arr = this.ack2List.toArray(new ExecutionReport[]{});
                this.ack2List.clear();
            }
            if (ack2Arr != null && ack2Arr.length > 0) {
                long t1 = new Date().getTime();
                List<Ack2Entity> ack2Entities = getAck2Entities(ack2Arr);
                dbController.createAck2(ack2Entities);
                long t2 = new Date().getTime();
                acum += ack2Arr.length;
                logger.info("Ack2 Rows Inserted: " + ack2Arr.length + ", " + (t2 - t1) + " ms");
            }
            logger.info("Ack2 Insert Acum: " + acum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Ack2Entity> getAck2Entities(ExecutionReport[] erArr) {
        List<Ack2Entity> ack2Entities = new ArrayList<>(erArr.length);
        for (ExecutionReport erMsg : erArr) {
            Ack2Entity ack2Entity = ack2Adapter.adapt(erMsg);
            ack2Entities.add(ack2Entity);
        }
        return ack2Entities;
    }

}
