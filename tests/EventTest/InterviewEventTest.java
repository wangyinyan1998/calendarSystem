package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Event;
import Event.InterviewEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Think on 2018/5/27.
 */
public class InterviewEventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class InterviewEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class InterviewEvent tests end! Are you satisfied?");
    }

    @Test
    public void testIsEquals() throws Exception{
        InterviewEvent interviewEvent = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview",new CalendarDate(2020,5,6),false,false);
        InterviewEvent interviewEvent1 = new InterviewEvent("2020-5-7","PuDongXinQu","director","IBM","interview",new CalendarDate(2020,5,6),false,false);
        InterviewEvent interviewEvent2 = new InterviewEvent("2020-5-6","YangPuQu","director","IBM","interview",new CalendarDate(2020,5,6),false,false);
        InterviewEvent interviewEvent3 = new InterviewEvent("2020-5-6","PuDongXinQu","cleaner","IBM","interview",new CalendarDate(2020,5,6),false,false);
        InterviewEvent interviewEvent4 = new InterviewEvent("2020-5-6","PuDongXinQu","director","Alibaba","interview",new CalendarDate(2020,5,6),false,false);
        InterviewEvent interviewEvent5 = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview1",new CalendarDate(2020,5,6),false,false);
        InterviewEvent interviewEvent6 = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview",new CalendarDate(2020,5,7),false,false);
        InterviewEvent interviewEvent7 = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview",new CalendarDate(2020,5,6),true,false);
        InterviewEvent interviewEvent8 = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview",new CalendarDate(2020,5,6),false,true);
        assertFalse(interviewEvent.equals(interviewEvent1));
        assertFalse(interviewEvent.equals(interviewEvent2));
        assertFalse(interviewEvent.equals(interviewEvent3));
        assertFalse(interviewEvent.equals(interviewEvent4));
        assertFalse(interviewEvent.equals(interviewEvent5));
        assertFalse(interviewEvent.equals(interviewEvent6));
        assertFalse(interviewEvent.equals(null));
        assertTrue(interviewEvent.equals(interviewEvent7));
        assertTrue(interviewEvent.equals(interviewEvent8));
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
        InterviewEvent event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview",date,false,false);
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

    @Test
    public void testSearchDateEventByPeriodTrue(){
        Event.eventClear();
        LocalTime actTimeBegin = new LocalTime(new CalendarDate(2018, 1, 1), 1, 0);
        LocalTime actTimeEnd = new LocalTime(new CalendarDate(2018, 1, 3), 2, 1);
        InterviewEvent event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", actTimeBegin, actTimeEnd,false,false);
        boolean remembered = Event.rememberEvents(event);
        LocalTime testTimeBegin = new LocalTime(new CalendarDate(2018, 1, 1), 1, 0);
        LocalTime testTimeEnd = new LocalTime(new CalendarDate(2018, 1, 3), 2, 1);
        ArrayList<Event> events = Event.searchEventByTime(testTimeBegin, testTimeEnd);
        assertTrue(events.size() == 1 && (events.get(0)).equals(event) && remembered);
    }
    // 当不正确填写时不能添加,由于测试的是构造函数，无法判断place和attendPeople为空的情况，但是在界面已经限制了不能为空，所以没有写。

    /*测试通过date搜索事件搜索不到的时候是否返回null，搜索一个没有事件的date，看是否会返回null*/
    @Test
    public void testSearchEventByDateFalse(){
        Event.eventClear();
        LocalTime timeBegin = new LocalTime(new CalendarDate(2018,1,1),1,0);
        LocalTime timeEnd = new LocalTime(new CalendarDate(2018,1,3),2,1);
        Event event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", timeBegin,timeEnd,false,false);
        Event.rememberEvents(event);
        assertEquals(null,Event.searchEventByDate(new CalendarDate(2018,1,5)));
    }
    /*测试通过时间段搜索事件的时候搜索不到是否返回null*/
    @Test
    public void testSearchEventByTimeFalse(){
        Event.eventClear();
        Event event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", new CalendarDate(2018, 2, 1),false,false);
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
        InterviewEvent event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", date,false,false);
        Event.rememberEvents(event);
        assertTrue("isTheDateHaveEvent is false",Event.isTheDateHaveEvent(date));
    }
    /*测试一个日期是否有事件的函数，没有，返回false*/
    @Test
    public void testIsTheDateHaveEventFalse(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,1);
        CalendarDate date2 = new CalendarDate(2018,1,2);
        Event event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", date2,false,false);
        Event.rememberEvents(event);
        assertFalse("isTheDateHaveEvent is false",Event.isTheDateHaveEvent(date));
    }
    /*将事件记录到内存中*/
    @Test
    public void testRememberEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        Event event = new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", date, false,false);
        boolean remembered = Event.rememberEvents(event);
        ArrayList<Event> events =  Event.searchEventByDate(date);
        assertTrue(events.size()==1 && (events.get(0)).equals(event) && remembered);
    }

    /*删除事件*/
    @Test
    public void testDeleteEventTrue(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,2);
        Event event =  new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", date,false,false);
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
        Event event =  new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", date,false,false);
        Event subAnniversary = EventTestUtil.getRandomEvent(0,new LocalTime(date,1,1),new LocalTime(date,1,3));
        Event subMeetingEvent = EventTestUtil.getRandomEvent(3,new LocalTime(date,2,1),new LocalTime(date,2,3));
        Event  subGeneralEvent = EventTestUtil.getRandomEvent(6,new LocalTime(date,3,1),new LocalTime(date,3,3));
        Event subTripEvent = EventTestUtil.getRandomEvent(5,new LocalTime(date,4,1),new LocalTime(date,4,3));
        Event.rememberEvents(event);
        event.addSubEvent(subAnniversary);
        event.addSubEvent(subMeetingEvent);
        event.addSubEvent(subGeneralEvent);
        event.addSubEvent(subTripEvent);
        ArrayList<Event> subEvents = event.getSubEvent();
        assertTrue(EventTestUtil.hasThisSubEvent(subAnniversary,subEvents) &&
                EventTestUtil.hasThisSubEvent(subMeetingEvent,subEvents)&& EventTestUtil.hasThisSubEvent(subGeneralEvent,subEvents) && EventTestUtil.hasThisSubEvent(subTripEvent,subEvents));
    }

    @Test
    public void testAddSubEventHaveConflict(){
        Event.eventClear();
        CalendarDate date = new CalendarDate(2018,1,20);
        Event event =  new InterviewEvent("2020-5-6","PuDongXinQu","director","IBM","interview", date, false,false);
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