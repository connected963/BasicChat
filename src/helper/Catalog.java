/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.net.Socket;
import java.util.ArrayList;
import object.Connections;

/**
 *
 * @author pedro.sirtoli
 */
public class Catalog {
    private static Catalog instance;
    
    public static Catalog getInstance(){
        if(instance==null){
            instance = new Catalog();
        }
        return instance;
    }
    
    public String[] splitEmail(String reciever){        
        return reciever.split(";");
    }
    
    public Connections findConnectionById(ArrayList<Connections> list, String email){
        Connections connections = null;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getClientEmail().equals(email)){
                connections = list.get(i);
            }
        }
        return connections;
    }
}
