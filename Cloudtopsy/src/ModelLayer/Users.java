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
    private String uName, curDir;
    
    public Users(){
        this(-1,"empty", "");
    }
    
    public Users(int uID, String uName, String curDir){
        this.uID = uID;
        this.uName = uName;
        this.curDir = curDir;
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
    
    public void setCurDir(String curDir){
        this.curDir = curDir;
    }
    
    public String getCurDir(){
        return curDir;
    }
    
    @Override
    public String dataType(){
        return("Users");
    }
    
    
    
}
