
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

    public static Connection getJDBCConnection() {
        String url = "jdbc:mysql://localhost:3306/user_bank?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "12345";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;

    }
    public static void main(String[] args) {
        Connection conn = getJDBCConnection();
        if (conn != null) {
            System.out.println("OK");
        } else {
            System.out.println("Not");
        }
    }
}
