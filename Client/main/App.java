package main;

import controller.communication.MessagesManager;
import controller.ui.UIAlertController;
import controller.ui.login.UILoginController;
import controller.ui.match.UIMatchController;
import controller.ui.match.UIOnlineMatchController;
import controller.ui.room.UICreateRoomController;
import controller.ui.room.UIRoomListController;
import model.define.SizeDefine;
import model.define.StringDefine;
import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import model.IResult;
import model.communication.client_server.ConnectingServer;
import model.communication.peer.WaitingClient;
import model.type.Board;
import model.type.Player;
import model.type.Room;
import model.type.enumeration.EPieceColors;
import view.UIView;

public class App extends JFrame {
    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 3000;
    
//    private RoomList rooms; // the rooms list which will be synchronized with server
    private Player player; // the player who logged in
    private Room room; // the room of which player is admin
    
    private UILoginController uiLoginController;
    private UIRoomListController uiRoomListController;
    private UIAlertController uiAlertController;
    private UICreateRoomController uiCreateRoomController;
    
    public void start() {
        player = null;
        // Alert
        uiAlertController = new UIAlertController();
        uiAlertController.setHideCancelButton(true);
        uiAlertController.centerWindow();
        uiAlertController.setOKButtonListener(() -> {
            if (uiAlertController.getNote().equals("EMPTY")) {
                uiCreateRoomController.show();
            }
        });
        
        // Create room
        uiCreateRoomController = new UICreateRoomController();
        uiCreateRoomController.centerWindow();
        
        // Rooms list
        uiRoomListController = new UIRoomListController();
        uiRoomListController.setCreateRoomListener(() -> {
            createNewRoom();
        });
        uiRoomListController.setEnterRoomListener((Object obj) -> {
            joinRoom((int)obj);
        });
        uiRoomListController.setJoinRoomListener((Object obj) -> {
            joinRoom((int)obj);
        });
        
        // Login
        uiLoginController = new UILoginController();
        uiLoginController.setLoginSuccessListener((Object obj) -> {
            if (player == null) {
                player = (Player)obj;
                WaitingClient.asynchronousWaitingOn(
                        player.getIp(),
                        player.getPort(),
                        100
                );
            } else {
                player = (Player)obj;
            }
            showRoomsList();
        });
        App.getInstance().show(uiLoginController.getContentView(), "đăng nhập");
    }
    
    private void createNewRoom() {
        if (ConnectingServer.CONNECTOR.create(SERVER_IP, SERVER_PORT)) {
            uiCreateRoomController.setTitle("Tạo phòng mới");
            uiCreateRoomController.setContent("Nhập tên phòng");
            uiCreateRoomController.setOKButtonListener((obj) -> {
                Object[] objs = (Object[])obj;
                String roomName = (String)objs[1];
                if (roomName.length() > 0) {
                    if ((boolean)objs[0]) {
                        playWithPC(roomName);
                    } else {
                        playWithPlayer(roomName);
                    }
                } else {
                    uiAlertController.setTitle("Chưa điền tên phòng");
                    uiAlertController.setContent("Bạn cần phải điền tên phòng trước khi xác nhận tạo phòng");
                    uiAlertController.setNote("EMPTY");
                    uiAlertController.show();
                }
            });
            uiCreateRoomController.show();
        } else {
            alertDisconnection();
        }
    }
    
    private void playWithPC(String roomName) {
        UIMatchController matchController = new UIMatchController(
                EPieceColors.WHITE,
                Board.create(),
                roomName,
                player // enemy
        );
        matchController.start();
        App.getInstance().show(matchController.getContentView(), String.format("%s - WHITE", player.getName()));
    }
    
    private void playWithPlayer(String roomName) {
        room = (Room)ConnectingServer.CONNECTOR.sendMessage(MessagesManager.MessageType.NEW_ROOM, new Object[] {
            roomName,
            player.getId(),
            player.getIp(),
            player.getPort()
        });

        if (room == null) {
            uiAlertController.setTitle("Không tạo được phòng");
            uiAlertController.setContent("Server hiện đang quá tải, bạn hãy thử tạo phòng lại sau nhé");
            uiAlertController.setNote("");
            uiAlertController.show();
        } else {
            showMatch(EPieceColors.WHITE, null, roomName);
        }
        
        uiRoomListController.refresh();
    }
    
