package Event;
import DateRelated.DateUtil;
import DateRelated.LocalTime;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by hp on 2018/5/23.
 */
public class AlarmEvent implements java.io.Serializable{
    public AlarmEvent() {
    }

    public static String showAlarm(Event alarmEvent,LocalTime timeEnd) {
        Alarm alarm=alarmEvent.getAlarm();
        String returnStr = "";
        Calendar calendar = Calendar.getInstance();
        // localTime 是现在的时间，一直在变的时间
        LocalTime localTime = new LocalTime(DateUtil.getToday(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        if (!alarmEvent.isWholeDay()&&alarm.getEarlyTime().compareTo(alarmEvent.getTimeEnd())<0) {//如果目前的时间与时间的终止时间相同则，结束提醒
            alarm.setIsAlarm(false);
        }
        else {
            if (alarm.isAlarm) {// 设置提醒
                //  setAlarmTime是事件设置提醒的最早时间
                LocalTime setAlarmTime = new LocalTime(alarm.getEarlyTime().getDate(), alarm.getEarlyTime().getHour(), alarm.getEarlyTime().getMinute());
                //System.out.println(setAlarmTime.toString()+" "+localTime.toString());
                if (setAlarmTime.compareTo(localTime) == 0) {
                    String s = getDatePoor(timeEnd, setAlarmTime);
                    //判断提醒策略
                    switch (alarm.getTypeOfStrategy()) {
                        case 0: //once
                            returnStr += strategyOnce(alarm, s);
                            //alarm.setIsAlarm(false);
                            break;
                        case 1: // 10/min
                            returnStr += strategyOnce(alarm, s);
                            break;
                        case 2:  // 1/hour
                            returnStr += strategyOnce(alarm, s);
                            break;
                        case 3:  // 1/day
                            returnStr += strategyOnce(alarm, s);
                            break;
                        default:
                            break;
                    }
                }
                if (alarm.getFirstSetTime() != null) {
                    if (alarm.getFirstSetTime().compareTo(localTime) > 0 && alarm.getIsOnlyFace()) {
                        String s = getDatePoor(timeEnd, setAlarmTime);
                        returnStr += wayInterface(alarm, s);
                    }
                }
            }
        }
        if(returnStr.length()==0)
            return null;
        return returnStr;
    }
    public static String strategyOnce(Alarm alarm, String s) {
        String res = "";
        switch (alarm.getTypeOfWay()) {
            case 0://interface
                res = wayInterface(alarm,s);
                //alarm.setOnlyFace(true);
                return res;
            case 1:// dialogue
                wayDialogue(alarm,s);
                return res;
            case 2://both ways
                //System.out.println("count:" + alarm.getAlarmCount());
                if(alarm.getAlarmCount() == 0){
                    res=wayBoth(alarm,s);
                }else{
                    //System.out.println("hahaha");
                    res = wayInterface(alarm,s);
                }

                //alarm.setOnlyFace(true);
                return res;
            default:
                return res;
        }
    }
    //要不断地设置earlyTime 或者 不断设置成once，并且设置earlyTime
    public static String strategyTen(Alarm alarm, String s) {
        String res = strategyOnce(alarm,s);
        // System.out.println(alarm.getEarlyTime().toString());
        // String res ="";
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int min = alarm.getEarlyTime().getMinute() + 10;
        if (min < 60) {
            alarm.getEarlyTime().setMinute(min);
        } else {
            min =(alarm.getEarlyTime().getMinute() + 10)-60;
            hour = alarm.getEarlyTime().getHour() + 1;
            if (hour < 24) {
                alarm.getEarlyTime().setHour(hour);
                alarm.getEarlyTime().setMinute(min);
            } else {
                hour = (alarm.getEarlyTime().getHour() + 1)-24;
                day = alarm.getEarlyTime().getDate().getDay() + 1;
                int getDayInMonth = DateUtil.getDayOfMonth(alarm.getEarlyTime().getDate().getMonth(), alarm.getEarlyTime().getDate().getYear());
                if (day <=getDayInMonth ) {
                    alarm.getEarlyTime().getDate().setDay(day);
                    alarm.getEarlyTime().setHour(hour);
                    alarm.getEarlyTime().setMinute(min);
                } else {
                    //day =getDayInMonth - alarm.getEarlyTime().getDate().getDay() + 1;
                    day =day- getDayInMonth;
                    month = alarm.getEarlyTime().getDate().getMonth() + 1;
                    if (month <= 12) {
                        alarm.getEarlyTime().getDate().setMonth(month);
                        alarm.getEarlyTime().getDate().setDay(day);
                        alarm.getEarlyTime().setHour(hour);
                        alarm.getEarlyTime().setMinute(min);
                    } else {
                        // month = 13 - alarm.getEarlyTime().getDate().getMonth();
                        month=month-12;
                        year = alarm.getEarlyTime().getDate().getYear() + 1;
                        alarm.getEarlyTime().getDate().setYear(year);
                        alarm.getEarlyTime().getDate().setMonth(month);
                        alarm.getEarlyTime().getDate().setDay(day);
                        alarm.getEarlyTime().setHour(hour);
                        alarm.getEarlyTime().setMinute(min);
                    }
                }
            }
        }
        alarm.setAlarmCount(0);
        return res;
    }
    public static String strategyHour(Alarm alarm, String s) {
        String res = strategyOnce(alarm,s);
        // System.out.println(alarm.getEarlyTime().toString());
        // String res ="";
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = alarm.getEarlyTime().getHour() + 1;
        if (hour < 24) {
            alarm.getEarlyTime().setHour(hour);
        } else {
            hour = (alarm.getEarlyTime().getHour() + 1)-24;
            day = alarm.getEarlyTime().getDate().getDay() + 1;
            int getDayInMonth = DateUtil.getDayOfMonth(alarm.getEarlyTime().getDate().getMonth(), alarm.getEarlyTime().getDate().getYear());
            if (day <= getDayInMonth) {
                alarm.getEarlyTime().setHour(hour);
                alarm.getEarlyTime().getDate().setDay(day);
            } else {
                day =day- getDayInMonth;
                // day = getDayInMonth - alarm.getEarlyTime().getDate().getDay() + 1;
                month = alarm.getEarlyTime().getDate().getMonth() + 1;
                if (month <= 12) {
                    alarm.getEarlyTime().getDate().setMonth(month);
                    alarm.getEarlyTime().getDate().setDay(day);
                    alarm.getEarlyTime().setHour(hour);
                } else {
                    month=month-12;
                    year = alarm.getEarlyTime().getDate().getYear() + 1;
                    alarm.getEarlyTime().getDate().setYear(year);
                    alarm.getEarlyTime().getDate().setMonth(month);
                    alarm.getEarlyTime().getDate().setDay(day);
                    alarm.getEarlyTime().setHour(hour);
                }
            }
        }
        alarm.setAlarmCount(0);
        return res;
    }
    public static String strategyDay(Alarm alarm, String s){
        // System.out.println(alarm.getEarlyTime().toString());
        // String res ="";
        String res = strategyOnce(alarm,s);
        int year = 0;
        int month = 0;
        int day = alarm.getEarlyTime().getDate().getDay() + 1;
        int getdayOfMonth = DateUtil.getDayOfMonth(alarm.getEarlyTime().getDate().getMonth(), alarm.getEarlyTime().getDate().getYear());
        if (day <= getdayOfMonth) {
            alarm.getEarlyTime().getDate().setDay(day);
        } else {
            day =day- getdayOfMonth;
            //day = getdayOfMonth - alarm.getEarlyTime().getDate().getDay() + 1;
            month = alarm.getEarlyTime().getDate().getMonth() + 1;
            if (month <= 12) {
                alarm.getEarlyTime().getDate().setMonth(month);
                alarm.getEarlyTime().getDate().setDay(day);
            } else {
                month=month-12;
                year = alarm.getEarlyTime().getDate().getYear() + 1;
                alarm.getEarlyTime().getDate().setYear(year);
                alarm.getEarlyTime().getDate().setMonth(month);
                alarm.getEarlyTime().getDate().setDay(day);
            }
        }
        alarm.setAlarmCount(0);
        return res;
    }
    public static void wayNotAlarm(Alarm alarm){alarm.setIsAlarm(false);}
    public static String wayInterface(Alarm alarm, String s) {
        AlarmMusic.musicPlay();
        return "提醒事项：" + alarm.getContext() + "\n" + "离待办事项还有："+s;
    }
    public static void wayDialogue(Alarm alarm, String s) {
        AlarmMusic.musicPlay();
        int n = JOptionPane.showConfirmDialog(null,
                "提醒事项：" + "\n" + alarm.getContext() + "\n" + "离待办事项还有："+s, " 事项提醒 ", JOptionPane.OK_CANCEL_OPTION);
        if(alarm.getAlarmCount() == 0){
            alarm.setAlarmCount(1);
        }
        if (n == JOptionPane.OK_OPTION)
            AlarmMusic.musicStop();
        else
            alarm.setIsAlarm(false);
    }
    public static String wayBoth(Alarm alarm, String s){
        AlarmMusic.musicPlay();
        int n = JOptionPane.showConfirmDialog(null,
                "提醒事项：" + "\n" + alarm.getContext() + "\n" + "离待办事项还有："+s, " 事项提醒 ", JOptionPane.OK_CANCEL_OPTION);
        if(alarm.getAlarmCount() == 0){
            alarm.setAlarmCount(1);
        }
        if (n == JOptionPane.OK_OPTION)
            AlarmMusic.musicStop();
        else
            alarm.setIsAlarm(false);
        return "提醒事项：" + alarm.getContext() + "\n" + "离待办事项还有："+s;
    }
    public static boolean isStopAlarm(LocalTime nowTime, LocalTime endTime) {
        if (nowTime == null || endTime == null) {
            return false;
        } else {
            int gapY = endTime.getDate().getYear() - nowTime.getDate().getYear();
            int gapM = endTime.getDate().getMonth() - nowTime.getDate().getMonth();
            int gapD = endTime.getDate().getDay() - nowTime.getDate().getDay();
            int gapH = endTime.getHour() - nowTime.getHour();
            int gapMin = endTime.getMinute() - nowTime.getMinute();
            if (gapY == 0 && gapM == 0 && gapD == 0 && gapH == 0 && gapMin == 0) {
                return true;
            }
            return false;
        }
    }
    public static String getDatePoor(LocalTime endTime, LocalTime nowTime) {
        if (endTime == null || nowTime == null) {
            return "";
        } else {
            SimpleDateFormat dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date endDate = null;
            try {
                endDate = dateStr.parse(endTime != null ? endTime.toString() : null);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date nowDate = null;
            try {
                nowDate = dateStr.parse(nowTime != null ? nowTime.toString() : null);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            // long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = (endDate != null ? endDate.getTime() : 0) - (nowDate != null ? nowDate.getTime() : 0);
            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;
            // 计算差多少秒//输出结果
            if(min<0 | hour<0 | day<0){
                return "事件正在进行！";
            }
            return day + "天" + hour + "小时" + min + "分钟";
        }
    }
}