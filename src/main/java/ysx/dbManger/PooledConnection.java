package ysx.dbManger;

import java.sql.Connection;

public class PooledConnection {

    Connection connection = null;
    boolean busy = false;

    public PooledConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

}
