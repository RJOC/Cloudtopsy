/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTLearnFrame.java
 * Class decription: 
 *          This frame allows the user to learn functions about the applciation
 *          It will list the main functions and describe the purpose of it.
 *          It will also des
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

public class CTLearnFrame extends JFrame implements ActionListener{
    
    private Cloudtopsy parent;
    
    private JButton back;
    private JLabel title,desc,invest,admin, funct,funct1;
    
    
    public CTLearnFrame(Cloudtopsy dad){
        parent = dad;
        
        //Frame configuration
        setTitle("About Cloud-topsy");
        setLayout(new BorderLayout());
        
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(2,1));
        
        
        title = new JLabel("What is Cloud-topsy?", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
        sec1.add(title);
        
        desc = new JLabel("Cloustopsy");
        desc.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        sec1.add(desc);
        //Second Section
        
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(2,2));
        
        invest = new JLabel("Investigator:", JLabel.CENTER);
        admin = new JLabel("Administrator:", JLabel.CENTER);
        invest.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        admin.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));      
        sec2.add(invest);
        sec2.add(admin);
        
                
        funct = new JLabel("Write the functions for investigator here");
        funct1 = new JLabel("Write the functions for admin here");
        sec2.add(funct);
        sec2.add(funct1);
        
        
        //Third Section
        JPanel sec3 = new JPanel();
        sec3.setLayout(new GridLayout(1,1));
        back = new JButton("Back");
        back.addActionListener(this);
        sec3.add(back);
        
        
        
        //JFrameLayout
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
            if(source == back){
                parent.setVisible(true);
                dispose();
            }
        }catch(Exception ee){
            System.err.print(ee.getMessage());
        }
    }
}
