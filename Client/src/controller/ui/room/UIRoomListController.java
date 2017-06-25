package controller.ui.room;

import controller.communication.MessagesManager;
import controller.ui.UIAlertController;
import controller.ui.UIController;
import javax.swing.JPanel;
import main.App;
import model.IResult;
import model.communication.client_server.ConnectingServer;
import model.type.Player;
import model.type.Room;
import model.type.RoomList;
import view.room.UIInfoBarView;
import view.room.UIPagingView;
import view.room.UIRoomListView;

public class UIRoomListController extends UIController {
    
    private final UIInfoBarController infoBarController;
    private final JPanel roomListPanel;
    private final JPanel emptyPanel;
    private final UIPagingController pagingController;
    
    private RoomList roomsList;
    private final UIRoomCellController[] roomCells;
    private UIPagingCellController curPagingCellController;
    private final UIAlertController logoutAlertController;
    
    private IResult.ResponseReceiver roomCellListener;
    
    public UIRoomListController() {
        super(new UIRoomListView());
        
        infoBarController = new UIInfoBarController((UIInfoBarView)super.findViewById("InfoBarView"));
        roomListPanel = (JPanel)super.findViewById("RoomListPanel");
        emptyPanel = (JPanel)super.findViewById("EmptyPanel");
        pagingController = new UIPagingController((UIPagingView)super.findViewById("PagingView"));
        
        roomCells = new UIRoomCellController[10];
        logoutAlertController = new UIAlertController();
        
        setEventListeners();
        setRoomCells();
        getRoomsList(0);
    }
    
    public void setPlayerInfo(Player player) {
        infoBarController.setPlayerInfo(player.getName(), player.getLevel());
    }
    
    private void setEventListeners() {
        logoutAlertController.setOKButtonListener(() -> {
            App.getInstance().close();
        });
        
        infoBarController.setLogoutListener(() -> {
            logoutAlertController.setTitle("Đăng xuất");
            logoutAlertController.setContent("Bạn chắc chắn muốn đăng xuất khỏi trò chơi?");
            logoutAlertController.centerWindow();
            logoutAlertController.show();
        });
        
        infoBarController.setRefreshListener(() -> {
            refresh();
        });
    }
    
    private void setRoomCells() { 
        UIRoomCellController roomCellController;
        for (int i = 0; i < 10; ++i) {
            roomCellController = new UIRoomCellController();
            roomCellController.hide();
            roomListPanel.add(roomCellController.getContentView());
            roomCellController.setJoinRoomListener((Object obj) -> {
                if (roomCellListener != null) {
                    roomCellListener.receiveResult(obj); // room's id
                }
            });
            roomCells[i] = roomCellController;
        }
    }
    
    private void getRoomsList(long roomListID) {
        if (ConnectingServer.CONNECTOR.create(App.SERVER_IP, App.SERVER_PORT)) {
            RoomList tmpRoomsList = (RoomList) ConnectingServer.CONNECTOR.sendMessage(
                    MessagesManager.MessageType.ROOMS_LIST,
                    (long)roomListID
            );

            if (tmpRoomsList != null) { // null when the rooms list don't have any change
                roomsList = tmpRoomsList;
                if (roomsList.size() > 0) {
                    setEmpty(false);
                    curPagingCellController = pagingController.paging(roomsList.getNumberPages(10), (Object obj) -> {
                        curPagingCellController.uncheck();
                        curPagingCellController = (UIPagingCellController)obj;
                        curPagingCellController.check();
                        changePage(curPagingCellController.getId());
                    });
                    changePage(1);
                } else {
                    setEmpty(true);
                }
            } else if (roomsList == null) {
                setEmpty(true);
            }
        } else {
            App.getInstance().alertDisconnection();
        }
    }
    
    private void changePage(int pageID) {
        int iRoomCell = 0;
        Room[] rooms = roomsList.getRoomOnPage(pageID, 10);
        if (rooms != null) {
            for (Room room : rooms) {
                setRoom(iRoomCell++, room);
            }
        }

        while (iRoomCell < 10) {
            setEmptyRoom(iRoomCell);
            ++iRoomCell;
        }
    }
    
    private void setRoom(int iRoomCell, Room room) {
        UIRoomCellController roomCellController = roomCells[iRoomCell];
        roomCellController.show();
        roomCellController.setId(room.getId());
        roomCellController.setRoomName(String.format("%S (%d)", room.getName(), room.getId()));
        roomCellController.setRoomDetail(String.format(
                "Số lượng người chơi: %d/2", room.getNumberPlayers()));
    }
    
    private void setEmptyRoom(int iRoomCell) {
        roomCells[iRoomCell].hide();
    }
    
    private void setEmpty(boolean isEmpty) {
        emptyPanel.setVisible(isEmpty);
        isEmpty = !isEmpty;
        roomListPanel.setVisible(isEmpty);
        pagingController.getContentView().setVisible(isEmpty);
    }
    
    public void refresh() {
        if (roomsList == null) {
            getRoomsList(0);
        } else {
            getRoomsList(roomsList.getTimestamp());
        }
    }
    
    public void setJoinRoomListener(IResult.ResponseReceiver listener) {
        this.roomCellListener = listener;
    }
    
    public void setEnterRoomListener(IResult.ResponseReceiver listener) {
        infoBarController.setEnterRoomListener(listener);
    }
    
    public void setCreateRoomListener(IResult.Responser listener) {
        infoBarController.setCreateRoomListener(listener);
    }
    
    public void show(Player player) {
        this.refresh();
        this.setPlayerInfo(player);
        App.getInstance().show(uiView, player.getName(), () -> {
            logoutAlertController.setTitle("Đăng xuất");
            logoutAlertController.setContent("Bạn chắc chắn muốn đăng xuất khỏi trò chơi?");
            logoutAlertController.centerWindow();
            logoutAlertController.show();
        });
    }
    
}
