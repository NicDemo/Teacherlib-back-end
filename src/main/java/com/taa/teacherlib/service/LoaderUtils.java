package com.taa.teacherlib.service;

import com.taa.teacherlib.business.Availability;
import com.taa.teacherlib.business.Teacher;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoaderUtils {

    private static LoaderUtils instance = null;

    public static LoaderUtils getInstance(){
        if (instance == null)
            instance = new LoaderUtils();
        return instance;
    }

    public void setAvailabilitiesNextDaysBeforeWeekForTeacher(Teacher t){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for(int j = cal.get(Calendar.DAY_OF_WEEK); j <= Calendar.FRIDAY; j++) {
            cal.set(Calendar.DAY_OF_WEEK, j);

            List<Date> morningDates = new ArrayList<>();
            List<Date> afternoonDates = new ArrayList<>();

            for(int h = 8; h <= 12; h++){
                cal.set(Calendar.HOUR_OF_DAY, h+1); // Une heure de décalage donc + 1
                morningDates.add(cal.getTime());
            }
            for(int i=0; i < morningDates.size()-1; i++) {
                Availability a = new Availability(morningDates.get(i), morningDates.get(i+1), t);
                t.addAvailability(a);
            }
            for(int h = 14; h <= 18; h++){
                cal.set(Calendar.HOUR_OF_DAY , h+1); // Une heure de décalage pourquoi ? donc + 1
                afternoonDates.add(cal.getTime());

            }
            for(int i=0; i < afternoonDates.size()-1; i++) {
                Availability a = new Availability(afternoonDates.get(i), afternoonDates.get(i+1), t);
                t.addAvailability(a);
            }
        }
    }

    public List<Date> getNextMondays(int nbMondays) {

        List<Date> nextMondays = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        LocalDate nextMonday = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        Date nextMondayDate = Date.from(nextMonday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        nextMondays.add(nextMondayDate);
        for (int i = 1; i < nbMondays; i++) {
            LocalDate next = nextMonday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            Date nextDate = Date.from(next.atStartOfDay(ZoneId.systemDefault()).toInstant());
            nextMondays.add(nextDate);
            nextMonday = next;
        }
        return nextMondays;
    }


    public void setFullWeekForTeacher(int nbWeek, Teacher t) throws ParseException {
        List<Date> nextMondays = getNextMondays(nbWeek);
        for (Date monday : nextMondays) {
            String mondayString = DateUtils.getInstance().prettyPrintFullDate(monday);
            setFullMorningWeekAvailabilitiesForTeacher(t, mondayString);
            setFullAfternoonWeekAvailabilitiesForTeacher(t, mondayString);
        }

    }

    public void setFullMorningWeekAvailabilitiesForTeacher(Teacher t, String startDate) throws ParseException {
        List<Availability> availabilities = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getInstance().dateFromString(startDate));
        for(int j = Calendar.MONDAY; j <= Calendar.FRIDAY; j++) {
            List<Date> dates = new ArrayList<>();
            cal.set(Calendar.DAY_OF_WEEK, j);
            for(int h = 8; h <= 12; h++){
                cal.set(Calendar.HOUR_OF_DAY, h+1); // Une heure de décalage pourquoi ? donc + 1
                dates.add(cal.getTime());
            }
            for(int i=0; i < dates.size()-1; i++) {
                Availability a = new Availability(dates.get(i), dates.get(i+1), t);
                t.addAvailability(a);
            }
        }
    }

    public void setFullAfternoonWeekAvailabilitiesForTeacher(Teacher t, String startDate) throws ParseException {
        List<Availability> availabilities = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getInstance().dateFromString(startDate));
        for(int j = Calendar.MONDAY; j <= Calendar.FRIDAY; j++) {
            List<Date> dates = new ArrayList<>();
            cal.set(Calendar.DAY_OF_WEEK, j);
            for(int h = 14; h <= 18; h++){
                cal.set(Calendar.HOUR_OF_DAY , h+1); // Une heure de décalage pourquoi ? donc + 1
                dates.add(cal.getTime());
            }
            for(int i=0; i < dates.size()-1; i++) {
                Availability a = new Availability(dates.get(i), dates.get(i+1), t);
                t.addAvailability(a);
            }
        }
    }

}
