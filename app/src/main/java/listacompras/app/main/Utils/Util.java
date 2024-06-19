package listacompras.app.main.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    private static final SimpleDateFormat sdf = (SimpleDateFormat)SimpleDateFormat.getInstance();
    private static String defaultDatePattern = "MM/dd/yyyy";

    public static Date strToDateTime (String data) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return formatter.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized String format(Date date, String pattern) {
        sdf.applyPattern(pattern == null ? defaultDatePattern : pattern);
        return sdf.format(date);
    }

    public static String formatDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    public static Date strToDateTimeDB (String data) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return formatter.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
