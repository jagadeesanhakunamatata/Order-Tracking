package com.inrange.trackapplication;


import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by hmspl on 27/5/15.
 */
public class CodeSnippet {

    public static final String TAG = "CodeSnippet";

    public static <T> String getJsonStringFromObject(T object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getObjectFromJson(String json, TypeReference<T> typeReference) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getObjectFromJson(String json, CollectionType classType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, classType);
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static <T> T getObjectFromJson(String json, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float convertPixelsToDp(float px, Resources resources) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int convertDpToPixel(float dp, Resources resources) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    public static Bitmap getBitmap(ContentResolver contentResolver, Uri uri) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        AssetFileDescriptor fileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");

        return BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(),
                null, options);
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String convertToHtml(String desc, String fontPath) {
        String header = "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +
                "\t\t<style>\n" +
                "\t\t\t/**\tSpecify a font named \"MyFont\",\n" +
                "\t\t\t\tand specify the URL where it can be found: */\n" +
                "\t\t\t@font-face {\n" +
                "\t\t\t  font-family: \"MyFont\";\n" +
                "\t\t\t  src: url('" + fontPath + "');\n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body style=\"font-family:MyFont\">";

        String footer = "</body></html>";
        return header + desc + footer;
    }

    public static String getFormattedDateString(String date, String neededFormat, String currentFormat) {
        return new SimpleDateFormat(neededFormat, Locale.getDefault()).format(getDateFromDateString(date, currentFormat, CustomDateFormats.TIME_ZONE_GMT));
    }

    public static String getFormattedDateString(String date, String neededFormat, String currentFormat, TimeZone timeZone) {
        return new SimpleDateFormat(neededFormat, Locale.getDefault()).format(getDateFromDateString(date, currentFormat, timeZone));
    }

    public static String getTimeZone() {

        DateFormat gmtSimpleDateFormat = new SimpleDateFormat("Z");
        gmtSimpleDateFormat.setTimeZone(TimeZone.getDefault());
        gmtSimpleDateFormat.format(Calendar.getInstance().getTime());
        return gmtSimpleDateFormat.format(Calendar.getInstance().getTime());
    }

    public static String getDateStringFromDate(Date date, String format, String timeZone) {
        DateFormat gmtSimpleDateFormat = new SimpleDateFormat(format);
        if (null != timeZone) {
            gmtSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return gmtSimpleDateFormat.format(date);
    }

    public static String getDateStringFromDate(Date date, String format) {
        DateFormat gmtSimpleDateFormat = new SimpleDateFormat(format);
        return gmtSimpleDateFormat.format(date);
    }

    public static String getDateStringFromDate(Date date, String format, TimeZone timeZone) {
        DateFormat gmtSimpleDateFormat = new SimpleDateFormat(format);
        if (null != timeZone) {
            gmtSimpleDateFormat.setTimeZone(timeZone);
        }
        return gmtSimpleDateFormat.format(date);
    }

    public static String getDateStringFromDateWithoutTimeZone(Date date, String format) {
        DateFormat gmtSimpleDateFormat = new SimpleDateFormat(format);
        return gmtSimpleDateFormat.format(date);
    }

    public static int getRandomNumber(int startRange, int endRange) {
        Random r = new Random();
        return r.nextInt(endRange - startRange) + startRange;
    }

    public static long getMillisecondsFromDateString(String dateString, String currentFormat, String timeZone) {
        Date date = getDateFromDateString(dateString, currentFormat, timeZone);
        return date.getTime();
    }

    public static String formatDateString(String dateString, String currentFormat, String requiredFormat, String currentFormatTimeZone, String requredFormatTimeZone) {
        return getDateStringFromDate(getDateFromDateString(dateString, currentFormat, currentFormatTimeZone), requiredFormat, requredFormatTimeZone);
    }

    public static String formatDateString(Date time, String requestedFormat, String requestedTimeZone) {
        return getDateStringFromDate(time, requestedFormat, requestedTimeZone);
    }

    public static String getDateStringFromLong(long timeInMilliSeconds, String format, String timeZone) {
        return getDateStringFromDate(getDateFromLong(timeInMilliSeconds), format, timeZone);
    }

    public static long getTime(String requiredTimeZone) {
        return getMillisecondsFromDateString(getDateStringFromLong(System.currentTimeMillis(), CustomDateFormats.SERVER_DATE_FORMAT,
                CustomDateFormats.TIME_ZONE_GMT), CustomDateFormats.SERVER_DATE_FORMAT,
                requiredTimeZone);
    }

    public static Date getDateFromLong(long timeInMilliSeconds) {
        return new Date(timeInMilliSeconds);
    }

    public static Date getDateFromDateString(String dateString, String format, String timeZone) {
        DateFormat simpleDateFormat = new SimpleDateFormat(format);
        if (null != timeZone) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromDateString(String dateString, String format, TimeZone timeZone) {
        DateFormat simpleDateFormat = new SimpleDateFormat(format);
        if (null != timeZone) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromDateString(String date, String currentFormat) {
        try {
            return (new SimpleDateFormat(currentFormat, Locale.US).parse(date));
        } catch (ParseException e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public static Date getUTCDateTimeFromString(String date, String currentFormate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentFormate);
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        simpleDateFormat.setTimeZone(utcZone);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return getDateFromDateString(date, currentFormate);
        }
    }

    public static int[] getTimeFromLongValue(long value) {
        int milliseconds = (int) value % 1000;
        int seconds = (int) (value / 1000) % 60;
        int minutes = (int) ((value / (1000 * 60)) % 60);
        int hours = (int) ((value / (1000 * 60 * 60)) % 24);
        return new int[]{hours, minutes, seconds, milliseconds};
    }

    public static int getDifferenceInDays(String initialDate, String initialDateFormat, String finalDate, String finalDateFormat) {
        int days = -1;
        SimpleDateFormat myFormat = new SimpleDateFormat(initialDateFormat);
        Date date = null;
        try {
            date = new SimpleDateFormat(finalDateFormat).parse(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        finalDate = new SimpleDateFormat(initialDateFormat).format(date);

        try {
            Date date1 = myFormat.parse(initialDate);
            Date date2 = myFormat.parse(finalDate);
            long diff = date1.getTime() - date2.getTime();
            days = (int) diff / (1000 * 60 * 60 * 24);
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static int[] getTimeDifferenceFromDataString(String currentTime, String time, String dateFormat, boolean futureTime, String timeZone) {
        int[] timeDifference;
        if (futureTime) {
            timeDifference = getTimeFromLongValue((getMillisecondsFromDateString(time, dateFormat, timeZone)
                    - getMillisecondsFromDateString(currentTime, dateFormat, timeZone)));
        } else {
            timeDifference = getTimeFromLongValue((getMillisecondsFromDateString(currentTime, dateFormat, timeZone)
                    - getMillisecondsFromDateString(time, dateFormat, timeZone)));
        }
        return timeDifference;
    }

    public static String getApplicationDateFormat(String sourceTime, long systemTime) {
        String appDateFormat;
        String tempSourceTime = formatDateString(sourceTime, CustomDateFormats.SERVER_DATE_FORMAT,
                CustomDateFormats.DD_MM_YYYY, CustomDateFormats.TIME_ZONE_GMT, CustomDateFormats.TIME_ZONE_GMT);
        long daysDifference = getDaysDifference(getMillisecondsFromDateString(tempSourceTime,
                CustomDateFormats.DD_MM_YYYY, CustomDateFormats.TIME_ZONE_GMT), systemTime, false);
        if (daysDifference > 0) {
            if (daysDifference < 7) {
                appDateFormat = daysDifference + " day" + (daysDifference > 1 ? "s" : "") + " ago";
            } else if (daysDifference <= 30) {
                int weeksDifference = ((int) daysDifference / 7);
                appDateFormat = weeksDifference + " week" + (weeksDifference > 1 ? "s" : "") + " ago";

            } else {
                int monthsDifference = ((int) daysDifference / 30);
                appDateFormat = monthsDifference + " month" + (monthsDifference > 1 ? "s" : "") + " ago";
            }
        } else {
            tempSourceTime = formatDateString(sourceTime, CustomDateFormats.SERVER_DATE_FORMAT,
                    CustomDateFormats.HH_MM_AA, CustomDateFormats.TIME_ZONE_GMT, CustomDateFormats.TIME_ZONE_GMT);
            String tempSystemTime = getDateStringFromLong(systemTime,
                    CustomDateFormats.HH_MM_AA, CustomDateFormats.TIME_ZONE_GMT);
            long timeDifference = getMillisecondsFromDateString(tempSystemTime,
                    CustomDateFormats.HH_MM_AA, CustomDateFormats.TIME_ZONE_GMT) - getMillisecondsFromDateString(tempSourceTime,
                    CustomDateFormats.HH_MM_AA, CustomDateFormats.TIME_ZONE_GMT);

            int[] splittedTime = getTimeFromLongValue(timeDifference);
            if (splittedTime[0] > 0) {
                appDateFormat = splittedTime[0] + " hour" + (splittedTime[0] > 1 ? "s" : "") + " ago";
            } else if (splittedTime[1] > 0) {
                appDateFormat = splittedTime[1] + " minute" + (splittedTime[1] > 1 ? "s" : "") + " ago";
            } else {
                appDateFormat = "Just now";
            }
        }
        return appDateFormat;
    }

    public static long getAbsoluteDaysDifference(String sourceTime, String serverTime, boolean futureTime) {
        String tempSourceTime = formatDateString(sourceTime, CustomDateFormats.SERVER_DATE_FORMAT,
                CustomDateFormats.DD_MM_YYYY, CustomDateFormats.TIME_ZONE_GMT, CustomDateFormats.INDIA_TIMEZONE);
        String tempServerTime = formatDateString(serverTime, CustomDateFormats.HEADER_TIME_FORMAT,
                CustomDateFormats.DD_MM_YYYY, CustomDateFormats.TIME_ZONE_GMT, CustomDateFormats.INDIA_TIMEZONE);
        if (futureTime) {
            return getDaysDifference(getMillisecondsFromDateString(tempSourceTime,
                    CustomDateFormats.DD_MM_YYYY, CustomDateFormats.INDIA_TIMEZONE), getMillisecondsFromDateString(tempServerTime,
                    CustomDateFormats.DD_MM_YYYY, CustomDateFormats.INDIA_TIMEZONE), true);
        } else {
            return getDaysDifference(getMillisecondsFromDateString(serverTime,
                    CustomDateFormats.DD_MM_YYYY, CustomDateFormats.INDIA_TIMEZONE), getMillisecondsFromDateString(sourceTime,
                    CustomDateFormats.DD_MM_YYYY, CustomDateFormats.INDIA_TIMEZONE), false);
        }
    }

    public static long getDaysDifference(long contentDateInMs, long serverDateInMS, boolean futureTime) {
        long diff;
        if (futureTime) {
            diff = contentDateInMs - serverDateInMS;
        } else {
            diff = serverDateInMS - contentDateInMs;
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    public static Uri getPictureUri() {
        File destination = new File(Environment.getExternalStorageDirectory(), "Gamesho");
        if (!destination.exists()) {
            if (!destination.mkdirs()) {
                return null;
            }
        }
        File imagePath = new File(destination.getPath() + File.separator + System.currentTimeMillis());
        if (imagePath.exists()) {
            imagePath.delete();
        }
        return Uri.fromFile(imagePath);
    }

    public static int[] getSampledSize(String path, int layoutWidth, int layoutHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int[] sampledSize = new int[2];
        final int width = options.outWidth;
        final int height = options.outHeight;
        if (height >= width) {
            sampledSize[1] = layoutHeight > height ? height : layoutHeight;
            sampledSize[0] = (width * sampledSize[1]) / height;
        } else {
            sampledSize[0] = layoutWidth > width ? width : layoutWidth;
            sampledSize[1] = (height * sampledSize[0]) / width;
        }
        return sampledSize;
    }
}
