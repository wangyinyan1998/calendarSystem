package EventTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;

import static org.junit.Assert.*;
import DateRelated.*;
import Event.*;

public class EventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class Event tests begin! Good luck!");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("Class Event tests end! Are you satisfied?");
    }
/*测试搜索null时返回null*/
    @Test
    public void testSearchNull(){
        ArrayList<Event> events = Event.searchEventByTime(null,null);
        assertEquals(null,events);
        ArrayList<Event> events1 = Event.searchEventByDate(null);
        assertEquals(null,events1);
    }
/*测试rememberEvent传入null时返回false*/
    @Test
    public void testRememberNull(){
        boolean remembered = Event.rememberEvents(null);
        assertFalse(remembered);
    }
    @Test
    public void testDeleteEventNull(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        Event event =  new MeetingEvent("event1", date, "lab", "discussion",false,false);
        Event.rememberEvents(event);
        Event.deleteEvents(null);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event));
    }
    @Test
    public void testIsTheDateHaveCourse() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,1);
        Event event = EventTestUtil.getRandomEvent(1,new LocalTime(date,1,1),new LocalTime(date,2,2));
        Event.rememberEvents(event);
        assertTrue(Event.isTheDateHaveCourse(date));
    }
    @Test
    public void testIsTheDateHaveAnniversary(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,3,2);
        Event event = EventTestUtil.getRandomEvent(0,new LocalTime(date,1,1),new LocalTime(date,2,2));
        Event.rememberEvents(event);
        assertTrue(Event.isTheDateHaveAnniversary(date));
    }
    @Test
    public void testIfHaveEventTrue(){
        Event.eventClear();
        CalendarDate date1 = new CalendarDate(2018,1,2);
        CalendarDate date2 = new CalendarDate(2018, 5, 12);
        CalendarDate date3 = new CalendarDate(2018,6,2);
        CalendarDate date4 = new CalendarDate(2018, 4, 12);
        CalendarDate date5 = new CalendarDate(2018,3,13);
        CalendarDate date6 = new CalendarDate(2018,7,12);
        CalendarDate date7 = new CalendarDate(2018,8,12);


        MeetingEvent meetingEevent = new MeetingEvent("event1",date1 , "lab", "discussion",false,false);
        Event anniversary = new AnniversaryEvent("event", date2, "生日", "anniversaryType",false,false);
        Event  generalEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date3,3,1),new LocalTime(date3,3,3));
        Event tripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date4,3,2),new LocalTime(date4,4,3));
        Event courseEvent = EventTestUtil.getRandomEvent(1,new LocalTime(date5,1,2),new LocalTime(date5,2,3));
        Event interEvent = EventTestUtil.getRandomEvent(4,new LocalTime(date6,1,2),new LocalTime(date6,2,3));
        Event dateEvent = EventTestUtil.getRandomEvent(2,new LocalTime(date7,1,2),new LocalTime(date7,2,3));
        Event.rememberEvents(meetingEevent);
        Event.rememberEvents(anniversary);
        Event.rememberEvents(tripEvent);
        Event.rememberEvents(generalEvent);
        Event.rememberEvents(courseEvent);
        Event.rememberEvents(interEvent);
        Event.rememberEvents(dateEvent);
    }

}