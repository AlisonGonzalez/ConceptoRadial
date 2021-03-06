package alisongonzalez.conceptoradial;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class PodcastCategoryFragment extends Fragment {
    private String podcastsJson;
    private TextView textView;

    public PodcastCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_podcast_category, container, false);
        podcastsJson = loadJSONFromAsset();
        final ListView listView = (ListView) view.findViewById(R.id.podcastListCategory);
        final ArrayList<String> arrayList = new ArrayList<>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        textView = (TextView) view.findViewById(R.id.category);
        Bundle bundle = getArguments();
        final String category = bundle.getString("category");
        textView.setText(category);

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
                if (podcastString != null){
                    arrayList.add(podcastString);
                }
            }
            arrayAdapter.notifyDataSetChanged();
        } catch (JSONException e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = arrayAdapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putString("podcast", name);
                bundle.putString("category", category);
                PlayPodcastFragment fragment = new PlayPodcastFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
