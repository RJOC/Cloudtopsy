/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: Users.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */

package ModelLayer;

public class Users implements CustomDataType{
    private int uID;
    private String uName;
    
    public Users(){
        this(-1,"empty");
    }
    
    public Users(int uID, String uName){
        this.uID = uID;
        this.uName = uName;
    }
    
    public int getuID(){
        return uID;
    }
    
    public String getuName(){
        return uName;
    }
    
    public void setuID(int uID) {
        this.uID = uID;
    }
    
    public void setuName(String uName){
        this.uName = uName;
    }
    
    @Override
    public String dataType(){
        return("Users");
    }
    
}
