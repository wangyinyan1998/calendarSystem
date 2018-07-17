package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;

/**
 * Created by Think on 2018/5/19.
 */
public class AnniversaryEvent extends Event {
    private String anniversaryName;
    private String anniversaryType;

    public AnniversaryEvent(String event, CalendarDate date, String anniversaryName, String anniversaryType, boolean isEmergency, boolean isImportant) {
        super(event, date, isEmergency, isImportant);
        this.anniversaryName = anniversaryName;
        this.anniversaryType = anniversaryType;
        this.type = eventType.ANNIVERSARY.ordinal();
    }

    public AnniversaryEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, String anniversaryName, String anniversaryType, boolean isEmergency, boolean isImportant) {
        super(eventString, timeBegin, timeEnd, isEmergency, isImportant);
        this.anniversaryName = anniversaryName;
        this.anniversaryType = anniversaryType;
        this.type = eventType.ANNIVERSARY.ordinal();
    }

    public String getAnniversaryName() {
        return this.anniversaryName;
    }

    public String getAnniversaryType() {
        return this.anniversaryType;
    }

    public void setAnniversaryName(String anniversaryName) {
        this.anniversaryName = anniversaryName;
    }

    public void setAnniversaryType(String anniversaryType) {
        this.anniversaryType = anniversaryType;
    }


    public String showEvent() {
        if (super.getEventString() == null)
            return null;
        String time = this.getTimeBegin().toString() + " - " + this.getTimeEnd().toString();
        String returnStr = this.getEventString() + " \n时间:" + time + " \n纪念日类型：" + this.anniversaryType + " " +
                "\n纪念日名称：" + this.anniversaryName + "\n" + EmergencyAndImportantStr() + "\n" + "状态: " +  this.getState();
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();
        return returnStr;
    }

    @Override
    public boolean equals(Event event2) {
        if (event2 instanceof AnniversaryEvent) {
            AnniversaryEvent event3 = (AnniversaryEvent) event2;
            if (event2 == null)
                return false;
            return (super.getEventString().equals(event2.getEventString()) &&
                    super.getTimeEnd().compareTo(event2.getTimeEnd()) == 0 && super.getTimeBegin().compareTo(event2.getTimeBegin()) == 0 &&
                    this.anniversaryType.equals(event3.getAnniversaryType()) && this.anniversaryName.equals(event3.getAnniversaryName()));
        }
        return false;
    }

    public boolean equalsAnniversary(Event event2) {
        if (event2 instanceof AnniversaryEvent) {
            AnniversaryEvent event3 = (AnniversaryEvent) event2;
            if (event2 == null)
                return false;
            return (super.getEventString().equals(event2.getEventString()) &&
                    (super.getTimeEnd().getDate().getMonth() == event2.getTimeEnd().getDate().getMonth()) && (super.getTimeEnd().getDate().getDay() == event2.getTimeEnd().getDate().getDay()) &&
                    this.anniversaryType.equals(event3.getAnniversaryType()) && this.anniversaryName.equals(event3.getAnniversaryName()));
        }
        return false;
    }
}
