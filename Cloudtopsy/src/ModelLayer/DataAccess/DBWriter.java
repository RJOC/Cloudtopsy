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
import java.sql.SQLException;
import java.sql.Statement;
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
     
    public static boolean createUser(String uname, String fname, String lname, String email, String pword, int permID){
        boolean result = false;
        Connection connection;
        PreparedStatement ps; 
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("INSERT INTO users (uname,hashpword,fname,sname,email,permissionID)VALUES(?,?,?,?,?,?)");
            ps.setString(1,uname);
            ps.setString(2,pword);
            ps.setString(3,fname);
            ps.setString(4,lname);
            ps.setString(5,email);
            ps.setInt(6,permID);
            ps.execute();
            result = true;
            connection.close();
            
        }catch (Exception ex){
            System.out.println(ex);
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
            
            System.out.println(result + "inside DBWriter");
        }
        
        return result;
    }
     
     
     public static boolean removeUser(String uname){
        boolean result = false;
        Connection connection;
        PreparedStatement ps; 
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("DELETE FROM users WHERE uname = ?");
            ps.setString(1,uname);
            if(ps.execute()){
                result = true;
            }
            connection.close();
            
        }catch (Exception ex){
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
         
         
         
         
         return result;
     }
     
     public static boolean addCase(String cname, String cdesc, String cdbdir, String curUName){
         boolean result = false;
         
        Connection connection;
        PreparedStatement ps; 
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("INSERT INTO cases(cname, cdesc, cdbdir, userID, opendate) VALUES (?, ?, ?, (select userID from users where uname = ?), CURDATE());");
            ps.setString(1,cname);
            ps.setString(2,cdesc);
            ps.setString(3,cdbdir);
            ps.setString(4,curUName);
            if(ps.execute()){
                result = true;
            }
            connection.close();
            
        }catch (Exception ex){
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
         
         
         return result;
     }
     
     
     public static boolean addFinding(Object[][] recordArr, String curDir) throws SQLException, ClassNotFoundException{
        boolean result = false;
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
        PreparedStatement ps;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            ps = connection.prepareStatement("INSERT INTO casedata(cid, fid, fname, fdir) VALUES ((SELECT cid FROM cases WHERE cdbdir = ?),?,?,?);");
            connection.setAutoCommit(false);
        
            int i = 0;
            for(Object data: recordArr){
                if(recordArr[i][0] != null){
                    //do nothing
                }else{
                    recordArr[i][0] = 0;
                }
                if(recordArr[i][1] != null){
                    //do nothing
                }else{
                    recordArr[i][1] = "Null";
                }
                if(recordArr[i][2] != null){
                    //do nothing
                }else{
                    recordArr[i][2] = "Null";
                }
                
                ps.setString(1,curDir);
                ps.setString(2,recordArr[i][0].toString());
                ps.setString(3,recordArr[i][1].toString());
                ps.setString(4,recordArr[i][2].toString());
                ps.addBatch();
                i++;
            }
            int[] results=  ps.executeBatch();
            connection.commit();
            connection.close();
            result = true;
        }catch(Exception e){
            e.printStackTrace();
            connection.rollback();
        }
        return result;
     }   
}
