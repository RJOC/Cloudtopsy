/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTCreateUser.java
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CTCreateUserFrame extends JFrame implements ActionListener{
    
    //Logic variables
    private AdminLogic adLogic;
    private CTMenuFrameAdmin parent;
    
    //Frame variables
    private JLabel fnameLab,snameLab,unameLab,emailLab,passLab, fill, fill1;
    private JTextField fname,sname,username,email;
    private JPasswordField password;
    private JButton submit, back, clear;
    private JRadioButton inButton, adButton;
    private ButtonGroup radioButtons;
    private String unameVar, fnameVar,lnameVar, emailVar, pwordVar;
    private int permID;
   
    public CTCreateUserFrame(CTMenuFrameAdmin dad, AdminLogic adLogic){
        parent = dad;
        this.adLogic = adLogic;
        
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Create User");
        setLayout(new BorderLayout());
        
        //First Section setting up
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(8,2)); 
        sec1.add(fill);
        sec1.add(fill1);
            //First name data
        fnameLab = new JLabel("Firstname: ",JLabel.CENTER);
        fnameLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        fname = new JTextField();
        fname.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        fname.setHorizontalAlignment(JTextField.CENTER);
        sec1.add(fnameLab);
        sec1.add(fname);
            //Second name data
        snameLab = new JLabel("Surname: ",JLabel.CENTER);
        snameLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sname = new JTextField();
        sname.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        sname.setHorizontalAlignment(JTextField.CENTER);
        sec1.add(snameLab);
        sec1.add(sname);
            //username data
        unameLab = new JLabel("Username: ",JLabel.CENTER);
        unameLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        username = new JTextField();
        username.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        username.setHorizontalAlignment(JTextField.CENTER);
        sec1.add(unameLab);
        sec1.add(username);
            //email data
        emailLab = new JLabel("Email: ",JLabel.CENTER);
        emailLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        email = new JTextField();
        email.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        email.setHorizontalAlignment(JTextField.CENTER);
        sec1.add(emailLab);
        sec1.add(email);
            //Password data
        passLab = new JLabel("Temporary Password: ",JLabel.CENTER);
        passLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        password = new JPasswordField();
        password.setHorizontalAlignment(JTextField.CENTER);
        password.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        sec1.add(passLab);
        sec1.add(password);
            //UserType field
        //find a radio button thing or something
        radioButtons = new ButtonGroup();
        inButton = new JRadioButton("Investigator");
        adButton = new JRadioButton("Admin");
        inButton.setHorizontalAlignment(JRadioButton.CENTER);
        adButton.setHorizontalAlignment(JRadioButton.CENTER);
        radioButtons.add(inButton);
        radioButtons.add(adButton);
        
        sec1.add(inButton);
        sec1.add(adButton);
        
        
        
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
            
         //JFRAME LAYOUT
        getContentPane().add(sec1,BorderLayout.CENTER);
        getContentPane().add(sec2,BorderLayout.SOUTH);
        setSize(650,500);
        setVisible(true);
            
            
            
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
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
        
        if(source == submit){
            fnameVar = fname.getText();
            lnameVar = sname.getText();
            unameVar = username.getText();
            password.getText();
 
            if(adButton.isSelected()){
                permID = 1;
                if(adLogic.createUser(unameVar,fnameVar,lnameVar,emailVar,pwordVar,permID)){
                    JOptionPane.showMessageDialog(null, "The user has been created!");
                    parent.setVisible(true);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "There has been an error: The user has not been created!");
                }
            }else if(inButton.isSelected()){
                permID = 2;
                if(adLogic.createUser(unameVar,fnameVar,lnameVar,emailVar,pwordVar,permID)){
                    JOptionPane.showMessageDialog(null, "The user has been created!");
                    parent.setVisible(true);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "There has been an error: The user has not been created!");
                }
            }else{
                //Need a fail here
                 JOptionPane.showMessageDialog(null,"Teacher has not been created! There was an error!");
            }
     
            
        }else if(source == clear){
                fname.setText("");
                sname.setText("");
                username.setText("");
                email.setText("");
                password.setText("");

        }else if(source == back){
            parent.setVisible(true);
            dispose();
        }
    }
}
