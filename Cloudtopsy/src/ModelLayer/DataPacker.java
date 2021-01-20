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
package ModelLayer;

import java.util.ArrayList;

/**
 *
 * @author oconn
 */
public class DataPacker implements CustomDataPacker{
    
    @Override
    public Data packData(CustomDataType data) {
        
        if(data instanceof Users){
            return packUsers(data);
        }
        /// Default return == unrecognised or not yet implemented type.
        return new Data();
    }
    
    private Data packUsers(CustomDataType data){
        Data packedUser = new Data();
        
        /// initialize the arraylist
        packedUser.getData().add(new ArrayList<>());
        
        if(data instanceof Admin){
            packedUser.setDataName("Manager");
            /// int uID, String uName, String firstName, String lastName
            packedUser.getData().get(0).add("" + ((Admin)(data)).getuID());
            packedUser.getData().get(0).add(((Admin)(data)).getuName());
            packedUser.getData().get(0).add(((Admin)(data)).getFirstName());
            packedUser.getData().get(0).add(((Admin)(data)).getLastName());          
        }
        else if(data instanceof Investigator){
            packedUser.setDataName("Teacher");
            
            packedUser.getData().get(0).add("" + ((Investigator)(data)).getuID());
            packedUser.getData().get(0).add(((Investigator)(data)).getuName());
        }
        else{
            /// Default User 
        }       
        return packedUser;
        
        
    }
}
