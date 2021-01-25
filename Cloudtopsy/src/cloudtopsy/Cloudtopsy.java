/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: Cloudtopsy.java
 * Class decription: 
 *      Main frame for the applications. 
 *      Contains the title, description and front page image
 *      Also allows the user to select the login button to move to next screen
 * 
 */
package cloudtopsy;

//logic imports
import ApplicationLayer.ApplicationLogic;

//Other imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


/**
 *
 * @author oconn
 */
public class Cloudtopsy extends JFrame implements ActionListener{
        
    //Logic declaration
    private ApplicationLogic appLogic;
    
    //Button declarations
    private JButton aboutBttn , loginBttn;
    
    public Cloudtopsy(ApplicationLogic appLogic) throws IOException{
        //Define logic
       this.appLogic = appLogic;
       
       //Frame configuration
        setTitle("Cloud-topsy");
        setLayout(new BorderLayout());
        
        
        //First Section
        JPanel sec1 = new JPanel();
        sec1.setLayout(new BorderLayout());
        JLabel heading = new JLabel("CLOUD-TOPSY",JLabel.CENTER);
        JLabel des = new JLabel("Data Forensics Tool for Investigating Subjects' Suspicious Cloud Activity!", JLabel.CENTER);
        
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
        des.setVerticalAlignment(JLabel.TOP);
        des.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        sec1.add(heading, BorderLayout.NORTH);
        sec1.add(des,BorderLayout.CENTER);
                    
                    
        //Second Section
        JPanel sec2 = new JPanel();
        BufferedImage Pic = ImageIO.read(this.getClass().getResource("cloudtopsyLogo.png"));
        JLabel Icon = new JLabel(new ImageIcon(Pic));
        sec2.add(Icon);
        sec2.setSize(400,400);
        
        //Third Section
        JPanel sec3 = new JPanel();
        sec3.setLayout(new GridLayout(1,2));
        loginBttn = new JButton("Login");
        loginBttn.addActionListener(this);
        aboutBttn = new JButton("Learn More");
        aboutBttn.addActionListener(this);
        aboutBttn.setSize(50,50);
        sec3.add(loginBttn);
        sec3.add(aboutBttn);
    
        
        //JFrame Layout
        getContentPane().add(sec1,BorderLayout.NORTH);
        getContentPane().add(sec2,BorderLayout.CENTER);
        getContentPane().add(sec3,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,550);
        setVisible(true);
    }
    
    
    //To handle button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Object source = e.getSource();
            if(source == loginBttn){
                setVisible(false);
                CTLoginFrame login = new CTLoginFrame(this, new ApplicationLogic());
            }
            if(source == aboutBttn){
                setVisible(false);
                CTLearnFrame Learn = new CTLearnFrame(this);
            }
        }catch(Exception ee){
            System.err.print(ee.getMessage());
        }
    }
    
}
