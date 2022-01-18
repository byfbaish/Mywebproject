package ysx.dbManger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

public class DataSourcePool {

    private static String jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
    private static String dbUrl = "jdbc:db2://9.197.75.8:50002/TESTDB";
    private static String dbUsername = "DB2INST1";
    private static String dbPassword = "12345678";
    private String testTable = "MOCK_CONTROL";
    private int initialConnections = 10;
    private int incrementalConnections = 5;
    private int maxConnections = 50;
    private Vector connections = null;

    public DataSourcePool(String jdbcDriver, String dbUrl, String dbUsername, String dbPassword) {

        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    private static class SingletonClassInstance {
        private static final DataSourcePool instance = new DataSourcePool(jdbcDriver, dbUrl, dbUsername, dbPassword);
    }

    public static Connection getDbConnection() {
        return SingletonClassInstance.instance.getConnection();
    }

    public int getInitialConnections() {
        return this.initialConnections;
    }

    public void setInitialConnections(int initialConnections) {
        this.initialConnections = initialConnections;
    }

    public int getIncrementalConnections() {
        return this.incrementalConnections;
    }

    public void setIncrementalConnections(int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getTestTable() {
        return this.testTable;
    }

    public void setTestTable(String testTable) {
        this.testTable = testTable;
    }

    public synchronized void createPool() throws Exception {

        if (connections != null) {
            return; // 假如己经创建，则返回
        }
        // 实例化 JDBC Driver 中指定的驱动类实例
        Driver driver = (Driver) (Class.forName(this.jdbcDriver).newInstance());
        DriverManager.registerDriver(driver);

        connections = new Vector();

        createConnections(this.initialConnections);
        System.out.println(" 数据库连接池创建成功！ ");
    }

    private void createConnections(int numConnections) throws SQLException {

        for (int x = 0; x < numConnections; x++) {
            if (this.maxConnections > 0 && this.connections.size() >= this.maxConnections) {
                break;
            }
            try {
                connections.addElement(new PooledConnection(newConnection()));
            } catch (SQLException e) {
                System.out.println(" 创建数据库连接失败！ " + e.getMessage());
                throw new SQLException();
            }
            System.out.println(" 数据库连接己创建 ......");
        }
    }

    private Connection newConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        if (connections.size() == 0) {
            DatabaseMetaData metaData = conn.getMetaData();
            int driverMaxConnections = metaData.getMaxConnections();
            System.out.println("目标数据库最大连接数量" + driverMaxConnections);
            if (driverMaxConnections > 0 && this.maxConnections > driverMaxConnections) {
                this.maxConnections = driverMaxConnections;
            }
        }
        return conn;
    }

    public synchronized Connection getConnection() {

        if (connections == null) {
            return null;
        }
        Connection conn = null;
        try {
            conn = getFreeConnection();
            while (conn == null) {
                wait(250);
                conn = getFreeConnection();
            }
        } catch (SQLException e) {
            System.out.println("get connection failure");
        }

        return conn;
    }

    private Connection getFreeConnection() throws SQLException {

        Connection conn = findFreeConnection();
        if (conn == null) {
            createConnections(incrementalConnections);
            conn = findFreeConnection();
            if (conn == null) {
                return null;
            }
        }
        return conn;
    }

    private Connection findFreeConnection() throws SQLException {
        Connection conn = null;
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();

        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            if (!pConn.isBusy()) {
                conn = pConn.getConnection();
                pConn.setBusy(true);

                if (!testConnection(conn)) {
                    try {
                        conn = newConnection();
                    } catch (SQLException e) {
                        System.out.println(" 创建数据库连接失败！ " + e.getMessage());
                        return null;
                    }
                    pConn.setConnection(conn);
                }
                break;
            }
        }
        return conn;
    }

    private boolean testConnection(Connection conn) {
        try {

            if (testTable.equals("")) {
                conn.setAutoCommit(true);
            } else {
                Statement stmt = conn.createStatement();
                stmt.execute("select count(*) from " + testTable);
            }
        } catch (SQLException e) {

            closeConnection(conn);
            return false;
        }

        return true;
    }

    public void returnConnection(Connection conn) {

        if (connections == null) {
            System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();

        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            if (conn == pConn.getConnection()) {
                pConn.setBusy(false);
                break;
            }
        }
    }

    public synchronized void refreshConnections() throws SQLException {
        if (connections == null) {
            System.out.println(" 连接池不存在，无法刷新 !");
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            // 获得一个连接对象
            pConn = (PooledConnection) enumerate.nextElement();
            if (pConn.isBusy()) {
                wait(5000); // 等 5 秒
            }
            closeConnection(pConn.getConnection());
            pConn.setConnection(newConnection());
            pConn.setBusy(false);
        }
    }

    public synchronized void closeConnectionPool() throws SQLException {
        // 确保连接池存在，假如不存在，返回
        if (connections == null) {
            System.out.println(" 连接池不存在，无法关闭 !");
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            // 假如忙，等 5 秒
            if (pConn.isBusy()) {
                wait(5000); // 等 5 秒
            }
            // 5 秒后直接关闭它
            closeConnection(pConn.getConnection());
            // 从连接池向量中删除它
            connections.removeElement(pConn);
        }
        // 置连接池为空
        connections = null;
    }

    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(" 关闭数据库连接出错： " + e.getMessage());
        }
    }

    private void wait(int mSeconds) {
        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
            System.out.print("sleep failure");
        }
    }
}
