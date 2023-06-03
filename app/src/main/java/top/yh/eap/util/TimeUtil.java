package top.yh.eap.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @user
 * @date
 */
public class TimeUtil {
    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日HH点mm时ss分"));
    }
}
