package io.frictionlessdata.tableschema;

import java.time.Duration;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * 
 */
public class FieldTest {
    @Test
    public void testFieldCastGeopointDefault() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_GEOPOINT, Field.FIELD_FORMAT_DEFAULT, "title", "description");
        int[] val = field.castValue("12,21");
        Assert.assertEquals(12, val[0]);
        Assert.assertEquals(21, val[1]);   
    }
    
    @Test
    public void testFieldCastGeopointArray() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_GEOPOINT, Field.FIELD_FORMAT_ARRAY, "title", "description");
        int[] val = field.castValue("[45,32]");
        Assert.assertEquals(45, val[0]);
        Assert.assertEquals(32, val[1]);   
    }
    
    @Test
    public void testFieldCastGeopointObject() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_GEOPOINT, Field.FIELD_FORMAT_OBJECT);
        int[] val = field.castValue("{\"lon\": 67, \"lat\": 19}");
        Assert.assertEquals(67, val[0]);
        Assert.assertEquals(19, val[1]);   
    }
    
    @Test
    public void testFieldCastInteger() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_INTEGER);
        int val = field.castValue("123");
        Assert.assertEquals(123, val); 
    }
    
    @Test
    public void testFieldCastDuration() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_DURATION);
        Duration val = field.castValue("P2DT3H4M");
        Assert.assertEquals(183840, val.getSeconds()); 
    }
    
    @Test
    public void testFieldCastGeojson() throws Exception{   
        Assert.fail("Test case not implemented yet.");
    }
    
    @Test
    public void testFieldCastTopojson() throws Exception{   
        Assert.fail("Test case not implemented yet.");
    }
    
    @Test
    public void testFieldCastObject() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_OBJECT);
        JSONObject val = field.castValue("{\"one\": 1, \"two\": 2, \"three\": 3}");
        Assert.assertEquals(3, val.length()); 
        Assert.assertEquals(1, val.getInt("one")); 
        Assert.assertEquals(2, val.getInt("two")); 
        Assert.assertEquals(3, val.getInt("three")); 
    }
    
    @Test
    public void testFieldCastArray() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_ARRAY); 
        JSONArray val = field.castValue("[1,2,3,4]");
        
        Assert.assertEquals(4, val.length()); 
        Assert.assertEquals(1, val.get(0));
        Assert.assertEquals(2, val.get(1));
        Assert.assertEquals(3, val.get(2));
        Assert.assertEquals(4, val.get(3));
    }
    
    @Test
    public void testFieldCastDateTime() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_DATETIME); 
        DateTime val = field.castValue("2008-08-30T01:45:36.123Z");
        
        Assert.assertEquals(2008, val.withZone(DateTimeZone.UTC).getYear());
        Assert.assertEquals(8, val.withZone(DateTimeZone.UTC).getMonthOfYear());
        Assert.assertEquals(30, val.withZone(DateTimeZone.UTC).getDayOfMonth());
        Assert.assertEquals(1, val.withZone(DateTimeZone.UTC).getHourOfDay());
        Assert.assertEquals(45, val.withZone(DateTimeZone.UTC).getMinuteOfHour());
        Assert.assertEquals("2008-08-30T01:45:36.123Z", val.withZone(DateTimeZone.UTC).toString());
    }
    
    @Test
    public void testFieldCastDate() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_DATE); 
        DateTime val = field.castValue("2008-08-30");
        
        Assert.assertEquals(2008, val.getYear());
        Assert.assertEquals(8, val.getMonthOfYear());
        Assert.assertEquals(30, val.getDayOfMonth());
    }
    
    @Test
    public void testFieldCastTime() throws Exception{
        Field field = new Field("test", Field.FIELD_TYPE_TIME); 
        DateTime val = field.castValue("14:22:33");
        
        Assert.assertEquals(14, val.getHourOfDay());
        Assert.assertEquals(22, val.getMinuteOfHour());
        Assert.assertEquals(33, val.getSecondOfMinute());
    }
    
    @Test
    public void testFieldCastYear() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_YEAR); 
        int val = field.castValue("2008");
        Assert.assertEquals(2008, val);
    }
    
    @Test
    public void testFieldCastYearmonth() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_YEARMONTH); 
        DateTime val = field.castValue("2008-08");
        
        Assert.assertEquals(2008, val.getYear());
        Assert.assertEquals(8, val.getMonthOfYear());
    }
    
    @Test
    public void testFieldCastNumber() throws Exception{   
        Assert.fail("Test case not implemented yet.");
    }
    
    @Test
    public void testFieldCastBoolean() throws Exception{  
        Field field = new Field("test", Field.FIELD_TYPE_BOOLEAN);
        
        Assert.assertFalse(field.castValue("f"));
        Assert.assertFalse(field.castValue("F"));
        Assert.assertFalse(field.castValue("False"));
        Assert.assertFalse(field.castValue("FALSE"));
        Assert.assertFalse(field.castValue("0"));
        Assert.assertFalse(field.castValue("no"));
        Assert.assertFalse(field.castValue("NO"));
        Assert.assertFalse(field.castValue("n"));
        Assert.assertFalse(field.castValue("N"));

        Assert.assertTrue(field.castValue("t"));
        Assert.assertTrue(field.castValue("T"));
        Assert.assertTrue(field.castValue("True"));
        Assert.assertTrue(field.castValue("TRUE"));
        Assert.assertTrue(field.castValue("1"));
        Assert.assertTrue(field.castValue("yes"));
        Assert.assertTrue(field.castValue("YES"));
        Assert.assertTrue(field.castValue("y"));
        Assert.assertTrue(field.castValue("Y"));
    }
    
    @Test
    public void testFieldCastString() throws Exception{   
        Field field = new Field("test", Field.FIELD_TYPE_STRING); 
        String val = field.castValue("John Doe");
        
        Assert.assertEquals("John Doe", val);
    }
    
    @Test
    public void testFieldCastAny() throws Exception{   
        Assert.fail("Test case not implemented yet.");
    }
}