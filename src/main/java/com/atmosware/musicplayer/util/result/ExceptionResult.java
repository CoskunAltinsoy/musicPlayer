package com.atmosware.musicplayer.util.result;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResult<T> {
    private LocalDateTime timestamp;
    private T message;
    private String type;
    private Integer httpStatus;

    public ExceptionResult(T message, String type, Integer httpStatus) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.type = type;
        this.httpStatus = httpStatus;
    }
}