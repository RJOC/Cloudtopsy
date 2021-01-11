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
    
    //Frame variables
    private Cloudtopsy parent;
    private CTMenuFrameAdmin menuParent;
    
    //Frame construction variables
    private JLabel heading, fill1, fill2, fill3, fill4;
    private JButton createInvst, removeInvst, createAdmin, removeAdmin, viewReports,viewCases, obtainCaseDataReport,changePassword,  logout;
   
    
    public CTMenuFrameAdmin(Cloudtopsy dad, String username, AdminLogic adLogic){
        this.adLogic = adLogic;
        parent = dad;
        menuParent = this;
        
        
        //Frame configuration
        setTitle("Admin Menu");
        setLayout(new BorderLayout());
        
        //fill variables
        fill1 = new JLabel(" ");
        fill2 = new JLabel(" ");
        fill3 = new JLabel(" ");
        fill4 = new JLabel(" ");
        
        //First Section
        heading = new JLabel("Welcome "+ "*insert name from DB*");
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        add(heading,BorderLayout.NORTH);
        
        //Second Section
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(6,1));
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
        createInvst = new JButton("Create Investigator");
        createInvst.addActionListener(this);
        sec1.add(createInvst);
        
        //Remove Investigator
        removeInvst = new JButton("Remove Investigator");
        removeInvst.addActionListener(this);
        sec1.add(removeInvst);
        
        //Create Admin
        createAdmin = new JButton("Create Admin");
        createAdmin.addActionListener(this);
        sec1.add(createAdmin);
        
        //Remove Admin
        removeAdmin = new JButton("Remove Admin");
        removeAdmin.addActionListener(this);
        sec1.add(removeAdmin);
        
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

        }
        else if(source == viewCases){

        }
        else if(source == obtainCaseDataReport){

        }
        else if(source == changePassword){

        }
        else if(source == createInvst){

        }
        else if(source == removeInvst){

        }
        else if(source == createAdmin){

        }
        else if(source == removeAdmin){

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