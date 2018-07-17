package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;

/**
 * Created by Think on 2018/5/6.
 */
public class MeetingEvent extends Event {
    private String place;
    private String theme;

    public MeetingEvent(String event, CalendarDate date, String place, String theme, boolean isEmergency, boolean isImportant) {
        super(event, date, isEmergency, isImportant);
        this.place = place;
        this.theme = theme;
        this.type = eventType.MEETING.ordinal();
    }

    public MeetingEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, String place, String theme, boolean isEmergency, boolean isImportant) {
        super(eventString, timeBegin, timeEnd, isEmergency, isImportant);
        this.place = place;
        this.theme = theme;
        this.type = eventType.MEETING.ordinal();
    }

    public String getPlace() {
        return this.place;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String showEvent() {
        if (super.getEventString() == null)
            return null;
        String time = this.getTimeBegin().toString() + " - " + this.getTimeEnd().toString();
        String returnStr = this.getEventString() + " \n时间:" + time + " \n地点：" + this.place + "\n会议主题："
                + this.theme + "\n" + EmergencyAndImportantStr() + "\n" + "状态: " +  this.getState();
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();
        return returnStr;
    }

    @Override
    public boolean equals(Event event2) {
        if (event2 instanceof MeetingEvent) {
            MeetingEvent event3 = (MeetingEvent) event2;
            if (event2 == null)
                return false;
            return (super.getEventString().equals(event2.getEventString()) &&
                    super.getTimeEnd().compareTo(event2.getTimeEnd()) == 0 && super.getTimeBegin().compareTo(event2.getTimeBegin()) == 0 &&
                    this.place.equals(event3.getPlace()) && this.theme.equals(event3.getTheme()));
        }
        return false;
    }
}
