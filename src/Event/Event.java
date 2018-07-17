package Event;

import DateRelated.*;
import DateRelated.LocalTime;
import DisplayWindows.Display;

import javax.swing.event.HyperlinkEvent;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Event implements java.io.Serializable{
    protected String eventString;
    protected LocalTime timeBegin;
    protected LocalTime timeEnd;
    protected boolean isWholeDay;
    protected boolean isEmergency;
    protected boolean isImportant;
    protected Alarm alarm;///by fulixue
    private static ArrayList<Event> events = new ArrayList<Event>();
    //add by wangjiahui
    protected ArrayList<Event> subEvent;
    public String[] eventState = {"未开始", "进行中", "已完成", "过期"};
    protected String state;
    protected boolean isComplete;
    protected int type;

    public enum eventType {
        ANNIVERSARY, COURSE, DATE, MEETING, INTERVIEW, TRIP, GENERAL
    }

    /*用来添加一整天的事件*/
    public Event(String event, CalendarDate date, boolean isEmergency, boolean isImportant) {
        this.eventString = event;
        isWholeDay = true;
        this.timeBegin = new LocalTime(date, 0, 0);
        this.timeEnd = new LocalTime(date, 23, 59);
        this.isEmergency = isEmergency;
        this.isImportant = isImportant;
        this.isComplete = false;
        this.alarm = new Alarm();
        //isNotWholeDay=false;
        //add by wangjiahi
        subEvent = new ArrayList<Event>();
    }

    public Event(String eventString, LocalTime timeBegin, LocalTime timeEnd, boolean isEmergency, boolean isImportant) {
        CalendarDate dateBegin = timeBegin.getDate();
        dateBegin.setHaveEvent(true);
        this.timeBegin = timeBegin;
        this.eventString = eventString;
        this.timeEnd = timeEnd;
        this.isWholeDay = false;
        this.isEmergency = isEmergency;
        this.isImportant = isImportant;
        this.isComplete = false;
        this.alarm = new Alarm();////
        subEvent = new ArrayList<Event>();
    }

    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }

    public LocalTime getTimeBegin() {
        return this.timeBegin;
    }

    public String getEventString() {
        return eventString;
    }

    public boolean getEmergency() {
        return isEmergency;
    }

    public boolean getImportant() {
        return isImportant;
    }

    public String getState() {
        return state;
    }

    public boolean getComplete() {
        return isComplete;
    }

    //    public static  boolean getNotWholeDay(){
