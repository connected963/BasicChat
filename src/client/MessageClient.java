/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import helper.SocketHelper;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import layout.temp;
import object.Connections;
import object.Message;

/**
 *
 * @author pedro.sirtoli
 */
public class MessageClient{
    String emailIn;
    public MessageClient() {
        Socket socket;
        temp tmp = new temp();
        String receiver = null;
        
        try {
            socket = new Socket("localhost", 26397);
            
            System.out.println("Digite se e-mail");
            emailIn = tmp.readText();
            
            SocketHelper.sendText(socket, emailIn);
            
            new ReceiveMessagesClient(socket).start();
            while(true){
                //buildMenu(socket);
                System.out.println("Digite o E-mail do usuário para enviar uma mensagem, Atualizar para atualizar ou sair para sair");
                receiver = tmp.readText();
                if(!receiver.equalsIgnoreCase("sair") && !receiver.equalsIgnoreCase("atualizar")){
                    sendMessage(socket, receiver);
                }
                else if(!receiver.equalsIgnoreCase("atualizar")){
                    exit(socket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private void sendMessage(Socket socket, String receiver){
        Message message;
        temp tmp = new temp();
        String text;
        
        text = tmp.readText();
        message = new Message(emailIn, receiver, text);
        
        SocketHelper.sendText(socket, "message");
        SocketHelper.sendObject(socket, message);           
  
    }
    
    private void exit(Socket socket){
        try {
            SocketHelper.sendText(socket, "exit");
            socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(MessageClient.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    private void buildMenu(Socket socket){
        ArrayList<Connections> connections;
        SocketHelper.sendText(socket, "contactOnline");
        connections = (ArrayList<Connections>)SocketHelper.readObject(socket);
        for (int i = 0; i < connections.size(); i++) {
            System.out.println(connections.get(i).getClientEmail());
        }
    }
    
    public static void main(String[] args) {
        new MessageClient();
    }
}
