package EventTest;
import DateRelated.BuildObjectException;
import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by hp on 2018/5/6.
 */
public class DateEventTest extends EventTest{

    @Before
    public void setUp() throws Exception {
        System.out.println("Class DateEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class DateEvent tests end! Are you satisfied?");
    }

    /**
     * test equals
     * @throws Exception
     */
    @Test
    public void testDateEventEqualsNull(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,5,12);
        DateEvent event = new DateEvent("event",date,"lab","group",false,false);
        assertFalse("event.equals is false",event.equals(new DateEvent("",new CalendarDate(2018,5,12),"lab","group",false,false)));
        assertFalse("event.equals is false",event.equals(new DateEvent("event",null,"lab","group",false,false)));
        assertFalse("event.equals is false",event.equals(new DateEvent("event",new CalendarDate(2018,5,12),"","group",false,false)));
        assertFalse("event.equals is false",event.equals(new DateEvent("event",new CalendarDate(2018,5,12),"lab","",false,false)));
    }
    @Test
    public void testDateEventEqualsTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,5,12);
        DateEvent event = new DateEvent("event",date,"lab","group",false,false);
        assertTrue("event.equals is false",event.equals(new DateEvent("event",new CalendarDate(2018,5,12),"lab","group",false,false)));
    }
    @Test
    public void testDateEventEqualsFalse() {
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018, 5, 12);
        DateEvent event = new DateEvent("event", date, "lab", "group",false,false);
        assertFalse("event.equals is false", event.equals(new DateEvent("chatting", new CalendarDate(2018, 5, 12), "lab", "group",false,false)));
        assertFalse("event.equals is false", event.equals(new DateEvent("event", new CalendarDate(2018, 5, 11), "lab", "group",false,false)));
        assertFalse("event.equals is false", event.equals(new DateEvent("event", new CalendarDate(2018, 5, 12), "library", "group",false,false)));
        assertFalse("event.equals is false", event.equals(new DateEvent("event", new CalendarDate(2018, 5, 12), "lab", "one of group",false,false)));
    }

    /**
     * test DateEvent(String event, CalendarDate date, String place, String attendPeople)
     * @throws Exception
     */
    @Test
    //当正确填写时能添加
    public void testDateEventByDateTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,5,12);
        DateEvent event = new DateEvent("event",date,"lab","group",false,false);
        Event.rememberEvents(event);
        CalendarDate testDate=new CalendarDate(2018,5,12);
        ArrayList<Event> events =  Event.searchEventByDate(testDate);
        assertTrue(events.size()==1 && (events.get(0)).equals(event));
    }
    // 当不正确填写时不能添加,由于测试的是构造函数，无法判断place和attendPeople为空的情况，但是在界面已经限制了不能为空，所以没有写。

    /**
     * test DateEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, String place, String attendPeople)
     * @throws Exception
     */
    //当正确填写时能添加
    @Test
    public void testDateEventByPeriodTrue(){
        Event.eventClear();
        LocalTime actTimeBegin = new LocalTime(new CalendarDate(2018,1,1),1,0);
        LocalTime actTimeEnd = new LocalTime(new CalendarDate(2018,1,3),2,1);
        DateEvent event = new DateEvent("event",actTimeBegin,actTimeEnd,"lab","group",false,false);
        boolean remembered = Event.rememberEvents(event);
        LocalTime testTimeBegin = new LocalTime(new CalendarDate(2018,1,1),1,0);
        LocalTime testTimeEnd = new LocalTime(new CalendarDate(2018,1,3),2,1);
        ArrayList<Event> events =  Event.searchEventByTime(testTimeBegin,testTimeEnd);
        assertTrue(events.size()==1 && (events.get(0)).equals(event) && remembered);
    }
// 当不正确填写时不能添加,由于测试的是构造函数，无法判断place和attendPeople为空的情况，但是在界面已经限制了不能为空，所以没有写。

    /*测试通过date搜索事件搜索不到的时候是否返回null，搜索一个没有事件的date，看是否会返回null*/
    @Test
    public void testSearchEventByDateFalse(){
        Event.eventClear();
        LocalTime timeBegin = new LocalTime(new CalendarDate(2018,1,1),1,0);
        LocalTime timeEnd = new LocalTime(new CalendarDate(2018,1,3),2,1);
        Event event = new DateEvent("event1", timeBegin,timeEnd, "机房", "王银燕 王佳惠 张质雅 符丽雪",false,false);
        Event.rememberEvents(event);
        assertEquals(null,Event.searchEventByDate(new CalendarDate(2018,1,5)));
    }
    /*测试通过时间段搜索事件的时候搜索不到是否返回null*/
    @Test
    public void testSearchEventByTimeFalse(){
        Event.eventClear();
        Event event = new DateEvent("event1", new CalendarDate(2018, 2, 1), "机房", "王银燕 王佳惠 张质雅 符丽雪",false,false);
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
        DateEvent event = new DateEvent("event1", date, "机房", "王银燕 王佳惠 张质雅",false,false);
        Event.rememberEvents(event);
        assertTrue("isTheDateHaveEvent is false",Event.isTheDateHaveEvent(date));
    }
    /*测试一个日期是否有事件的函数，没有，返回false*/
    @Test
    public void testIsTheDateHaveEventFalse(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,1);
        CalendarDate date2 = new CalendarDate(2018,1,2);
        Event event = new DateEvent("event1", date2, "机房", "王银燕 王佳惠 张质雅",false,false);
        Event.rememberEvents(event);
        assertFalse("isTheDateHaveEvent is false",Event.isTheDateHaveEvent(date));
    }
    /*将事件记录到内存中*/
    @Test
    public void testRememberEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        Event event = new DateEvent("event1", date, "机房", "王银燕 王佳惠 张质雅",false,false);
        boolean remembered = Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event) && remembered);
    }

    /*删除事件*/
    @Test
    public void testDeleteEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        Event event =  new DateEvent("event1", date, "机房", "王银燕 王佳惠 张质雅",false,false);
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
        Event event =  new DateEvent("event1", date, "机房", "王银燕 王佳惠 张质雅",false,false);
        Event subAnniversary = EventTestUtil.getRandomEvent(0,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event subDateEvent = EventTestUtil.getRandomEvent(2,new LocalTime(date,2,1),new LocalTime(date,2,3));
        Event  subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,4,1),new LocalTime(date,4,3));

        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subDateEvent);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);
        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(EventTestUtil.hasThisSubEvent(subAnniversary,subEvents) &&
                EventTestUtil.hasThisSubEvent(subDateEvent,subEvents)&& EventTestUtil.hasThisSubEvent(subGeneralEvent,subEvents) && EventTestUtil.hasThisSubEvent(subTripEvent,subEvents));
    }
    @Test
    public void testAddSubEventHaveConflict(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        Event event =  new DateEvent("event1", date, "机房", "王银燕 王佳惠 张质雅",false,false);
        Event subAnniversary = new AnniversaryEvent("event", date, "生日", "anniversaryType",false,false);
        Event subDateEvent = EventTestUtil.getRandomEvent(2,new LocalTime(date,1,1),new LocalTime(date,2,3));
        Event subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,3,1),new LocalTime(date,4,3));

        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subDateEvent);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);
        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(subEvents.size()==1);
    }



}