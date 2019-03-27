package com.example.alan.rapplayer;

public class Song {

    private String mSongName;
    private String mArtist;
    private int mSongResourceId;

    public Song(String songName, String artist, int songResourceId) {
        mSongName = songName;
        mArtist = artist;
        mSongResourceId = songResourceId;
    }

    public String getSongName() {
        return mSongName;
    }

    public String getArtist() {
        return mArtist;
    }

    public int getSongResourceId() {
        return mSongResourceId;
    }
}
