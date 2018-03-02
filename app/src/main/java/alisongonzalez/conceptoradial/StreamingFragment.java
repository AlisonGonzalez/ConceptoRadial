package alisongonzalez.conceptoradial;


import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class StreamingFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private Button playButton;

    public StreamingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_streaming, container, false);
        playButton = (Button) view.findViewById(R.id.play);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource("http://204.12.193.98:8139/");
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playButton.setText("Iniciar Streaming");
                } else {
                    mediaPlayer.start();
                    playButton.setText("Pausar Streaming");
                }
            }
        });
        return view;
    }

}
