package com.info.dummycontacts;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelper {

    public static final String SERVER_DATE_FORMAT_WITH_FRACTION = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SERVER_DATE_FORMAT_WITHOUT_FRACTION = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String SERVER_DATE_FORMAT_WITHOUT_TIME = "yyyy-MM-dd";
    public static final String LEAVE_STATUS_SHORT_DATE_PATTERN = "MMM dd";
    public static final String LEAVE_STATUS_FULL_DATE_PATTERN = "MMM dd, yyyy";

    public static final String LEAVE_STATUS_POST_SHORT_DATE_PATTERN = "EEE MM/dd";
    public static final String LEAVE_STATUS_POST_FULL_DATE_PATTERN = "MM/dd/yyyy";


    public static long ONE_DAY_TIME = 1000 * 60 * 60 * 24;

    private static long _getTodayMidnightTime() {
        return org.apache.commons.lang3.time.DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).getTime();
    }

    public static boolean isToday(Date date) {
        long todayMidnight = _getTodayMidnightTime();
        return todayMidnight <= date.getTime() && date.getTime() < todayMidnight + ONE_DAY_TIME;
    }

    public static boolean isTomorrow(Date date) {
        long todayMidnight = _getTodayMidnightTime();
        return todayMidnight + ONE_DAY_TIME <= date.getTime() && date.getTime() < todayMidnight + ONE_DAY_TIME * 2;
    }

    public static boolean isYesterday(Date date) {
        long todayMidnight = _getTodayMidnightTime();
        return todayMidnight - ONE_DAY_TIME <= date.getTime() && date.getTime() < todayMidnight;
    }

    public static boolean isInNextWeek(Date date) {
        long todayMidnight = _getTodayMidnightTime();
        return todayMidnight + ONE_DAY_TIME < date.getTime() && date.getTime() < todayMidnight + ONE_DAY_TIME * 7;
    }

    public static boolean isInLastWeek(Date date) {
        long todayMidnight = _getTodayMidnightTime();
        return todayMidnight - ONE_DAY_TIME * 6 < date.getTime() && date.getTime() < todayMidnight;
    }

    public static String getRelativeDate(Date date) {
        return getRelativeDate(date, true);
    }

    public static String getRelativeDate(Date date, boolean showTime) {

        if (isToday(date)) {
            return toString(date.getTime(), "hh:mm a");
        } else if (isTomorrow(date)) {
            return showTime ? String.format("Tomorrow %s", toString(date.getTime(), "hh:mm a")) : "Tomorrow";
        } else if (isYesterday(date)) {
            return showTime ? String.format("Yesterday %s", toString(date.getTime(), "hh:mm a")) : "Yesterday";
        } else if (isInLastWeek(date) || isInNextWeek(date)) {
            return showTime ? String.format("%s %s", getDateOfWeek(date), toString(date.getTime(), "hh:mm a")) : getDateOfWeek(date);
        } else {
            return showTime ? toString(date.getTime(), "MMM dd, yyyy", "hh:mm a") : toString(date.getTime(), "MMM dd, yyyy", "");
        }
    }

    public static String getDateOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";

        }
        return "";
    }

    public static int getNumberDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getNumberYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static String toString(long milliSecond, String dateFormat, String timeFormat) {
        Date date = new Date(milliSecond);
        if (Math.abs(System.currentTimeMillis() - milliSecond) < 60 * 60 * 24 * 1000l) {
            return DateFormat.format(timeFormat, date).toString();
        }
        return DateFormat.format(dateFormat, date) + " " + DateFormat.format(timeFormat, date);
    }

    public static String toString(long milli, String timeFormat) {
        Date date = new Date(milli);
        return DateFormat.format(timeFormat, date).toString();
    }

    public static String toServerFormatWithFraction(Date date) {
        java.text.DateFormat dateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT_WITH_FRACTION);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getElapsedTime(long from) {
        StringBuilder result = new StringBuilder("");

        Date date = new Date(from);//convertFromUTCToLocaltimezone(new Date(from));
        if (isToday(date)) {
            long elapsedMillis = System.currentTimeMillis() - date.getTime();
            long millisPerMinute = 1000 * 60;
            long millisPerHour = millisPerMinute * 60;
            long elapsedHours = elapsedMillis / millisPerHour;
            long elapsedMinutes = elapsedMillis / millisPerMinute;
            if (elapsedHours > 0)
                result.append(elapsedHours).append(" hr").append(elapsedHours > 1 ? "s" : "").append(" ago");
            else if (elapsedMinutes > 0)
                result.append(elapsedMinutes).append(" min").append(elapsedMinutes > 1 ? "s" : "").append(" ago");
            else
                result.append("Just now");
        } else if (isYesterday(date)) {
            result.append("Yesterday");
            result.append(" at ").append(new SimpleDateFormat("hh:mm a").format(date));
        } else if (isInLastWeek(date)) {
            result.append(getDateOfWeek(date));
            result.append(" at ").append(new SimpleDateFormat("hh:mm a").format(date));
        } else {
            result.append(new SimpleDateFormat("MMM dd, hh:mm a").format(date));
        }

        return result.toString();
    }

    public static Date convertFromUTCToLocaltimezone(Date date) {
        if (date == null)
            return null;

        long resultInMillis = date.getTime();

        TimeZone tz = Calendar.getInstance().getTimeZone();

        resultInMillis += tz.getRawOffset();

        if (tz.inDaylightTime(new Date(resultInMillis))) {
            resultInMillis += tz.getDSTSavings();
        }

        return new Date(resultInMillis);
    }

    public static String getElapsedMonth(Date date) {
        if (date == null || date.getTime() > System.currentTimeMillis())
            return "";

        Calendar current = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int elapseYear = current.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        int elapsedMonth = current.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
        int elapseDay = current.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);

        if (elapseDay < 0) {
            elapseDay += 30;
            elapsedMonth--;
        }
        if (elapsedMonth < 0) {
            elapseYear--;
            elapsedMonth += 12;
        }

        if (elapseYear > 0 && elapsedMonth > 0) {
            return String.format("%d year" + (elapseYear > 1 ? "s" : "") + ", %d month" + (elapsedMonth > 1 ? "s" : ""), elapseYear, elapsedMonth);
        } else if (elapseYear > 0 && elapsedMonth == 0) {
            return String.format("%d year" + (elapseYear > 1 ? "s" : ""), elapseYear);
        } else if (elapseYear == 0 && elapsedMonth > 0) {
            return String.format("%d month" + (elapsedMonth > 1 ? "s" : ""), elapsedMonth);
        }

        return "";
    }

    public static String formatDateWithOrdinal(Date date) {
        if (date == null)
            return "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new SimpleDateFormat(String.format("MMMM d'%s', yyyy", getOrdinal(day))).format(date);
    }

    public static String formatDateForTimelineEvent(Date date) {
        if (date == null)
            return "";

        Date localTime = convertFromUTCToLocaltimezone(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(localTime);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new SimpleDateFormat(String.format("MMM dd'%s' hh:mm aa", getOrdinal(day))).format(localTime);
    }

    public static String formatDateForCalendar(Date date) {
        if (date == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat(SERVER_DATE_FORMAT_WITHOUT_TIME);
        return sdf.format(date).toString();
    }

    public static Date getDateWithoutTime(Date date) {
        if (date == null)
            return new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(SERVER_DATE_FORMAT_WITHOUT_TIME);
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return "Jan";
            case Calendar.FEBRUARY:
                return "Feb";
            case Calendar.MARCH:
                return "Mar";
            case Calendar.APRIL:
                return "Apr";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "Jun";
            case Calendar.JULY:
                return "Jul";
            case Calendar.AUGUST:
                return "Aug";
            case Calendar.SEPTEMBER:
                return "Sep";
            case Calendar.OCTOBER:
                return "Oct";
            case Calendar.NOVEMBER:
                return "Nov";
            case Calendar.DECEMBER:
                return "Dec";
        }
        return "";
    }

    public static String getOrdinal(int number) {
        int modOf100 = number % 100;
        if (modOf100 == 11 || modOf100 == 12 || modOf100 == 13)
            return "th";
        else
            switch (number % 10) {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    return "th";
            }
    }

    public static boolean isInCurrentYear(Date date) {
        return date != null && new Date().getYear() == date.getYear();
    }

    public static String format(Date date, String pattern){
        if(date == null)
            return "";

        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date todayMidnight() {
        return removeHourAndMinute(new Date());
    }

    public static Date removeHourAndMinute(Date date) {
        if(date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }



}
