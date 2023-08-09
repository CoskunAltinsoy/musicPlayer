package com.atmosware.musicplayer.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeUtil {
    private final Clock clock;
    public TimeUtil() {
        this.clock = Clock.systemDefaultZone();
    }
    public LocalDateTime getLocalDateTimeNow() {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(instant, clock.getZone());
    }
}
