package com.atmosware.musicplayer.util.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Result implements Serializable {
    private String message;

    public Result() {

    }
    public Result(String message) {
        this.message = message;
    }

}
