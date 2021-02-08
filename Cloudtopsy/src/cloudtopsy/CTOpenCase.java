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
package cloudtopsy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.Examples.Sample;
import org.sleuthkit.datamodel.Image;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.SleuthkitJNI;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskDataException;

/**
 *
 * @author oconn
 */
public class CTOpenCase {
    
    
    
    private static final Logger LOGGER = Logger.getLogger(CTCreateCase.class.getName());
    private static SleuthkitCase caseDB;
    private static String imagePath = "E:\\My Backups\\Windows10img.E01";
    
    //private static String imagePath = "C:\\sqlite\\db\\ryans.iso";
    
    
    public CTOpenCase() throws SQLException{        
        try{

            //Opens an existing db
            SleuthkitCase existingCase = SleuthkitCase.openCase(imagePath + ".db");
            
            // print out all the images found, and their children
            List<Image> images = existingCase.getImages();
            for (Image image : images) {
                    System.out.println("Found image: " + image.getName());
                    System.out.println("There are " + image.getChildren().size() + " children.");
                    for (Content content : image.getChildren()) {
                            System.out.println('"' + content.getName() + '"' + " is a child of " + image.getName());
                    }
            }
            
            // print out all .txt files found
            List<AbstractFile> files = existingCase.findAllFilesWhere("LOWER(name) LIKE LOWER('%.dll')");
            for (AbstractFile file : files) {
                    System.out.println("Found dll file: " + file.getName());
            }
                
        }catch(TskCoreException e){ //Something major happened
            System.out.println("Exception caught: " + e.getMessage());
            Sample.usage(e.getMessage());
        }
    }
    
    
    
    
    
    
    
    public static void usage(String error){
        System.out.println("Usage: ant -Dimage:{image string} run-sample");
        System.out.println(error);
        if (error.contains("deleted first")) {
                System.out.println("A database for the image already exists. Delete it to run this sample again.");
        } else if (error.contains("unable to open database")) {
                System.out.println("Image must be encapsulated by double quotes. Ex: ant -Dimage=\"C:\\Users\\You\\image.E01\" run-sample");
        }
    }
    
}
