/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import client.MessageClient;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.sirtoli
 */
public class SocketHelper {
    
    public static void sendText(Socket socket, String text){
        DataOutputStream dataOutputStream;

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendObject(Socket socket, Object object){
        ObjectOutputStream objectOutputStream;
        
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static String readText(Socket socket){
        String text = null;
        DataInputStream dataInputStream;
        
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            text = dataInputStream.readUTF();            
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }
    
    public static Object readObject(Socket socket){
        Object object = null;
        ObjectInputStream objectInputStream;
        
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return object;
    }
}
