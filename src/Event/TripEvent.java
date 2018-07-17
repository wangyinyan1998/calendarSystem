package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;

/**
 * Created by ZZY on 2018/5/19.
 */
public class TripEvent extends Event {
    private String transportation;
    private String destination;
    private String transNumber;

    //here super.eventString represents note
    public TripEvent(String event, CalendarDate date, String transportation, String destination, String transNumber, boolean isEmergency, boolean isImportant) {
        super(event, date, isEmergency, isImportant);
        this.transNumber = transNumber;
        this.destination = destination;
        this.transportation = transportation;
        this.type = eventType.TRIP.ordinal();
    }

    public TripEvent(String eventString, LocalTime timeBegin, LocalTime timeEnd, String transportation, String destination, String transNumber, boolean isEmergency, boolean isImportant) {
        super(eventString, timeBegin, timeEnd, isEmergency, isImportant);
        this.transNumber = transNumber;
        this.destination = destination;
        this.transportation = transportation;
        this.type = eventType.TRIP.ordinal();
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    public String getTransNumber() {
        return transNumber;
    }

    public String showEvent() {
        if (super.getEventString() == null)
            return null;
        String time = this.getTimeBegin().toString() + " - " + this.getTimeEnd().toString();
        String returnStr = "备注：" + this.getEventString() + " \n时间:" + time + " \n交通方式：" + this.transportation + "\n地点：" + this.destination +
                "\n车次/航班号：" + this.transNumber + "\n" + EmergencyAndImportantStr() + "\n" + "状态: " +  this.getState();
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();
        return returnStr;
    }
    public boolean equals(Event event2) {
        if (event2 instanceof TripEvent) {
            TripEvent event3 = (TripEvent) event2;
            if (event2 == null)
                return false;
            return (super.getEventString().equals(event2.getEventString()) &&
                    super.getTimeEnd().compareTo(event2.getTimeEnd()) == 0 && super.getTimeBegin().compareTo(event2.getTimeBegin()) == 0 &&
                    this.destination.equals(event3.getDestination()) && this.transportation.equals(event3.getTransportation()) && this.transNumber.equals(event3.getTransNumber())
                    && this.getEmergency() == event2.getEmergency() && this.getImportant() == event2.getImportant());
        }
        return false;
    }
}
