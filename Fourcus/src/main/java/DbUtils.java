import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    private static DbUtils dbConnect = new DbUtils();

    private DbUtils() {
    }

    public static DbUtils getInstance() {
        return dbConnect;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://fourcus.cxnnfucixc3w.ap-northeast-2.rds.amazonaws.com/wordpress",
                    "admin",
                    "Fourcusadmin!2");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}