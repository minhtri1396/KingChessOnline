package main;

import controller.communication.MessagesManager;
import controller.ui.UISubWindowController;
import controller.ui.login.UILoginController;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import model.IResult;
import model.communication.client_server.ConnectingServer;
import model.communication.peer.WaitingClient;
import model.type.Board;
import model.type.Player;
import model.type.Room;
import model.type.RoomList;
import model.type.enumeration.EPieceColors;
import model.type.piece.Piece;
import view.UIAlertView;
import view.room.UIRoomListView;

public class Main
{
    public static void main(String[] args) {
        App.getInstance().start();
        
//        App app = App.getInstance();
//        controller.ui.UIController uiController = 
//                new controller.ui.match.UIMatchController(
//                        EPieceColors.WHITE,
//                        Board.create(),
//                        "1304",
//                        new Player(0, "192.168.0.199", 2000)
//                );
//        
//        WaitingClient.asynchronousWaitingOn("192.168.0.190", 2000, 10);
//        
//        MessagesManager.setPeerMessageListener((controller.ui.match.UIMatchController)uiController);
        

//        controller.ui.UIController uiController = new UILoginController();
//        app.show(uiController.getContentView(), "đăng nhập");



//        controller.ui.room.UIRoomListController uicontroller = new controller.ui.room.UIRoomListController();
//        
//        uicontroller.setJoinRoomListener((Object obj) -> {
//            int roomID = (int)obj;
//            System.out.println(roomID);
//        });
//        
//        uicontroller.setEnterRoomListener((Object obj) -> {
//            String roomID = (String)obj;
//            System.out.println(roomID);
//        });
//        
//        uicontroller.setCreateRoomListener(() -> {
//            System.out.println("Create Room");
//        });
//        
//        app.show(uicontroller.getContentView(), "danh sách phòng");


    
//        UISubWindowController subWindowController = new UISubWindowController(
//                new UIAlertView());
//        subWindowController.show();


        
//        Board board = new Board();
//        board.init();
//        Piece piece = board.getPiece(EPositions.D2);
//        
//        ArrayList<EPositions> validPositions = piece.suggestMoving();
//        
//        System.out.println("Name: " + piece.getType());
//        System.out.println("Color: " + piece.getColor());
//        System.out.println("Position: " + piece.getPosition());
//        System.out.println("Number of suggestion: " + validPositions.size());
//        validPositions.forEach((position) -> {
//            System.out.println(position);
//        });
    }
    
}
