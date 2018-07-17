package Event;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;

/**
 * Created by hp on 2018/5/21.
 */
public class Alarm implements java.io.Serializable{
    protected LocalTime earlyTime;//提醒最早时间,要进行时间的转换 分秒===小时==天数
    protected LocalTime firstSetTime;
    protected int way;//提醒方式
    protected int strategy;//提醒策略
    protected String context;//提醒内容
    protected boolean isAlarm;
    private int alarmCount;
    /*fulix新加*/
    protected boolean onlyFace;

    //public void setOnlyFace(boolean isOnlyFace) {
       // this.onlyFace = isOnlyFace;
   // }

    public boolean getIsOnlyFace() {
        return onlyFace;
    }

    public Alarm() {
        this.firstSetTime = new LocalTime(new CalendarDate(208, 4, 3), 0, 0);
        this.earlyTime = new LocalTime(new CalendarDate(208, 4, 3), 0, 0);
        this.context = "";
        this.way = -1;
        this.strategy = -1;
        alarmCount = 0;
        this.isAlarm = false;
    }

    public static enum wayType {
        INTERFACE, DIALOGUE, BOTH
    }

    public static enum strategyType {
        ONCE, PER_TEN_MINUTES, PER_ONE_HOUR, PER_ONE_DAY
    }

    public LocalTime getEarlyTime() {
        return earlyTime;
    }

    public int getTypeOfWay() {
        return way;
    }

    public int getTypeOfStrategy() {
        return strategy;
    }

    public int getAlarmCount() {return alarmCount;}

    public boolean getIsAlarm() {
        return isAlarm;
    }

    public String getContext() {
        return context;
    }

    public void setEarlyTime(LocalTime time) {
        this.earlyTime = time;
    }

    public void setTypeOfWay(int type) {
        this.way = type;
    }

    public void setTypeOfStrategy(int type) {
        this.strategy = type;
    }

    public void setAlarmCount(int count) {this.alarmCount = count;}
    public void setContext(String context) {
        this.context = context;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public void setFirstSetTime(LocalTime firstSetTime) {
        this.firstSetTime = firstSetTime;
    }

    public LocalTime getFirstSetTime() {
        return firstSetTime;
    }

    //王银燕修改**/
    public String getWayString() {
        switch (way) {
            case 0:
                return "界面提醒";
            case 1:
                return "对话框提醒";
            case 2:
                return "界面提醒 & 对话框提醒";
        }
        return "";
    }

    public String getStrategyString() {
        switch (strategy) {
            case 0:
                return "仅一次提醒";
            case 1:
                return "每十分钟提醒";
            case 2:
                return "每一小时提醒";
            case 3:
                return "每天提醒";
        }
        return "";
    }

    public String getAlarmInfo() {
        if (!isAlarm) {
            return "未添加提醒";
        }
        return "提醒开始时间：" + getFirstSetTime().toString() + "\n提醒方式：" + getWayString() + "\n提醒策略：" + getStrategyString();
    }
}
