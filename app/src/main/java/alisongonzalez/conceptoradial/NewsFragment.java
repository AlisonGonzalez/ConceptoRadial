package alisongonzalez.conceptoradial;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private ListView listView;
    private NewsAdapter adapter;
    private String newsJson;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listView = (ListView) view.findViewById(R.id.newsList);
        adapter = new NewsAdapter(getContext(), R.layout.news_layout, new ArrayList<NewsPojo>());
        listView.setAdapter(adapter);
        newsJson = loadJSONFromAsset();
        try{
            JSONArray jsonArray = new JSONArray(newsJson);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                NewsPojo pojo = new NewsPojo();
                pojo.title = jsonObject.getString("titulo");
                pojo.content = jsonObject.getString("cuerpo");
                pojo.url = jsonObject.getString("url");
                adapter.add(pojo);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsPojo n = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putString("title", n.title);
                FullNewsFragment fragment = new FullNewsFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
            }
        });

        return view;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("news.json");
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
