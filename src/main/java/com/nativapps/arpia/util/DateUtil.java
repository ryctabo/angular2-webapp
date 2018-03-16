package com.nativapps.arpia.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DateUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    public DateUtil() {
    }

    /**
     * This method is used to know if the given parameters are not null.
     *
     * @throws IllegalArgumentException if first or second calendar is null
     */
    private static void evaluateParameter(Calendar... dates)
            throws IllegalArgumentException {
        for (Calendar date : dates) {
            if (date == null) {
                throw new IllegalArgumentException("The calendar objects to "
                        + "evaluate cannot be null.");
            }
        }
    }

    /**
     * Evaluate if first calendar and second calendar are in the same day.
     * <p>
     * If the two calendar share the same year, month and day, then this method
     * return {@code true} else return {@code false}
     *
     * @param c1 first calendar
     * @param c2 second calendar
     * @return true, if first and second calendar are in the same day.
     */
    public static boolean isSameDay(Calendar c1, Calendar c2) {
        evaluateParameter(c1, c2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Evaluate if the given {@link Calendar} object is today.
     *
     * @param date the calendar object
     * @return true, if the given {@code date} is today
     */
    public static boolean isToday(Calendar date) {
        evaluateParameter(date);
        return isSameDay(date, Calendar.getInstance());
    }

    /**
     * Evaluate if the first date is before the second date ignoring time.
     *
     * @param c1 first date
     * @param c2 second date
     * @return true, if first date is before second date
     */
    public static boolean isBeforeDay(Calendar c1, Calendar c2) {
        evaluateParameter(c1, c2);
        return c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)
                || c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Evaluate if the given date is before today ignoring time.
     *
     * @param date the calendar object
     * @return true, if the given calendar is before today
     */
    public static boolean isBeforeToday(Calendar date) {
        evaluateParameter(date);
        return isBeforeDay(date, Calendar.getInstance());
    }

    /**
     * Evaluate if first date is after to second date ignoring time.
     *
     * @param c1 first date
     * @param c2 second date
     * @return true, if the first date is after to the second date
     */
    public static boolean isAfterDay(Calendar c1, Calendar c2) {
        evaluateParameter(c1, c2);
        return c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)
                || c1.get(Calendar.DAY_OF_YEAR) > c2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Evaluate if the given date is after to today ignoring time.
     *
     * @param date the calendar object
     * @return true, if the given calendar is after today
     */
    public static boolean isAfterToday(Calendar date) {
        evaluateParameter(date);
        return isAfterDay(date, Calendar.getInstance());
    }

    /**
     * This method is used to compare two objects of type Calendar ignoring the
     * time.
     * <p>
     * <ul>The cases are:
     * <li>-1, if the first date is before to the second date.</li>
     * <li>0, if the two date are same day.</li>
     * <li>1, if the first date is after to the second date.</li>
     * </ul>
     *
     * @param c1 first date
     * @param c2 second date
     * @return a number
     */
    public static int compareDayTo(Calendar c1, Calendar c2) {
        evaluateParameter(c1, c2);
        int compareToYear = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        int compareToDayOfYear = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);
        return compareToYear == 0 ? compareToDayOfYear : compareToYear;
    }

    /**
     * This method convert a string value with the format yyyy-MM-dd'T'HH:mm:ssZ
     * in a calendar object.
     *
     * @param dateTimeStr string with calendar format
     * @return Calendar object
     */
    public static Calendar valueOf(String dateTimeStr) {

        if (dateTimeStr != null) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                Calendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(df.parse(dateTimeStr).getTime());

                return calendar;
            } catch (final ParseException ex) {
                throw new WebApplicationException(ex);
            }
        }
        return null;
    }

}
