package com.atmosware.musicplayer.util.constant;

public class Message {
    public static class User{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "USER_NOT_EXIST";
        public static final String exist = "USER_ALREADY_EXIST";
        public static final String incorrectPassword = "INCORRECT_PASSWORD";
        public static final String demandCreated = "DEMAND_ALREADY_CREATED";
        public static final String approvalCreated = "APPROVAL_ALREADY_CREATED";
        public static final String mustCreateDemand = "MUST_CREATE_DEMAND";
    }
    public static class Song{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "SONG_NOT_EXIST";
        public static final String exist = "SONG_ALREADY_EXIST";
    }

    public static class Artist{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "ARTIST_NOT_EXIST";
        public static final String exist = "ARTIST_ALREADY_EXIST";
        public static final String notApprovedYet = "NOT_APPROVED_YET";
    }
    public static class Album{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "ALBUM_NOT_EXIST";
        public static final String exist = "ALBUM_ALREADY_EXIST";
        public static final String existWithByThatName = "ALBUM_ALREADY_EXIST";
    }
    public static class Favorite{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "FAVORITE_NOT_EXIST";
        public static final String exist = "FAVORITE_ALREADY_EXIST";
    }
    public static class Genre{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "GENRE_NOT_EXIST";
        public static final String exist = "GENRE_ALREADY_EXIST";
    }
    public static class Playlist{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "PLAYLIST_NOT_EXIST";
        public static final String exist = "PLAYLIST_ALREADY_EXIST";
    }
    public static class Role{
        public static final String successful = "SUCCESSFUL";
        public static final String notExist = "ROLE_NOT_EXIST";
        public static final String exist = "ROLE_ALREADY_EXIST";
    }
}
