/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTMenuFrameInvst.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package cloudtopsy;

import ApplicationLayer.InvstLogic;
import ApplicationLayer.ApplicationLogic;
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Users;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class CTMenuFrameInvst extends JFrame implements ActionListener {
    //Logic  variable
    private InvstLogic inLogic;

    
    //Frame variables
    private Cloudtopsy parent;
    private CTMenuFrameInvst menuParent;
    
    //Frame construction variables
    private JLabel heading, fill1, fill2, fill3, fill4, dirHead;
    private JButton createCase, openCase, cloudUse, removeImage, listFiles, createReport, closeCase, changePassword,  logout;
    
    
    //Sinleton related
    private Users curUser; 
    private String uname, curDir;
    
    
    public CTMenuFrameInvst(Cloudtopsy dad, InvstLogic inLogic ){
        this.inLogic = inLogic;
        parent = dad;
        menuParent = this;
        
        //Singleton call
        curUser = CurrentUserSingleton.getInstance();
        uname = curUser.getuName();
        curDir = curUser.getCurDir();
        
        
        
        
        //Frame configuration
        setTitle("Investigator Menu");
        setLayout(new BorderLayout());
        
        //fill variables
        fill1 = new JLabel(" ");
        fill2 = new JLabel(" ");
        fill3 = new JLabel(" ");
        fill4 = new JLabel(" ");
        
        //First Section
        JPanel secHead = new JPanel();
        secHead.setLayout(new GridLayout(2,1));
   
        
        heading = new JLabel("Welcome "+ uname);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
    
        dirHead = new JLabel("Current Case DB: " + curDir);
        dirHead.setHorizontalAlignment(JLabel.CENTER);
        dirHead.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));      
        secHead.add(heading);
        secHead.add(dirHead);
    
        //Second Section
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(5,1));

        
        //Create new case
        createCase = new JButton("Create Case");
        createCase.addActionListener(this);
        sec1.add(createCase);
        
        //Upload Disk Image
        openCase = new JButton("Open Case");
        openCase.addActionListener(this);
        sec1.add(openCase);
        
        //Establish use of cloud storage platforms
        cloudUse = new JButton("Establish Cloud Use");
        cloudUse.addActionListener(this);
        sec1.add(cloudUse);
        
        //List specified files
        listFiles = new JButton("List Files of Type");
        listFiles.addActionListener(this);
        sec1.add(listFiles);
        
        //Remove Disk Image
        removeImage = new JButton("Remove Disk Image");
        removeImage.addActionListener(this);
        sec1.add(removeImage);
        
        
        //create report based on report listings
        createReport = new JButton("Create Report");
        createReport.addActionListener(this);
        sec1.add(createReport);
        
        //Close Case
        closeCase = new JButton("Close Case");
        closeCase.addActionListener(this);
        sec1.add(closeCase);
        
        //Change password
        changePassword = new JButton("Change Password");
        changePassword.addActionListener(this);
        sec1.add(changePassword);
        
        //Spacing for gap
        sec1.add(fill3);
        sec1.add(fill4); 
        
        //Bottom buttons
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,1));
        logout = new JButton("Logout");
        logout.addActionListener(this);
        sec2.add(logout);
        
        
       //JFrame Layout
        setSize(650,550);
        getContentPane().add(secHead,BorderLayout.NORTH);
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
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if(source == createCase){
            setVisible(false);
            try {
                CTCreateCase createCase = new CTCreateCase(this, inLogic);
            } catch (SQLException ex) {
                Logger.getLogger(CTMenuFrameInvst.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(source == cloudUse){
            setVisible(false);
            CTEstablishCU estcloudu = new CTEstablishCU(this, inLogic);
        }else if(source == listFiles){
            setVisible(false);
            CTListFiles listfiles = new CTListFiles(this, inLogic);
        }else if(source == openCase){
            setVisible(false);
 
            try {
                CTOpenCase openase = new CTOpenCase(this, inLogic);
            } catch (SQLException ex) {
                Logger.getLogger(CTMenuFrameInvst.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if(source == removeImage){

        }else if(source == createReport){
            
        }else if(source == closeCase){

        }else if(source == changePassword){
            //To change password
            setVisible(false);
            CTChangePwordFrame changePword = new CTChangePwordFrame(this, new ApplicationLogic());
        }else if(source == logout){
            inLogic.logout();
            parent.setVisible(true);
            dispose();
        }else{
             JOptionPane.showMessageDialog(null, "Program not responding! Bringing you to login screen");
             parent.setVisible(true);
             dispose();
        }
    }
}