    private void joinRoom(int roomID) {
        if (ConnectingServer.CONNECTOR.create(SERVER_IP, SERVER_PORT)) {
            // 0: room, 1: admin
            Object[] result = (Object[])ConnectingServer.CONNECTOR.sendMessage(MessagesManager.MessageType.JOIN_ROOM, roomID);
            if (result == null) {
                uiAlertController.setTitle("Không vào được phòng");
                uiAlertController.setContent("Ván đấu đã đủ người hoặc không còn tồn tại. Bạn hãy chọn ván đấu khác!");
                uiAlertController.setNote("");
                uiAlertController.show();
            } else {
                room = (Room)result[0];
                Player admin = (Player)result[1];
                showMatch(EPieceColors.BLACK, admin, room.getName());
            }
            uiRoomListController.refresh();
        } else {
            alertDisconnection();
        }
    }
    
    private void showMatch(EPieceColors playerColor, Player enemy, String roomName) {
        UIOnlineMatchController matchController = new UIOnlineMatchController(
                playerColor,
                Board.create(),
                roomName,
                enemy
        );
        matchController.setExitListener(() -> {
            uiRoomListController.refresh();
        });
        MessagesManager.setPeerMessageListener(matchController);
        matchController.show();
    }
    
    public void alertDisconnection() {
        uiAlertController.setTitle("Lỗi kết nối");
        uiAlertController.setContent("Không tìm thấy kết nối tới server");
        uiAlertController.setNote("");
        uiAlertController.show();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Room getRoom() {
        return room;
    }
    
    private void showRoomsList() {
        uiRoomListController.refresh();
        uiRoomListController.setPlayerInfo(player);
        App.getInstance().show(uiRoomListController.getContentView(), player.getName());
    }
    
    //-------------------------- Process screen --------------------//
    private LinkedList<String> screenNamesQueue;
    
    private static final App THIS = new App();
    
    private CardLayout card = new CardLayout();
    
    private HashMap<String, UIView> namesMap;
            
    private String showingViewName;
    
    private HashMap<String, IResult.Responser> closeListenerMap;
    
    public static App getInstance() {
        return THIS;
    }
    
    private App() {
        super.setMinimumSize(SizeDefine.MINIMUM_SIZE_FRAME);
        super.setSize(SizeDefine.MINIMUM_SIZE_FRAME);
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        super.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                UIView showingView = namesMap.get(showingViewName);
                namesMap.remove(showingViewName);
                IResult.Responser closeListener = closeListenerMap.get(showingViewName);
                if (closeListener != null) {
                    closeListenerMap.remove(showingViewName);
                }
                if (screenNamesQueue.isEmpty()) {
                    System.exit(0);
                } else {
                    if (closeListener != null) {
                        closeListener.response();
                    }
                    showingViewName = screenNamesQueue.pop();
                    card.removeLayoutComponent(showingView);
                    getContentPane().remove(showingView);
                    setTitle(String.format("%s (%s)", StringDefine.PROGRAM_NAME, showingViewName));
                    card.show(getContentPane(), showingViewName);
                    setExtendedState(JFrame.MAXIMIZED_BOTH); 
                    setVisible(true);
                    revalidate();
                    repaint();
                }
            }
        });
        
        card = new CardLayout();
        super.getContentPane().setLayout(card);
        
        namesMap = new HashMap<>();
        closeListenerMap = new HashMap<>();
        screenNamesQueue = new LinkedList<>();
        showingViewName = null;
    }
    
    public void show(UIView uiView, String viewName) {
        show(uiView, viewName, null);
    }
    
    public void show(UIView uiView, String viewName, IResult.Responser listener) {
        if (showingViewName != null) {
            screenNamesQueue.push(showingViewName);
        } else {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            this.setVisible(true);
        }
        getContentPane().add(uiView, viewName);
        card.show(getContentPane(), viewName);
        
        showingViewName = viewName;
        namesMap.put(viewName, uiView);
        if (listener != null) {
            closeListenerMap.put(showingViewName, listener);
        }
        
        super.setTitle(String.format("%s (%s)", StringDefine.PROGRAM_NAME, showingViewName));
    }
    
    public void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
