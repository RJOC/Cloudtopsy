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

import ModelLayer.ApplicationModel;
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Data;
import ModelLayer.DataAccess.DBReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ApplicationLogic {
    ApplicationModel model;
    
    public ApplicationLogic(){
        model = new ApplicationModel();
    }
    
    public boolean login(String uname, String pword) throws IOException, ClassNotFoundException{
        boolean login =  DBReader.login(uname, pword);
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
    
    public boolean changePword(String oldPword, String newPword){
        return true;
    }
    
    public boolean removeUser(){
        
    }
       
    
    public void logout(){
        CurrentUserSingleton.logOut();
    }
    
}
