/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: CTListFiles.java
 * Class decription: 
 *          Director search
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.sleuthkit.datamodel.TskCoreException;

public class CTListFiles extends JFrame implements ActionListener {
    
    
        //Logic  variable
    private InvstLogic inLogic;

    
        //Frame variables
    private CTMenuFrameInvst parent;
    
    
    //JFrame Vars
    private JLabel fill, fill1, fileLab;
    private JButton back, submit;
    private JComboBox fileext;
    private DefaultListCellRenderer listRenderer;
    private JTable filetable;
    private ArrayList<String[]> row = new ArrayList<String[]>(); 
    
        //Sinleton related
    private Users curUser = CurrentUserSingleton.getInstance();; 
    private String curDir = "";
    
    
    public CTListFiles(CTMenuFrameInvst dad, InvstLogic inLogic){
        this.inLogic = inLogic;
        parent = dad;
       

        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        
        
        //Frame configuration
        setTitle("Directory Search");
        setLayout(new BorderLayout());
         
        //Section top 
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(2,1));
        fileLab = new JLabel("Search for Directory:", JLabel.CENTER);
        fileLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        sec1.add(fileLab);
        
        
        
        ArrayList<String> fileextList = new ArrayList<String>();
        fileextList.add("dropbox");
        fileextList.add("googledrive");
        fileextList.add("s3");
        fileextList.add("evernote");
        fileext = new JComboBox(fileextList.toArray());
        fileext.setEditable(true);
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        fileext.setRenderer(listRenderer);
        fileext.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        
        sec1.add(fileext);
        
       
        
        
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
            50, 150,450
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
        sec2.setLayout(new GridLayout(1,2));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        submit =  new JButton("Submit Selection");
        submit.addActionListener(this);
        sec2.add(submit);
        
        curDir = curUser.getCurDir();
        System.out.println("Cur Dir : " + curDir);
        //action listener for the combobox
        fileext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                tabmodel.setRowCount(0);
                JComboBox fileext = (JComboBox) event.getSource();
                Object selected = fileext.getSelectedItem();
                String column[]={"ID","File","Directory"};
                try {
                    if(curDir != ""){
                        System.out.println(curDir);
                        row = inLogic.getExtDir(selected,curDir);
                    }else{
                        JOptionPane.showMessageDialog(null, "There is no open case: Head over to Open Case!");
                    }
                } catch (TskCoreException ex) {
                    Logger.getLogger(CTListFiles.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CTListFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Iterator i = row.iterator();
                while(i.hasNext()){
                    String temp[] = (String[]) i.next();

                    tabmodel.addRow(temp);
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
                recordArr[i][2] = filetable.getValueAt(select, 2).toString();
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
