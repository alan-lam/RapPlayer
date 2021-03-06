package com.example.alan.rapplayer;

import android.content.Context;
import android.media.AudioManager;
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
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("Ice Ice Baby", "Vanilla Ice", R.raw.iceicebaby));
        songs.add(new Song("Gold Digger", "Kanye West ft. Jamie Foxx", R.raw.golddigger));
        songs.add(new Song("Feels", "Calvin Harris ft. Pharrell Williams, Katy Perry, Big Sean", R.raw.feels));
        songs.add(new Song("Clique", "Kanye West ft. JAY-Z, Big Sean", R.raw.clique));
        songs.add(new Song("The Motto", "Drake ft. Lil Wayne, Tyga", R.raw.themotto));
        songs.add(new Song("You Already Know", "Fergie ft. Nicki Minaj", R.raw.youalreadyknow));
        songs.add(new Song("Mo Bounce", "Iggy Azalea", R.raw.mobounce));
        songs.add(new Song("Logic", "Wu Tang Forever", R.raw.logic));
        songs.add(new Song("Ghostface Killah", "Wu Tang Forever", R.raw.ghostfacekillah));
        songs.add(new Song("Raekwon", "Wu Tang Forever", R.raw.raekwon));
        songs.add(new Song("RZA", "Wu Tang Forever", R.raw.rza));
        songs.add(new Song("Method Man", "Wu Tang Forever", R.raw.methodman));
        songs.add(new Song("Inspectah Deck", "Wu Tang Forever", R.raw.inspectahdeck));
        songs.add(new Song("Cappadonna", "Wu Tang Forever", R.raw.cappadonna));
        songs.add(new Song("Jackpot Scotty Wotty", "Wu Tang Forever", R.raw.jackpotscottywotty));
        songs.add(new Song("U-God", "Wu Tang Forever", R.raw.ugod));
        songs.add(new Song("Masta Killa", "Wu Tang Forever", R.raw.mastakilla));
        songs.add(new Song("GZA", "Wu Tang Forever", R.raw.gza));

        SongAdapter adapter = new SongAdapter(this, songs);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = songs.get(position);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, song.getSongResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
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
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
