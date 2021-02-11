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

import java.util.List;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.Image;

/**
 *
 * @author oconn
 */
public class BACKUPjava {
    
    public boolean BACKUPjava(){
        
        
            // print out all the images found, and their children
            List<Image> images = sk.getImages();
            for (Image image : images) {
                    System.out.println("Found image: " + image.getName());
                    System.out.println("There are " + image.getChildren().size() + " children.");
                    for (Content content : image.getChildren()) {
                            System.out.println('"' + content.getName() + '"' + " is a child of " + image.getName());
                    }
            }

            // print out all .txt files found
            List<AbstractFile> files = sk.findAllFilesWhere("LOWER(name) LIKE LOWER('%.txt')");
            for (AbstractFile file : files) {
                    System.out.println("Found text file: " + file.getName());
            }
            
    }
    
    
}
