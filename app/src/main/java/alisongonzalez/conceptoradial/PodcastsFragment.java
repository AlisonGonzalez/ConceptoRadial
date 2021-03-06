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
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
            JSONArray jsonArray = new JSONArray(podcastsJson);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                bundle.putString("category", name);
                PodcastCategoryFragment fragment = new PodcastCategoryFragment();
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
