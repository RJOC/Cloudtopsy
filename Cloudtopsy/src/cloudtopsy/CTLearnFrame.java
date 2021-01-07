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
    public CTLearnFrame(Cloudtopsy dad){
        parent = dad;
        
        //Frame configuration
        setTitle("About Cloud-topsy");
        setLayout(new BorderLayout());
        
        //First Section
        
        //Second Section
        
        //Third Section
        JPanel sec3 = new JPanel();
        sec3.setLayout(new GridLayout(1,1));
        back = new JButton("Back");
        back.addActionListener(this);
        sec3.add(back);
        
        
        //JFrameLayout
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
