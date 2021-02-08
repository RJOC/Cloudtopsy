/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTCreateCase.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package cloudtopsy;

import ApplicationLayer.InvstLogic;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.Image;
import org.sleuthkit.datamodel.SleuthkitCase;
import org.sleuthkit.datamodel.SleuthkitJNI.CaseDbHandle.AddImageProcess;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskDataException;

public class CTCreateCase extends JFrame implements ActionListener{
    
    
    //Logic Variables
    private InvstLogic inLogic;
    //Frame to go back to
    private CTMenuFrameInvst parent;
    
    //Frame Variables
    private JLabel fill, fill1, CImageFLab, CNameLab, CDescLab, dbDirLab, dbDir, createCLab;
    private JTextField CName;
    private JButton back, clear, submit, CImageF;
    private JTextArea CDesc;
    private JFileChooser fileChoser;
    private String dir;
    private int something4;
    
    
    
    //Related to the sleuth kit
    private static final Logger LOGGER = Logger.getLogger(CTCreateCase.class.getName());
    private static SleuthkitCase caseDB;
    private static String imagePath = "E:\\My Backups\\Windows10img.E01";
    //private static String imagePath = "C:\\sqlite\\db\\ryans.iso";
  
    
    
    public CTCreateCase( CTMenuFrameInvst dad, InvstLogic inLogic) throws SQLException{
        parent = dad;
        this.inLogic = inLogic;
        
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Create Case");
        setLayout(new BorderLayout());
        
        createCLab = new JLabel("Create New Case:", JLabel.CENTER);
        createCLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        
        //First Section setting up
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(6,2));
               
        //Fill vars
        sec1.add(fill);
        sec1.add(fill1);
        
            //Case name
        CNameLab =  new JLabel("Case Name:",JLabel.CENTER);
        CNameLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        CName =  new JTextField();
        CName.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        sec1.add(CNameLab);
        sec1.add(CName);
            //Description
        CDescLab = new JLabel("Case Description:",JLabel.CENTER);
        CDescLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        CDesc = new JTextArea(1,1);
        CDesc.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        CDesc.setWrapStyleWord(true);
        CDesc.setLineWrap(true);
        CDesc.setWrapStyleWord(true);
        CDesc.setEditable(true);
        JScrollPane scroller = new JScrollPane(CDesc);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sec1.add(CDescLab);
        sec1.add(scroller);
            //Case Image
        CImageFLab =  new JLabel("Upload Image:", JLabel.CENTER);
        CImageFLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        CImageF = new JButton("Browse");
        CImageF.addActionListener(new OpenL());    
        sec1.add(CImageFLab);
        sec1.add(CImageF);
            //Case databse label
        dbDirLab = new JLabel("Case database will be stored as: ",JLabel.CENTER); 
        dbDirLab.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        dbDir = new JLabel("",JLabel.CENTER); 
        dbDir.setFont(new Font(Font.SANS_SERIF, Font.PLAIN , 15));
        sec1.add(dbDirLab);
        sec1.add(dbDir);
        
        
        //Second section 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,3));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        sec2.add(clear);
        submit = new JButton("Submit");
        submit.addActionListener(this);
        sec2.add(submit); 
            
        
        
        
        
        //JFrame Layout
        setSize(650,550);
        
        getContentPane().add(createCLab,BorderLayout.NORTH);   
        getContentPane().add(sec1,BorderLayout.CENTER);
        getContentPane().add(sec2,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        
        //Window Listener (Handles the closing of the window
        WindowListener exitListener = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                parent.setVisible(true);
                dispose();
            }
        };
        addWindowListener(exitListener);

    }

    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileChoser = new JFileChooser();
            int rVal = fileChoser.showOpenDialog(CTCreateCase.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                CImageFLab.setText("Disk Image Chosen");
                CImageF.setText(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName());
                CImageF.setForeground(Color.BLACK);
                dbDir.setText(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName()+".db");
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                CImageF.setText("No Image Selected");
                CImageF.setForeground(Color.red);
          }
        }
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String cname,cdesc;
        if(source == submit){
            cname = CName.getText();
            cdesc = CDesc.getText();
            
            if(cname != null && cdesc != null){
                          
                System.out.println(CName.getText());
                System.out.println(CDesc.getText());
                
            }

            //System.out.println(c.getCurrentDirectory().toString()+ "\\" + c.getSelectedFile().getName());
            
        }else if(source == clear){
            CName.setText("");
            CDesc.setText("");
            CImageF.setText("Browse");
            dbDir.setText("");
            
        }else if(source == back){
            parent.setVisible(true);
            dispose();
        }else{
            
        }
    }
    
    
////////////////////////////////////////////////////////////////////////////////////////////
    
    
    public boolean createCase(){
        boolean result = false;
        try{
            //Creates the database and configures it
            SleuthkitCase sk = SleuthkitCase.newCase(imagePath + ".db");


            SleuthkitCase existingCase = SleuthkitCase.openCase(imagePath + ".db");



            //Timezone input
            String timezone = "";

            //Add image process, number of steps process but this returns an object that allows it to happen
            //Timezone, addUnallocSpace, noFatFsOrphans
            AddImageProcess process = sk.makeAddImageProcess(timezone,false,false,"");


            //Creates a nwe arraylist of strings
            ArrayList<String> paths = new ArrayList<String>();
            //adds the image path to the array list
            paths.add(imagePath);

            //Trying to do something
            try{
                System.out.println("Logger output 1");
                process.run(UUID.randomUUID().toString(), paths.toArray(new String[paths.size()]), 0);
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
            CTCreateCase.usage(e.getMessage());
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
