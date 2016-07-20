/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import object.Connections;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author pedro.sirtoli
 */
public class MessageServer {
    public ArrayList<Connections> listConnections = new ArrayList();

    public MessageServer() {
        ServerSocket serverSocket;
        AcceptConnectionsServer threadAccept;
        
        try {
            System.out.println("Creating server...");
            serverSocket = new ServerSocket(26397);
            threadAccept = new AcceptConnectionsServer(serverSocket, this);
            threadAccept.start();
  
        } catch (Exception e) {
            
        }
    }
    
    public static void main(String[] args) {
        new MessageServer();
    }
    
}
