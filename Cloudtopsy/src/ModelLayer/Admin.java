/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: Admin.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ModelLayer;

public class Admin extends Users {
    private String fName;
    private String lName;
    private int permissions;
    
    public Admin(){
        this(-1,"Default","Unknown","Unknown");
    }
    
    public Admin(int uID, String uName, String fName, String lName){
        super(uID, uName);
        this.fName = fName;
        this.lName = lName;
    }
    
    public String getFirstName(){
        return fName;
    }
    
    public void setFirstName(String fName){
        this.fName = fName;
    }
    
    public String getLastName() {
        return lName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }
    
    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }  
}
