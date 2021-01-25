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
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CTRemoveUserFrame extends JFrame implements ActionListener{
    //Logic Variables
    private AdminLogic adLogic ;
    //frame to go back to 
    private CTMenuFrameAdmin parent;
    
    
    //Frame variables
    private JLabel fill, fill1,fill2,removeLab;
    private JComboBox users;
    private String [] userList;
    private JButton back, submit;
    private DefaultListCellRenderer listRenderer;
    private String returnStr, selectedVal;
    
    public CTRemoveUserFrame(CTMenuFrameAdmin dad, AdminLogic adLogic ){
        
        this.adLogic = adLogic;
        parent = dad;
        
        fill = new JLabel("                           ");
        fill1 = new JLabel("                          ");
        fill2 = new JLabel("                          ");
         //Frame configuration
        setTitle("Remove User");
        setLayout(new BorderLayout());
        
         //First Section setting up
        JPanel sec1 = new JPanel();
        sec1.setLayout(new GridLayout(5,1)); 
        sec1.add(fill);
            //Title for removing the user
        removeLab = new JLabel("Select user to remove:");
        removeLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sec1.add(removeLab);
            //JCombobox for the list of users
        userList = adLogic.getAllUsers();
        if(userList.length == 0 ){
            System.out.println("There user list is empty!");
        }
        users = new JComboBox(userList);
        
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        users.setRenderer(listRenderer);
        users.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        
        sec1.add(users);
        sec1.add(fill1);
        sec1.add(fill2);
        
        //Section Section Bottom buttons
                //Second section 
        JPanel sec2 = new JPanel();
        sec2.setLayout(new GridLayout(1,2));
        back = new JButton("Back");
        back.addActionListener(this);
        sec2.add(back);
        submit = new JButton("Remove");
        submit.addActionListener(this);
        sec2.add(submit); 
        
        //JFRAME LAYOUT
        getContentPane().add(sec1,BorderLayout.CENTER);
        getContentPane().add(sec2,BorderLayout.SOUTH);
        setSize(650,500);
        setVisible(true);
        
            
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
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
        boolean remove = false;
        
        if(source == submit){
            selectedVal = users.getSelectedItem().toString();
            
            //pls work
            int selection = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to remove "+selectedVal+"?",
                "Are you sure?",
                JOptionPane.YES_NO_OPTION);

            if(selection == JOptionPane.YES_OPTION){
                remove = adLogic.removeUser(selectedVal);
            }
            else {
                remove = false;
            }

            if(remove == true){
                returnStr = selectedVal + " has been removed!";
                JOptionPane.showMessageDialog(null,returnStr);
                parent.setVisible(true);
                dispose();
            }else{
                returnStr = selectedVal + " has not been removed!";
                JOptionPane.showMessageDialog(null,returnStr);
            }
        }else if(source == back ){
            parent.setVisible(true);
            dispose();
        }else{
            
        }
    }
}
