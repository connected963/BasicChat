/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author pedro.sirtoli
 */
public class CryptographyHelper {
    private static CryptographyHelper instance;
    private String algorithm;
    private String privateKeyPath;
    private String publicKeyPath;
    static String IV = "AAAAAAAAAAAAAAAA";
    
    public static CryptographyHelper GetInstance(){
        if(instance==null){
            instance = new CryptographyHelper();
        }
        return instance;
    }
    
    public void buildKeyRSA(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            KeyPair keyPair;
            File privateKeyFile, publicKeyFile;
            ObjectOutputStream objectOutputStream;
            
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.genKeyPair();
            
            privateKeyFile = new File(privateKeyPath);
            publicKeyFile = new File(publicKeyPath);
            
            if(!privateKeyFile.getParentFile().exists() && privateKeyFile.getAbsoluteFile() != null){
                privateKeyFile.mkdirs();
            }
            if(!publicKeyFile.getParentFile().exists() && publicKeyFile.getAbsoluteFile() != null){
                publicKeyFile.mkdirs();
            }
            
            privateKeyFile.createNewFile();
            publicKeyFile.createNewFile();
            
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            objectOutputStream.writeObject(keyPair.getPublic());
            objectOutputStream.close();
            
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            objectOutputStream.writeObject(keyPair.getPrivate());
            objectOutputStream.close();
            
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean keyRSAExists(){
        File privateKeyFile = new File(privateKeyPath), publicKeyFile = new File(publicKeyPath);
        
        if(privateKeyFile.exists() && publicKeyFile.exists()){
            return true;
        }
        return false;
    }
    
    public byte[] cryptographyTextRSA(String text, PublicKey key){
        byte[] cipherText = null;
        Cipher cipher; 
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return cipherText;
    }
    
    public File cryptographyFileRSA(File file, PublicKey key){
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        byte[] cipherText = null, temp = null;
        Cipher cipher; 
        try {
            temp = new byte[100];
            
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file.getPath()+"encrypted");
            int cont;
            while((cont = fileInputStream.read(temp, 0, 100))>=0){
                cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                cipherText = cipher.doFinal(temp,0 ,100);
                fileOutputStream.write(cipherText);
                fileOutputStream.flush();
            }
            fileInputStream.close();
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return new File(file.getPath()+"encrypted");
    }
    
    public String decryptTextRSA(byte[] text, PrivateKey key){
        Cipher cipher;
        
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            text = cipher.doFinal(text);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return new String(text);
    }
    
    public File decryptFileRSA(File file, PrivateKey key){
        Cipher cipher;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        byte[] bytes = new byte[256];
        try {
            
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
         
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file.getPath().substring(0, file.getPath().length()-9));
            int cont;
            while((cont = fileInputStream.read(bytes))>=0){
                fileOutputStream.write(cipher.doFinal(bytes));
            }
            
            fileOutputStream.close();   
            fileInputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return new File(file.getPath().substring(0, file.getPath().length()-9));
    }
    
    public SecretKeySpec buildKeyAES(){
        KeyGenerator keyGenerator;
        SecretKey secretKey;
        SecretKeySpec secretKeySpec = null;
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(256);
            
            secretKey = keyGenerator.generateKey();
            secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), algorithm);   
        } catch (Exception e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return secretKeySpec;
    }
    
    public byte[] encryptTextAES(SecretKeySpec secretKeySpec, String text){
        byte[] bytes = null;
        Cipher cipher;
        
        try {
            cipher = Cipher.getInstance(algorithm);
            
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);       
            bytes = cipher.doFinal(text.getBytes());          

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return bytes;
    }
    
    public String decryptTextAES(SecretKeySpec secretKeySpec, byte[] bytes){
        Cipher cipher;    
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);       
            bytes = cipher.doFinal(bytes);           
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return new String(bytes);
    }
    
     public File cryptographyFileAES(File file, SecretKeySpec secretKeySpec){
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        byte[] cipherText = new byte[256];
        Cipher cipher; 
        
        try {
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file.getPath()+"encrypted");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes("UTF-8")));
            int cont;
            while((cont = fileInputStream.read(cipherText))>=0){
                fileOutputStream.write(cipher.update(cipherText, 0, cont));
                fileOutputStream.flush();
            }
            fileOutputStream.write(cipher.doFinal());
            fileInputStream.close();
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return new File(file.getPath()+"encrypted");
    }
     
    public File decryptFileAES(File file, SecretKeySpec secretKeySpec){
        Cipher cipher;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        byte[] bytes = new byte[256];
        try {
            
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes("UTF-8")));
         
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file.getPath().substring(0, file.getPath().length()-9));
            int cont;
            while((cont = fileInputStream.read(bytes))>=0){
                fileOutputStream.write(cipher.update(bytes, 0 , cont));
            }
            fileOutputStream.write(cipher.doFinal(bytes));
            
            fileOutputStream.close();   
            fileInputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            Logger.getLogger(CryptographyHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return new File(file.getPath().substring(0, file.getPath().length()-9));
    }
    
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }
}