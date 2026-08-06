package com.community.demo.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimestampUtils {

    public static String zone = "Asia/Seoul";
    public static String timePattern = "yyyy-MM-dd HH:mm:ss";

    public static String getZonedTime(Instant time) {
        return time.atZone(ZoneId.of(zone)).format(DateTimeFormatter.ofPattern(timePattern));
    }
}
