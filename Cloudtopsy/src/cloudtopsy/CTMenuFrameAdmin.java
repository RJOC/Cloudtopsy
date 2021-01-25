/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTMenuFrameAdmin.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package cloudtopsy;

import ApplicationLayer.AdminLogic;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CTMenuFrameAdmin extends JFrame implements ActionListener{
    
    //Logic  variable
    private AdminLogic adLogic;
    private ApplicationLogic appLogic;
    
    //Frame variables
    private Cloudtopsy parent;
    private CTMenuFrameAdmin menuParent;
    
    //Frame construction variables
    private JLabel heading, fill1, fill2, fill3, fill4;
    private JButton createUser, removeUser, viewReports,viewCases, obtainCaseDataReport,changePassword,  logout;
   
    
    //Sinleton related
    private Users curUser; 
    private String uname;
    
    public CTMenuFrameAdmin(Cloudtopsy dad, AdminLogic adLogic){
        this.adLogic = adLogic;
        parent = dad;
        menuParent = this;
        
        
        //Singleton call
        curUser = CurrentUserSingleton.getInstance();
        uname = curUser.getuName();
        
        //Frame configuration
        setTitle("Admin Menu");
        setLayout(new BorderLayout());
        
        //fill variables
        fill1 = new JLabel(" ");
        fill2 = new JLabel(" ");
        fill3 = new JLabel(" ");
        fill4 = new JLabel(" ");
        
        //First Section
        heading = new JLabel("Welcome "+ uname);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        add(heading,BorderLayout.NORTH);
        
        //Second Section
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(5,2));
        sec1.add(fill1);
        sec1.add(fill2);     
                
        //View All Reports
        viewReports = new JButton("View All Reports");
        viewReports.addActionListener(this);
        sec1.add(viewReports);
        
        //View Active Cases
        viewCases = new JButton("View Active Cases");
        viewCases.addActionListener(this);
        sec1.add(viewCases);
        
        //Obtain Case Data & Report
        obtainCaseDataReport = new JButton("Obtain Case Data & Report");
        obtainCaseDataReport.addActionListener(this);
        sec1.add(obtainCaseDataReport);
        
        //Change Password
        changePassword = new JButton("Change Password");
        changePassword.addActionListener(this);
        sec1.add(changePassword);
        
        //Create Investigator
        createUser = new JButton("Create User");
        createUser.addActionListener(this);
        sec1.add(createUser);
        
        //Remove Investigator
        removeUser = new JButton("Remove User");
        removeUser.addActionListener(this);
        sec1.add(removeUser);       
        
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
        setVisible(true);
        getContentPane().add(sec1,BorderLayout.CENTER);
        getContentPane().add(sec2,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        
        
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.setVisible(true);
                dispose();
            }
        };
        addWindowListener(exitListener);
    }
    
    
    
    //Handle buttons being clicked
    @Override
    public void actionPerformed(ActionEvent e) {
       Object source = e.getSource();
       
        if(source == viewReports){
            //Related to the sleuth kit 
        }
        else if(source == viewCases){
            //Related to the sleuth kit 
        }
        else if(source == obtainCaseDataReport){
            //Related to the sleuth kit 
        }
        else if(source == changePassword){
            //To change password
            setVisible(false);
            CTChangePwordFrame changePword = new CTChangePwordFrame(this, adLogic);
        }
        else if(source == createUser){
            //create user
            setVisible(false);
            CTCreateUserFrame createUser = new CTCreateUserFrame(this, adLogic);
        }
        else if(source == removeUser){
            //remove user
            setVisible(false);
            CTRemoveUserFrame removeUser = new CTRemoveUserFrame(this, adLogic);
        }
        else if(source == logout){
            adLogic.logout();
            parent.setVisible(true);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Program not responding! Bringing you to login screen");
            parent.setVisible(true);
            dispose();
        }
       
    }
}