//        return isNotWholeDay;}///fulixue
    public Alarm getAlarm() {
        return alarm;
    }///////

    public static void setEvents(ArrayList<Event> read) {
        for (Event event : read) {
            setEventState(event);
        }
        Event.events = read;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Event> getSubEvent() {
        return subEvent;
    }

    //add by ZZY
    public void setIsWholeDay(boolean b) {
        isWholeDay = b;
    }

    public void setEmergency(boolean b) {
        isEmergency = b;
    }

    public void setImportant(boolean b) {
        isImportant = b;
    }

    public void setTimeBegin(LocalTime begin) {
        this.timeBegin = begin;
    }

    public void setTimeEnd(LocalTime end) {
        this.timeEnd = end;
    }

    public void setSubEvent(ArrayList<Event> read) {
        subEvent = read;
    }
    //ZZY
//    public static void setNotWholeDay(boolean b){
//        isNotWholeDay=b;
//    }///fulixue

    public void setState(String state) {
        this.state = state;
    }

    public void setComplete(boolean isComplete) {
        if (!this.isComplete) {
            this.isComplete = isComplete;
        }
    }

    public void setAlarm(Alarm isAlarm) {
        this.alarm = isAlarm;
    }//by fulixue

    public int getType() {
        return type;
    }

    public boolean isEventValid() {
        if (timeBegin.compareTo(timeEnd) <= 0)
            return false;
        return true;
    }

    //返回事件是否是全天，用于事件的详细信息页进行展示 事件信息
    public boolean isWholeDay() {
        return isWholeDay;
    }

    public static boolean isTheDateHaveEvent(CalendarDate date) {
        for (Event event : events) {
            int compare1 = event.getTimeBegin().compareToDate(date);//事件的开始时间要早于结束时间段
            int compare2 = event.getTimeEnd().compareToDate(date);//事件的结束时间要晚于开始时间段
            if (compare1 >= 0 && compare2 <= 0)
                return true;
        }
        return false;
    }

    public static boolean isTheDateHaveAnniversary(CalendarDate date) {
        for (Event event : events) {
            if (event instanceof AnniversaryEvent) {
                int month = event.getTimeBegin().getDate().getMonth();
                int day = event.getTimeBegin().getDate().getDay();
                int year = date.getYear();
                if ((month == date.getMonth()) && (day == date.getDay())) {
                    LocalTime newBeginTime = new LocalTime(new CalendarDate(year, month, day), event.getTimeBegin().getHour(), event.getTimeBegin().getMinute());
                    LocalTime newEndTime = new LocalTime(new CalendarDate(year, month, day), event.getTimeEnd().getHour(), event.getTimeEnd().getMinute());
                    AnniversaryEvent anniversaryEvent = new AnniversaryEvent(event.getEventString(), newBeginTime, newEndTime,
                            ((AnniversaryEvent) event).getAnniversaryName(), ((AnniversaryEvent) event).getAnniversaryType(), event.getEmergency(), event.getImportant());
                    Event.rememberEvents(anniversaryEvent);
                    return true;
                }
            }
        }
        return false;
    }

    //add by ZZY
    public static boolean isTheDateHaveCourse(CalendarDate date) {
        for (Event event : events) {
            if (event instanceof CourseEvent) {
                CalendarDate courseDate = ((CourseEvent) event).getStartCourseTime().getDate();
                if (courseDate.equals(date)) {
                    return true;
                }
                int counter = 1;
                while (counter < ((CourseEvent) event).getLastWeeks()) {
                    courseDate = DateUtil.getDayOfNextWeek(courseDate);
                    if (courseDate.equals(date)) {
                        LocalTime timeBegin = new LocalTime(date, ((CourseEvent) event).getTimeBegin().getHour(), ((CourseEvent) event).getTimeBegin().getMinute());
                        LocalTime timeEnd = new LocalTime(date, ((CourseEvent) event).getTimeEnd().getHour(), ((CourseEvent) event).getTimeEnd().getMinute());
                        CourseEvent nextWeekCourse = new CourseEvent(((CourseEvent) event).getStartCourseTime(), ((CourseEvent) event).getCourseName(), ((CourseEvent) event).getPlace(), ((CourseEvent) event).getLastWeeks(), ((CourseEvent) event).getTeacher(), ((CourseEvent) event).getCourseContent(),
                                ((CourseEvent) event).getEventString(), timeBegin, timeEnd, ((CourseEvent) event).getEmergency(), ((CourseEvent) event).getImportant());
                        Event.rememberEvents(nextWeekCourse);
                        return true;
                    }
                    counter++;
                }
            }
        }
        return false;
    }
    //ZZY

    /*记住（保存）事件，对它进行重写了，主要修改的是在保存之前已经保存过的事件时，会将原来的事件删除，然后添加最新的事件*/
    /*记住（保存）事件，对它进行重写了，主要修改的是在保存之前已经保存过的事件时，会将原来的事件删除，然后添加最新的事件*/
    public static boolean rememberEvents(Event event) {
        if (event == null)
            return false;
        if (EventUtil.haveConflict(event))
            return false;
        for (Event event1 : events) {
            if (event1.equals(event)) {
                events.sort(new SortEvent());
                return true;
            }
        }
        if (event.isEventValid()) {
            events.add(event);
            events.sort(new SortEvent());
            return true;
        }
        return false;
    }

    //添加子事件，这个方法还对保存有父事件的events进行了刷新，即保存的是存有父事件的events
    public boolean addSubEvent(Event event) {
        if (event == null)
            return false;
        if (EventUtil.conflictWithOtherSubEvent(event, subEvent) != null)
            return false;
        for (Event event1 : subEvent) {
            if (event1.equals(event)) {
                subEvent.remove(event1);
                subEvent.add(event);
                subEvent.sort(new SortEvent());
                rememberEvents(this);
                events.sort(new SortEvent());
                return true;
            }
        }
        if (event.isEventValid()) {
            subEvent.add(event);
            rememberEvents(this);
            subEvent.sort(new SortEvent());
            events.sort(new SortEvent());
            return true;
        }
        return false;
    }


    /*删除事件*/
    public static boolean deleteEvents(Event event) {
        for (Event event1 : events) {
            if (event1.equals(event)) {
                events.remove(event);
                return true;
            }
        }
        return false;
    }

    public boolean deleteSubEvent(Event event) {
        for (Event event1 : subEvent) {
            if (event1.equals(event)) {
                subEvent.remove(event);
                return true;
            }
        }
        return false;
    }


    /*根据提供的日期查询事件*/
    public static ArrayList<Event> searchEventByDate(CalendarDate date) {
        isTheDateHaveAnniversary(date);
        isTheDateHaveCourse(date);
        ArrayList<Event> returnEvents = new ArrayList<Event>();
        //notWholeAndNotComplete(events);//用来在时间为23:59分的时候判断事件notWholeDay且未完成
        for (Event event : events) {
            if (event instanceof GeneralEvent && ((GeneralEvent) event).isWithoutSpecificTimeSetting()) {
                if (((GeneralEvent) event).getFinishTime() == null) {
                    if (date.equals(DateUtil.getToday()))
                        returnEvents.add(event);
                } else {
                    if (date.equals(((GeneralEvent) event).getFinishTime().getDate()))
                        returnEvents.add(event);
                }
            } else if (event.getTimeBegin().compareToDate(date) >= 0 && event.getTimeEnd().compareToDate(date) <= 0)//判断自定义事件是否完成，若未完成则置顶
                returnEvents.add(event);
        }
        if (returnEvents.size() == 0)
            return null;

        returnEvents.sort(new SortEvent());
        return returnEvents;
    }

    /*根据时间段查询事件*/
    public static ArrayList<Event> searchEventByTime(LocalTime timeBegin, LocalTime timeEnd) {
        if (timeBegin == null || timeEnd == null)
            return null;
        ArrayList<CalendarDate> betweenDates = DateUtil.getBetweenDates(timeBegin.getDate(), timeEnd.getDate());
        if (betweenDates != null) {
            for (CalendarDate date : betweenDates) {
                isTheDateHaveAnniversary(date);
                isTheDateHaveCourse(date);
            }
        }
        ArrayList<Event> returnEvent = new ArrayList<Event>();
        for (Event event : events) {
            if (event instanceof GeneralEvent && ((GeneralEvent) event).isWithoutSpecificTimeSetting()) {
                if (((GeneralEvent) event).getFinishTime() == null) {
                    int compare1 = event.getTimeBegin().compareTo(timeEnd);//事件的开始时间要早于结束时间段
                    int compare2 = event.getTimeEnd().compareTo(timeBegin);//事件的结束时间要晚于开始时间段
                    if (compare1 >= 0 && compare2 <= 0)
                        returnEvent.add(event);
                } else {
                    int compare1 = ((GeneralEvent) event).getFinishTime().compareTo(timeEnd);//事件的完成时间要早于结束时间段
                    int compare2 = ((GeneralEvent) event).getFinishTime().compareTo(timeBegin);//事件的完成时间要晚于开始时间段
                    if (compare1 >= 0 && compare2 <= 0)
                        returnEvent.add(event);
                }
            } else {
                int compare1 = event.getTimeBegin().compareTo(timeEnd);//事件的开始时间要早于结束时间段
                int compare2 = event.getTimeEnd().compareTo(timeBegin);//事件的结束时间要晚于开始时间段
                if (compare1 >= 0 && compare2 <= 0)
                    returnEvent.add(event);
            }
        }

        if (returnEvent.size() == 0)
            return null;
        returnEvent.sort(new SortEvent());
        return returnEvent;
    }

    //这个函数用于单元测试
    public static void eventClear() {
        Event.events = new ArrayList<Event>();
    }

    /*判断两个事件是否是同一事件（用于搜索事件的函数中）*/
    public abstract boolean equals(Event event2);

    /*输出事件的详细信息*/
    public abstract String showEvent();

    public int EmergencyAndImportant() {
        if (isEmergency && isImportant) {
            return 1;
        }
        if (isEmergency && !isImportant) {
            return 2;
        }
        if (!isEmergency && isImportant) {
            return 3;
        }
        return 4;
    }

    public String EmergencyAndImportantStr() {
        if (isEmergency && isImportant) {
            return "优先级：紧急&重要";
        }
        if (isEmergency && !isImportant) {
            return "优先级：紧急&不重要";
        }
        if (!isEmergency && isImportant) {
            return "优先级：不紧急&重要";
        }
        return "优先级：不紧急&不重要";
    }

    //ADD BY WANGJIAHUI
    public String getSubEventString() {
        String returnStr = "";
        if (this.getSubEvent().size() != 0) {
            returnStr = "\n" + "子待办事项: ";
            for (Event event : this.getSubEvent()) {
                returnStr+= "  "+event.getEventTypeString()+"  ";
            }
        }
        return returnStr;
    }

    //add by ZZY
    public void setEmergencyAndImportant(int i) {
        if (i == 1) {
            setEmergency(true);
            setImportant(true);
        }
        if (i == 2) {
            setEmergency(true);
            setImportant(false);
        }
        if (i == 3) {
            setEmergency(false);
            setImportant(true);
        }
        if (i == 4) {
            setEmergency(false);
            setImportant(false);
        }
    }

    //at 23:59 reset the time of gereral without specific time setting by ZZY
    public static void resetTimeOfGeneralEventWithoutSpecificTime() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.HOUR_OF_DAY) == 23 & cal.get(Calendar.MINUTE) == 59) {
            for (Event event : events) {
                if ((event instanceof GeneralEvent) && ((GeneralEvent) event).isWithoutSpecificTimeSetting() && !event.isComplete) {
                    LocalTime newTimeBegin = new LocalTime(event.getTimeBegin().getDate().getTomorrow(), event.getTimeBegin().getHour(), event.getTimeBegin().getMinute());
                    LocalTime newTimeEnd = new LocalTime(event.getTimeEnd().getDate().getTomorrow(), event.getTimeEnd().getHour(), event.getTimeEnd().getMinute());
                    event.setTimeBegin(newTimeBegin);
                    event.setTimeEnd(newTimeEnd);
                }
            }
        } else
            return;
    }

    public static void setEventState(Event event) {
        CalendarDate todayDate = DateUtil.getToday();
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        LocalTime currentTime = new LocalTime(todayDate, hour, minute);
        if (event.getComplete()) {
            event.setState("已完成");
        } else if (event.getTimeBegin().compareTo(currentTime) < 0) {
            event.setState("未开始");
        } else if (event.getTimeBegin().compareTo(currentTime) >= 0 && event.getTimeEnd().compareTo(currentTime) <= 0) {
            event.setState("进行中");
        } else if (event.getTimeEnd().compareTo(currentTime) > 0) {
            event.setState("过期");
        }
    }


    //王银燕修改1


    public String getObtainInformation(boolean haveConflict) {
        String s = "事件类型：" + getEventTypeString() +
                "\n事件详情：" + eventString + "\n开始时间：" + timeBegin.toString() + "\n结束时间：" +
                timeEnd.toString() + "\n" +"事件状态："+state;
        if(haveConflict)
            return s;

        s += "\n"+alarm.getAlarmInfo()+
                "\n子事件个数：" + subEvent.size();
        int i = 1;
        for (Event e : subEvent) {
            s +="\n  "+ i + "、" + e.getEventTypeString() + " 状态："+e.getState();
            i++;
        }
        return s;
    }

    public String getEventTypeString() {
        switch (type) {
            case 0:
                return "纪念日";
            case 1:
                return "课程";
            case 2:
                return "约会";
            case 3:
                return "会议";
            case 4:
                return "面试";
            case 5:
                return "旅程";
            case 6:
                return "自定义事项";
        }
        return null;
    }
}


