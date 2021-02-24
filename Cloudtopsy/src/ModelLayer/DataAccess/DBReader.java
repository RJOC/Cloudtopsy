/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: 
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
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DBReader implements DBReadBroker{
    
    @Override
    public Data readFromDB(String instruction, String keyWords){
        Data data = new Data();
        
        if (instruction.equals("GetUser")){      
            try {
                data = getUser(keyWords);
            } catch (IOException ex) {
                Logger.getLogger(DBReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBReader.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }
       
        return data;
    }
    
    //ClassNotFoundException note
    //https://www.java67.com/2015/07/javalangclassnotfoundexception-com.mysql.jdbc.Driver-solution.html
    
    public static boolean login(String uname, String pword) throws IOException, ClassNotFoundException{
        Connection connection;
        PreparedStatement ps;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("SELECT * FROM users WHERE uname = ? AND hashpword = ?");
            ps.setString(1, uname);
            ps.setString(2, pword);
            
            ResultSet result = ps.executeQuery();

            if( result.next()){
                System.out.println("SUCCESS");
                String username = result.getString("uname");
                String pwordhash = result.getString("hashpword");
                System.out.println("Username:"+username+"  hashpword:" + pwordhash);
                connection.close();
                 return true;
             }else{
                System.out.println("FAIL");
                connection.close();
                return false;
             }
        }catch (SQLException ex) {
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    
    private static  Data getUser(String uname) throws IOException, ClassNotFoundException{    
        Connection connection;
        PreparedStatement ps;
        Data data = new Data();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("SELECT * FROM users WHERE uname = ? ");
            ps.setString(1, uname);
 

            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                int uID  = result.getInt(1);
                String username = result.getString(2);
                String fname = result.getString(4);
                String lname = result.getString(5);
                int permID = result.getInt(7);

                data.getData().add(new ArrayList<>());
                /// int uID, String uName, String firstName, String lastName
                data.getData().get(0).add(Integer.toString(uID));
                data.getData().get(0).add(username);
                data.getData().get(0).add(fname);
                data.getData().get(0).add(lname);
                data.getData().get(0).add(Integer.toString(permID));

                if(data.getData().get(0).get(4).equals("1")){
                    data.setDataName("Admin");
                    System.out.println("Loading admin menu now... Singleton used!");
                    
                }else if (data.getData().get(0).get(4).equals("2")){
                    data.setDataName("Investigator");
                    System.out.println("Loading investigator menu now... Singleton used!");
                }
            }
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return data;
    }
    
    
    public static ArrayList<String> getUnameList(String curUname) throws ClassNotFoundException{
        //String [] unameList = {};
        ArrayList<String> unameList = new ArrayList<String>();
        
        Connection connection;
        PreparedStatement ps;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("SELECT uname FROM users WHERE uname NOT LIKE ?");
            ps.setString(1,curUname);
 

            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String username = result.getString(1);
                        System.out.println("The current user is equal to:::" + username);
                unameList.add(username);
            }
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return unameList;
        
    }
    
    public static ArrayList<String>  getCases() throws ClassNotFoundException {
        ArrayList<String>  caselist = new ArrayList<String>();
        Connection connection;
        PreparedStatement ps;
        
         try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("SELECT cname FROM cases");
            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                String casename = result.getString(1);
                System.out.println("The caseis equal to:::" + casename);
                caselist.add(casename);
            }
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return caselist;
    }
    
    public static String [] getCaseInfo(String cname) throws ClassNotFoundException{
        String[] resultinfo = new String[4];
        Connection connection;
        PreparedStatement ps;
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("SELECT cdbdir,opendate,closedate,cid FROM cases where cname = ?");
            ps.setString(1, cname);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                resultinfo[0] = result.getString(1);
                resultinfo[1] = result.getString(2);
                resultinfo[2] = result.getString(3);
                resultinfo[3] = result.getString(4);
            }
            
            if(resultinfo[2] == null|| resultinfo[2].equals(null)){
                resultinfo[2] = "Case is Open";
            }            
            connection.close();
        }catch (SQLException ex) {
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultinfo;
    }
   
    
    
}
