package cencor.meif.fix.client.db.impl;

import cencor.meif.fix.client.OtrosAdapter;
import cencor.meif.fix.client.OtrosAdapterImpl;
import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.InsertFixMsgThread;
import cencor.meif.fix.client.jpa.entities.OtrosMsjFixEntity;
import org.apache.log4j.Logger;
import quickfix.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mhernandez on 6/16/15.
 */
public class InsertFixMsgThreadImpl extends Thread implements InsertFixMsgThread {
    private static Logger logger = Logger.getLogger(InsertFixMsgThreadImpl.class);

    private DBController dbController;

    private CopyOnWriteArrayList<Message> msgList;
    private OtrosAdapter msgAdapter;

    public InsertFixMsgThreadImpl(DBController dbController) {
        this.dbController = dbController;
        this.msgList = new CopyOnWriteArrayList<>();
        this.msgAdapter = new OtrosAdapterImpl();
    }

    @Override
    public synchronized void put(Message msg) {
        msgList.add(msg);
    }

    @Override
    public void start() {
        super.start(); // Starts this thread
    }

    @Override
    public void run() {
        Message[] msgArr = null;
        int acum = 0;
        while (true) {
            synchronized (this.msgList) {
                msgArr = this.msgList.toArray(new Message[]{});
                this.msgList.clear();
            }
            if (msgArr != null && msgArr.length > 0) {
                long t1 = new Date().getTime();
                List<OtrosMsjFixEntity> otrosMsgEntities = getOtrosMsgEntities(msgArr);
                dbController.createOtrosMsjFix(otrosMsgEntities);
                long t2 = new Date().getTime();
                acum += msgArr.length;
                logger.info("OtrosMsgFix Rows Inserted: " + msgArr.length + ", " + (t2 - t1) + " ms");
            }
            logger.info("OtrosMsgFix Insert Acum: " + acum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<OtrosMsjFixEntity> getOtrosMsgEntities(Message[] otrosArr) {
        List<OtrosMsjFixEntity> otrosEntities = new ArrayList<>(otrosArr.length);
        for (Message msg : otrosArr) {
            OtrosMsjFixEntity otrosMsjFixEntity = msgAdapter.adapt(msg);
            otrosEntities.add(otrosMsjFixEntity);
        }
        return otrosEntities;
    }

}
