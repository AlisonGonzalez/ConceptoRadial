package alisongonzalez.conceptoradial;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PodcastsFragment extends Fragment {
    private String podcastsJson;

    public PodcastsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_podcasts, container, false);

        podcastsJson = loadJSONFromAsset();
        final ListView listView = (ListView) view.findViewById(R.id.podcastList);
        final ArrayList<String> arrayList = new ArrayList<>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        try{
            JSONObject jsonObject = new JSONObject(podcastsJson);
            JSONArray jsonArray = jsonObject.getJSONArray("Medio Equis");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject podcast = jsonArray.getJSONObject(i);
                String podcastString = podcast.optString("titulo");
                if (podcastString != null){
                    arrayList.add(podcastString);
                }
            }
            arrayAdapter.notifyDataSetChanged();
        } catch (JSONException e){
            e.printStackTrace();
        }

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
