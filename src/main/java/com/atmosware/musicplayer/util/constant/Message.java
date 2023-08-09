package com.atmosware.musicplayer.util.constant;

public class Message {
    private Message(){}
    public static class User{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "USER_NOT_EXIST";
        public static final String EXIST = "USER_ALREADY_EXIST";
        public static final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
        public static final String DEMAND_CREATED = "DEMAND_ALREADY_CREATED";
        public static final String APPROVAL_CREATED = "APPROVAL_ALREADY_CREATED";
        public static final String MUST_CREATE_DEMAND = "MUST_CREATE_DEMAND";
    }
    public static class Song{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "SONG_NOT_EXIST";
        public static final String EXIST = "SONG_ALREADY_EXIST";
    }

    public static class Artist{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "ARTIST_NOT_EXIST";
        public static final String EXIST = "ARTIST_ALREADY_EXIST";
        public static final String NOT_APPROVED_YET = "NOT_APPROVED_YET";
    }
    public static class Album{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "ALBUM_NOT_EXIST";
        public static final String EXIST = "ALBUM_ALREADY_EXIST";
        public static final String EXIST_WITH_BY_THAT_NAME = "ALBUM_ALREADY_EXIST";
    }
    public static class Favorite{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "FAVORITE_NOT_EXIST";
        public static final String EXIST = "FAVORITE_ALREADY_EXIST";
    }
    public static class Genre{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "GENRE_NOT_EXIST";
        public static final String EXIST = "GENRE_ALREADY_EXIST";
    }
    public static class Playlist{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "PLAYLIST_NOT_EXIST";
        public static final String EXIST = "PLAYLIST_ALREADY_EXIST";
    }
    public static class Role{
        public static final String SUCCESSFUL = "SUCCESSFUL";
        public static final String NOT_EXIST = "ROLE_NOT_EXIST";
        public static final String EXIST = "ROLE_ALREADY_EXIST";
    }
}
