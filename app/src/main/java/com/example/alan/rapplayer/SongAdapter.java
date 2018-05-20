package com.example.alan.rapplayer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter (Activity context, ArrayList<Song> songs) {
        super (context, 0, songs);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from (getContext()).inflate (R.layout.list_item, parent, false);
        }

        Song currentSong = getItem (position);

        TextView songName = listItemView.findViewById (R.id.song_name);
        songName.setText (currentSong.getSongName());

        TextView artist = listItemView.findViewById (R.id.artist);
        artist.setText (currentSong.getArtist());

        return listItemView;
    }
}
