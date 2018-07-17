package SavingOperation;

import java.awt.print.PrinterGraphics;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import DateRelated.LocalTime;
import Event.*;

/**
 * Created by ZZY on 2018/5/20.
 */
public class Saving {
    private static File local = new File("local.txt");
    public static void saveBeforeClose(ArrayList<Event> list){
        saveBeforeClose(list, local);
    }
    public static void saveBeforeClose(ArrayList<Event> list, File f) {
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < list.size(); i++) {
                Event event = list.get(i);
                writeEvent(pw, event);
                ArrayList<Event> subList = event.getSubEvent();
                pw.println(subList.size());
                for (int j = 0; j < subList.size(); j++) {
                    writeEvent(pw, subList.get(j));
                }
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeEvent(PrintWriter pw, Event event) {
        if (event instanceof DateEvent) {
            pw.println("DateEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(((DateEvent) event).getPlace());
            pw.println(((DateEvent) event).getAttendPeople());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            printAlarm(pw,event);


        } else if (event instanceof MeetingEvent) {
            pw.println("MeetingEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(((MeetingEvent) event).getPlace());
            pw.println(((MeetingEvent) event).getTheme());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            printAlarm(pw,event);
            //pw.println(event.getState());
        } else if (event instanceof AnniversaryEvent) {
            pw.println("AnniversaryEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(((AnniversaryEvent) event).getAnniversaryName());
            pw.println(((AnniversaryEvent) event).getAnniversaryType());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            printAlarm(pw,event);
            //pw.println(event.getState());
        } else if (event instanceof CourseEvent) {
            pw.println("CourseEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(((CourseEvent) event).getStartCourseTime().toString());
            pw.println(((CourseEvent) event).getCourseName());
            pw.println(((CourseEvent) event).getCourseContent());
            pw.println(((CourseEvent) event).getLastWeeks());
            pw.println(((CourseEvent) event).getPlace());
            pw.println(((CourseEvent) event).getTeacher());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            printAlarm(pw,event);
            //pw.println(event.getState());
        } else if (event instanceof GeneralEvent) {
            pw.println("GeneralEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            pw.println(((GeneralEvent) event).isWithoutSpecificTimeSetting()?1:0);
            if(((GeneralEvent) event).getFinishTime() == null)
                pw.println("null");
            else
                pw.println(((GeneralEvent) event).getFinishTime().toString());
            printAlarm(pw,event);
            //pw.println(event.getState());
        } else if (event instanceof InterviewEvent) {
            pw.println("InterviewEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(((InterviewEvent) event).getInterviewTime());
            pw.println(((InterviewEvent) event).getPlace());
            pw.println(((InterviewEvent) event).getCompany());
            pw.println(((InterviewEvent) event).getJob());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            printAlarm(pw,event);
            //pw.println(event.getState());
        } else if (event instanceof TripEvent) {
            pw.println("TripEvent");
            pw.println(event.getEventString());
            pw.println(event.getTimeBegin().toString());
            pw.println(event.getTimeEnd().toString());
            pw.println(((TripEvent) event).getTransportation());
            pw.println(((TripEvent) event).getDestination());
            pw.println(((TripEvent) event).getTransNumber());
            pw.println(event.EmergencyAndImportant());
            pw.println(event.getComplete()? 1:0);
            printAlarm(pw,event);
            //pw.println(event.getState());
        }
    }

    public static void readAfterOpen() {
        ArrayList<Event> list = file2Array(local);
        for(Event fe : list){
            Event.setEventState(fe);
            for(Event se : fe.getSubEvent()){
                Event.setEventState(se);
            }
        }
        Event.setEvents(list);
    }

    public static ArrayList<Event> file2Array(File f) {
        ArrayList<Event> result = new ArrayList<Event>();
        try {
            Scanner input = new Scanner(f);
            while(input.hasNext()){
                Event e = readEvent(input);
                int sublistSize = Integer.parseInt(input.nextLine());
                int counter = 0;
                ArrayList<Event> sublist = new ArrayList<Event>();
                while(counter < sublistSize){
                    Event sub = readEvent(input);
                    sublist.add(sub);
                    counter ++;
                }
                e.setSubEvent(sublist);
                result.add(e);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Event readEvent(Scanner input) {
        if (input.hasNext()) {
            String type = input.nextLine();
            if (type.equals("DateEvent")) {
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());
                }catch (Exception e){
                    e.printStackTrace();
                }
                String place = input.nextLine();
                String attendPeople = input.nextLine();
                int priority = Integer.parseInt(input.nextLine());
                DateEvent event = new DateEvent(eventString, timeBegin, timeEnd, place,attendPeople,true, true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);
              //  event.setState(input.nextLine());
                return event;
            }
            else if(type.equals("MeetingEvent")){
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());

                }catch (Exception e){
                    e.printStackTrace();
                }
                String place = input.nextLine();
                String theme = input.nextLine();
                int priority = Integer.parseInt(input.nextLine());
                MeetingEvent event = new MeetingEvent(eventString, timeBegin, timeEnd,place,theme, true, true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);
                //event.setState(input.nextLine());
                return event;
            }
            else if(type.equals("CourseEvent")){
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                LocalTime startCourseTime = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());
                    startCourseTime = new LocalTime(input.nextLine());
                }catch (Exception e){
                    e.printStackTrace();
                }
                String courseName = input.nextLine();
                String courseContent = input.nextLine();
                int lastWeeks = Integer.parseInt(input.nextLine());
                String place = input.nextLine();
                String teacher = input.nextLine();
                int priority = Integer.parseInt(input.nextLine());
                CourseEvent event = new CourseEvent(startCourseTime,courseName, place, lastWeeks, teacher,courseContent, eventString,  timeBegin, timeEnd,true,true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);

                return event;
            }
            else if(type.equals("AnniversaryEvent")){
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());

                }catch (Exception e){
                    e.printStackTrace();
                }
                String name = input.nextLine();
                String atype = input.nextLine();
                int priority = Integer.parseInt(input.nextLine());
                AnniversaryEvent event = new AnniversaryEvent(eventString, timeBegin, timeEnd,name,atype, true, true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);
                return event;
            }
            else if(type.equals("GeneralEvent")){
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                LocalTime ft = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());

                }catch (Exception e){
                    e.printStackTrace();
                }
                int priority = Integer.parseInt(input.nextLine());
                GeneralEvent event = new GeneralEvent(eventString, timeBegin, timeEnd, true, true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                event.setWithoutSpecificTimeSetting(Integer.parseInt(input.nextLine())==1?true : false);
                String finishTime = input.nextLine();
                if(!finishTime.equals("null"))
                    try{
                        ft = new LocalTime(finishTime);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                event.setFinishTime(ft);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);
                return event;
            }
            else if(type.equals("TripEvent")){
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());

                }catch (Exception e){
                    e.printStackTrace();
                }
                String transportation = input.nextLine();
                String destination = input.nextLine();
                String transNumber = input.nextLine();
                int priority = Integer.parseInt(input.nextLine());
                TripEvent event = new TripEvent(eventString,timeBegin,timeEnd,transportation,destination,transNumber,true,true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);
                return event;
            }
            else{
                String eventString = input.nextLine();
                LocalTime timeBegin = null;
                LocalTime timeEnd = null;
                try {
                    timeBegin = new LocalTime(input.nextLine());
                    timeEnd = new LocalTime(input.nextLine());

                }catch (Exception e){
                    e.printStackTrace();
                }
                String interviewTime = input.nextLine();
                String place = input.nextLine();
                String company = input.nextLine();
                String job = input.nextLine();
                int priority = Integer.parseInt(input.nextLine());
                InterviewEvent event = new InterviewEvent(interviewTime, place, company, job, eventString, timeBegin,timeEnd,true,true);
                if (isRepresentingWholeDay(timeBegin, timeEnd))
                    event.setIsWholeDay(true);
                event.setEmergencyAndImportant(priority);
                event.setComplete(Integer.parseInt(input.nextLine())==1?true : false);
                Alarm a = readAlarm(input);
                if(a != null)
                    event.setAlarm(a);
                return event;
            }
        }
        else
            return null;
    }

    private static boolean isRepresentingWholeDay(LocalTime st, LocalTime et) {
        if ((st.getDate().equals(et.getDate())) && (st.getHour() == 0) && (st.getMinute() == 0) && (et.getHour() == 23) && (et.getMinute() == 59))
            return true;
        else
            return false;
    }

    private static void printAlarm(PrintWriter pw, Event event){
        pw.println(event.getAlarm().getIsAlarm()? 1:0);
        if(event.getAlarm().getIsAlarm()){
            pw.println(event.getAlarm().getAlarmCount());
            pw.println(event.getAlarm().getContext());
            pw.println(event.getAlarm().getTypeOfStrategy());
            pw.println(event.getAlarm().getTypeOfWay());
            pw.println(event.getAlarm().getEarlyTime().toString());
            pw.println(event.getAlarm().getFirstSetTime().toString());
        }
    }

    private static Alarm readAlarm(Scanner input){
        while(input.hasNext()){
            boolean hasAlarm = Integer.parseInt(input.nextLine()) == 1? true:false;
            if(hasAlarm){
                int alarmCount = Integer.parseInt(input.nextLine());
                String context = input.nextLine();
                int typeOfStrategy = Integer.parseInt(input.nextLine());
                int typeOfWay = Integer.parseInt(input.nextLine());
                LocalTime earlyTime = null;
                LocalTime firstSetTime = null;
                try{
                    earlyTime = new LocalTime(input.nextLine());
                    firstSetTime = new LocalTime(input.nextLine());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                Alarm a = new Alarm();
                a.setAlarmCount(alarmCount);
                a.setContext(context);
                a.setEarlyTime(earlyTime);
                a.setFirstSetTime(firstSetTime);
                a.setTypeOfStrategy(typeOfStrategy);
                a.setTypeOfWay(typeOfWay);
                a.setIsAlarm(true);
                return a;
            }
            else
                return null;
        }
        return  null;
    }
}

