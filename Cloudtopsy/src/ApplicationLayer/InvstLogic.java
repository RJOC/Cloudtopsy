/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: InvstLogic.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ApplicationLayer;

import cloudtopsy.CTCreateCase;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.Image;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.SleuthkitJNI;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskDataException;

public class InvstLogic extends ApplicationLogic{
    
    public boolean storeCaseData(String cname,String cdesc,String cimagepath,String cdbpath){
        boolean result = false;
        result = true;
        return result;
    }
    
    public boolean createCase(String imagepath){
        boolean result = false;
        try{
            //Creates the database and configures it
            SleuthkitCase sk = SleuthkitCase.newCase(imagepath + ".db");
            
            //Timezone input
            String timezone = "";

            //Add image process, number of steps process but this returns an object that allows it to happen
            //Timezone, addUnallocSpace, noFatFsOrphans
            SleuthkitJNI.CaseDbHandle.AddImageProcess process = sk.makeAddImageProcess(timezone,false,false,"");


            //Creates a nwe arraylist of strings
            ArrayList<String> paths = new ArrayList<String>();
            //adds the image path to the array list
            paths.add(imagepath);

            //Trying to do something
            try{
                System.out.println("Logger output 1");
                process.run(UUID.randomUUID().toString(), paths.toArray(new String[paths.size()]), 0);
                result = true;
                System.out.println("Logger end 1");
                 
            } catch(TskDataException ex){
                System.out.println("Logger output 2");
                Logger.getLogger(CTCreateCase.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Logger end 2");
            }

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

        }catch(TskCoreException e){ //Something major happened
            System.out.println("Exception caught: " + e.getMessage());
            InvstLogic.usage(e.getMessage());
        }     
        return result;
        
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
