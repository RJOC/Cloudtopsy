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
public class InvestigatorTest {
    
    public InvestigatorTest() {
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
     * Test of getFirstName method, of class Investigator.
     */
    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        Investigator instance = new Investigator();
        String expResult = "Unknown";
        String result = instance.getFirstName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getCurDir method, of class Investigator.
     */
    @Test
    public void testGetCurDir() {
        System.out.println("getCurDir");
        Investigator instance = new Investigator();
        String expResult = "Unknown";
        String result = instance.getCurDir();
        assertEquals(expResult, result);
        
    }



    /**
     * Test of getLastName method, of class Investigator.
     */
    @Test
    public void testGetLastName() {
        System.out.println("getLastName");
        Investigator instance = new Investigator();
        String expResult = "Unknown";
        String result = instance.getLastName();
        assertEquals(expResult, result);
        
    }


    /**
     * Test of getPermissions method, of class Investigator.
     */
    @Test
    public void testGetPermissions() {
        System.out.println("getPermissions");
        Investigator instance = new Investigator();
        int expResult = 0;
        int result = instance.getPermissions();
        assertEquals(expResult, result);
       
    }
    
}
