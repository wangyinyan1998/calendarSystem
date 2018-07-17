package EventTest;
import java.util.ArrayList;
import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Alarm;
import Event.CourseEvent;
import Event.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ZZY on 2018/5/27.
 */
public class CourseEventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class CourseEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CourseEvent tests end! Are you satisfied?");
    }

    @Test
    public void testEqualsTrue() throws Exception {
        Event.eventClear();
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce2 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce3 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        Alarm alarm = new Alarm();
        alarm.setIsAlarm(true);
        ce3.setAlarm(alarm);
        assertTrue(ce1.equals(ce2));
        assertTrue(ce2.equals(ce3));
    }

    @Test
    public void testEqualsFalse() throws Exception{
        Event.eventClear();
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce2 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"tt","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce3 = new CourseEvent(new LocalTime("2018-4-3 12:20"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce4 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",10,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce5 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),false,true);
        assertFalse(ce1.equals(ce2));
        assertFalse(ce1.equals(ce3));
        assertFalse(ce1.equals(ce4));
        assertFalse(ce1.equals(ce5));
    }

    @Test
    public void testEqualsNull() throws  Exception{
        Event.eventClear();
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        assertFalse(ce1.equals(null));
    }

    @Test
    public void testIsSameCourseTrue() throws Exception {
        Event.eventClear();
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",50,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce2 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",50,"t","cc","ss",new LocalTime("2018-4-10 11:12"),new LocalTime("2018-4-10 12:11"),true,true);
        CourseEvent ce3 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",50,"t","cc","ss",new LocalTime("2018-4-17 11:12"),new LocalTime("2018-4-17 12:11"),true,true);
        CourseEvent ce4 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",50,"t","cc","ss",new LocalTime("2018-5-2 11:12"),new LocalTime("2018-5-2 12:11"),true,true);
        CourseEvent ce5 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",50,"t","cc","ss",new LocalTime("2018-6-6 11:12"),new LocalTime("2018-6-6 12:11"),true,true);
       assertTrue(ce1.isSameCourse(ce2));
        assertTrue(ce1.isSameCourse(ce3));
        assertTrue(ce1.isSameCourse(ce4));
        assertTrue(ce1.isSameCourse(ce5));
    }

    @Test
    public void testIsSameCourseFalse() throws Exception{
        Event.eventClear();
        CourseEvent ce0 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",2,"t","cc","ss",new LocalTime("2018-4-5 11:12"),new LocalTime("2018-4-5 12:11"),true,true);
        CourseEvent ce2 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"tt","cc","ss",new LocalTime("2018-4-10 11:12"),new LocalTime("2018-4-10 12:11"),true,true);
        CourseEvent ce3 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"n","p",3,"t","cc","ss",new LocalTime("2018-5-15 11:12"),new LocalTime("2018-5-15 12:11"),true,true);
        assertFalse(ce0.isSameCourse(ce1));
        assertFalse(ce0.isSameCourse(ce2));
        assertFalse(ce0.isSameCourse(ce3));
    }

    @Test
    public void isTheDateHaveEventTrue() throws Exception {
        Event.eventClear();
        CourseEvent ce0 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-10 11:12"),new LocalTime("2018-4-10 12:11"),true,true);
        Event.rememberEvents(ce0);
        Event.rememberEvents(ce1);
        assertTrue(Event.isTheDateHaveEvent(new CalendarDate("2018-4-3")));
        assertTrue(Event.isTheDateHaveEvent(new CalendarDate("2018-4-10")));
    }

    @Test
    public void isTheDateHaveEventFalse() throws  Exception{
        Event.eventClear();
        CourseEvent ce0 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        CourseEvent ce1 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-10 11:12"),new LocalTime("2018-4-10 12:11"),true,true);
        Event.rememberEvents(ce0);
        Event.rememberEvents(ce0);
        assertFalse(Event.isTheDateHaveEvent(new CalendarDate("2017-4-3")));
        assertFalse(Event.isTheDateHaveEvent(new CalendarDate("2018-4-20")));
    }

    @Test
    public void isTheDateHaveCourse() throws Exception {
        Event.eventClear();
        CourseEvent ce0 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        Event.rememberEvents(ce0);
        assertTrue(Event.isTheDateHaveCourse(new CalendarDate("2018-4-10")));
        assertTrue(Event.isTheDateHaveCourse(new CalendarDate("2018-4-17")));
    }


    @Test
    public void testAddSubEventTrue() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        CourseEvent event = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
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
    public void testDeleteEventTrue() throws Exception{
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        CourseEvent event = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event));
        Event.deleteEvents(event);
        assertEquals(null,Event.searchEventByDate(date));
    }

    @Test
    public void testSearchDateEventByPeriodTrue() throws Exception{
        Event.eventClear();
        CourseEvent event = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        boolean remembered = Event.rememberEvents(event);
        LocalTime testTimeBegin = new LocalTime(new CalendarDate(2018, 4, 3), 10, 30);
        LocalTime testTimeEnd = new LocalTime(new CalendarDate(2018, 4, 3), 12, 1);
        ArrayList<Event> events = Event.searchEventByTime(testTimeBegin, testTimeEnd);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event) && remembered);
    }

    @Test
    public void testSearchEventByPeriodFalse() throws Exception{
        Event.eventClear();
        CourseEvent event = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        boolean remembered = Event.rememberEvents(event);
        LocalTime testTimeBegin = new LocalTime(new CalendarDate(2018, 4, 2), 10, 30);
        LocalTime testTimeEnd = new LocalTime(new CalendarDate(2018, 4, 2), 12, 11);
        ArrayList<Event> events = Event.searchEventByTime(testTimeBegin, testTimeEnd);
        assertTrue(events == null);
    }

    @Test
    public void testSearchEventByDateTrue() throws Exception{
        Event.eventClear();
        CourseEvent event = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        Event.rememberEvents(event);
        assertTrue(event.equals(Event.searchEventByDate(new CalendarDate("2018-4-3")).get(0)));
    }

    @Test
    public void testSearchEventByDateFalse() throws Exception{
        Event.eventClear();
        CourseEvent event = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),true,true);
        Event.rememberEvents(event);
        assertTrue(Event.searchEventByDate(new CalendarDate("2018-4-5")) == null);
    }

}