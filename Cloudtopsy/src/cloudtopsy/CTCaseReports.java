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

import ApplicationLayer.AdminLogic;
import ApplicationLayer.InvstLogic;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author oconn
 */
public class CTCaseReports extends JFrame implements ActionListener {
    private AdminLogic adlogic;
    private InvstLogic inlogic = new InvstLogic();
    private CTMenuFrameAdmin parent;
    private DefaultListCellRenderer listRenderer;
    
    private JFileChooser fileChoser;
    
        //JFrame Vars
    private JLabel fill, fill1, fileLab,fill3,fill2,title, CSVDirLab, DetailsLab, Details;
    private JButton back, submit, CDirF;
    private String caseName, directory;
    
    private JComboBox fileext;
    public CTCaseReports(CTMenuFrameAdmin dad, AdminLogic addLogic ) throws ClassNotFoundException{
        
        this.adlogic = adlogic;
        parent = dad;
        
        
                
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        fill2 = new JLabel("                           ");
        fill3 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Case Reports");
        setLayout(new BorderLayout());
        
        title = new JLabel("Export Case Report:", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        //Section top 
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(5,1));
        fileLab = new JLabel("Select Case:");
        fileLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        //sec1.add(fill);
        sec1.add(fileLab);
        
        ArrayList<String> fileextList = addLogic.getCases();        
        fileext = new JComboBox(fileextList.toArray());
        fileext.setEditable(false);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        fileext.setRenderer(listRenderer);
        fileext.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sec1.add(fileext);
        
        //CSV Storage location
        CSVDirLab =  new JLabel("Select Save Location:");
        CSVDirLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        CDirF = new JButton("Browse");
        CDirF.addActionListener(new CTCaseReports.OpenD());    
        sec1.add(CSVDirLab);
        sec1.add(CDirF);

        Details = new JLabel("", JLabel.CENTER);
        Details.setFont(new Font(Font.SANS_SERIF, Font.BOLD , 15));
        sec1.add(Details);
        
        
        
        //Second bottom 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,2));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        submit = new JButton("Create CSV");
        submit.addActionListener(this);
        sec2.add(submit);
        
        
        //JFrame Layout
        setSize(650,550);
        
        getContentPane().add(title,BorderLayout.NORTH);
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
    
    
    
        class OpenD implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileChoser = new JFileChooser();
            fileChoser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int rVal = fileChoser.showOpenDialog(CTCaseReports.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                
                
                caseName = fileext.getSelectedItem().toString();
                directory = fileChoser.getSelectedFile() + "\\";
                String caseLoc = caseName + " saved to "+ directory;
                Details.setText(caseLoc);
                //CImageFLab.setText("Disk Image Chosen");
                //CImageF.setText(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName());
                //CImageF.setForeground(Color.BLACK);
                //dbDir.setText(fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName()+".db");
                //curDir = fileChoser.getCurrentDirectory().toString()+ "\\" + fileChoser.getSelectedFile().getName()+".db";
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                //CImageF.setText("No Image Selected");
                //CImageF.setForeground(Color.red);
          }
        }
    }
        
    
      @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if(source == submit){
            try {
                //Create and save CSV
                inlogic.writeCSV(directory, caseName);
                System.out.println(directory);
                System.out.println("Please work");
                System.out.println(caseName);
            } catch (IOException ex) {
                Logger.getLogger(CTCaseReports.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CTCaseReports.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                    
            
            
        }else if(source == back){
            parent.setVisible(true);
            dispose();
        }else{
            parent.setVisible(true);
            dispose();
        }
    }
}
