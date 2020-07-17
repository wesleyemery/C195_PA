package Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.TimeZone;

public class Time {

    public static String dateTime(){
        Date t = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:SS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String d = dateFormat.format(t);
        //System.out.println("date:" + d);

        return d;

    }

    public static Timestamp now() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    public static Instant instantNow(){
        Instant timestamp = Instant.now();
        return timestamp;
    }

    public static LocalDateTime getLocalDateTime(){
    LocalDateTime now = LocalDateTime.now();
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zonedDateTime = now.atZone(zoneId);
    LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    return localDateTime;
    }

    public static LocalDateTime getLocalDateTimeAdd15() {
        LocalDateTime now = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime localDateTimeAdd15 = localDateTime.plusMinutes(15);
        return localDateTimeAdd15;
    }

    public static String getMonth() {
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return month;
    }
    public static String getEndOfMonth() {

        String endOfMonth = LocalDateTime.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return endOfMonth;
    }
    public static String getWeek() {
        String week = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return week;
    }
    public static String getWeekLater() {
        String weekLater = LocalDateTime.now().plusWeeks(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return weekLater;
    }

    public static String getStartDateTime(String start) {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        ZonedDateTime dbDateTime = ZonedDateTime.parse(start.replace(" ", "T") + ZoneOffset.UTC + "[" + ZoneId.of("UTC") + "]");
        Instant utcToLocalInstant = dbDateTime.toInstant();
        ZonedDateTime utcToLocal = utcToLocalInstant.atZone(zoneId);
        String date = String.valueOf(utcToLocal.toLocalDate());
        String time = String.valueOf(utcToLocal.toLocalTime());
        String localDateTimeString = date + " " + time;
        return localDateTimeString;
    }

    public static String getEndDateTime(String end) {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        ZonedDateTime dbDateTime = ZonedDateTime.parse(end.replace(" ", "T") + ZoneOffset.UTC + "[" + ZoneId.of("UTC") + "]");
        Instant utcToLocalInstant = dbDateTime.toInstant();
        ZonedDateTime utcToLocal = utcToLocalInstant.atZone(zoneId);
        String date = String.valueOf(utcToLocal.toLocalDate());
        String time = String.valueOf(utcToLocal.toLocalTime());
        String localDateTimeString = date + " " + time;
        return localDateTimeString;
    }

    public static String getTimestamp() {
        ZonedDateTime tS = ZonedDateTime.now(ZoneOffset.UTC);
        String date = String.valueOf(tS.toLocalDate());
        String time = String.valueOf(tS.toLocalTime());
        String dateTimeString = date + " " + time;
        return dateTimeString;
    }


}
