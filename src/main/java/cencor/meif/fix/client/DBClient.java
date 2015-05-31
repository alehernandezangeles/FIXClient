package cencor.meif.fix.client;

import cencor.meif.fix.client.db.DBController;
import cencor.meif.fix.client.db.impl.DBControllerImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by alejandro on 5/30/15.
 */
public class DBClient {

    public static Properties fixClientProps = new Properties();

    private DBController dbController;

    public DBClient() {
        dbController = new DBControllerImpl();
    }

    private void start() {

    }

    public static void main(String[] args) throws IOException {
        fixClientProps.load(new FileInputStream("fixclient.properties"));

        DBClient dbClient = new DBClient();
        dbClient.start();
    }

}