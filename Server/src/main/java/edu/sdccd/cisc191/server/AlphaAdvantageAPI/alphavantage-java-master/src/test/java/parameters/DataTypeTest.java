package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

import static org.junit.Assert.assertEquals;

import com.crazzyghost.alphavantage.parameters.DataType;

import org.junit.Test;

public class DataTypeTest {

    @Test 
    public void testJson(){
        assertEquals(DataType.JSON.toString(), "json");
    }

    @Test 
    public void testCSV(){
        assertEquals(DataType.CSV.toString(), "csv");
    }

}
