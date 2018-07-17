package DateRelated;

import Event.Event;
import HolidayRelated.Holidays;

/**
 * We have finished part of this class yet, you should finish the rest.
 * 1. A constructor that can return a CalendarDate object through the given string.
 * 2. A method named getDayOfWeek() that can get the index of a day in a week.
 */
public class CalendarDate implements java.io.Serializable{
    private int year;
    private int month;
    private int day;
    private boolean haveEvent;

    public CalendarDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
        this.haveEvent = Event.isTheDateHaveEvent(this);
    }
    public CalendarDate(String dateString) throws BuildObjectException {
        if(DateUtil.isFormatted(dateString)) {
            String[] dateInfo = dateString.split("-");
            this.year = Integer.parseInt(dateInfo[0]);
            this.month = Integer.parseInt(dateInfo[1]);
            this.day = Integer.parseInt(dateInfo[2]);
            this.haveEvent = Event.isTheDateHaveEvent(this);
        }
        else
            throw new BuildObjectException("你要查询的日期有误，请输入正确的日期,例如2018-3-28");
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    /**
     * Get index of the day in a week for one date.
     *
     * Don't use the existing implement like Calendar.setTime(),
     * try to implement your own algorithm.
     * @return 1-7, 1 stands for Monday and 7 stands for Sunday
     */
    public int getDayOfWeek(){
        if(!DateUtil.isValid(this)) {
            return -1;
        }
        int totalDaysFromTheYearBegin = day;
        for (int i = 1; i < month; i++)
            totalDaysFromTheYearBegin += DateUtil.getDayOfMonth(i, year);
        int week = (year + year / 4 + year / 400 - year / 100 + totalDaysFromTheYearBegin) % 7 - 1;
        if(week==-1)
            return 6;
        if (week == 0)
            return 7;
        return week;
    }

    /*设置date是否有事件*/
    public void setHaveEvent(boolean haveEvent) {
        this.haveEvent = haveEvent;
    }
    /*得到date是否有事件*/
    public boolean ifHaveEvent(){
        this.haveEvent = Event.isTheDateHaveEvent(this);
        return this.haveEvent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        int year = ((CalendarDate) obj).getYear();
        int month = ((CalendarDate) obj).getMonth();
        int day = ((CalendarDate) obj).getDay();
        return this.getYear() == year && this.getMonth() == month && this.getDay() == day;
    }
    /*将date的信息字符串化*/
    public String toString(){
        return getYear()+"-"+getMonth()+"-"+getDay();
    }
    /*比较两个日期的早晚，如果前者比后者早，则返回1，相同则返回0，否则返回-1*/
    public int compareTo(CalendarDate date2){
        if(date2==null)
            return -1;
        if(year<date2.getYear())
            return 1;
        if(year>date2.getYear())
            return -1;
        if(month<date2.getMonth())
            return 1;
        if(month>date2.getMonth())
            return -1;
        if(day < date2.getDay())
            return 1;
        if (day > date2.getDay())
            return -1;
        return 0;
    }


    public CalendarDate getTomorrow(){
        if(day<DateUtil.getDayOfMonth(month,year))
            return new CalendarDate(year,month,day+1);
        else {
            if(month<12){
                return new CalendarDate(year,month+1,1);
            }
            return new CalendarDate(year+1,1,1);
        }
    }

}

