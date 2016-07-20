/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.net.Socket;
import java.security.PublicKey;

/**
 *
 * @author pedro.sirtoli
 */
public class Connections {
    private Socket clientSocket;
    private String clientEmail;

    public Connections(Socket clientSocket, String clientEmail) {
        this.clientSocket = clientSocket;
        this.clientEmail = clientEmail;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
   
}
