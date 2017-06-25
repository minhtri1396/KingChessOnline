package model.dao;
import helper.ErrorLogger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.type.Player;

public class UserDAO  { 
    public static final UserDAO BUILDER = new UserDAO();
    
    // Get information of user has the playerID
    public Player getInfo(int playerID) {
        Player player = null;
        
        try (Connection conn = Database.createConnection()) {
            
            if(conn != null){
                CallableStatement cs = conn.prepareCall(
                        String.format("SELECT * FROM USER WHERE id = ?"));
                cs.setInt(1, playerID);                
                ResultSet rs = cs.executeQuery();          
                rs.next();
                player = new Player(rs.getInt(1), null, 0);
                player.setName(rs.getString(3));
                player.setLevel(rs.getInt(4));
                return player;
            }
        }
        catch (SQLException e) {
            // Could not find the database driver
            ErrorLogger.log(this.getClass(), e);
        }
        
        return player;
    }
    
}
