/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: AdminLogic.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ApplicationLayer;

import ModelLayer.CurrentUserSingleton;
import ModelLayer.DataAccess.DBReader;
import ModelLayer.DataAccess.DBWriter;
import ModelLayer.Users;
import java.util.ArrayList;

public class AdminLogic extends ApplicationLogic{
    
    public static final String SALT = "cloudtopsy-salt-text";
    //Sinleton related
    private Users curUser; 
    private String uname;
    
    
    public boolean createUser(String uname, String fname, String lname, String email, String pword, int permID){
        //Call to db to create the user 
        String hashedPassword = generateHash(pword);
        boolean checkCreateUser = DBWriter.createUser(uname, fname, lname, email, hashedPassword, permID);
        
        if(checkCreateUser){
            return true;
        }else{
            System.out.println("CHECKCREATEUSER_ADLOGIC");
            return false;
        }
    }
   
    //Gets list of users but takes out CurrentUserSingleton one
    public ArrayList<String> getAllUsers() throws ClassNotFoundException{
        
        //Singleton call
        curUser = CurrentUserSingleton.getInstance();
        uname = curUser.getuName();
        
        ArrayList<String> usersList = DBReader.getUnameList(uname);
        return usersList;
    }
    
    public boolean removeUser(String uname){
        boolean checkRemove = DBWriter.removeUser(uname);
        return true;
    }
    
}
