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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class CTViewAllCases extends JFrame implements ActionListener {
    
        //Logic Variables
    private InvstLogic inLogic;
    //Frame to go back to
    private CTMenuFrameInvst parent;
    
        //Frame variables
    private CTMenuFrameInvst invstParent;
    private CTMenuFrameAdmin adminParent;
    
    
    //JFrame Vars
    private JLabel fill, fill1, fileLab, cdescLab,cdesc, cdbLab, cdbLoc, copenLab, copen, ccloseLab, cclose;
    private JButton back, submit;
    private JComboBox fileext;
    private DefaultListCellRenderer listRenderer;
    private JTable filetable;
    private ArrayList<String[]> row = new ArrayList<String[]>(); 
    
        //Sinleton related
    private Users curUser = CurrentUserSingleton.getInstance();; 
    private String curDir = "";
    
    
    private ApplicationLogic appLog = new ApplicationLogic();
    
    
        //This sets the return frame for the menu screen
    public CTViewAllCases(CTMenuFrameInvst dad, ApplicationLogic appLogic ) throws ClassNotFoundException{
        invstParent = dad;
        CTViewAllCasesFrame(appLogic);
    }
    
    //This sets the return frame for the menu screen
    public CTViewAllCases(CTMenuFrameAdmin dad, ApplicationLogic appLogic ) throws ClassNotFoundException{
        adminParent =  dad;
        CTViewAllCasesFrame(appLogic);
    }
    
    

    
    
    public void CTViewAllCasesFrame(ApplicationLogic appLogic) throws ClassNotFoundException{
        this.inLogic = inLogic;


        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("View Report");
        setLayout(new BorderLayout());
         
        //Section top 
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(3,1));
        fileLab = new JLabel("Select case to investigate:");
        fileLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        sec1.add(fileLab);
        
        
        
        ArrayList<String> fileextList = appLog.getCases();        
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
        sec2.setLayout(new GridLayout(1,1));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        
        curDir = curUser.getCurDir();
        //action listener for the combobox
        fileext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                
                tabmodel.setRowCount(0);
                JComboBox fileext = (JComboBox) event.getSource();
                Object selected = fileext.getSelectedItem();
                String column[]={"ID","File","Directory"};
                String [] caseinfo = null;
                try {
                    caseinfo = appLog.getCaseInfo(selected.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CTViewReport.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cdbLoc.setText(caseinfo[0]);
                copen.setText(caseinfo[1]);
                cclose.setText(caseinfo[2]);
                
                cdesc.setText(caseinfo[4]);
                     
                try {
                    row = appLog.getCaseData(caseinfo[3]);
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
        
        if(source == submit){
            //do something
        }else if(source == back){
            returnToLastFrame();
            dispose();
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
