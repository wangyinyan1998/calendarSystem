package Event;

import DateRelated.LocalTime;
import java.util.Comparator;

 /*用来根据事件开始时间对事件集进行排序，这样就可以在搜索事件的时候更加快速*/
 public class SortEvent implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Event event1 = (Event)o1;
        Event event2 = (Event)o2;
        int emergencyImportant1 = event1.EmergencyAndImportant();
        int emergencyImportant2 = event2.EmergencyAndImportant();
        if(emergencyImportant1<emergencyImportant2)
            return -1;
        if(emergencyImportant1>emergencyImportant2)
            return 1;
        LocalTime timeBegin1 = event1.getTimeBegin();
        LocalTime timeBegin2 = event2.getTimeBegin();
        if(timeBegin1==null && timeBegin2 == null)
            return 0;
        if (timeBegin1==null)
            return -1;
        if (timeBegin1==null)
            return 1;
        return timeBegin1.compareTo(timeBegin2);
    }
}
