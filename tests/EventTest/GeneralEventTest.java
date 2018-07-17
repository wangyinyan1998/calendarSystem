package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Event;
import Event.GeneralEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by ZZY on 2018/5/27.
 */
public class GeneralEventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class GeneralEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class GeneralEvent tests end! Are you satisfied?");
    }

    @Test
    public void testEqualsTrue() throws Exception {
        GeneralEvent ge = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        GeneralEvent ge1 = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        GeneralEvent ge2 = new GeneralEvent("s",new LocalTime(new CalendarDate("2018-4-3"),0,0),new LocalTime(new CalendarDate("2018-4-3"),23,59),true,false);
        assertTrue(ge.equals(ge1));
        assertTrue(ge.equals(ge2));
    }

    public void testEqualsFalse() throws Exception{
        GeneralEvent ge = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        GeneralEvent ge1 = new GeneralEvent("s",new CalendarDate("2018-4-3"),false,false);
        GeneralEvent ge2 = new GeneralEvent("s",new CalendarDate("2018-2-3"),true,false);
        GeneralEvent ge3 = new GeneralEvent("ss",new CalendarDate("2018-4-3"),true,false);
        assertFalse(ge.equals(ge1));
        assertFalse(ge.equals(ge2));
        assertFalse(ge.equals(ge3));
    }

    @Test
    public void testRememberEventTrue() throws Exception{
        Event.eventClear();
        GeneralEvent event = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        boolean remembered = Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(new CalendarDate("2018-4-3"));
        assertTrue(events.size()==1 && (events.get(0)).equals(event) && remembered);
    }

    @Test
    /*测试通过date搜索事件是否成功，记录两个事件，这两个事件是在同一天都有，然后看按照date搜索事件是否会成功*/
    public void testSearchDateEventByDateTrue() throws Exception{
        Event.eventClear();
        GeneralEvent event = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        Event.rememberEvents(event);
        CalendarDate testDate = new CalendarDate(2018, 4, 3);
        ArrayList<Event> events = Event.searchEventByDate(testDate);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event));
    }
    @Test
    public void testSearchDateEventByPeriodTrue() throws Exception{
        Event.eventClear();
        GeneralEvent event = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        boolean remembered = Event.rememberEvents(event);
        LocalTime testTimeBegin = new LocalTime(new CalendarDate(2018, 4, 3), 12, 0);
        LocalTime testTimeEnd = new LocalTime(new CalendarDate(2018, 6, 3), 2, 1);
        ArrayList<Event> events = Event.searchEventByTime(testTimeBegin, testTimeEnd);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event) && remembered);
    }

    @Test
    public void testSearchEventByDateFalse() throws Exception{
        Event.eventClear();
        GeneralEvent event = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        Event.rememberEvents(event);
        assertEquals(null,Event.searchEventByDate(new CalendarDate(2018,1,5)));
    }

    @Test
    public void testAddSubEventTrue() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        GeneralEvent event = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        Event subAnniversary = EventTestUtil.getRandomEvent(0,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event  subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,4,1),new LocalTime(date,4,3));
        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);
        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(EventTestUtil.hasThisSubEvent(subAnniversary,subEvents) && EventTestUtil.hasThisSubEvent(subGeneralEvent,subEvents) && EventTestUtil.hasThisSubEvent(subTripEvent,subEvents));
    }

    @Test
    public void testDeleteEventTrue() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,4,3);
        GeneralEvent event = new GeneralEvent("s",new CalendarDate("2018-4-3"),true,false);
        Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event));
        Event.deleteEvents(event);
        assertEquals(null,Event.searchEventByDate(date));
    }

}