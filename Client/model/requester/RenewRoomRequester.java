/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.requester;

/**
 *
 * @author minhtri1396
 */
public class RenewRoomRequester implements Requester {
    public static final NewRoomRequester Instance = new NewRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
