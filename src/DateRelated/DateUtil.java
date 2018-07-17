package DateRelated;
/*
 * This class provides some utils that may help you to finish this lab.
 * getToday() is finished, you can use this method to get the current date.
 * The other four methods getDaysInMonth(), isValid(), isFormatted() and isLeapYear() are not finished,
 * you should implement them before you use.
 *
 * */

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class DateUtil  {
    private DateUtil() {
    }

    public static CalendarDate getToday() {
        Calendar calendar = Calendar.getInstance();
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        if (!isValid(date))
            return null;
        List<CalendarDate> dateList = new LinkedList<CalendarDate>();
        int month = date.getMonth();
        int year = date.getYear();
        int dayNumber = getDayOfMonth(month, year);
        for (int i = 1; i <= dayNumber; i++) {
            dateList.add(new CalendarDate(year, month, i));
        }
        return dateList;
    }

    public static boolean isValid(CalendarDate date) {
        if (date == null)
            return false;
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        return (year >= 0 && month > 0 && month <= 12 && getDayOfMonth(month, year) > 0 && day > 0 && day <= getDayOfMonth(month, year));
    }

    public static boolean isFormatted(String dateString) {
        if (dateString == null)
            return false;
        return Pattern.compile("[0-9]{0,4}-[0-9]{1,2}-[0-9]{1,2}").matcher(dateString).matches();
    }

    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
    }

    public static int getDayOfMonth(int month, int year) {
        if (month > 0 || month <= 12) {
            int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (isLeapYear(year))
                dayOfMonth[1] = 29;
            return dayOfMonth[month - 1];
        } else
            return -1;
    }

    public static ArrayList<CalendarDate> getBetweenDates(CalendarDate startDate, CalendarDate endDate) {

        ArrayList<CalendarDate> dates = new ArrayList<>();
        CalendarDate date = startDate;
        while (date.compareTo(endDate) >= 0) {
            dates.add(date);
            date = date.getTomorrow();
        }
        return dates;
    }

    // the day of next week by ZZY
    public static CalendarDate getDayOfNextWeek(CalendarDate date) {
        CalendarDate result;
        int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int totalDayInThisMonth = dayOfMonth[date.getMonth() - 1];
        if (isLeapYear(date.getYear()) && date.getMonth() == 2)
            totalDayInThisMonth++;
        if (date.getDay() <= 21) {
            result = new CalendarDate(date.getYear(), date.getMonth(), date.getDay() + 7);
            return result;
        }
        if (date.getDay() == 22) {
            if (totalDayInThisMonth == 28) {
                result = new CalendarDate(date.getYear(), date.getMonth() + 1, 1);
                return result;
            } else if (totalDayInThisMonth > 28) {
                result = new CalendarDate(date.getYear(), date.getMonth(), date.getDay() + 7);
                return result;
            }
        }
        if (date.getDay() == 23) {
            if (totalDayInThisMonth <= 29) {
                result = new CalendarDate(date.getYear(), date.getMonth() + 1, date.getDay() + 7 - totalDayInThisMonth);
                return result;
            } else {
                result = new CalendarDate(date.getYear(), date.getMonth(), date.getDay() + 7);
                return result;
            }
        }
        if (date.getDay() == 24) {
            if (totalDayInThisMonth <= 30) {
                result = new CalendarDate(date.getYear(), date.getMonth() + 1, date.getDay() + 7 - totalDayInThisMonth);
                return result;
            } else {
                result = new CalendarDate(date.getYear(), date.getMonth(), date.getDay() + 7);
                return result;
            }
        } else {
            if (totalDayInThisMonth <= 30) {
                result = new CalendarDate(date.getYear(), date.getMonth() + 1, date.getDay() + 7 - totalDayInThisMonth);
                return result;
            } else {
                if (date.getMonth() == 12) {
                    result = new CalendarDate(date.getYear() + 1, 1, date.getDay() + 7 - totalDayInThisMonth);
                    return result;
                } else {
                    result = new CalendarDate(date.getYear(), date.getMonth() + 1, date.getDay() + 7 - totalDayInThisMonth);
                    return result;
                }
            }
        }
    }

    public static LocalTime getCurrentTime() {
        CalendarDate date = getToday();
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new LocalTime(date, hour, minute);
    }
}


