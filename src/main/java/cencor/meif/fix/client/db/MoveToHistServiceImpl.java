package cencor.meif.fix.client.db;

import cencor.meif.fix.client.Service;
import org.apache.log4j.Logger;

/**
 * Created by mhernandez on 6/16/15.
 */
public class MoveToHistServiceImpl implements Service, Runnable {

    public static final int TIME_TO_WAIT = 60000;
    private static Logger logger = Logger.getLogger(MoveToHistServiceImpl.class);

    private Thread moveToHistThread;
    private DBController dbController;

    public MoveToHistServiceImpl(DBController dbController) {
        this.dbController = dbController;
        moveToHistThread = new Thread(this);
        moveToHistThread.setName("MoveToHistThread");
    }

    @Override
    public void start() {
        moveToHistThread.start();
    }

    @Override
    public void run() {
        while (true) {
            logger.info("Begin moveToHistoricDB");
            dbController.moveToHistoricDB();
            logger.info("Ended moveToHistoricDB");

            try {
                Thread.sleep(TIME_TO_WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
