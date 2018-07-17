package AutoTest;
import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import  Event.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Created by ZZY on 2018/5/31.
 */
public class addAndDeleteEventTest  {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class addAndDeleteEventTest tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class addAndDeleteEventTest tests end! Are you satisfied?");
    }

    @Test
    public void addAndDeleteEventTest() throws Exception {
        Event.eventClear();
        AnniversaryEvent e1 = new AnniversaryEvent("v", new CalendarDate("1997-4-3"), "j", "b", true, true);
        TripEvent e2 = new TripEvent("kj",new CalendarDate("2018-4-3"), "bj","n"," b",true,true);
        GeneralEvent e3 = new GeneralEvent("bj", new CalendarDate("2018-3-7"),true,true);
        InterviewEvent e4 = new InterviewEvent("v","nj","b","n","nj", new CalendarDate("2019-4-3"),true,true);
        DateEvent e5 = new DateEvent("vh",new CalendarDate("2019-3-10"),"b","nj",true,true);
        MeetingEvent e6 = new MeetingEvent("bh",new CalendarDate("2016-3-2"),"bhj","bh",true,false);
        Event.rememberEvents(e1);
        Event.rememberEvents(e2);
        Event.rememberEvents(e3);
        Event.rememberEvents(e4);
        Event.rememberEvents(e5);
        Event.rememberEvents(e6);
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("1997-4-3")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2018-4-3")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2018-3-7")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2019-4-3")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2019-3-10")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2016-3-2")));
        Event.deleteEvents(e1);
        Event.deleteEvents(e2);
        Event.deleteEvents(e3);
        Event.deleteEvents(e4);
        Event.deleteEvents(e5);
        Event.deleteEvents(e6);
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("1997-4-3")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2018-4-3")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2018-3-7")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2019-4-3")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2019-3-10")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2016-3-2")));
    }

    @Test
    public void addAndSearchSubEventTest() throws Exception {
        Event.eventClear();
        GeneralEvent e = new GeneralEvent("bj", new LocalTime(new CalendarDate("1996-3-7"),0,0),new LocalTime(new CalendarDate("2020-8-9"),0,0),true,true);
        AnniversaryEvent e1 = new AnniversaryEvent("v", new CalendarDate("1997-4-3"), "j", "b", true, true);
        TripEvent e2 = new TripEvent("kj",new CalendarDate("2018-4-3"), "bj","n"," b",true,true);
        GeneralEvent e3 = new GeneralEvent("bj", new CalendarDate("2018-3-7"),true,true);
        InterviewEvent e4 = new InterviewEvent("v","nj","b","n","nj", new CalendarDate("2019-4-3"),true,true);
        DateEvent e5 = new DateEvent("vh",new CalendarDate("2019-3-10"),"b","nj",true,true);
        MeetingEvent e6 = new MeetingEvent("bh",new CalendarDate("2016-3-2"),"bhj","bh",true,false);
        e.addSubEvent(e1);
        e.addSubEvent(e2);
        e.addSubEvent(e3);
        e.addSubEvent(e4);
        e.addSubEvent(e5);
        e.addSubEvent(e6);
        Event.rememberEvents(e);
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("1997-4-3")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2018-4-3")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2018-3-7")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2019-4-3")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2019-3-10")));
        assertTrue(Event.isTheDateHaveEvent( new CalendarDate("2016-3-2")));
        e.deleteSubEvent(e1);
        e.deleteSubEvent(e2);
        e.deleteSubEvent(e3);
        e.deleteSubEvent(e4);
        e.deleteSubEvent(e5);
        e.deleteSubEvent(e6);
        Event.deleteEvents(e);
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("1997-4-3")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2018-4-3")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2018-3-7")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2019-4-3")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2019-3-10")));
        assertFalse(Event.isTheDateHaveEvent( new CalendarDate("2016-3-2")));

    }

}
