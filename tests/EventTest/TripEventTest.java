package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.TripEvent;
import Event.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ZZY on 2018/5/27.
 */
public class TripEventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class TripEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class TripEvent tests end! Are you satisfied?");
    }

    @Test
    public void tesEqualsTrue() throws Exception {
        TripEvent tp = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        TripEvent tp2 = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        TripEvent tp3 =new TripEvent("s",new LocalTime(new CalendarDate("2018-4-3"),0,0),new LocalTime(new CalendarDate("2018-4-3"),23,59),"dd","dsd","dew",true,false);
        assertTrue(tp.equals(tp2));
        assertTrue(tp.equals(tp3));
    }

    @Test
    public void testEqualsFalse() throws Exception{
        TripEvent tp = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        TripEvent tp1 = new TripEvent("2",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        TripEvent tp2 = new TripEvent("s",new CalendarDate("2018-5-3"),"dd","dsd","dew",true,false);
        TripEvent tp3 = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",false,false);
        assertFalse(tp.equals(tp1));
        assertFalse(tp.equals(tp2));
        assertFalse(tp.equals(tp3));
    }

    @Test
    public void testRememberEventTrue() throws Exception{
        Event.eventClear();
        TripEvent event = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        boolean remembered = Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(new CalendarDate("2018-4-3"));
        assertTrue(events.size()==1 && (events.get(0)).equals(event) && remembered);
    }

    @Test
    /*测试通过date搜索事件是否成功，记录两个事件，这两个事件是在同一天都有，然后看按照date搜索事件是否会成功*/
    public void testSearchDateEventByDateTrue() throws Exception{
        Event.eventClear();
        TripEvent event = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        Event.rememberEvents(event);
        CalendarDate testDate = new CalendarDate(2018, 4, 3);
        ArrayList<Event> events = Event.searchEventByDate(testDate);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event));
    }

    public void testSearchDateEventByPeriodTrue() throws Exception{
        Event.eventClear();
        TripEvent event = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        boolean remembered = Event.rememberEvents(event);
        LocalTime testTimeBegin = new LocalTime(new CalendarDate(2018, 4, 3), 12, 0);
        LocalTime testTimeEnd = new LocalTime(new CalendarDate(2018, 6, 3), 2, 1);
        ArrayList<Event> events = Event.searchEventByTime(testTimeBegin, testTimeEnd);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event) && remembered);
    }

    @Test
    public void testSearchEventByDateFalse() throws Exception{
        Event.eventClear();
        TripEvent event = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        Event.rememberEvents(event);
        assertEquals(null,Event.searchEventByDate(new CalendarDate(2018,1,5)));
    }

    @Test
    public void testAddSubEventTrue() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        TripEvent event = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        Event subAnniversary = EventTestUtil.getRandomEvent(0,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event  subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,4,1),new LocalTime(date,4,3));
        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);
        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(  EventTestUtil.hasThisSubEvent(subAnniversary,subEvents)&&EventTestUtil.hasThisSubEvent(subGeneralEvent,subEvents) && EventTestUtil.hasThisSubEvent(subTripEvent,subEvents));
    }

    @Test
    public void testDeleteEventTrue() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,4,3);
        TripEvent event = new TripEvent("s",new CalendarDate("2018-4-3"),"dd","dsd","dew",true,false);
        Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event));
        Event.deleteEvents(event);
        assertEquals(null,Event.searchEventByDate(date));
    }
}