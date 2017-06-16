package model.dao;

import model.type.Player;

public class UserDAO {
    
    public static final UserDAO BUILDER = new UserDAO();
    
    private UserDAO() {}
    
    // Get information of user has the playerID
    public Player getInfo(int playerID) {
        Player player = new Player(playerID, "", 0);
        
        player.setLevel(99);
        player.setName("Tri Dao");
        
        return player;
    }
    
}
