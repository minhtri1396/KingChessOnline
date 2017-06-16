package model.communication.client_server;

import controller.communication.MessagesManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import main.App;
import model.type.Room;

public class AsyncUpdateRoom implements ActionListener {
    
    private final Room room;
    
    private final Timer timer;
    
    public AsyncUpdateRoom(Room room) {
        this.room = room;
        timer = new Timer(5000, this); // 5 seconds
    }
    
    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ConnectingServer.CONNECTOR.create(App.SERVER_IP, App.SERVER_PORT)) {
            if ((boolean)ConnectingServer.CONNECTOR.sendMessage(MessagesManager.MessageType.REFRESH_ROOM, room)) {
            } else {
                timer.stop();
            }
        } else {
            App.getInstance().alertDisconnection();
        }
    }
}
