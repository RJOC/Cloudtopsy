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
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Users;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class CTOpenCase extends JFrame implements ActionListener{
    
    
    //Logic Variables
    private InvstLogic inLogic;
    //Frame to go back to
    private CTMenuFrameInvst parent;
    private Cloudtopsy home;
    
    //Frame Variables
    private JLabel fill, fill1, fill2, orLab, dbDirLab, dbDir, openCaseLab, CImageFLab, caseLab;
    private JButton back, clear, submit, CImageF;
    private JFileChooser fileChoser;

    //Sinleton related
    private Users curUser = CurrentUserSingleton.getInstance();; 
    private String curDir = "";

    //For the select a case
    private JComboBox fileext;
    private DefaultListCellRenderer listRenderer;
    
    public CTOpenCase(CTMenuFrameInvst dad, InvstLogic inLogic) throws SQLException, ClassNotFoundException{        
     
        parent = dad;
        this.inLogic = inLogic;
        
        
        
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        fill2 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Open Case");
        setLayout(new BorderLayout());
        
        openCaseLab = new JLabel("Open an Existing Case:", JLabel.CENTER);
        openCaseLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        
        //First Section setting up
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(6,2));
               
        //Fill vars
        sec1.add(fill);
        sec1.add(fill1);
       
            //Case Image
        CImageFLab =  new JLabel("Select Database:", JLabel.CENTER);
        CImageFLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        CImageF = new JButton("Browse");
        CImageF.addActionListener(new CTOpenCase.OpenL());    
        sec1.add(CImageFLab);
        sec1.add(CImageF);
        
            //OR label
        orLab = new JLabel("OR", JLabel.CENTER);
        orLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sec1.add(orLab);
        sec1.add(fill2);
        
            //Choose Case
        caseLab = new JLabel("Select a Case: ", JLabel.CENTER);
        caseLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sec1.add(caseLab);
        
        
        
        ArrayList<String> fileextList = inLogic.getOpenCases();        
        fileext = new JComboBox(fileextList.toArray());
        fileext.setEditable(false);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        fileext.setRenderer(listRenderer);
        fileext.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sec1.add(fileext);
        
        
        
        
            //Case databse label
        dbDirLab = new JLabel("Case database that will be opened: ",JLabel.CENTER); 
        dbDirLab.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        dbDir = new JLabel("",JLabel.CENTER); 
        dbDir.setFont(new Font(Font.SANS_SERIF, Font.PLAIN , 15));
        sec1.add(dbDirLab);
        sec1.add(dbDir);
        
        
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
            
        
        
        
        
        //JFrame Layout
        setSize(650,550);
        
        getContentPane().add(openCaseLab,BorderLayout.NORTH);   
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
        
        
        
                //action listener for the combobox
        fileext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                
                JComboBox fileext = (JComboBox) event.getSource();
                Object selected = fileext.getSelectedItem();
                
                try {
                    curDir = inLogic.getDBLoc(selected.toString());
                    curUser.setCurDir(curDir);
                    CImageFLab.setText("Case Chosen");
                    caseLab.setText("Case Chosen");
                    dbDir.setText(curDir);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CTOpenCase.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
    
        class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileChoser = new JFileChooser();
            int rVal = fileChoser.showOpenDialog(CTOpenCase.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                CImageFLab.setText("Database Chosen");
                CImageF.setText(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName());
                CImageF.setForeground(Color.BLACK);
                dbDir.setText(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName());
                curUser.setCurDir(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName());
                curDir = fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName();
                caseLab.setText("Database Chosen");
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                CImageF.setText("No Image Selected");
                CImageF.setForeground(Color.red);
          }
        }
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == submit){
            if(curDir != ""){
                CTMenuFrameInvst openase = new CTMenuFrameInvst(home,inLogic);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Please select a case to be opened!");
            }
        }else if(source == clear){
            CImageFLab.setText("Select Database:");
            CImageF.setText("Browse");
            dbDir.setText("");
            
        }else if(source == back){
            parent.setVisible(true);
            dispose();
        }else{
            parent.setVisible(true);
            dispose();
        }
        
    }

}
