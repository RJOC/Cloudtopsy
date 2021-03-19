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

import ModelLayer.CurrentUserSingleton;
import ModelLayer.DataAccess.DBReader;
import ModelLayer.DataAccess.DBWriter;
import ModelLayer.Users;
import cloudtopsy.CTCreateCase;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.Image;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.SleuthkitJNI;
import org.sleuthkit.datamodel.TagName;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskData;
import org.sleuthkit.datamodel.*;
import org.sleuthkit.datamodel.TskDataException;

public class InvstLogic extends ApplicationLogic{
    
    
     //Sinleton related
    private Users curUser; 
    
    
    public boolean storeCaseData(String cname,String cdesc,String cimagepath,String cdbpath){
        boolean result = false;
        result = true;
        return result;
    }
    
    public boolean createCase(String imagepath, String cname, String cdesc, String cdbpath){
        boolean result = false;
        try{
            //Creates the database and configures it
            SleuthkitCase sk = SleuthkitCase.newCase(imagepath + ".db");
            
            //Timezone input
            String timezone = "";
            curUser = CurrentUserSingleton.getInstance(); 
            String curUName = curUser.getuName();

            //Add image process, number of steps process but this returns an object that allows it to happen
            //Timezone, addUnallocSpace, noFatFsOrphans
            SleuthkitJNI.CaseDbHandle.AddImageProcess process = sk.makeAddImageProcess(timezone,false,false,"");


            //Creates a nwe arraylist of strings
            ArrayList<String> paths = new ArrayList<String>();
            //adds the image path to the array list
            paths.add(imagepath);

            //Trying to do something
            try{
                //System.out.println("Logger output 1");
                process.run(UUID.randomUUID().toString(), paths.toArray(new String[paths.size()]), 0);
                DBWriter.addCase(cname, cdesc, cdbpath, curUName);
                result = true;
                //System.out.println("Logger end 1");
                 
            } catch(TskDataException ex){
                //System.out.println("Logger output 2");
                Logger.getLogger(CTCreateCase.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("Logger end 2");
            }

//            // print out all the images found, and their children
//            List<Image> images = sk.getImages();
//            for (Image image : images) {
//                    System.out.println("Found image: " + image.getName());
//                    System.out.println("There are " + image.getChildren().size() + " children.");
//                    for (Content content : image.getChildren()) {
//                            System.out.println('"' + content.getName() + '"' + " is a child of " + image.getName());
//                    }
//            }

        }catch(TskCoreException e){ //Something major happened
            System.out.println("Exception caught: " + e.getMessage());
            InvstLogic.usage(e.getMessage());
        }     
        return result;
        
    }
    
    public ArrayList<String[]> getFiles(Object selected, String imagepath) throws TskCoreException, SQLException{
         SleuthkitCase existingCase = SleuthkitCase.openCase(imagepath);
        
        ArrayList<String[]> fileDataList = new ArrayList<String[]>();
        List<AbstractFile> files;
            // print out all the images found, and their children
        List<Image> images = existingCase.getImages();
        for (Image image : images) {
            System.out.println("Found image: " + image.getName());
            System.out.println("There are " + image.getChildren().size() + " children.");
            for (Content content : image.getChildren()) {
                System.out.println('"' + content.getName() + '"' + " is a child of " + image.getName());
            }
        }

            files = existingCase.findAllFilesWhere("LOWER(name) LIKE LOWER('%"+selected+"%')");
  
        
        for (AbstractFile file : files) {
            String[] fileData = new String[3];
            fileData[0] = (String.valueOf(file.getId()));
            fileData[1] = file.getName();
            fileData[2] = file.getParentPath();
            fileDataList.add((String[])fileData);
        }
        existingCase.close();
        return fileDataList;
    }
    
    public ArrayList<String[]> getExtFiles(Object selected, String imagepath) throws TskCoreException, SQLException{
        SleuthkitCase existingCase = SleuthkitCase.openCase(imagepath);
 
        ArrayList<String[]> fileDataList = new ArrayList<String[]>();
        
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
        List<AbstractFile> files = existingCase.findAllFilesWhere("LOWER(name) LIKE LOWER('%" + selected + "%')");
        
        
        for (AbstractFile file : files) {
            String[] fileData = new String[3];
            fileData[0] = (String.valueOf(file.getId()));
            fileData[1] = file.getName();
            fileData[2] = file.getParentPath();
            fileDataList.add((String[])fileData);
        }
        existingCase.close();
        return fileDataList;
    }
    
        public ArrayList<String[]> getExtDir(Object selected, String imagepath) throws TskCoreException, SQLException{
        SleuthkitCase existingCase = SleuthkitCase.openCase(imagepath);
 
        ArrayList<String[]> fileDataList = new ArrayList<String[]>();
        
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
        List<AbstractFile> files = existingCase.findAllFilesWhere("LOWER(parent_path) LIKE LOWER('%" + selected + "%')");
            System.out.println("this is a big test :" + selected + ":end of test");
        
        for (AbstractFile file : files) {
            String[] fileData = new String[3];
            fileData[0] = (String.valueOf(file.getId()));
            fileData[1] = file.getName();
            fileData[2] = file.getParentPath();
            fileDataList.add((String[])fileData);
        }
        existingCase.close();
        return fileDataList;
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
    
    public static ArrayList<String[]> checkCloudUse(String imagepath) throws TskCoreException, SQLException, ClassNotFoundException{
        
        SleuthkitCase existingCase = SleuthkitCase.openCase(imagepath);
        ArrayList<String[]> fileDataList = new ArrayList<String[]>();
        String[] fileData = {};
        
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
        List<AbstractFile> files = existingCase.findAllFilesWhere("LOWER(parent_path) LIKE LOWER('%%')");
 
        
        Object [][] recordArr = new Object[files.size()][3];
        int i = 0;

       for(AbstractFile file: files){
   
            fileData = new String[4];
            fileData[0] = (String.valueOf(file.getId()));
            fileData[1] = file.getName();
            fileData[3] = file.getParentPath();
           
            //Dropbox
            if(fileData[1].contains("dropbox")){
                fileData[2] = "Dropbox";
                //System.out.println("It has dropbox file");
                i++;
            }else if(fileData[3].contains("dropbox")){
                fileData[2] = "Dropbox";
                //System.out.println("It has dropbox dirr");
                i++;
            }
            
            //google drive
            else if(fileData[1].contains("googledrive")){
                fileData[2] = "GoogleDrive";
                //System.out.println("It has Google Drive file");
                i++;
            }else if(fileData[3].contains("googledrive")){
                fileData[2] = "Google Drive";
                //System.out.println("It has Google Drive dirr");
                i++;
            }
            
            //evernote
            else if(fileData[1].contains("evernote")){
                //System.out.println("found element");
                //System.out.println(fileData[1]);
                fileData[2] = "evernote";
                //System.out.println("It has evernote file");
                i++;
            }else if(fileData[3].contains("evernote")){
                //System.out.println("found element");
                //System.out.println(fileData[3]);
                fileData[2] = "evernote";
                //System.out.println("It has evernote dirr");
                i++;
            }
            
            //one drive
            else if(fileData[1].contains("onedrive")){
                //System.out.println("found element");
                //System.out.println(fileData[1]);
                fileData[2] = "OneDrive";
                //System.out.println("It has onedrive file");
                i++;
            }else if(fileData[3].contains("onedrive")){
                //System.out.println("found element");
                //System.out.println(fileData[3]);
                fileData[2] = "OneDrive";
                //System.out.println("It has onedrive dirr");
                i++;
                        
            }else{
                i++;
                continue;
            }

            recordArr[i][0] = file.getId();
            recordArr[i][1] = file.getName();
            recordArr[i][2] = file.getParentPath();
            fileDataList.add((String[])fileData);
       }
        
        return fileDataList;
    }
    
    
    
    public boolean addFindingsToDB(Object[][] recordArr) throws SQLException, ClassNotFoundException{
        Boolean result = false;
        int i = 0;
        curUser = CurrentUserSingleton.getInstance();
        String curDir= curUser.getCurDir();
        
        result = DBWriter.addFinding(recordArr, curDir);
        return result;
    }
    
    public boolean closeCase(String cdbloc) throws SQLException{
        Boolean result = false;
        System.out.println(cdbloc);
        result = DBWriter.closeCase(cdbloc);
        return result;
    }
    
    public String getDBLoc(String caseName) throws ClassNotFoundException{
        String result = "";
        
        result = DBReader.getCaseDB(caseName);
        
        return result;
    }
    
    public boolean writeCSV(String cname) throws IOException, ClassNotFoundException{
        boolean result = false;
        String [] results =  DBReader.getCaseInfo(cname);
        
        System.out.println("Save location :" + results[0]);
        
        FileWriter csvWriter = new FileWriter(results[0]+"_Cloudtopsy.csv");
        csvWriter. append("CaseID");
        csvWriter.append(",");
        csvWriter.append("CaseName");
        csvWriter.append(",");
        csvWriter.append("CaseDesc");
        csvWriter.append(",");
        csvWriter.append("Investigator");
        csvWriter.append(",");
        csvWriter.append("OpenDate");
        csvWriter.append(",");
        csvWriter.append("CloseDate");
        csvWriter.append("\n");

        
        
        String investID = DBReader.getInvestID(cname);
        String investName = DBReader.getInvestName(investID);      
        csvWriter.append(results[3]); //CID
        csvWriter.append(",");
        csvWriter.append(cname); //CName
        csvWriter.append(",");
        csvWriter.append(results[4]); //CDesc
        csvWriter.append(",");
        csvWriter.append(investName); //Investigator name
        csvWriter.append(",");
        csvWriter.append(results[1].toString()); //Investigator name
        csvWriter.append(",");
        csvWriter.append(results[2]); //Investigator name
        
        
        csvWriter.append("\n");
        csvWriter.append("\n");
        
        
        
        csvWriter. append("FileID");
        csvWriter.append(",");
        csvWriter.append("FileName");
        csvWriter.append(",");
        csvWriter.append("FileDir");
           
        csvWriter.append("\n");
        
        ArrayList<String[]> casedata = getCaseData(results[3]);
        
        
        for(String[] rowData : casedata){
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        
        
        return result;
    }
    
    public boolean writeCSV(String cdbLoc, String cname) throws IOException, ClassNotFoundException{
        boolean result = false;
        String [] results =  DBReader.getCaseInfo(cname);
        
        System.out.println("Save location :" + results[0]);
        
        FileWriter csvWriter = new FileWriter(cdbLoc + cname + "_Cloudtopsy.csv");
        csvWriter. append("CaseID");
        csvWriter.append(",");
        csvWriter.append("CaseName");
        csvWriter.append(",");
        csvWriter.append("CaseDesc");
        csvWriter.append(",");
        csvWriter.append("Investigator");
        csvWriter.append(",");
        csvWriter.append("OpenDate");
        csvWriter.append(",");
        csvWriter.append("CloseDate");
        csvWriter.append("\n");

        
        
        String investID = DBReader.getInvestID(cname);
        String investName = DBReader.getInvestName(investID);
        
                
                
        csvWriter.append(results[3]); //CID
        csvWriter.append(",");
        csvWriter.append(cname); //CName
        csvWriter.append(",");
        csvWriter.append(results[4]); //CDesc
        csvWriter.append(",");
        csvWriter.append(investName); //Investigator name
        csvWriter.append(",");
        csvWriter.append(results[1].toString()); //Investigator name
        csvWriter.append(",");
        csvWriter.append(results[2]); //Investigator name
        
        
        csvWriter.append("\n");
        csvWriter.append("\n");
        
         
        
        
//        // Our example data
//        List<List<String>> rows = Arrays.asList(
//            Arrays.asList("Jean", "author", "Java"),
//            Arrays.asList("David", "editor", "Python"),
//            Arrays.asList("Scott", "editor", "Node.js")
//        );
        
        csvWriter. append("FileID");
        csvWriter.append(",");
        csvWriter.append("FileName");
        csvWriter.append(",");
        csvWriter.append("FileDir");
           
        csvWriter.append("\n");
        
        ArrayList<String[]> casedata = getCaseData(results[3]);
        
        
        for(String[] rowData : casedata){
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        
        
        return result;
    }
    
}

    