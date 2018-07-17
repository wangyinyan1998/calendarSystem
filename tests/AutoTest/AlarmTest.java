package AutoTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Alarm;
import Event.AlarmEvent;
import Event.Event;
import Event.*;
import Event.GeneralEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlarmTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class AlarmTest tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class AlarmTest tests end! Are you satisfied?");
    }
    //测试添加待办事项、搜索待办事项和设置单次提醒事项
    @Test
    public void testAlarmAndSearchEvent() throws Exception{
        Event.eventClear();
        /*测试搜索事件*/
        GeneralEvent event = new GeneralEvent("event",new CalendarDate(2018,4,4),false,false);
        Event.rememberEvents(event);//event时间最新，优先级仅次于紧急&重要
        CalendarDate date = new CalendarDate(2018,1,20);
        Event event1 =  new AnniversaryEvent("event1", date, "Mr Ming's birthday", "生日",false,false);
        CourseEvent event2 = new CourseEvent(new LocalTime("2018-4-3 12:11"),"sn","p",3,"t","cc","ss",new LocalTime("2018-4-3 11:12"),new LocalTime("2018-4-3 12:11"),
                true,true);//紧急&重要，优先级最高
        Event.rememberEvents(event1);//event1时间较早，优先级最低
        Event.rememberEvents(event2);
        ArrayList<Event> searchEvents = Event.searchEventByTime(new LocalTime("2018-1-1 1:1"),new LocalTime("2018-4-4 12:12"));
        assertTrue(event1.equals(searchEvents.get(2)));
        assertTrue(event2.equals(searchEvents.get(0)));
        assertTrue(event.equals(searchEvents.get(1)));

        /*测试闹钟提醒事件*/
        Alarm actualAlarm=new Alarm();
        actualAlarm.setIsAlarm(true);
        searchEvents.get(2).setAlarm(actualAlarm);
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,28),10,0);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,27),0,10);
        Alarm getEventAlarm = Event.searchEventByDate(new CalendarDate(2018,4,4)).get(0).getAlarm();
        String s =AlarmEvent.getDatePoor(timeEnd,timeBegin);
        /*测试单次提醒*/
        getEventAlarm.setTypeOfStrategy(0);
        int [] type={0,1,2};
        for(int i=0;i<type.length;i++){
            switch (i){
                case 0:
                    getEventAlarm.setTypeOfWay(0);/*无提醒*/
                    AlarmEvent.wayNotAlarm(getEventAlarm);
                    assertFalse(getEventAlarm.getIsAlarm());
                    break;
                case 1:
                    getEventAlarm.setTypeOfWay(1);   /*界面提醒*/
                    getEventAlarm.setIsAlarm(true);
                    assertTrue(getEventAlarm.getIsAlarm());
                    String actualResult=AlarmEvent.wayInterface(getEventAlarm,s);
                    String expectResult="提醒事项：" + "" + "\n" + "离待办事项还有："+s;
                    assertEquals(actualResult,expectResult);
                    break;
                default://因为case 2 时是弹窗提示，所以测试不了。
                    break;
            }
        }
        /*测试十分钟提醒*/
        getEventAlarm.setTypeOfStrategy(1);
        getEventAlarm.setTypeOfWay(1);
        getEventAlarm.setIsAlarm(true);
        assertTrue(getEventAlarm.getIsAlarm());
        //取消提醒
        getEventAlarm.setIsAlarm(false);
        assertFalse(getEventAlarm.getIsAlarm());

        /*测试一小时提醒*/
        getEventAlarm.setTypeOfStrategy(2);
        getEventAlarm.setIsAlarm(true);
        assertTrue(getEventAlarm.getIsAlarm());
        //取消提醒
        getEventAlarm.setIsAlarm(false);
        assertFalse(getEventAlarm.getIsAlarm());

        /*测试一天提醒*/
        getEventAlarm.setTypeOfStrategy(3);
        getEventAlarm.setIsAlarm(true);
        assertTrue(getEventAlarm.getIsAlarm());
        //取消提醒
        getEventAlarm.setIsAlarm(false);
        assertFalse(getEventAlarm.getIsAlarm());
    }
}
