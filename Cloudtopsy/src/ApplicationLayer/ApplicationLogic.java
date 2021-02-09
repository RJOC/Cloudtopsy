/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: ApplicationLogic.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ApplicationLayer;

import static ApplicationLayer.AdminLogic.SALT;
import ModelLayer.ApplicationModel;
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Data;
import ModelLayer.DataAccess.DBReader;
import ModelLayer.DataAccess.DBWriter;
import ModelLayer.Users;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class ApplicationLogic {
    ApplicationModel model;
    
    private Users curUser; 
    private String uname;
    
    
    public ApplicationLogic(){
        model = new ApplicationModel();
    }
    
    public boolean login(String uname, String pword) throws IOException, ClassNotFoundException{
        String hashedPassword = generateHash(pword);
        boolean login =  DBReader.login(uname, hashedPassword);
        System.out.println();
        if(login == true){
            //user exists: call database to get user details
            Data curUser = model.dbRead("GetUser",uname);     
            if(curUser.getData().size()>0){
                CurrentUserSingleton.getInstance(curUser.getDataName(), Integer.parseInt(curUser.getData().get(0).get(0)),uname, "uFName","uLName");
            }else{
                //User exists but was not read in correctly; assign default.
                CurrentUserSingleton.getInstance();
            }
            return true;
        }else{
            return false;
        }
    }
    
    public boolean changePword(String oldPword, String newPword) throws IOException, ClassNotFoundException{
        
        //Singleton call
        curUser = CurrentUserSingleton.getInstance();
        uname = curUser.getuName();
        
        oldPword = generateHash(oldPword);
            
        boolean checkCurPword = DBReader.login(uname,oldPword); 
        
        if(checkCurPword){
            System.out.println("CheckCurPword is true!!");
            newPword = generateHash(newPword);
            boolean checkWrite = DBWriter.changePword(uname,newPword);
            
            if(checkWrite){
                return true;
            }else{
                return false;
            } 
        }else{
            System.out.println("CheckCurPword is false!!");
            return false;
        }
    }
    
    public String generateHash(String input){
        input = SALT + input;
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                            'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                    byte b = hashedBytes[idx];
                    hash.append(digits[(b & 0xf0) >> 4]);
                    hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
                // handle error here.
        }

        return hash.toString();
    }
    
    public void logout(){
        CurrentUserSingleton.logOut();
    }
    
}
