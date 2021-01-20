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
    
    
    
    public static boolean login(String uname, String pword) throws IOException, ClassNotFoundException{
        Connection connection;
        PreparedStatement ps;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloudtopsy?zeroDateTimeBehavior=convertToNull","root","");
            ps = connection.prepareStatement("SELECT * FROM users WHERE uname = ? AND pword = ?");
            ps.setString(1, uname);
            ps.setString(2,pword);

            ResultSet result = ps.executeQuery();
            
            while(result.next()){
                int id  = result.getInt(1);
                String username = result.getString("uname");
            }
            
            if(result != null ){
                 return true;
             }else{
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
        }catch (SQLException ex) {
            Logger.getLogger(ApplicationLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
      return data;
    }
}
