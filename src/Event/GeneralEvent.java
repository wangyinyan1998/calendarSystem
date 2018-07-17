package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;


public class GeneralEvent extends Event {

    protected boolean withoutSpecificTimeSetting;//ZZY
    protected LocalTime finishTime;//ZZY
    public GeneralEvent(String event, CalendarDate date, boolean isEmergency, boolean isImportant) {
        super(event, date, isEmergency, isImportant);
        this.type = eventType.GENERAL.ordinal();
        this.withoutSpecificTimeSetting = false;//ZZY
    }

    /*用来添加有时间段的事件*/
    public GeneralEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, boolean isEmergency, boolean isImportant) {
        super(eventString, timeBegin, timeEnd, isEmergency, isImportant);
        this.type = eventType.GENERAL.ordinal();
        this.withoutSpecificTimeSetting = false;//ZZY
    }

    public boolean equals(Event event2) {
        if (event2 == null)
            return false;
        return (eventString.equals(event2.getEventString()) && timeEnd.compareTo(event2.getTimeEnd()) == 0 &&
                timeBegin.compareTo(event2.getTimeBegin()) == 0&& this.isEmergency == event2.isEmergency && this.isImportant == event2.isImportant);
    }

    public String showEvent() {
        if (this.eventString == null)
            return null;
        String time = timeBegin.toString() + " - " + timeEnd.toString();
        String returnStr = eventString + " \n时间:" + time + "\n" + EmergencyAndImportantStr() + "\n" + "状态: "
                + this.getState() + "\n";
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();
        return returnStr;
    }
    //ZZY
    public void setWithoutSpecificTimeSetting(boolean b){withoutSpecificTimeSetting = b;}

    public boolean isWithoutSpecificTimeSetting(){return withoutSpecificTimeSetting;}

    public void setFinishTime(LocalTime finishTime){
        this.finishTime = finishTime;
    }

    public LocalTime getFinishTime(){
        return finishTime;
    }

}