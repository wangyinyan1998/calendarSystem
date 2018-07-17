package DateRelatedTest;

import Event.Event;
import Event.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import DateRelated.*;

import static org.junit.Assert.*;

public class CalendarDateTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Class CalendarDate tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CalendarDate tests end! Are you satisfied?");
    }

    @Test
    public void testGetDayOfWeekTrue() {
        CalendarDate date = new CalendarDate(2018, 4, 2);
        int actual = date.getDayOfWeek();
        assertEquals(1, actual);
    }

    @Test
    public void testGetDayOfWeekFalse(){
        CalendarDate date1 = new CalendarDate(2018, 4, 2);
        int actual1 = date1.getDayOfWeek();
        assertNotEquals(6, actual1);
        CalendarDate date2 = new CalendarDate(2018, 2, 29);
        int actual2 = date2.getDayOfWeek();
        assertEquals(-1, actual2);
    }
/*测试ifHaveEvent是否正确*/
    @Test
    public void testIfHaveEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,5,12);
        MeetingEvent event = new MeetingEvent("event1", new CalendarDate(2018, 5, 12), "lab", "discussion",false,false);
        Event.rememberEvents(event);
        assertEquals(true,date.ifHaveEvent());
    }
    @Test
    public void testIfHaveEventFalse(){
        Event.eventClear();
        CalendarDate date1 = new CalendarDate(2018,1,1);
        assertEquals(false,date1.ifHaveEvent());
    }
}