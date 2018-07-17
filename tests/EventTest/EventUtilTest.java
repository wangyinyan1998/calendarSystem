package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Event;
import Event.MeetingEvent;
import Event.EventUtil;
import Event.AnniversaryEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Think on 2018/5/27.
 */
public class EventUtilTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class MeetingEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class MeetingEvent tests end! Are you satisfied?");
    }

    @Test
    public void isHaveConflict(){
        CalendarDate date = new CalendarDate(2018,1,20);
        Event interview = EventTestUtil.getRandomEvent(4,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event meetingEvent = EventTestUtil.getRandomEvent(3,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event  generalEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event.rememberEvents(interview);
        ArrayList<Event> events = Event.getEvents();
        assertTrue(events.size() == 1);
        Event.rememberEvents(meetingEvent);
        assertTrue(events.size() == 1);
        Event.rememberEvents(generalEvent);
        assertTrue(events.size() == 2);
    }

    @Test
    public void getConflictTimeTest(){
        LocalTime actTimeBegin = new LocalTime(new CalendarDate(2018, 1, 1), 1, 0);
        LocalTime actTimeEnd = new LocalTime(new CalendarDate(2018, 1, 1), 2, 1);
        LocalTime actTimeBegin1 = new LocalTime(new CalendarDate(2018, 1, 1), 1, 1);
        LocalTime actTimeEnd1 = new LocalTime(new CalendarDate(2018, 1, 1), 2, 1);
        MeetingEvent event = new MeetingEvent("event", actTimeBegin, actTimeEnd, "lab", "discussion",false,false);
        MeetingEvent event1 = new MeetingEvent("event", actTimeBegin1, actTimeEnd1, "lab", "discussion",false,false);
        assertNull(EventUtil.getConflictTimeString(event,event));
        assertTrue((EventUtil.getConflictTimeString(event,event1)).equals("2018-1-1 1:1 到 2018-1-1 2:1"));
        assertFalse(EventUtil.getConflictTimeString(event,event1).equals("2018-1-1 1 :0 到 2018-1-1 2:1"));
    }

    @Test
    public void isConflictWithSubEvent(){
        LocalTime actTimeBegin = new LocalTime(new CalendarDate(2018, 1, 1), 1, 0);
        LocalTime actTimeEnd = new LocalTime(new CalendarDate(2018, 1, 1), 2, 1);
        LocalTime actTimeBegin1 = new LocalTime(new CalendarDate(2018, 1, 2), 1, 1);
        LocalTime actTimeEnd1 = new LocalTime(new CalendarDate(2018, 1, 2), 2, 1);
        LocalTime actTimeBegin2 = new LocalTime(new CalendarDate(2018, 1, 1), 1, 1);
        LocalTime actTimeEnd2 = new LocalTime(new CalendarDate(2018, 1, 1), 2, 1);
        MeetingEvent event = new MeetingEvent("event", actTimeBegin, actTimeEnd, "lab", "discussion",false,false);
        MeetingEvent event1 = new MeetingEvent("event", actTimeBegin1, actTimeEnd1, "lab", "discussion",false,false);
        MeetingEvent event2 = new MeetingEvent("event",actTimeBegin2,actTimeEnd2,"lab","discussion",false,false);
        ArrayList<Event> list = new ArrayList<Event>();
        ArrayList<Event> list1 = new ArrayList<Event>();
        list.add(event1);
        list.add(event2);
        list1.add(event1);
        assertTrue(EventUtil.conflictWithOtherSubEvent(event,list)!=null);
        assertFalse(EventUtil.conflictWithOtherSubEvent(event,list1)!=null);
    }
    @Test
    public void isFatherEventCompletelyContainsSubEvent(){
        CalendarDate date = new CalendarDate(2018,5,27);
        CalendarDate date1 = new CalendarDate(2018,5,28);
        AnniversaryEvent event =  new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        MeetingEvent event1 = new MeetingEvent("event",new LocalTime(date,1,1),new LocalTime(date,2,2),"lab","discussion",false,false);
        MeetingEvent event2 = new MeetingEvent("event",new LocalTime(date,1,1),new LocalTime(date1,2,2),"lab","discussion",false,false);
        assertTrue(EventUtil.fatherEventCompletelyContainsSubEvent(event,event1));
        assertFalse(EventUtil.fatherEventCompletelyContainsSubEvent(event,event2));
    }
}