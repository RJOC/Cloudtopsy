/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CurrentUserSingleton.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ModelLayer;


public class CurrentUserSingleton {
    private static Users currentUser = null;
    
  
    private CurrentUserSingleton(){
        //Private constructor
        //There can only be one
    }
    
    
    public static Users getInstance(){
        if(currentUser == null){
            currentUser = new Users();
        }
        return currentUser;
    }
    
    
    public static Users getInstance(String uType,int uID, String uName, String uFName, String uLName){
        
        //Lazy initialization #GangOfFour
        if(currentUser == null){
            switch(uType){
                case "Admin": //admin permission ID is 1
                    currentUser = new Admin(uID, uName, uFName, uLName);
                    break;
                case "Investigator": //invest permission IF is 2
                    currentUser = new Investigator(uID, uName,uFName, uLName);
                    break;
                    
                default:
                    currentUser = new Users();
                    break;         
            }
        }
        return currentUser;
    }
    
    public static void logOut(){
        currentUser = null;
    }
    
}
