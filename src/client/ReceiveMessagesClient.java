/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import helper.SocketHelper;
import java.net.Socket;
import object.Message;

/**
 *
 * @author pedro.sirtoli
 */
public class ReceiveMessagesClient extends Thread{
    Socket socket;

    public ReceiveMessagesClient(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run(){
        String action;
        Message message;

        while(true){
            action = SocketHelper.readText(socket);
            if(action.equalsIgnoreCase("message")){
                message = (Message)SocketHelper.readObject(socket);
                
                System.out.println("Message received from "+message.getEmailSender()+" "+message.getMessage());
                
                SocketHelper.sendText(socket, "confirmation");
                
                SocketHelper.sendText(socket, message.getEmailSender());
                
            }
            else if(action.equalsIgnoreCase("confirmation")){
                System.out.println(SocketHelper.readText(socket));
            }
        }
    }
}
