package Event;

import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;

import javax.swing.*;
import java.util.ArrayList;

public class EventUtil {
    private EventUtil() {
    }

    //判断要保存的事件与已保存的事项是否有时间冲突，如果有，则要跳出弹框。
    public static boolean haveConflict(Event event) {
        boolean haveConflict = false;
        String message = "";
        for (Event event1 : Event.getEvents()) {
            if(ifTwoEventsHaveConflict(event1,event)){
                message += "新添加的事项与 "+event1.getObtainInformation(true) + " 在 " + getConflictTimeString(event, event1)+"有冲突!\n";
                haveConflict = true;
            }
        }
        if (message.length() != 0)
            JOptionPane.showMessageDialog(null, message, " 日历", JOptionPane.ERROR_MESSAGE);
        return haveConflict;
    }
    public static boolean ifTwoEventsHaveConflict(Event event1,Event event2){
        if(event1==null || event2==null)
            return false;
        int type1 = event1.getType();
        boolean event1TypeLimit = (type1 == Event.eventType.MEETING.ordinal() || type1 == Event.eventType.COURSE.ordinal() ||
                type1 == Event.eventType.DATE.ordinal() || type1 == Event.eventType.TRIP.ordinal() || type1 == Event.eventType.INTERVIEW.ordinal());
        int type2 = event2.getType();
        //  event1HaveSubEvent = event1.getSubEvent().size() != 0;
       boolean event2TypeLimit = (type2 == Event.eventType.MEETING.ordinal() || type2 == Event.eventType.COURSE.ordinal() ||
                type2 == Event.eventType.DATE.ordinal() || type1 == Event.eventType.TRIP.ordinal() || type2 == Event.eventType.INTERVIEW.ordinal());
       String conflictString = getConflictTimeString(event1, event2);
       return event1TypeLimit && event2TypeLimit && conflictString!=null;
    }

    //判断两个event是否有冲突的时间段，如果有，返回一个冲突时间段信息，如果没有，返回null
    public static String getConflictTimeString(Event event1, Event event2) {
        String conflictTime = null;
        if(event1.equals(event2))
            return null;
        if(event2.getTimeBegin().compareTo(event1.getTimeEnd())>=0 && event1.getTimeBegin().compareTo(event2.getTimeBegin())>=0
                &&!(event2.getTimeBegin().compareTo(event1.getTimeEnd())==0 && event1.getTimeBegin().compareTo(event2.getTimeBegin())==0) ){
            if(event1.getTimeEnd().compareTo(event2.getTimeEnd())>0)
                conflictTime = event2.getTimeBegin().toString() + " 到 " + event1.getTimeEnd().toString();
            else
                conflictTime = event2.getTimeBegin().toString() + " 到 " + event2.getTimeEnd().toString();
        }
        else if(event1.getTimeBegin().compareTo(event2.getTimeEnd())>=0 && event2.getTimeBegin().compareTo(event1.getTimeBegin())>=0 &&
                !(event1.getTimeBegin().compareTo(event2.getTimeEnd())==0 && event2.getTimeBegin().compareTo(event1.getTimeBegin())==0) ){
            if(event2.getTimeEnd().compareTo(event1.getTimeEnd())>=0)
                conflictTime = event1.getTimeBegin().toString() + " 到 " + event2.getTimeEnd().toString();
            else
                conflictTime = event1.getTimeBegin().toString() + " 到 " + event1.getTimeEnd().toString();
        }
        return conflictTime;
    }

    //add by ZZY, to determine whether this event has conflict with a list of other events.
    public static String conflictWithOtherSubEvent(Event thisEvent, ArrayList<Event> subEventList){
        String conflictStr;
        String message="";
        if(thisEvent.getTimeBegin().compareTo(thisEvent.getTimeEnd())==0)
            return "事件的起始时间和结束时间相同";
        for(Event event: subEventList){
            conflictStr = getConflictTimeString(event, thisEvent);
            if(conflictStr != null)
                message +="新添加的事项与 "+event.getObtainInformation(true) + " 在 " + getConflictTimeString(event, thisEvent)+"有冲突\n";
        }
        if(message.length()!=0)
            return message;
        for(Event event:Event.getEvents()){
            ArrayList<Event> tmpSubEvents = event.getSubEvent();
            if(tmpSubEvents.size()!=0){
                for(Event subEvent:tmpSubEvents){
                    if(ifTwoEventsHaveConflict(thisEvent,subEvent))
                       message += "新添加的事项与 "+subEvent.getObtainInformation(true) + " 在 " + getConflictTimeString(thisEvent, subEvent)+"有冲突\n";
                }
            }
        }
        if(message.length()==0)
            return null;
        return message;
    }

    //this method changed by ZZY
    public static boolean fatherEventCompletelyContainsSubEvent(Event father, Event son){
        if(!(son instanceof CourseEvent)) {
            int c1 = father.getTimeBegin().compareTo(son.getTimeBegin());
            int c2 = father.getTimeEnd().compareTo(son.getTimeEnd());
            return c1 >= 0 && c2 <= 0;
        }
        else{
            LocalTime sonRealTimeBegin = ((CourseEvent) son).getStartCourseTime();
            CalendarDate finalDate = sonRealTimeBegin.getDate();
            for(int i=0;i<((CourseEvent) son).getWeekRepeat()-1;i++){
                finalDate = DateUtil.getDayOfNextWeek(finalDate);
            }
            LocalTime sonRealTimeEnd = new LocalTime(finalDate, sonRealTimeBegin.getHour(),sonRealTimeBegin.getMinute());
            int c1 = father.getTimeBegin().compareTo(sonRealTimeBegin);
            int c2 = father.getTimeEnd().compareTo(sonRealTimeEnd);
            return c1 >= 0 && c2 <= 0;
        }

    }

    public static ArrayList<Event> getAlarmEvents(){
        ArrayList<Event> events =  Event.getEvents();
        ArrayList<Event> alarmEvents = new ArrayList<Event>();
        for(Event event:events){
            if(event.getAlarm().getIsAlarm() ){
                alarmEvents.add(event);
            }
        }
        return alarmEvents;
    }

    public static ArrayList<Event> getSubAlarmEvents(Event parentEvent){
        ArrayList<Event> events =  parentEvent.getSubEvent();
        ArrayList<Event> alarmEvents = new ArrayList<Event>();
        for(Event event:events){
            if(event.getAlarm().getIsAlarm() ){
                alarmEvents.add(event);
            }
        }
        return alarmEvents;
    }
}
