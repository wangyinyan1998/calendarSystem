package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;

import java.util.ArrayList;

/**
 * Created by Think on 2018/5/6.
 */
public class DateEvent extends Event {
    private String place;
    private String attendPeople;

    public DateEvent(String event, CalendarDate date, String place, String attendPeople, boolean isEmergency, boolean isImportant) {
        super(event, date, isEmergency, isImportant);
        this.place = place;
        this.attendPeople = attendPeople;
        this.type = eventType.DATE.ordinal();
    }

    public DateEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, String place, String attendPeople, boolean isEmergency, boolean isImportant) {
        super(eventString, timeBegin, timeEnd, isEmergency, isImportant);
        this.place = place;
        this.attendPeople = attendPeople;
        this.type = eventType.DATE.ordinal();
    }

    public String getPlace() {
        return this.place;
    }

    public String getAttendPeople() {
        return this.attendPeople;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setAttendPeople(String attendPeople) {
        this.attendPeople = attendPeople;
    }

    @Override
    public String showEvent() {
        if (super.getEventString() == null)
            return null;
        String time = this.getTimeBegin().toString() + " - " + this.getTimeEnd().toString();
        String returnStr = this.getEventString() + " \n时间:" + time + " \n地点：" +
                this.place + " \n人员：" + this.attendPeople + "\n" + EmergencyAndImportantStr() + "\n" + "状态: " +  this.getState();
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();
        return returnStr;
    }

    @Override
    public boolean equals(Event event2) {
        if (event2 instanceof DateEvent) {
            DateEvent event3 = (DateEvent) event2;
            if (event2 == null)
                return false;
            return (super.getEventString().equals(event2.getEventString()) &&
                    super.getTimeEnd().compareTo(event2.getTimeEnd()) == 0 && super.getTimeBegin().compareTo(event2.getTimeBegin()) == 0 &&
                    this.place.equals(event3.getPlace()) && this.attendPeople.equals(event3.getAttendPeople()));
        }
        return false;
    }
}
