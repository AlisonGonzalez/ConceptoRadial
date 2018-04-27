package alisongonzalez.conceptoradial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayPodcastFragment extends Fragment {
    private String podcastsJson, url;
    private TextView podcastName, podcastProgram;
    private Button button;

    public PlayPodcastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_podcast, container, false);

        podcastsJson = loadJSONFromAsset();
        button = (Button) view.findViewById(R.id.playPodcast);
        podcastProgram = (TextView) view.findViewById(R.id.podcastCategory);
        podcastName = (TextView) view.findViewById(R.id.podcastTitle);
        Bundle bundle = getArguments();
        String category = bundle.getString("category");
        String name = bundle.getString("podcast");
        podcastProgram.setText(category);
        podcastName.setText(name);

        try{
            JSONArray jsonArray = new JSONArray(podcastsJson);
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String podcastString = jsonObject.optString("titulo");
                if (podcastString.equalsIgnoreCase(category)){
                    break;
                }
            }
            jsonArray = jsonObject.getJSONArray("podcasts");
            for (int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String podcastString = jsonObject.optString("titulo");
                if (podcastString.equalsIgnoreCase(name)){
                    url = jsonObject.optString("URL");
                    break;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(url);
            }
        });

        return view;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("podcasts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
