package AutoTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.AnniversaryEvent;
import Event.Event;
import Event.MeetingEvent;
import Event.SortEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Think on 2018/5/28.
 */
public class TestState {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class CompleteTest tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CompleteTest tests end! Are you satisfied?");
    }
    @Test
    public void testIsComplete(){
        Event.eventClear();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        System.out.print(month+"  "+day);
        AnniversaryEvent anniversaryEvent1 = new AnniversaryEvent("anniversary1",new CalendarDate(2018,month + 1,day),"Mr Ming's birthday","生日",true,false);
        Event.rememberEvents(anniversaryEvent1);
        Event.setEventState(anniversaryEvent1);
        assertTrue(anniversaryEvent1.getState().equals("进行中"));
        anniversaryEvent1.setComplete(true);
        Event.setEventState(anniversaryEvent1);
        assertTrue(anniversaryEvent1.getState().equals("已完成"));
        anniversaryEvent1.setComplete(false);
        Event.setEventState(anniversaryEvent1);
        assertTrue(anniversaryEvent1.getState().equals("已完成"));
        LocalTime actTimeBegin = new LocalTime(new CalendarDate(2018, month + 1, day), hour, minute+1);
        LocalTime actTimeEnd = new LocalTime(new CalendarDate(2018, month + 1, day), hour, minute +3);
        MeetingEvent event = new MeetingEvent("event", actTimeBegin, actTimeEnd, "lab", "discussion",true,true);
        Event.rememberEvents(event);
        Event.setEventState(event);
        assertTrue(event.getState().equals("未开始"));
        AnniversaryEvent anniversaryEvent2 = new AnniversaryEvent("anniversary1",new CalendarDate(2018,month + 1,day - 1),"Mr Ming's birthday","生日",true,true);
        Event.rememberEvents(anniversaryEvent2);
        Event.setEventState(anniversaryEvent2);
        assertTrue(anniversaryEvent2.getState().equals("过期"));
        anniversaryEvent2.setComplete(true);
        Event.setEventState(anniversaryEvent2);
        assertTrue(anniversaryEvent2.getState().equals("已完成"));
        assertTrue(Event.getEvents().size() == 3);
        SortEvent sort = new SortEvent();
        ArrayList<Event> events = new ArrayList<Event>();
        events = Event.getEvents();
        events.sort(sort);

        ArrayList<Event> searchEvent = Event.searchEventByTime(new LocalTime(new CalendarDate(2018,month + 1, day),0,0),
                new LocalTime(new CalendarDate(2018,month + 1,day + 1),23,59));
        assertTrue(searchEvent.size() == 2);
        Event event1 = searchEvent.get(0);
        assertTrue(event1.equals(event));
        Event event2 = searchEvent.get(1);
        assertTrue(event2.equals(anniversaryEvent1));

        ArrayList<Event> searchDate = Event.searchEventByDate(new CalendarDate(2018,month + 1, day));
        assertTrue(searchEvent.size() == 2);
        Event event3 = searchEvent.get(0);
        assertTrue(event1.equals(event));
        Event event4 = searchEvent.get(1);
        assertTrue(event2.equals(anniversaryEvent1));
    }
}

