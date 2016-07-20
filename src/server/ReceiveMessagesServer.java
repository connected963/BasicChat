/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import helper.SocketHelper;
import object.Connections;
import object.Message;

/**
 *
 * @author pedro.sirtoli
 */
public class ReceiveMessagesServer extends Thread{
    
    private final Connections connections;
    private final MessageServer messageServer;

    public ReceiveMessagesServer(Connections connections, MessageServer messageServer) {
        this.connections = connections;
        this.messageServer = messageServer;
    }
    
    @Override
    public void run(){
        Message message = null;
        
        while(true){
            System.out.println("Waiting for contact from "+connections.getClientSocket().getInetAddress());
            String action = SocketHelper.readText(connections.getClientSocket());
            if(action.equalsIgnoreCase("message")){
                System.out.println("Waiting for message from "+connections.getClientSocket().getInetAddress());
                message = (Message)SocketHelper.readObject(connections.getClientSocket());
                
                new SendMessagesServer(connections, messageServer, message).start();
            }
            else if(action.equalsIgnoreCase("confirmation")){
                System.out.println("Waiting for confirmation from "+connections.getClientSocket().getInetAddress());
                new CheckReceivedMessageServer(connections, messageServer, SocketHelper.readText(connections.getClientSocket())).start();
            }
            else if(action.equalsIgnoreCase("contactOnline")){
                System.out.println("Sending contacts list from "+connections.getClientSocket().getInetAddress());
                SocketHelper.sendObject(connections.getClientSocket(), messageServer.listConnections);
            }
            else if(action.equalsIgnoreCase("exit")){
                System.out.println("Disconnecting from "+connections.getClientSocket().getInetAddress());
                messageServer.listConnections.remove(connections);
                break;
            }             
        }
    }
    
}
