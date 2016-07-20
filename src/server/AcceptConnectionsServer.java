/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import helper.SocketHelper;
import object.Connections;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

/**
 *
 * @author pedro.sirtoli
 */
public class AcceptConnectionsServer extends Thread{
    private final ServerSocket serverSocket;
    private final MessageServer messageServer;
    
    public AcceptConnectionsServer(ServerSocket serverSocket, MessageServer messageServer) {
        this.serverSocket = serverSocket;
        this.messageServer = messageServer;
    }
    
    @Override
    public void run(){
        try {
            while(true){
                Socket socket;
                System.out.println("Waiting for connection...");
                socket = serverSocket.accept();
                System.out.println("Connected with "+socket.getInetAddress());
                System.out.println("Customer recognition...");

                Connections connections = new Connections(socket, SocketHelper.readText(socket));
                messageServer.listConnections.add(connections);
                new ReceiveMessagesServer(connections, messageServer).start();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
