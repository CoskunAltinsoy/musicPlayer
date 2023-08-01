package com.atmosware.musicplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MusicPlayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicPlayerApplication.class, args);
    }

}
