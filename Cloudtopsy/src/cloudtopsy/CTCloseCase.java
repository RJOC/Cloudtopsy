/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTCloseCase.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package cloudtopsy;

import ApplicationLayer.ApplicationLogic;
import ApplicationLayer.InvstLogic;
import ModelLayer.Admin;
import ModelLayer.CurrentUserSingleton;
import ModelLayer.Investigator;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class CTCloseCase extends JFrame implements ActionListener {
    
            //Logic  variable
    private InvstLogic inLogic= new InvstLogic();

    
        //Frame variables
    private CTMenuFrameInvst invstParent;
    private CTMenuFrameAdmin adminParent;
    
    //To get case name for the csv
    private Object selected;
    
    //JFrame Vars
    private JLabel fill, fill1, fileLab, cdescLab,cdesc, cdbLab, cdbLoc, copenLab, copen, ccloseLab, cclose;
    private JButton back, submit, getcsv;
    private JComboBox fileext;
    private DefaultListCellRenderer listRenderer;
    private JTable filetable;
    private ArrayList<String[]> row = new ArrayList<String[]>(); 
    
        //Sinleton related
    private Users curUser = CurrentUserSingleton.getInstance();; 
    private String curDir = "";
    
            //This sets the return frame for the menu screen
    public CTCloseCase(CTMenuFrameInvst dad, ApplicationLogic appLogic ) throws ClassNotFoundException{
        invstParent = dad;
        CTCloseCaseFrame(appLogic);
    }
    
    //This sets the return frame for the menu screen
    public CTCloseCase(CTMenuFrameAdmin dad, ApplicationLogic appLogic ) throws ClassNotFoundException{
        adminParent =  dad;
        CTCloseCaseFrame(appLogic);
    }
    
    
    public void CTCloseCaseFrame(ApplicationLogic appLogic) throws ClassNotFoundException {
        this.inLogic = inLogic;
        
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Close Case");
        setLayout(new BorderLayout());
         
        //Section top 
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(3,1));
        fileLab = new JLabel("Select case to close:");
        fileLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        sec1.add(fileLab);
        
        
        
        ArrayList<String> fileextList = appLogic.getOpenCases();        
        fileext = new JComboBox(fileextList.toArray());
        fileext.setEditable(false);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        fileext.setRenderer(listRenderer);
        fileext.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sec1.add(fileext);
        
        JPanel secinner = new JPanel();
        secinner.setLayout(new GridLayout(4,2));
        
        cdbLab = new JLabel("Case Database Location:");
        cdbLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        secinner.add(cdbLab);
        
        cdbLoc = new JLabel("",JLabel.CENTER);
        cdbLoc.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        secinner.add(cdbLoc);
        
        copenLab = new JLabel("Case Open Date:");
        copenLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        secinner.add(copenLab);
        
        copen = new JLabel("",JLabel.CENTER);
        copen.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        secinner.add(copen);
        
        ccloseLab = new JLabel("Case Close Date:");
        ccloseLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        secinner.add(ccloseLab);
        
        cclose = new JLabel("",JLabel.CENTER);
        cclose.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        secinner.add(cclose);
        
        cdescLab=new JLabel("Case Description:");
        cdescLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        secinner.add(cdescLab);
        
        cdesc = new JLabel("",JLabel.CENTER);
        cdesc.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        secinner.add(cdesc);
        
        //add the inner section to the outer
        sec1.add(secinner);
        
        //Section middle

     
        filetable = new JTable();
        
            //Table model 
        Object[] columns ={"ID", "File Name", "File Directory"};
        DefaultTableModel tabmodel = new DefaultTableModel();
        tabmodel.setColumnIdentifiers(columns);
        filetable.setModel(tabmodel);
        
        //configure table style
        filetable.setBackground(Color.LIGHT_GRAY);
        filetable.setForeground(Color.BLACK);
        Font font = new Font("",0,15);
        filetable.setFont(font);
        
                
        int[] columnsWidth = {
             50, 150, 450
        };
        int j = 0;
        for(int widthL: columnsWidth ){
            TableColumn column = filetable.getColumnModel().getColumn(j++);
            column.setPreferredWidth(widthL);
        }
        
        filetable.setDefaultEditor(Object.class, null); 
        JScrollPane filescroll = new JScrollPane(filetable); 
        filescroll.setBounds(0,0,880,200);

        
        
        
        
        
        //Second bottom 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,3));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        
        submit = new JButton("Close Case");
        submit.addActionListener(this);
        sec2.add(submit);
        
        getcsv = new JButton("Get CSV");
        getcsv.addActionListener(this);
        sec2.add(getcsv);
        
        curDir = curUser.getCurDir();
        //action listener for the combobox
        fileext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                
                tabmodel.setRowCount(0);
                JComboBox fileext = (JComboBox) event.getSource();
                selected = fileext.getSelectedItem();
                String column[]={"ID","File","Directory"};
                String [] caseinfo = null;
                try {
                    caseinfo = appLogic.getCaseInfo(selected.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CTViewReport.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cdbLoc.setText(caseinfo[0]);
                copen.setText(caseinfo[1]);
                cclose.setText(caseinfo[2]);
                
                cdesc.setText(caseinfo[4]);
                     
                try {
                    row = appLogic.getCaseData(caseinfo[3]);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CTViewReport.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Iterator i = row.iterator();
                while(i.hasNext()){
                    tabmodel.addRow((String[]) i.next());
                }  
            }
        });

        //JFrame Layout
        setSize(650,550);
        
        getContentPane().add(sec1,BorderLayout.NORTH);
        getContentPane().add(filescroll,BorderLayout.CENTER);
        getContentPane().add(sec2,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        
        //Window Listener (Handles the closing of the window
        WindowListener exitListener = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                returnToLastFrame();
                dispose();
            }
        };
        addWindowListener(exitListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        Boolean result = false;
        if(source == submit){
            if(cdbLoc.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Select a case: No case has been selected to close!");
            }else{
                try {
                    //Close Case
                    result = inLogic.closeCase(cdbLoc.getText());
                    if(result == true){
                        JOptionPane.showMessageDialog(null,"Case has been closed!");
                    }else{
                        JOptionPane.showMessageDialog(null,"There has been an error, please try again!");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CTCloseCase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }else if(source == back){
            returnToLastFrame();
            dispose();
        }else if(source == getcsv){
            if(selected != null){
                try {
                    //CSV Hanler here
                    inLogic.writeCSV(selected.toString());
                    JOptionPane.showMessageDialog(null, "CSV has been created and stored beside the case database!");
                } catch (IOException ex) {
                    Logger.getLogger(CTCloseCase.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CTCloseCase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Select a case: No case has been selected to create a CSV for!");
            }
            
        }else{
            returnToLastFrame();
            dispose();
        }
    }
    
    
    
    
    public void returnToLastFrame(){
        if( CurrentUserSingleton.getInstance() instanceof Admin){ 
            adminParent.setVisible(true);
            dispose();
        }else if(CurrentUserSingleton.getInstance() instanceof Investigator){
            invstParent.setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(null,"There has been an error, please restart application");
            System.exit(0); 
        }
    }
    
}
