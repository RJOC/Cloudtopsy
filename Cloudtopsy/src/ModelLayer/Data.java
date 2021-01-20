/*
 * Created By: Ryan O'Connor (17209382)
 * Project: Cloudtopsy (FYP: JW01)
 * Course: Mobile Communications & Security
 * University: University of Limerick, Ireland
 * 
 * 
 * Class name: Data.java
 * Class decription: 
 * 
 * 
 * 
 * 
 */
package ModelLayer;
import java.util.ArrayList; // import the ArrayList class


public class Data implements CustomDataType {
    private ArrayList<ArrayList<String>> data;
    private String dataName;
    
    public Data(){
        this.dataName =  "EmptyDataObject";
        this.data = new ArrayList<ArrayList<String>>();
    }
    
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
    
    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
    
    @Override
    public String dataType() {
        return "Data";
    }
}
