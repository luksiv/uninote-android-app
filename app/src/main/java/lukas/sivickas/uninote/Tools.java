package lukas.sivickas.uninote;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tools {
    public static String convertToString(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if(calendar.get(Calendar.MONTH)+1 < 10)
            month = "0" + month;
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10)
            day = "0" + day;
        if(calendar.get(Calendar.HOUR_OF_DAY) < 10)
            hour = "0" + hour;
        if(calendar.get(Calendar.MINUTE) < 10)
            minute = "0" + minute;

        return String.format("%s-%s-%s %s:%s",
                year,
                month,
                day,
                hour,
                minute);
    }
}
