package HolidayRelatedTest;

import DateRelated.CalendarDate;
import DateRelated.LocalTime;
import HolidayRelated.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HolidaysTest {
    Holidays holidays=new Holidays();
    @Before
    public void setUp() throws Exception {
        System.out.println("Class CalendarDate tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CalendarDate tests end! Are you satisfied?");
    }

    @Test
    public void isDateDuringHoliday() {
        CalendarDate date = new CalendarDate(2018,2,15);
        assertTrue("testify DateDuringHoliday() TRUE is unsuccessful",holidays.isDateDuringHoliday(date));
        date = new CalendarDate(2018,1,9);
        assertFalse("testify DateDuringHoliday() FALSE is unsuccessful",holidays.isDateDuringHoliday(date));
        assertFalse("testify DateDuringHoliday() NULL is unsuccessful",holidays.isDateDuringHoliday(null));
    }

    @Test
    public void getHoliday() {
        Holiday holiday = new Holiday(new CalendarDate(2018,1,1),
                new CalendarDate(2018,1,1),new CalendarDate(2018,1,1),"元旦");
        CalendarDate date = new CalendarDate(2018,1,1);
        assertTrue("testify getHoliday TRUE is unsuccessful",holiday.equals(holidays.getHoliday(date)));
        date = new CalendarDate(2018,2,2);
        assertFalse("testify getHoliday False is unsuccessful",holiday.equals(holidays.getHoliday(date)));
        assertFalse("testify getHoliday NULL is unsuccessful",holiday.equals(holidays.getHoliday(null)));
    }

    @Test
    public void isDateHolidayTime() {
        CalendarDate date = new CalendarDate(2018,2,16);
        assertTrue("testify DateHolidayTime() TRUE is unsuccessful",holidays.isDateHolidayTime(date));
        date = new CalendarDate(2018,2,15);
        assertFalse("testify DateHolidayTime() FALSE is unsuccessful",holidays.isDateHolidayTime(date));
        assertFalse("testify DateHolidayTime() NULL is unsuccessful",holidays.isDateHolidayTime(null));
    }

    @Test
    public void isWorkday() {
        CalendarDate date = new CalendarDate(2018,2,11);
        assertTrue("testify isWorkday() TRUE is unsuccessful",holidays.isWorkday(date));
        date = new CalendarDate(2018,2,15);
        assertFalse("testify DateHolidayTime() FALSE is unsuccessful",holidays.isWorkday(date));
        assertFalse("testify DateHolidayTime() NULL is unsuccessful",holidays.isWorkday(null));
    }
}