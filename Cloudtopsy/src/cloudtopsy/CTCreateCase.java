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
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Users;
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
import javax.swing.JOptionPane;
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
    private String cimagepath, cdbpath;
    private int something4;
    
    
    //Sinleton related
    private Users curUser = CurrentUserSingleton.getInstance();; 
    private String curDir = "";
    
    
    //Related to the sleuth kit
    private static final Logger LOGGER = Logger.getLogger(CTCreateCase.class.getName());
    private static SleuthkitCase caseDB;

  
    
    
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
                curDir = fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName()+".db";
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
            cimagepath = CImageF.getText();
            cdbpath = dbDir.getText();
            if(cname != null && cdesc != null&& cimagepath != null && cdbpath != null){
                System.out.println(cname);
                System.out.println(cdesc);
                System.out.println(cimagepath);
                System.out.println(cdbpath);
                boolean result =  false;
                 
                JOptionPane.showMessageDialog(null, "Creating database now: This may take a long time! You will be notified upon completion!");
                result = inLogic.createCase(cimagepath);
                if(result == true){
                    if(inLogic.storeCaseData(cname, cdesc, cimagepath, cdbpath)){
                        JOptionPane.showMessageDialog(null, "The case has been created!"); 
                        curUser.setCurDir(curDir);
                        parent.setVisible(true);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "The case database has been created, but we could not store case details!");
                    }  
                }else{
                    JOptionPane.showMessageDialog(null, "There has been an error: There has been a problem creating the case!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "There has been an error: Please ensure all fields are filled!");
            }
        }else if(source == clear){
            CName.setText("");
            CDesc.setText("");
            CImageF.setText("Browse");
            dbDir.setText("");
            
        }else if(source == back){
            parent.setVisible(true);
            dispose();
        }else{
            parent.setVisible(true);
            dispose();
        }
    }
}
