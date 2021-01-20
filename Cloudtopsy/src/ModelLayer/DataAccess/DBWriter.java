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

import ModelLayer.Data;
import java.io.IOException;

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
                registerUser("email", "firstName", "lastName", "pword", Integer.parseInt(data.getData().get(0).get(0)), data.getData().get(0).get(1));
            }
            
        }else if(instruction.equals("RegisterClass")){
            /// TODO: implement method.
        }
    }
    
     private static boolean registerUser(String email, String fname, String sname, String pword, int perID, String uname) throws IOException{
     
         
         return true;
     }
        
        
    
}
