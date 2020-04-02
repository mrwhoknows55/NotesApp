package com.mrwhoknows.notes.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public static String getCurrentTimeStamp(){

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", Locale.US);
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
