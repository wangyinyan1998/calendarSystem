package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.AnniversaryEvent;
import Event.Event;
import EventTest.EventTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Think on 2018/5/27.
 */
public class AnniversaryEventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class AnniversaryEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class AnniversaryEvent tests end! Are you satisfied?");
    }
    @Test
    public void testIsEqual() throws Exception{
        AnniversaryEvent anniversaryEvent1 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent2 = new AnniversaryEvent("anniversary1",new CalendarDate("2019-5-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent3 = new AnniversaryEvent("anniversary2",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent4 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-6-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent5 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-28"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent6 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Wang's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent7 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","结婚纪念日",false,false);
        AnniversaryEvent anniversaryEvent8 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",true,false);
        AnniversaryEvent anniversaryEvent9 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",false,true);
        AnniversaryEvent anniversaryEvent10 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",false,false);
        assertFalse(anniversaryEvent1.equals(anniversaryEvent2));
        assertFalse(anniversaryEvent1.equals(anniversaryEvent3));
        assertFalse(anniversaryEvent1.equals(anniversaryEvent4));
        assertFalse(anniversaryEvent1.equals(anniversaryEvent5));
        assertFalse(anniversaryEvent1.equals(anniversaryEvent6));
        assertFalse(anniversaryEvent1.equals(anniversaryEvent7));
        assertTrue(anniversaryEvent1.equals(anniversaryEvent8));
        assertTrue(anniversaryEvent1.equals(anniversaryEvent9));
        assertTrue(anniversaryEvent1.equals(anniversaryEvent10));
        assertFalse(anniversaryEvent1.equals(null));
    }
    @Test
    public void testIsEqualAnniversary() throws Exception{
        AnniversaryEvent anniversaryEvent1 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent2 = new AnniversaryEvent("anniversary1",new CalendarDate("2019-5-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent3 = new AnniversaryEvent("anniversary2",new CalendarDate("2018-5-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent4 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-6-27"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent5 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-28"),"Mr Ming's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent6 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Wang's birthday","生日",false,false);
        AnniversaryEvent anniversaryEvent7 = new AnniversaryEvent("anniversary1",new CalendarDate("2018-5-27"),"Mr Ming's birthday","结婚纪念日",false,false);
        assertTrue(anniversaryEvent1.equalsAnniversary(anniversaryEvent2));
        assertFalse(anniversaryEvent1.equalsAnniversary(anniversaryEvent3));
        assertFalse(anniversaryEvent1.equalsAnniversary(anniversaryEvent4));
        assertFalse(anniversaryEvent1.equalsAnniversary(anniversaryEvent5));
        assertFalse(anniversaryEvent1.equalsAnniversary(anniversaryEvent6));
        assertFalse(anniversaryEvent1.equalsAnniversary(anniversaryEvent7));
        assertFalse(anniversaryEvent1.equals(null));
    }

    /**
     * test  MeetingEvent(String event, CalendarDate date,String place,String theme)
     *
     * @throws Exception
     */
    @Test
    /*测试通过date搜索事件是否成功，记录两个事件，这两个事件是在同一天都有，然后看按照date搜索事件是否会成功*/
    public void testSearchDateEventByDateTrue() {
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018, 5, 12);
        AnniversaryEvent event = new AnniversaryEvent("anniversary1", date, "Mr Ming's birthday", "生日",false,false);
        Event.rememberEvents(event);
        CalendarDate testDate = new CalendarDate(2018, 5, 12);
        ArrayList<Event> events = Event.searchEventByDate(testDate);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event));
    }
    // 当不正确填写时不能添加,由于测试的是构造函数，无法判断place和attendPeople为空的情况，但是在界面已经限制了不能为空，所以没有写。
    /**
     * test DateEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, String place, String attendPeople)
     *
     * @throws Exception
     */
    // 当不正确填写时不能添加,由于测试的是构造函数，无法判断place和attendPeople为空的情况，但是在界面已经限制了不能为空，所以没有写。
    @Test
    public void testSearchEventByDateFalse(){
        Event.eventClear();
        AnniversaryEvent event = new AnniversaryEvent("event1", new CalendarDate(2018,1,3), "Mr Ming's birthday", "生日",false,false);
        Event.rememberEvents(event);
        assertEquals(null,Event.searchEventByDate(new CalendarDate(2018,1,5)));
    }
    /*测试通过时间段搜索事件的时候搜索不到是否返回null*/
    @Test
    public void testSearchEventByTimeFalse(){
        Event.eventClear();
        AnniversaryEvent event = new AnniversaryEvent("event1", new CalendarDate(2018, 2, 1), "lab", "discussion",false,false);
        Event.rememberEvents(event);
        LocalTime searchTimeBegin = new LocalTime(new CalendarDate(2018,2,2),1,50);
        LocalTime searchTimeEnd = new LocalTime(new CalendarDate(2018,2,5),1,50);
        ArrayList<Event> events= Event.searchEventByTime(searchTimeBegin,searchTimeEnd);
        assertEquals(null,events);
    }

    /*测试检测一个日期是否有事件的函数，有，则应该返回true*/
    @Test
    public void testIsTheDateHaveEventTrue() {
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        AnniversaryEvent event = new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        Event.rememberEvents(event);
        assertTrue("isTheDateHaveEvent is false",Event.isTheDateHaveEvent(date));
    }
    /*测试一个日期是否有事件的函数，没有，返回false*/
    @Test
    public void testIsTheDateHaveEventFalse(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,1);
        CalendarDate date2 = new CalendarDate(2018,1,2);
        AnniversaryEvent event = new AnniversaryEvent("event1", date2, "Mr Ming's birthday", "生日",false,false);
        Event.rememberEvents(event);
        assertFalse("isTheDateHaveEvent is false",Event.isTheDateHaveEvent(date));
    }
    /*将事件记录到内存中*/
    @Test
    public void testRememberEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        AnniversaryEvent event = new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        boolean remembered = Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event) && remembered);
    }

    /*删除事件*/
    @Test
    public void testDeleteEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        AnniversaryEvent event =  new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event));
        Event.deleteEvents(event);
        assertEquals(null,Event.searchEventByDate(date));
    }


    @Test
    public void testAddSubEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        AnniversaryEvent event =  new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        Event subAnniversary = EventTestUtil.getRandomEvent(0,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event subMeetingEvent = EventTestUtil.getRandomEvent(3,new LocalTime(date,2,1),new LocalTime(date,2,3));
        Event  subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,4,1),new LocalTime(date,4,3));
        Event subCourseEvent = EventTestUtil.getRandomEvent(1,new LocalTime(date,5,1),new LocalTime(date,5,3));
        Event subDateEvent = EventTestUtil.getRandomEvent(2,new LocalTime(date,6,1),new LocalTime(date,6,3));
        Event subInterviewEvent = EventTestUtil.getRandomEvent(4,new LocalTime(date,7,1),new LocalTime(date,7,3));
        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subMeetingEvent);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);
        event.addSubEvent(subCourseEvent);
        event.addSubEvent(subDateEvent);
        event.addSubEvent(subInterviewEvent);
        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(EventTestUtil.hasThisSubEvent(subAnniversary,subEvents) &&
                EventTestUtil.hasThisSubEvent(subAnniversary,subEvents)&& EventTestUtil.hasThisSubEvent(subMeetingEvent,subEvents) && EventTestUtil.hasThisSubEvent(subGeneralEvent,subEvents)
                && EventTestUtil.hasThisSubEvent(subTripEvent,subEvents) && EventTestUtil.hasThisSubEvent(subCourseEvent,subEvents) && EventTestUtil.hasThisSubEvent(subDateEvent,subEvents));
    }

    @Test
    public void testAddSubEventHaveConflict(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        Event event =  new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        Event subAnniversary = EventTestUtil.getRandomEvent(0,new LocalTime(date,0,0),new LocalTime(date,23,59));
        Event subInterviewEvent = EventTestUtil.getRandomEvent(4,new LocalTime(date,1,1),new LocalTime(date,2,3));
        Event  subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,3,2),new LocalTime(date,4,3));
        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subInterviewEvent);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);

        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(subEvents.size()==1);
    }
}