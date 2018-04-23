package alisongonzalez.conceptoradial;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class StreamingFragment extends Fragment {
    private boolean isPlaying;
    private Button playButton;
    private StreamingService streamingService;

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
        isPlaying = false;
        streamingService = new StreamingService();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startService(new Intent(getActivity(), streamingService.getClass()));
                if (!isPlaying){
                    getActivity().startService(new Intent(getActivity(), streamingService.getClass()));
                    isPlaying = true;
                } else {
                    getActivity().stopService(new Intent(getActivity(), streamingService.getClass()));
                    isPlaying = false;
                }
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
