/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTLoginFrame.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package cloudtopsy;

//logic imports
import ApplicationLayer.ApplicationLogic;
import ApplicationLayer.AdminLogic;
import ApplicationLayer.InvstLogic;
import ModelLayer.Admin;
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Investigator;

//Other imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTLoginFrame extends JFrame implements ActionListener {
    
    private Cloudtopsy parent;
    private ApplicationLogic appLogic;
    
    //Variables for frame
    private JLabel usernameLab, passwordLab;
    private JTextField username;
    private JPasswordField password;
    private JButton clear, submit, back;
    
    
    
    public CTLoginFrame (Cloudtopsy dad, ApplicationLogic appLogic){
        parent = dad;
        this.appLogic = appLogic;
        
        
        
        
        //Frame configuration
        setTitle("Cloud-topsy Login");
        setLayout(new BorderLayout());
        
        
        
        //First Section
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(5,2));
        usernameLab = new JLabel("Username: ",JLabel.CENTER);
        usernameLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        username = new JTextField();
        username.setFont(new Font(Font.SANS_SERIF, Font.ITALIC,20 ));
        username.setHorizontalAlignment(JTextField.CENTER);

        sec1.add(usernameLab);
        sec1.add(username);
        passwordLab = new JLabel("Password: ",JLabel.CENTER);
        passwordLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        password = new JPasswordField();
        password.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        password.setPreferredSize(new Dimension(50,50));
        password.setHorizontalAlignment(JPasswordField.CENTER);
        sec1.add(passwordLab);
        sec1.add(password);
        
        
        //Second Section 
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
                parent.setVisible(true);
                dispose();
            }
        };
        addWindowListener(exitListener);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if(source == clear){
            username.setText("");
            password.setText("");
        }
        if(source  == submit){
            String uname = username.getText();
            String pword = password.getText();
            boolean value = false;
            try {
                value = appLogic.login(uname, pword);
            } catch (IOException ex) {
                Logger.getLogger(CTLoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CTLoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(value){
                username.setText("");
                password.setText("");
                username.requestFocusInWindow();
                setVisible(false);  
//Temp set to go to the admin frame for configuration setup
                if( CurrentUserSingleton.getInstance() instanceof Admin){ //Admin
                    CTMenuFrameAdmin menu = new CTMenuFrameAdmin(parent, new AdminLogic()); //put manLogic here
                }else if (CurrentUserSingleton.getInstance() instanceof Investigator){//FInvestigator
                    CTMenuFrameInvst menu = new CTMenuFrameInvst(parent, new InvstLogic());
                }else{
                    JOptionPane.showMessageDialog(null,"Could not find user");
                    username.setText("");
                    password.setText("");
                    username.requestFocusInWindow(); 
                }
            }else{
                JOptionPane.showMessageDialog(null,"There has been an error");
                username.setText("");
                password.setText("");
                username.requestFocusInWindow();
            }
        }
        if(source == back){
            parent.setVisible(true);
            dispose();
        }
    }
    
    
    
    
}
