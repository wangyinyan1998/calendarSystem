package SavingOperationTest;
import DateRelated.CalendarDate;
import Event.Event;
import SavingOperation.Saving;
import SavingOperation.SerializedSaving;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Event.*;
import EventTest.EventTestUtil;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;
/**
 * Created by ZZY on 2018/6/19.
 */
public class SerializedSavingTest {
    private static final int TEST_NUMBER = 100;
    private static File testFile = new File("serializedTest.txt");
    @Before
    public void setUp() throws Exception {
        System.out.println("Class Saving tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class Saving tests end! Are you satisfied?");
    }

    @org.junit.Test
    public void testSaving() throws Exception {
        ArrayList<Event> testList = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<TEST_NUMBER;i++){
            testList.add(EventTestUtil.getRandomEvent(Math.abs(random.nextInt())%7));
        }
        for(int i=1;i<TEST_NUMBER;i+=10){
            Alarm alarm = new Alarm();
            alarm.setIsAlarm(true);
            testList.get(i).setAlarm(alarm);
        }

        SerializedSaving.saveBeforeClose(testList,testFile);
        ArrayList<Event> resultList = SerializedSaving.file2Array(testFile);
        assertTrue(testList.size() == resultList.size());
        for(int i=0;i<testList.size();i++){
            Event e1 = testList.get(i);
            Event e2 = resultList.get(i);
            assertTrue(e1.getType()==e2.getType());
            switch (testList.get(i).getType()){
                case 0: assertTrue(((AnniversaryEvent)e1).equals((AnniversaryEvent)e2));break;
                case 1: assertTrue(((CourseEvent)e1).equals((CourseEvent) e2));break;
                case 2: assertTrue(((DateEvent)e1).equals((DateEvent) e2));break;
                case 3: assertTrue(((MeetingEvent)e1).equals((MeetingEvent) e2));break;
                case 4: assertTrue(((InterviewEvent)e1).equals((InterviewEvent) e2));break;
                case 5: assertTrue(((TripEvent)e1).equals((TripEvent) e2));break;
                default: assertTrue(((GeneralEvent)e1).equals((GeneralEvent) e2));break;
            }
        }
    }

}
