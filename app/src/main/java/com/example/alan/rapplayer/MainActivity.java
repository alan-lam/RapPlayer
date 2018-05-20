package com.example.alan.rapplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion (MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        final ArrayList<Song> songs = new ArrayList<>();

        songs.add (new Song ("Ice Ice Baby", "Vanilla Ice", R.raw.iceicebaby));
        songs.add (new Song ("Gold Digger", "Kanye West ft. Jamie Foxx", R.raw.golddigger));
        songs.add (new Song ("Feels", "Calvin Harris ft. Pharrell Williams, Katy Perry, Big Sean", R.raw.feels));
        songs.add (new Song ("Clique", "Kanye West ft. JAY-Z, Big Sean", R.raw.clique));
        songs.add (new Song ("The Motto", "Drake ft. Lil Wayne, Tyga", R.raw.themotto));

        SongAdapter adapter = new SongAdapter (this, songs);
        ListView listView = findViewById (R.id.list);
        listView.setAdapter (adapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Song song = songs.get (position);
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create (MainActivity.this, song.getSongResourceId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener (completionListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
