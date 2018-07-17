package DateRelatedTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import DateRelated.*;

public class LocalTimeTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Class LocalTime tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class LocalTime tests end! Are you satisfied?");
    }

    @Test
    public void testCompareToTrue() {
        LocalTime time1 = new LocalTime(new CalendarDate(2018,1,1),1,1);
        LocalTime time2 = new LocalTime(new CalendarDate(2018,1,1),1,1);
        assertEquals(0,time1.compareTo(time2));
    }
    @Test
    public void testCompareToFalse(){
        LocalTime time1 = new LocalTime(new CalendarDate(2018,1,1),1,1);
        LocalTime time2 = new LocalTime(new CalendarDate(2019,1,1),1,1);
        assertEquals(1,time1.compareTo(time2));
        assertEquals(-1,time2.compareTo(time1));
    }

    @Test
    public void testCompareToDateTrue() {
        LocalTime time = new LocalTime(new CalendarDate(2018,1,1),1,1);
        CalendarDate date = new CalendarDate(2018,1,1);
        assertEquals(0,time.compareToDate(date));
    }
    @Test
    public void testCompareToDateFalse(){
        LocalTime time1 = new LocalTime(new CalendarDate(2018,1,1),1,1);
        CalendarDate date = new CalendarDate(2018,2,1);
        LocalTime time2 = new LocalTime(new CalendarDate(2018,3,3),1,1);
        assertEquals(1,time1.compareToDate(date));
        assertEquals(-1,time2.compareToDate(date));
    }
    @Test
    public void localTimeToString() {
        LocalTime time = new LocalTime(new CalendarDate(2018,1,1),1,1);
        String timeString = time.toString();
        assertEquals("2018-1-1 1:1",timeString);
    }
}