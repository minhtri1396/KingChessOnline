package main;

import model.WaitingClient;

public class Main {

    public static void main(String[] args) {
        WaitingClient.asynchronousWaitingOn("127.0.0.1", 3000, 100);
    }
    
}
