package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import DisplayWindows.Display;

public class InterviewEvent extends Event {
    String interviewTime;
    String place;
    String company;
    ;
    String job;

    public InterviewEvent(String interviewTime, String place, String company, String job, String event, CalendarDate date, boolean isEmergency, boolean isImportant) {
        super(event, date, isEmergency, isImportant);
        this.interviewTime = interviewTime;
        this.company = company;
        this.job = job;
        this.place = place;
        this.type = eventType.INTERVIEW.ordinal();
    }

    public InterviewEvent(String interviewTime, String place, String company, String job, String event, LocalTime timeBegin, LocalTime timeEnd, boolean isEmergency, boolean isImportant) {
        super(event, timeBegin, timeEnd, isEmergency, isImportant);
        this.interviewTime = interviewTime;
        this.company = company;
        this.job = job;
        this.place = place;
        this.type = eventType.INTERVIEW.ordinal();
    }

    @Override
    public String showEvent() {
        String time = getTimeBegin().toString() + " - " + getTimeEnd().toString();
        String returnStr = "公司面试： \n时间:" + time + " \n面试时间：" + interviewTime.toString() + " \n面试地点：" + this.place +
                "\n面试公司：" + company + "\n面试职位：" + job + "\n备注：" + eventString + "\n" + EmergencyAndImportantStr() + "\n" + "状态: " +  this.getState();
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();
        return returnStr;
    }

    @Override
    public boolean equals(Event event2) {
        if (event2 instanceof InterviewEvent) {
            return (company.equals(((InterviewEvent) event2).getCompany()) && place.equals(((InterviewEvent) event2).getPlace())
                    && interviewTime.equals(((InterviewEvent) event2).getInterviewTime()) && getEventString().equals(event2.getEventString()) &&
                    getTimeEnd().compareTo(event2.getTimeEnd()) == 0 && getTimeBegin().compareTo(event2.getTimeBegin()) == 0 && job.equals(((InterviewEvent) event2).getJob()));
        }
        return false;
    }

    public String getJob() {
        return job;
    }

    public String getPlace() {
        return place;
    }

    public String getCompany() {
        return company;
    }

    public String getInterviewTime() {
        return interviewTime;
    }


}
