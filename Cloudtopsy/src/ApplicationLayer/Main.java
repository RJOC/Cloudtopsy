/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name:          Main.java
 * Class decription:    
 *      Calls the Cloudtopsy GUI frames 
 *      Instanciates applicaionlogic instance to pass into the frames
 * 
 */
package ApplicationLayer;

import java.io.IOException;
import cloudtopsy.Cloudtopsy;

public class Main{
    
     public static void main(String[] args) throws IOException {
        ApplicationLogic logic = new ApplicationLogic();
        Cloudtopsy applicationInstance = new Cloudtopsy(logic);
     }
}
