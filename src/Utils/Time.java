package Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
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


}
