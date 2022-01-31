package com.taa.teacherlib.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static DateUtils instance = null;
    private static SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat formatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    public static DateUtils getInstance(){
        if (instance == null)
            instance = new DateUtils();
        return instance;
    }

    public String prettyPrintDate(Date d) {
        String prettyPrintDate =  formatter1.format(d);
        return  prettyPrintDate;
    }

    public String prettyPrintHour(Date d) {
        String prettyPrintHour =  formatter2.format(d);
        return  prettyPrintHour;
    }

    public String prettyPrintFullDate(Date d){
        String prettyPrintedFullDate = formatter3.format(d);
        return prettyPrintedFullDate;
    }



    public Date dateFromString(String s) throws ParseException {
        formatter3.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        Date date = formatter3.parse(s);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();
    }
    
    public String longDurationToString(long l){
        long quotient = l / 60;
        long remainder = l % 60;
        String remainderPadded = String.format("%02d", remainder);

        if(quotient > 0) {
            return String.valueOf(quotient) + "h" + remainderPadded;
        }else {
            return String.valueOf(remainder) + " minutes";
        }
    }
}
