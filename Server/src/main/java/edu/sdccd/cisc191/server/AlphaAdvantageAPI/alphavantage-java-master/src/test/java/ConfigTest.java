package edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertNotNull;

import com.crazzyghost.alphavantage.Config;

import org.junit.Test;;

public class ConfigTest {
    
    @Test 
    public void testDefaultHttpClient(){
        Config cfg = Config.builder().build();
        assertNotNull(cfg.getOkHttpClient());
    }

    @Test 
    public void testConfigKey(){
        Config cfg = Config.builder().key("EQ77NH6JLVR3EXBF").build();
        assertEquals(cfg.getKey(), "EQ77NH6JLVR3EXBF");
    }

    @Test 
    public void testConfigTimeout(){
        Config cfg = Config.builder().timeOut(15).key("EQ77NH6JLVR3EXBF").build();
        assertEquals(cfg.getTimeOut(), 15);
    }

    private static class Config {
        public static edu.sdccd.cisc191.server.AlphaAdvantageAPI.alphavantage.Config.Builder builder() {
            return null;
        }

        public int getTimeOut() {
            return 0;
        }

        public long getKey() {
            return 0;
        }

        public Object getOkHttpClient() {
        }
    }
}