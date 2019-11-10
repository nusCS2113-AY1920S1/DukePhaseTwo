package duke.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Util class helps to handle dates easily.
 */
public class DateHandler {
    /**
     * Method will create a string date in the way the user defines.
     *
     * @param format The format of the date to be returned e.g "ddMMyyyy"
     * @param day    The selected day
     * @param month  The selected month
     * @param year   The selected year
     * @return String of formatted date
     */
    public static String stringDate(String format, int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.YEAR, year);
        DateFormat df = new SimpleDateFormat(format);
        String date = df.format(cal.getTime());
        return date;
    }

    /**
     * Validate if the date selected is inside the selected month.
     *
     * @param day the selected day
     * @param month the selected month
     * @return dateExists check to see if the date is valid
     */
    public static boolean dateCheck(int day, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, month - 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        boolean dateExits = day < daysInMonth;
        return dateExits;
    }

    /**
     * Method will format any date that you put inside.
     *
     * @param oldFormat the old format to be accepted
     * @param newFormat the new format the user wants
     * @param input the string to be parsed
     * @return newString which has been formated
     */
    public static String dateFormatter(String oldFormat, String newFormat, String input) {
        try {
            Date tempDate = new SimpleDateFormat(oldFormat).parse(input);
            DateFormat dateFormat = new SimpleDateFormat(newFormat);
            String newString = dateFormat.format(tempDate);
            return newString;
        } catch (ParseException e) {
            e.getMessage();
            return "Error";
        }
    }

}
