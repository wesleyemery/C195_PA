package Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static Statement statement;

    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();

    }
    //Statement Getter

    public static Statement getStatement(){
        return statement;
    }
}
