package com.atmosware.musicplayer.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeFormatter {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDateTime format(LocalDateTime time){
        String dateTime = formatter.format(time);
        return LocalDateTime.parse(dateTime);
    }
}
