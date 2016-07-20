/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import helper.Catalog;
import helper.SocketHelper;
import object.Connections;
import object.Message;

/**
 *
 * @author pedro.sirtoli
 */
public class SendMessagesServer extends Thread{
    
    private final Connections connections;
    private final MessageServer messageServer;
    private final Message message;

    public SendMessagesServer(Connections connections, MessageServer messageServer, Message message) {
        this.connections = connections;
        this.messageServer = messageServer;
        this.message = message;
    }
       
    @Override
   public void run(){
        String[] recievers = Catalog.getInstance().splitEmail(message.getEmailReceiver());
        try{
            for (int i = 0; i < recievers.length; i++) {
                for (int j = 0; j < messageServer.listConnections.size(); j++) {
                    if(recievers[i].equals(messageServer.listConnections.get(j).getClientEmail())){
                        
                        System.out.println("Sending messages from "+messageServer.listConnections.get(j).getClientEmail());
                        
                        SocketHelper.sendText(messageServer.listConnections.get(j).getClientSocket(), "Message");
                        SocketHelper.sendObject(messageServer.listConnections.get(j).getClientSocket(), message);
                        break;
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
   }
    
}
