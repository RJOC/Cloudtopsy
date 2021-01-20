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

public interface DBWriteBroker {
    public void writeToDB(String instruction, Data data) throws IOException;
}
