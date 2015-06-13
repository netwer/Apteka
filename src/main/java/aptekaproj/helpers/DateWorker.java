package aptekaproj.helpers;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 13.06.2015.
 */
public class DateWorker {

    public static Date AddDaysToDate(Date currentDate,int countDays){
        Date date = currentDate;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, countDays);
        return c.getTime();
    }

    public static Date MaxDate(Date d1,Date d2){
        if(TimeIgnoringComparator.compare(d1,d2) >= 0)
            return d1;
        else
            return d2;
    }
}
