/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTShowFindings.java
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
import java.util.List;
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
import org.sleuthkit.datamodel.TskCoreException;


public class CTShowFindings extends JFrame implements ActionListener {
    
    
    
    
    
        //Logic  variable
    private InvstLogic inLogic;

    
        //Frame variables
    private CTEstablishCU parent;
    
    
    //JFrame Vars
    private JLabel fill, fill1, fileLab;
    private JButton back, submit;
    private JComboBox fileext;
    private DefaultListCellRenderer listRenderer;
    private JTable filetable;
    private ArrayList<String[]> row = new ArrayList<String[]>(); 
    
    
    private Users curUser = CurrentUserSingleton.getInstance();; 
    private String curDir = "";
    
    
    public CTShowFindings(CTEstablishCU dad, InvstLogic inLogic, List<String[]> findings ){
        this.inLogic = inLogic;
        parent = dad;
       

        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Findings");
        setLayout(new BorderLayout());
         

        
       
        
        
        //Section middle
        filetable = new JTable();
        
            //Table model 
        Object[] columns ={"ID", "File Name", "Storage", "File Directory"};
        DefaultTableModel tabmodel = new DefaultTableModel();
        tabmodel.setColumnIdentifiers(columns);
        filetable.setModel(tabmodel);
        
        //configure table style
        filetable.setBackground(Color.LIGHT_GRAY);
        filetable.setForeground(Color.BLACK);
        Font font = new Font("",0,15);
        filetable.setFont(font);

        int[] columnsWidth = {
            50, 150,70,380
        };
        int j = 0;
        for(int widthL: columnsWidth ){
            TableColumn column = filetable.getColumnModel().getColumn(j++);
            column.setPreferredWidth(widthL);
        }
        
        filetable.setDefaultEditor(Object.class, null); 
        JScrollPane filescroll = new JScrollPane(filetable); 
        filescroll.setBounds(0,0,880,200);

        for(String[] data : findings){
            tabmodel.addRow(data);
        }
        
        //Second bottom 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,2));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        submit =  new JButton("Submit Selection");
        submit.addActionListener(this);
        sec2.add(submit);
        
        
        

        
        //JFrame Layout
        setSize(650,550);
       
        getContentPane().add(filescroll,BorderLayout.CENTER);
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
             //record results
            int i = 0 ;
            int [] selected = filetable.getSelectedRows();
            Object [][] recordArr = new Object[selected.length][3];
            for(int select: selected){
                recordArr[i][0] = Integer.parseInt(filetable.getValueAt(select, 0).toString());
                recordArr[i][1] = filetable.getValueAt(select, 1).toString();
                recordArr[i][2] = filetable.getValueAt(select, 3).toString();
                //System.out.println(recordArr[i][0].toString() + recordArr[i][1] + recordArr[i][2]);
                i++;
            }
            try {
                inLogic.addFindingsToDB(recordArr);
                JOptionPane.showMessageDialog(null, "The files you have selected had been added to the case record!");
            } catch (SQLException ex) {
                Logger.getLogger(CTSearchFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CTSearchFile.class.getName()).log(Level.SEVERE, null, ex);
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
