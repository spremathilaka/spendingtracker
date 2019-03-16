package au.com.spendingtracker.util;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {

    public static final String DATE_FORMAT_DD_MMM_YYYY = "dd MMM yyyy";

    private static final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_DD_MMM_YYYY, Locale.US);;

    public static String formatAmount(Double amount){
        return DecimalFormat.getCurrencyInstance().format(amount);
    }

    public static String formatDate(@NonNull Date date){
        return format.format(date);
    }
}
