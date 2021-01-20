/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: DataFactory.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ModelLayer;

public class DataFactory implements CustomDataFactory{
    
    @Override 
    public CustomDataType buildData(Data data){
        CustomDataType builtData = data;
        
        switch(data.getDataName()){
            case "Admin": builtData = buildUser(data);
                break;
            case "Investigator": builtData = buildUser(data);
                break;
            default:
                break;
        }
        return builtData;
    }
    
    private CustomDataType buildUser(Data data){
        CustomDataType build;
        
        switch (data.getDataName()) {
            case "Manager":
                build = new Admin();
                //int uID, String uName, String firstName, String lastName
                ((Admin)build).setuID(Integer.parseInt(data.getData().get(0).get(0)));
                ((Admin)build).setuName(data.getData().get(0).get(1));
                ((Admin)build).setFirstName(data.getData().get(0).get(2));
                ((Admin)build).setLastName(data.getData().get(0).get(3));
                break;
            case "Investigator":
                build = new Investigator();
                ((Investigator)build).setuID(Integer.parseInt(data.getData().get(0).get(0)));
                ((Investigator)build).setuName(data.getData().get(0).get(1));
                break;
            case "UsersList":
                //// users list should not pass through this method
                //// error.
            default:
                build = new Users();
                break;
        }
        
        return build;  
    }
}
