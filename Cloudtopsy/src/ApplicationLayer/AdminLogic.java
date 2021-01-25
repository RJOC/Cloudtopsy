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

public class AdminLogic extends ApplicationLogic{
    
    public static final String SALT = "cloudtopsy-salt-text";
            
    public boolean createUser(String uname, String fname, String lname, String email, String pword, int permID){
        //Call to db to create the user 
        String saltedPassword = SALT + pword;
        String hashedPassword = generateHash(saltedPassword);
        return true;
    }
   
    
    public String [] getAllUsers(){
        String [] users = {"Ryan","Matt","Conor"};
        
        return users;
    }
    
}
