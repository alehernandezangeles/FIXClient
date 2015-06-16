package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.ACK1Adapter;
import cencor.meif.fix.client.ACK1AdapterImpl;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.InsertThread;
import cencor.meif.fix.client.jpa.entities.Ack1Entity;
import org.apache.log4j.Logger;
import quickfix.fix44.ExecutionReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mhernandez on 6/16/15.
 */
public class Ack1InsertThreadImpl extends Thread implements InsertThread {
    private static Logger logger = Logger.getLogger(Ack1InsertThreadImpl.class);

    private DBController dbController;

    private CopyOnWriteArrayList<ExecutionReport> ack1List;
    private ACK1Adapter ack1Adapter;

    public Ack1InsertThreadImpl(DBController dbController) {
        this.dbController = dbController;
        this.ack1List = new CopyOnWriteArrayList<>();
        this.ack1Adapter = new ACK1AdapterImpl();
    }

    @Override
    public synchronized void put(ExecutionReport er) {
        ack1List.add(er);
    }

    @Override
    public void start() {
        super.start(); // Starts this thread
    }

    @Override
    public void run() {
        ExecutionReport[] ack1Arr = null;
        int acum = 0;
        while (true) {
            synchronized (this.ack1List) {
                ack1Arr = this.ack1List.toArray(new ExecutionReport[]{});
                this.ack1List.clear();
            }
            if (ack1Arr != null && ack1Arr.length > 0) {
                long t1 = new Date().getTime();
                List<Ack1Entity> ack1Entities = getAck1Entities(ack1Arr);
                dbController.createAck1(ack1Entities);
                long t2 = new Date().getTime();
                acum += ack1Arr.length;
                logger.info("Ack1 Rows Inserted: " + ack1Arr.length + ", " + (t2 - t1) + " ms");
            }
            logger.info("Ack1 Insert Acum: " + acum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Ack1Entity> getAck1Entities(ExecutionReport[] erArr) {
        List<Ack1Entity> ack1Entities = new ArrayList<>(erArr.length);
        for (ExecutionReport erMsg : erArr) {
            Ack1Entity ack1Entity = ack1Adapter.adapt(erMsg);
            ack1Entities.add(ack1Entity);
        }
        return ack1Entities;
    }

}
