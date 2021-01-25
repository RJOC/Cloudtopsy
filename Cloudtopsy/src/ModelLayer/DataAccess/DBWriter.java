/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: DBWriter.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ModelLayer.DataAccess;

import ApplicationLayer.ApplicationLogic;
import ModelLayer.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oconn
 */
public class DBWriter implements DBWriteBroker{
    
    @Override
    public void writeToDB(String instruction, Data data) throws IOException {
        if(instruction.equals("RegisterUser")){
            if(data.getDataName().equals("Admin")){
                /// email, firstName, LastName, password, perID, username
                registerUser("email", data.getData().get(0).get(2), data.getData().get(0).get(3), "pword", Integer.parseInt(data.getData().get(0).get(0)), data.getData().get(0).get(1));
            }else if(data.getDataName().equals("Investigator")){
                registerUser("email", "firstName", "lastName", "hashpword", Integer.parseInt(data.getData().get(0).get(0)), data.getData().get(0).get(1));
            }
            
        }else if(instruction.equals("RegisterClass")){
            /// TODO: implement method.
        }
    }
    
     private static boolean registerUser(String email, String fname, String sname, String pword, int perID, String uname) throws IOException{
     
         
         return true;
     }
     
     public static boolean changePword(String uname, String newPword) throws ClassNotFoundException{
        Connection connection;
        PreparedStatement ps;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("UPDATE users SET hashPword = ? WHERE uname = ?");
            ps.setString(1,newPword);
            ps.setString(2,uname);
            ps.execute();
            connection.close();
            return true;
            
        }catch (Exception ex){
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
     }
     
     
     public static boolean removeUser(String uname){
         boolean result = false;
         
         
         
         
         return result;
     }
     
     
     public static boolean connectToDB()throws ClassNotFoundException{
        boolean result = false;
        Connection connection;
        PreparedStatement ps;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            
            
            result = true;
        }catch(Exception ex){
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        
        return result;
     }
}
