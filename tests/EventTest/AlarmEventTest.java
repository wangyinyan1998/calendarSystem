package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Alarm;
import Event.AlarmEvent;
import Event.GeneralEvent;
import Event.MeetingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hp on 2018/5/27.
 */
public class AlarmEventTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class AlarmEvent tests begin! Good luck!");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("Class AlarmEvent tests end! Are you satisfied?");
    }

    @Test
    public void testShowAlarmIsNull(){
        MeetingEvent event = new MeetingEvent("event",new CalendarDate(2018,5,27),"fdu","lab",false,false);
        Alarm actualAlarm=event.getAlarm();//当没有设置提醒时返回"";
        actualAlarm.setIsAlarm(false);
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,27),0,0);
        String actualResult=AlarmEvent.showAlarm(event,timeEnd);
        assertEquals(actualResult,null);
    }


    @Test
    public void testStrategyOnceAndTestTypeOfWay(){
        GeneralEvent event = new GeneralEvent("event",new CalendarDate(2018,5,27),false,false);
        Alarm actualAlarm=new Alarm();
        actualAlarm.setIsAlarm(true);
        event.setAlarm(actualAlarm);
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,28),10,0);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,27),0,10);
        String s=AlarmEvent.getDatePoor(timeEnd,timeBegin);
        actualAlarm.setTypeOfStrategy(0);
        int [] type={0,1,2};
        for(int i=0;i<type.length;i++){
           switch (i){
              case 0:
                  actualAlarm.setTypeOfWay(0);
                  AlarmEvent.wayNotAlarm(actualAlarm);
                  assertFalse(actualAlarm.getIsAlarm());
                  break;
              case 1:
                  actualAlarm.setTypeOfWay(1);
                  String actualResult=AlarmEvent.wayInterface(actualAlarm,s);
                  String expectResult="提醒事项：" + "" + "\n" + "离待办事项还有："+s;
                  assertEquals(actualResult,expectResult);
                  break;
              default://因为case 2 时是弹窗提示，所以测试不了。
                 break;
           }
        }
    }
    @Test
    public void testWayInterface(){
        GeneralEvent event = new GeneralEvent("event",new CalendarDate(2018,5,27),false,false);
        Alarm actualAlarm=new Alarm();
        actualAlarm.setIsAlarm(true);
        event.setAlarm(actualAlarm);
        actualAlarm.setTypeOfWay(1);
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,28),10,0);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,27),0,10);
        String s=AlarmEvent.getDatePoor(timeEnd,timeBegin);
        String actualResult=AlarmEvent.wayInterface(actualAlarm,s);
        String expectResult="提醒事项：" + "" + "\n" + "离待办事项还有："+s;
        assertEquals(actualResult,expectResult);
    }
    @Test
    public void testIsStopAlarmFalse(){
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,28),10,0);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,27),0,10);
        assertFalse(AlarmEvent.isStopAlarm(null,null));
        assertFalse(AlarmEvent.isStopAlarm(timeBegin,null));
        assertFalse(AlarmEvent.isStopAlarm(null,timeEnd));
    }
    @Test
    public void testIsStopAlarmTrue(){
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,27),11,3);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,27),11,3);
        assertTrue(AlarmEvent.isStopAlarm(timeBegin,timeEnd));
    }
    @Test
    public void testGetDatePoorNull(){
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,28),10,0);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,27),0,10);
        assertEquals(AlarmEvent.getDatePoor(null,null),"");
        assertEquals(AlarmEvent.getDatePoor(timeBegin,null),"");
        assertEquals(AlarmEvent.getDatePoor(null,timeEnd),"");
    }
    @Test
    public void testGetDatePoorNotNull(){
        LocalTime timeEnd=new LocalTime(new CalendarDate(2018,5,28),10,10);
        LocalTime timeBegin=new LocalTime(new CalendarDate(2018,5,28),1,0);
        String actualResult=AlarmEvent.getDatePoor(timeEnd,timeBegin);
        assertEquals(actualResult,"0天9小时10分钟");
    }
}