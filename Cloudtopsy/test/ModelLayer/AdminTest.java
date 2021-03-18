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
package ModelLayer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oconn
 */
public class AdminTest {
    
    public AdminTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFirstName method, of class Admin.
     */
    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        Admin instance = new Admin();
        String expResult = "Unknown";
        String result = instance.getFirstName();
        assertEquals(expResult, result);

    }



    /**
     * Test of getLastName method, of class Admin.
     */
    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        Admin instance = new Admin();
        String expResult = "Unknown";
        String result = instance.getLastName();
        assertEquals(expResult, result);
       
    }


    /**
     * Test of getPermissions method, of class Admin.
     */
    @Test
    public void testGetPermissions() {
        System.out.println("getPermissions");
        Admin instance = new Admin();
        int expResult = 0;
        int result = instance.getPermissions();
        assertEquals(expResult, result);
        
    }


    
}
