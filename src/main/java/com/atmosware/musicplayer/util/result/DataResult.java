package com.atmosware.musicplayer.util.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResult<T> extends Result {
    private T data;
    public DataResult(T data) {
        super();
        this.data = data;
    }
    public DataResult(String message, T data) {
        super(message);
        this.data = data;
    }
}
