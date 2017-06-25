package model.dao;
import helper.ErrorLogger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    public static final AccountDAO BUILDER = new AccountDAO();
    
    // Check the mapping of username and password
    // Return id of player if it's correct, otherwise -1
    public int isCorrect(String username, String password) {
        try (Connection conn = Database.createConnection()) {
            if(conn != null){
                CallableStatement cs = conn.prepareCall(
                        String.format("SELECT * FROM ACCOUNT WHERE username = ?")
                );
                cs.setString(1, username);
                ResultSet rs = cs.executeQuery();          
                rs.next();
                if (rs.getString(2).equals(password)) {
                    return rs.getInt(3);
                }
            }
        }
        catch (SQLException e) {
            // Could not find the database driver
            ErrorLogger.log(this.getClass(), e);
        }
        return -1;
    }
      
    
}
