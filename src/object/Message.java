/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.Serializable;

/**
 *
 * @author pedro.sirtoli
 */
public class Message implements Serializable{
    private String EmailSender;
    private String EmailReceiver;
    private String message;

    public Message(String EmailSender, String EmailReceiver, String message) {
        this.EmailSender = EmailSender;
        this.EmailReceiver = EmailReceiver;
        this.message = message;
    }
    
    public String getEmailSender() {
        return EmailSender;
    }

    public void setEmailSender(String EmailSender) {
        this.EmailSender = EmailSender;
    }

    public String getEmailReceiver() {
        return EmailReceiver;
    }

    public void setEmailReceiver(String EmailReceiver) {
        this.EmailReceiver = EmailReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
