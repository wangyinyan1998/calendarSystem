package DateRelated;


/**
 * 这个函数是用来定义时间段的，主要包括日期（date）和小时和分钟
 */

public class LocalTime implements java.io.Serializable{
    private CalendarDate date;
    private int hour;
    private int minute;

    public LocalTime(CalendarDate date, int hour, int minute) {
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    public LocalTime(String s) throws BuildObjectException {
        if (s == null)
            throw new BuildObjectException("null string cannot build LocalTime");
        else {
            String[] parts = s.split(" ");
            CalendarDate cd = new CalendarDate(parts[0]);
            String[] timeParts = parts[1].split(":");
            int h = Integer.parseInt(timeParts[0]);
            int m = Integer.parseInt(timeParts[1]);
            this.date = cd;
            this.hour = h;
            this.minute = m;
        }
    }

    public void setDate(CalendarDate date) {
        this.date = date;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public CalendarDate getDate() {
        return this.date;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    /*对两个时间段进行比较，如果前者比后者时间早，那么返回1，如果在同一时间，则返回0，否则返回-1*/
    public int compareTo(LocalTime time2) {
        if (time2 == null)
            return -1;
        int i = date.compareTo(time2.getDate());
        if (i != 0)
            return i;
        if (hour < time2.getHour())
            return 1;
        if (hour > time2.getHour())
            return -1;
        if (minute < time2.getMinute())
            return 1;
        if (minute > time2.getMinute())
            return -1;
        return 0;
    }

    /*将时间段和日期进行比较，如果前者比后者早，则返回1，如果两个是在同一天则返回0，否则返回-1*/
    public int compareToDate(CalendarDate date2) {
        return date.compareTo(date2);
    }

    /*将时间段写成字符串形式*/
    @Override
    public String toString() {
        return date.toString() + " " + hour + ":" + minute;
    }
}
