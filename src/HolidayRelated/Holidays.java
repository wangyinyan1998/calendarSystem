package HolidayRelated;



import DateRelated.CalendarDate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 王银燕新添加的类，目的是读取节日信息，同时可以用于在display中搜索节日信息
 */
public class Holidays {
    private static ArrayList<Holiday> holidays = new ArrayList<Holiday>();
    private static ArrayList<CalendarDate> workDays = new ArrayList<>();
    private int year;
    public Holidays(){
        initHolidays();
    }
    private void initHolidays(){
        String jsonString=null;
       try{
           BufferedReader br = new BufferedReader(new FileReader(new File("src/HolidayRelated/holiday.json")));
           String s;
           while ((s=br.readLine())!=null)
              jsonString+=s;
       }
       catch (Exception e){
           e.printStackTrace();
       }
       try {
           while (jsonString.charAt(0)!='{')
               jsonString = jsonString.substring(1);
           JSONObject jsonObject = new JSONObject(jsonString);
           year = jsonObject.getInt("year");
           JSONArray workdayArray = jsonObject.getJSONArray("workday");
           for(int i=0;i<workdayArray.length();i++){
               workDays.add(new CalendarDate(workdayArray.get(i).toString()));
           }
           JSONArray holidaysJson = jsonObject.getJSONArray("holiday");
           for(int i=0;i<holidaysJson.length();i++){
               JSONObject jsonObject1= holidaysJson.getJSONObject(i);
               CalendarDate startTime = new CalendarDate(jsonObject1.getString("start_time"));
               CalendarDate endTime = new CalendarDate(jsonObject1.getString("end_time"));
               CalendarDate holidayTime = new CalendarDate(jsonObject1.getString("holiday_time"));
               String holidayName = jsonObject1.getString("zh_name");
               Holiday holiday = new Holiday(startTime,endTime,holidayTime,holidayName);
               holidays.add(holiday);
           }
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }
    public boolean isDateDuringHoliday(CalendarDate date){
        for(Holiday holiday:holidays){
            int compare1 = holiday.getStartTime().compareTo(date);//事件的开始时间要早于结束时间段
            int compare2 = holiday.getEndTime().compareTo(date);//事件的结束时间要晚于开始时间段
            if(compare1>=0 && compare2<=0)
                return true;
        }
        return false;
    }
    public Holiday getHoliday(CalendarDate date){
        for(Holiday holiday:holidays){
            int compare1 = holiday.getStartTime().compareTo(date);//事件的开始时间要早于结束时间段
            int compare2 = holiday.getEndTime().compareTo(date);//事件的结束时间要晚于开始时间段
            if(compare1>=0 && compare2<=0)
               return holiday;
        }
        return null;
    }
    public boolean isDateHolidayTime(CalendarDate date){
        Holiday holiday = getHoliday(date);
        if(holiday!=null)
            return holiday.dateIsHolidayTime(date);
        return false;
    }

    public boolean isWorkday(CalendarDate date){
        for(CalendarDate dateTmp:workDays){
            if(dateTmp.equals(date))
                return true;
        }
        return false;
    }
}
