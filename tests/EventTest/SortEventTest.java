package EventTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import DateRelated.*;
import Event.*;

import java.util.Random;

import static org.junit.Assert.*;

public class SortEventTest {
    CalendarDate date1 = new CalendarDate(2018,1,1);
    CalendarDate date2 = new CalendarDate(2018,2,2);
    LocalTime localTime1 = new LocalTime(date1,1,1);
    LocalTime localTime2 = new LocalTime(date1,22,59);
    LocalTime localTime3 = new LocalTime(date2,2,2);
    LocalTime localTime4 = new LocalTime(date2,20,59);
    SortEvent sortEvent = new SortEvent();

    @Before
    public void setUp() throws Exception {
        System.out.println("Class SortEvent tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class SortEvent tests end! Are you satisfied?");
    }
    @Test
    public void compareByTimeEqualTest(){
        Random random = new Random();
        int max = 6;
        int type = random.nextInt(max);
        Event event1 = EventTestUtil.getRandomEvent(type,1);
        assertTrue("emergency compareEqual is false",sortEvent.compare(event1,event1)==0);
    }
    @Test
    public void compareByTimeNotEqualTest(){
       Event event1 = EventTestUtil.getRandomEvent(0,localTime1,localTime2);
       Event event2 = EventTestUtil.getRandomEvent(0,localTime3,localTime4);
       testCompareTwoEventByTime(event1,event2,"0-0");
        event2 = EventTestUtil.getRandomEvent(1,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"0-1");
        event2 = EventTestUtil.getRandomEvent(2,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"0-2");
        testCompareTwoEventByTime(event1,event2,"0-3");
        event2 = EventTestUtil.getRandomEvent(4,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"0-4");
        event2 = EventTestUtil.getRandomEvent(5,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"0-5");
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"0-6");

        event1 = EventTestUtil.getRandomEvent(1,localTime1,localTime2);
        event2 = EventTestUtil.getRandomEvent(1,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"1-1");
        event2 = EventTestUtil.getRandomEvent(2,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"1-2");
        event2 = EventTestUtil.getRandomEvent(3,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"1-3");
        event2 = EventTestUtil.getRandomEvent(4,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"1-4");
        event2 = EventTestUtil.getRandomEvent(5,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"1-5");
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"1-6");

        event1 = EventTestUtil.getRandomEvent(2,localTime1,localTime2);
        event2 = EventTestUtil.getRandomEvent(2,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"2-2");
        event2 = EventTestUtil.getRandomEvent(3,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"2-3");
        event2 = EventTestUtil.getRandomEvent(4,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"2-4");
        event2 = EventTestUtil.getRandomEvent(5,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"2-5");
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"2-6");

        event1 = EventTestUtil.getRandomEvent(3,localTime1,localTime2);
        event2 = EventTestUtil.getRandomEvent(3,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"3-3");
        event2 = EventTestUtil.getRandomEvent(4,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"3-4");
        event2 = EventTestUtil.getRandomEvent(5,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"3-5");
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"3-6");

        event1 = EventTestUtil.getRandomEvent(4,localTime1,localTime2);
        event2 = EventTestUtil.getRandomEvent(4,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"4-4");
        event2 = EventTestUtil.getRandomEvent(5,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"4-5");
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"4-6");

        event1 = EventTestUtil.getRandomEvent(5,localTime1,localTime2);
        event2 = EventTestUtil.getRandomEvent(5,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"5-5");
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"6-6");

        event1 = EventTestUtil.getRandomEvent(6,localTime1,localTime2);
        event2 = EventTestUtil.getRandomEvent(6,localTime3,localTime4);
        testCompareTwoEventByTime(event1,event2,"6-6");
    }
    //保证前面的事件的优先级比后面事件的优先级高
    private void testCompareTwoEventByTime(Event event1,Event event2,String compareMessage){
        assertTrue("compareLater is false "+compareMessage,sortEvent.compare(event1,event2)==1);
        assertTrue("compareLater is false "+compareMessage,sortEvent.compare(event2,event1)==-1);
    }
    @Test
    public void compareByWeightTest(){
        Random random = new Random();
        int max = 6;
        int type = random.nextInt(max);
        Event event1 = EventTestUtil.getRandomEvent(type,1);
        Event event2 = EventTestUtil.getRandomEvent(type,2);
        testCompareTwoEventByTime(event2,event1,type+"-"+type+" "+"1-2");
        type = random.nextInt(max);
        event2 = EventTestUtil.getRandomEvent(type,3);
        testCompareTwoEventByTime(event2,event1,type+"-"+type+" "+"1-3");
        type = random.nextInt(max);
        event2 = EventTestUtil.getRandomEvent(type,4);
        testCompareTwoEventByTime(event2,event1,type+"-"+type+" "+"1-4");

        type = random.nextInt(max);
        event1 = EventTestUtil.getRandomEvent(type,2);
        event2 = EventTestUtil.getRandomEvent(type,3);
        testCompareTwoEventByTime(event2,event1,type+"-"+type+" "+"2-3");
        type = random.nextInt(max);
        event2 = EventTestUtil.getRandomEvent(type,4);
        testCompareTwoEventByTime(event2,event1,type+"-"+type+" "+"2-4");

        type = random.nextInt(max);
        event1 = EventTestUtil.getRandomEvent(type,3);
        event2 = EventTestUtil.getRandomEvent(type,4);
        testCompareTwoEventByTime(event2,event1,type+"-"+type+" "+"3-4");
    }
}