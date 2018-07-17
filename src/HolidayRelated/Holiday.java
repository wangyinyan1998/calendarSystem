package HolidayRelated;


import DateRelated.CalendarDate;

/**
 * 王银燕新添加的类，指的是节日类
 */
public class Holiday {
    CalendarDate startTime;
    CalendarDate endTime;
    CalendarDate holidayTime;
    String holidayZHName;
    public Holiday(CalendarDate startTime,CalendarDate endTime,CalendarDate holidayTime,String holidayZHName){
        this.startTime = startTime;
        this.endTime = endTime;
        this.holidayTime = holidayTime;
        this.holidayZHName = holidayZHName;
    }
    public String getHolidayZHName() {
        return holidayZHName;
    }
    public CalendarDate getStartTime(){
        return startTime;
    }
    public CalendarDate getEndTime(){
        return endTime;
    }
    public CalendarDate getHolidayTime(){
        return holidayTime;
    }
    public boolean dateIsHolidayTime(CalendarDate date){
        return holidayTime.equals(date);
    }
    @Override
    public boolean equals(Object obj) {
        Holiday holiday2 = (Holiday)obj;
        if (holiday2==null)
            return false;
        return holidayZHName.equals(holiday2.getHolidayZHName());
    }
}
