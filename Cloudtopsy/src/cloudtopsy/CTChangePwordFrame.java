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

import ApplicationLayer.ApplicationLogic;
import ModelLayer.Admin;
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Investigator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class CTChangePwordFrame extends JFrame implements ActionListener{
    private CTMenuFrameAdmin adminParent;
    private CTMenuFrameInvst invstParent;
    private ApplicationLogic appLogic;
    
    //Frame variabels;
    private JButton submit, back, clear;
    private JLabel heading, fill1, fill2, fill3, fill4, oldPWordLab, newPwordLab;
    private JPasswordField newPword;
    private JTextField oldPword;
            
    //PWord variables
    private String oldpassword, newpassword;
    private boolean success;
    
    //This sets the return frame for the menu screen
    public CTChangePwordFrame(CTMenuFrameInvst dad, ApplicationLogic appLogic ){
        invstParent = dad;
        ChangePword(appLogic);
    }
    
    //This sets the return frame for the menu screen
    public CTChangePwordFrame(CTMenuFrameAdmin dad, ApplicationLogic appLogic ){
        adminParent =  dad;
        ChangePword(appLogic);
    }
    
    
    public void ChangePword(ApplicationLogic appLogic){
        this.appLogic = appLogic;
        
        
        
        //Frame configuration
        setTitle("Change Password");
        setLayout(new BorderLayout());
        
         //fill variables
        fill1 = new JLabel(" ");
        fill2 = new JLabel(" ");
        fill3 = new JLabel(" ");
        fill4 = new JLabel(" ");
        
        
        //First Section
        heading = new JLabel("Change password:");
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        add(heading,BorderLayout.NORTH);
        
        //Second Section
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(5,2));
        oldPWordLab = new JLabel("Old Password: ",JLabel.CENTER);
        oldPWordLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        oldPword = new JTextField();
        oldPword.setFont(new Font(Font.SANS_SERIF, Font.ITALIC,20 ));
        oldPword.setHorizontalAlignment(JTextField.CENTER);

        sec1.add(oldPWordLab);
        sec1.add(oldPword);
        
        newPwordLab = new JLabel("New Password: ",JLabel.CENTER);
        newPwordLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        newPword = new JPasswordField();
        newPword.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        newPword.setPreferredSize(new Dimension(50,50));
        newPword.setHorizontalAlignment(JPasswordField.CENTER);
        
        sec1.add(newPwordLab);
        sec1.add(newPword);
        
        
        
        //third Section 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,3));
        back = new JButton("Back");
        clear = new JButton("Clear");
        submit = new JButton("Submit"); 
        sec2.add(back);
        sec2.add(clear);
        sec2.add(submit);
        
        //action listeners for buttons
        back.addActionListener(this);
        submit.addActionListener(this);
        clear.addActionListener(this);
        
        
        //JFrame Layout
        getContentPane().add(sec1,BorderLayout.CENTER);
        getContentPane().add(sec2,BorderLayout.SOUTH);
        setSize(650,550);
        setVisible(true);
        
        
        //Window Listener
        WindowListener exitListener = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                returnToLastFrame();
            }
        };
        addWindowListener(exitListener);
        
    }
  
        @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        oldpassword = oldPword.getText();
        newpassword = newPword.getText();
        
        if(source == submit){
            try {
                success = appLogic.changePword(oldpassword,newpassword);
            } catch (IOException ex) {
                Logger.getLogger(CTChangePwordFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CTChangePwordFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(success){
    //Returning to the previous screen using the singleton method
                JOptionPane.showMessageDialog(null,"Password has been changed!");
                returnToLastFrame();
    //end of returning 
            }else{
                JOptionPane.showMessageDialog(null,"Unable to change the password at this time!");
            }
        }else if(source == back){
            returnToLastFrame();
        }else if(source == clear){
            oldPword.setText("");
            newPword.setText("");
        }else{
//Returning to the previous screen using the singleton method
            JOptionPane.showMessageDialog(null, "Program not responding! Bringing you to menu screen");
            returnToLastFrame();
//end of returning
        }
    }
    
    
    public void returnToLastFrame(){
        if( CurrentUserSingleton.getInstance() instanceof Admin){ 
            adminParent.setVisible(true);
            dispose();
        }else if(CurrentUserSingleton.getInstance() instanceof Investigator){
            invstParent.setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"There has been an error, please restart application");
            System.exit(0); 
        }
    }
}
