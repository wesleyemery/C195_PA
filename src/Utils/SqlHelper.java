package Utils;

import java.sql.SQLException;
import Utils.DBConnection;

public class SqlHelper {

    private static class SqlHelperSingleton {
        private static final SqlHelper INSTANCE = new SqlHelper();
    }

    private SqlHelper() {}

    public static SqlHelper getInstance() {
        return SqlHelperSingleton.INSTANCE;
    }

    public int update(String q) {
        int result = 0;
        try  {
            result = DBConnection.getInstance().connection().createStatement().executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
