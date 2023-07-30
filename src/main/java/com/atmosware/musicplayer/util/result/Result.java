package com.atmosware.musicplayer.util.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private String message;
    private boolean isSuccessful;

    public Result() {

    }
    public Result(String message) {
        this.message = message;
    }
    public Result(String message, boolean isSuccessful) {
        this.message = message;
    }
}
