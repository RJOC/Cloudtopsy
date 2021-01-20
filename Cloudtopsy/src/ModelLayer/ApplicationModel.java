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

import ModelLayer.DataAccess.DBReadBroker;
import ModelLayer.DataAccess.DBWriteBroker;
import ModelLayer.DataAccess.DBReader;
import ModelLayer.DataAccess.DBWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ApplicationModel {

    private DBReadBroker dbReader;
    private DBWriteBroker dbWriter;
    private CustomDataFactory dataFactory;
    private CustomDataPacker dataPacker;
    
    public ApplicationModel() {
        this("Default");
    }
    
    public ApplicationModel(String dbReadWriteType){
        /// TODO: function call to Change dbTypes.
        dbReadBrokerFactory();
        dbWriteBrokerFactory();
        dataPacker = new DataPacker();
        dataFactory = new DataFactory();

    }
    
    private void dbReadBrokerFactory(){        
            dbReader = new DBReader();
    }
    
    private void dbWriteBrokerFactory(){
            dbWriter = new DBWriter();
    }
    
    public void dbWrite(String instruction, CustomDataType data) throws IOException{
        /// Pack data into a Data object, and Pass to DBBroker
        this.dbWriter.writeToDB(instruction, dataPacker.packData(data));
    }
    
    public int dbRemove(String instruction){
        /// temp return for compile
        return 1;
    }
    
    public Data dbRead(String instruction, String keyWords){
        return dbReader.readFromDB(instruction, keyWords);
    }
    
    
    public CustomDataType unpackData(Data data){
        return dataFactory.buildData(data);
    }
    
}
