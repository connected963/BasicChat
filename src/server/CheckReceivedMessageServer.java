/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import object.Connections;
import helper.Catalog;
import helper.SocketHelper;

/**
 *
 * @author pedro.sirtoli
 */
public class CheckReceivedMessageServer extends Thread{
    private Connections sender, receiver;
    private final MessageServer messageServer;
    private final String senderEmail;

    public CheckReceivedMessageServer(Connections receiver, MessageServer messageServer, String sender) {
        this.receiver = receiver;
        this.messageServer = messageServer;
        this.senderEmail = sender;
    }
    
    @Override
    public void run(){
        sender = Catalog.getInstance().findConnectionById(messageServer.listConnections, senderEmail);
        SocketHelper.sendText(sender.getClientSocket(), "confirmation");
        SocketHelper.sendText(sender.getClientSocket(), "Received - "+receiver.getClientEmail());   
    }
}
