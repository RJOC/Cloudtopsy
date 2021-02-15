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

import ApplicationLayer.InvstLogic;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author oconn
 */
public class CTEstablishCU extends JFrame implements ActionListener{
    
        //Logic  variable
    private InvstLogic inLogic;

    
        //Frame variables
    private CTMenuFrameInvst parent;
    
    
    
    //JFrame Vars
    private JLabel dropboxTick,googleTick,onedriveTick, evernoteTick, fill, fill1,fill2,fill3,establishCULab,dropboxLab,googleLab,evernoteLab,onedriveLab;
    private JButton back, submit, record;
    private BufferedImage nottick, tick;
    
    public CTEstablishCU(CTMenuFrameInvst dad, InvstLogic inLogic) throws IOException{
        this.inLogic = inLogic;
        parent = dad;
    
        
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        fill2 = new JLabel("                           ");
        fill3 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Establish Cloud Use");
        setLayout(new BorderLayout());
         
        //Section top 
        establishCULab = new JLabel("Establish uses of Cloud Platforms:", JLabel.CENTER);
        establishCULab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        
        //Section middle
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(6,2));

     
        //First images line
        BufferedImage Picd = ImageIO.read(this.getClass().getResource("dropbox.png"));
        JLabel Icon = new JLabel(new ImageIcon(Picd));
        sec1.add(Icon);
        
        BufferedImage Picg = ImageIO.read(this.getClass().getResource("googled.png"));
        JLabel Icong = new JLabel(new ImageIcon(Picg));
        sec1.add(Icong);
        
        //file labels line
        dropboxLab = new JLabel("Dropbox Remnants Found:", JLabel.CENTER);
        sec1.add(dropboxLab);
        
        googleLab = new JLabel("Google Drive Remnants Found:", JLabel.CENTER);
        sec1.add(googleLab);
        
        //First set of ticks
        tick = ImageIO.read(this.getClass().getResource("tick.png"));
        nottick = ImageIO.read(this.getClass().getResource("nottick.png"));
        
        dropboxTick = new JLabel(new ImageIcon(tick));
        sec1.add(dropboxTick);
        dropboxTick.setVisible(false);
        
        googleTick = new JLabel(new ImageIcon(tick));
        sec1.add(googleTick);
        googleTick.setVisible(false);
        
        //Second image line
        BufferedImage Pice = ImageIO.read(this.getClass().getResource("evernote.png"));
        JLabel Icone = new JLabel(new ImageIcon(Pice));
        sec1.add(Icone);
        
        BufferedImage Pico = ImageIO.read(this.getClass().getResource("onedrive.png"));
        JLabel Icono = new JLabel(new ImageIcon(Pico));
        sec1.add(Icono);
        
        //second labels line
        evernoteLab =  new JLabel("Evernote Remnants Found:", JLabel.CENTER);
        sec1.add(evernoteLab);
                
        onedriveLab = new JLabel("One Drive Remnants Found:", JLabel.CENTER);
        sec1.add(onedriveLab);
        
        //Second set of ticks
        
        evernoteTick = new JLabel(new ImageIcon(tick));
        sec1.add(evernoteTick);
        evernoteTick.setVisible(false);
        
        onedriveTick =new JLabel(new ImageIcon(tick));
        sec1.add(onedriveTick);
        onedriveTick.setVisible(false);
        
        
        
        
        
        //Second bottom 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,3));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        submit = new JButton("Start Search");
        submit.addActionListener(this);
        sec2.add(submit); 
        record = new JButton("Submit Findings");
        record.addActionListener(this);
        sec2.add(record);
        
        //JFrame Layout
        setSize(650,550);
        
        getContentPane().add(establishCULab,BorderLayout.NORTH);
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
        if(source == submit){

            //DropBox
            if(true){
                //do something
                dropboxTick.setIcon(new ImageIcon(nottick));
            }
            //Google Drive
            if(true){
                googleTick.setIcon(new ImageIcon(nottick));
            }
            //evernote
            if(true){
                evernoteTick.setIcon(new ImageIcon(nottick));
            }   
            //One drive
            if(true){
                onedriveTick.setIcon(new ImageIcon(nottick));        
            }
            googleTick.setVisible(true);
            dropboxTick.setVisible(true);
            evernoteTick.setVisible(true);
            onedriveTick.setVisible(true);
        }else if(source == record){
            googleTick.setVisible(false);
            dropboxTick.setVisible(false);
            evernoteTick.setVisible(false);
            onedriveTick.setVisible(false);
        
        }else if(source == back){
            parent.setVisible(true);
            dispose();
        }else{
            parent.setVisible(true);
            dispose();
        }
    }
}