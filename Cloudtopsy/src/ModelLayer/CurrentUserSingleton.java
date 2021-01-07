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
    
    public static Users getInstance(String uType, String uName, String fullName){
        
        //Lazy initialization #GangOfFour
        if(currentUser == null){
            switch(uType){
                case "Admin":
                    currentUser = new Admin(uID, uName, fName);
                    break;
                case "Investigator":
                    currentUser = new Investigator(uID, fName);
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
