package aptekaproj.helpers;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 13.06.2015.
 */
public class TimeIgnoringComparator implements Comparable<Date> {
    @Override
    public int compareTo(Date o) {
        return 0;
    }

    public static int compare(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
            return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
    }
}
