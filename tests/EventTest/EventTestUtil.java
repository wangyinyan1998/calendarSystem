package EventTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import Event.Event;
import Event.*;

import java.util.ArrayList;


public class EventTestUtil {
    public static Event getRandomEvent(int type){
        Event event;
        CalendarDate date1 = new CalendarDate(2018,1,1);
        CalendarDate date2 = new CalendarDate(2018,2,2);
        LocalTime localTime1 = new LocalTime(date1,1,1);
        LocalTime localTime2 = new LocalTime(date2,23,59);
        switch (type){
            case 0:
                event = new AnniversaryEvent("event", new CalendarDate(2018,1,1),  "生日", "anniversaryType",false,false);
                break;
            case 1:
                event = new CourseEvent(localTime1,"courseName", "place", 20, "teacher", "courseContent",
                   "eventString", localTime1, localTime2, false,false);
                break;
            case 2:
                event =  new DateEvent("event",localTime1,localTime2,"lab","group",false,false);
                break;
            case 3:
                event =new MeetingEvent("event1", localTime1,localTime2, "lab", "discussion",false,false);
                break;
            case 4:
                event = new InterviewEvent("interviewTime", "place", "company","job", "event", date1, false, false);
                break;
            case 5:
                event = new TripEvent("eventString", localTime1, localTime2,"transportation", "destination", "transNumber",false,false);
                break;
            default:
                event = new GeneralEvent("eventString", localTime1,localTime2, false,false);
        }
        return event;
    }
    public static Event getRandomEvent(int type,LocalTime beginTime,LocalTime endTime){
        Event  event = getRandomEvent(type);
        event.setTimeBegin(beginTime);
        event.setTimeEnd(endTime);
        return event;
    }
    public static Event getRandomEvent(int type,int emergencyAndImportantType){
        Event  event = getRandomEvent(type);
        event.setEmergencyAndImportant(emergencyAndImportantType);
        return event;
    }

    public static boolean hasThisSubEvent(Event sub, ArrayList<Event> list){
        for(Event e: list){
            if(e.getType()==sub.getType()&&e.equals(sub))
                return true;
        }
        return false;
    }
}
