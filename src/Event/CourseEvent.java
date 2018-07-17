package Event;

import DateRelated.CalendarDate;
import DateRelated.DateUtil;
import DateRelated.LocalTime;

public class CourseEvent extends Event {
    String courseName;
    LocalTime startCourseTime;
    int lastWeeks;
    String teacher;
    int weekRepeat;
    String place;
    String courseContent;
    private String[] weeks = {"一", "二", "三", "四", "五", "六", "七"};

    public CourseEvent(LocalTime startCourseTime, String courseName, String place, int lastWeeks, String teacher, String courseContent,
                       String eventString, LocalTime timeBegin, LocalTime timeEnd, boolean isEmergency, boolean isImportant) {
        super(eventString, timeBegin, timeEnd, isEmergency, isImportant);
        this.startCourseTime = startCourseTime;
        this.courseName = courseName;
        this.lastWeeks = lastWeeks;
        this.teacher = teacher;
        this.weekRepeat = startCourseTime.getDate().getDayOfWeek();
        this.place = place;
        this.courseContent = courseContent;
        this.type = eventType.COURSE.ordinal();
    }

    @Override
    public String showEvent() {
        if (getEventString() == null)
            return null;
        String returnStr = "课程：\n课程名称：" + courseName + " \n上课时间:" + getTimeBegin().toString() + " \n下课时间：" + getTimeEnd().toString() +
                " \n上课地点：" + this.place + "\n上课老师：" + teacher + "\n课程内容：" + courseContent + "\n持续时间：" +
                lastWeeks + "周 \n课程重复周天：" + weeks[weekRepeat - 1] + "\n备注：" + eventString + "\n"
                + EmergencyAndImportantStr() + "\n" + "状态: " +  this.getState();
        returnStr += this.getSubEventString();
        returnStr += "\n"+alarm.getAlarmInfo();

        return returnStr;
    }

    @Override
    public boolean equals(Event event2) {
        if (event2 instanceof CourseEvent) {
            CourseEvent event = (CourseEvent) event2;
            return (getStartCourseTime().compareTo(((CourseEvent) event2).getStartCourseTime())==0&&getTimeEnd().compareTo(event.getTimeEnd()) == 0 && getTimeBegin().compareTo(event.getTimeBegin()) == 0 &&
                    weekRepeat == event.getWeekRepeat() && courseName.equals(event.getCourseName()) && lastWeeks == event.getLastWeeks()
                    && place.equals(event.getPlace()) && teacher.equals(event.getTeacher())&&courseName.equals(event.getCourseName())
                    && this.getEmergency()==event.getEmergency()&&this.getImportant()==event.getImportant());
        }
        return false;
    }

    public int getLastWeeks() {
        return lastWeeks;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getPlace() {
        return place;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getWeekRepeat() {
        return weekRepeat;
    }

    //add by ZZY
    public LocalTime getStartCourseTime(){return startCourseTime;}
    public String getCourseContent(){return courseContent;}

    public boolean isSameCourse(Event event){
        if(event == null)
            return false;
        if(event instanceof CourseEvent){
            CourseEvent ce = (CourseEvent)event;
            if(this.getEventString().equals(ce.getEventString())&&this.getCourseName().equals(ce.getCourseName())&&this.getStartCourseTime().compareTo(ce.getStartCourseTime())==0&&this.getLastWeeks()==ce.getLastWeeks()&&this.getTeacher().equals(ce.getTeacher())&&
                    this.getWeekRepeat()==ce.getWeekRepeat()&&this.getPlace().equals(ce.getPlace())&&this.getCourseContent().equals(ce.getCourseContent())){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
}
