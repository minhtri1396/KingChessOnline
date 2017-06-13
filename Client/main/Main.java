package main;

import controller.communication.MessagesManager;
import controller.ui.UISubWindowController;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import model.communication.peer.WaitingClient;
import model.type.Board;
import model.type.Player;
import model.type.enumeration.EPieceColors;
import model.type.piece.Piece;
import view.UIAlertView;

public class Main
{
    public static void main(String[] args) {
        App app = App.getInstance();
        controller.ui.UIController uiController = 
                new controller.ui.match.UIMatchController(
                        EPieceColors.BLACK,
                        Board.create(),
                        "1304",
                        new Player(0, "192.168.0.141", 2000)
                );
        
        WaitingClient.asynchronousWaitingOn("192.168.0.110", 2000, 10);
        
        MessagesManager.setPeerMessageListener((controller.ui.match.UIMatchController)uiController);
        
        app.show(uiController.getContentView(), "đăng nhập");


    
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
