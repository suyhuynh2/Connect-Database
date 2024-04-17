
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class User {

    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Mã hóa dữ liệu sử dụng Base64
    public String encryptData(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    // Giải mã dữ liệu đã được mã hóa bằng Base64
    public String decryptData(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public void addUser(String username, String password) {
        String encodeName = encryptData(username);
        String encodePass = encryptData(password);

        String insertQuery = "INSERT INTO `user` (`username` , `password`) VALUES (?, ?) ";

        try {
            PreparedStatement ps = DB.getJDBCConnection().prepareStatement(insertQuery);
            ps.setString(1, encodeName);
            ps.setString(2, encodePass);

            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "ĐĂNG KÝ THÀNH CÔNG", "ĐĂNG KÝ", 1);
            } else {
                JOptionPane.showMessageDialog(null, "ĐĂNG KÝ THẤT BẠI", "ĐĂNG KÝ", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void checkUser(String username, String password) {
        String encodeName = encryptData(username);
        String encodePass = encryptData(password);
        ResultSet rs;
        PreparedStatement ps;
        String checkQuery = "SELECT * FROM `user` WHERE `username` = ? AND `password` = ?";
        try {
            ps = DB.getJDBCConnection().prepareStatement(checkQuery);
            ps.setString(1, encodeName);
            ps.setString(2, encodePass);
            rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "ĐĂNG NHẬP THÀNH CÔNG", "ĐĂNG NHẬP", 1);
            } else {
                JOptionPane.showMessageDialog(null, "TÀI KHOẢN KHÔNG TỒN TẠI", "ERROR", 2);

            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
