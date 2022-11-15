package ysx.dbManger;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbManger {
    Connection connection = null;
    String jndiName = "jdbc/defaultD";

    public static Connection getConnection() throws Exception {
        //
        // DataSourcePool.getDbConnection();

        //

        return getConnection("jdbc/defaultDS");
    }

    private static Connection getConnection(String jndiName) throws Exception {

        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(jndiName);
        return ds.getConnection();
    }

}
