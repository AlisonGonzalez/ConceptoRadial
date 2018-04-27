package alisongonzalez.conceptoradial;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class FullNewsFragment extends Fragment {
    private String newsJson, titleString;
    private TextView title, content;
    private NetworkImageView imageView;
    private String url;


    public FullNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_news, container, false);
        title = (TextView) view.findViewById(R.id.fullTitle);
        content = (TextView) view.findViewById(R.id.fullContent);
        imageView =(NetworkImageView) view.findViewById(R.id.fullImage);
        Bundle bundle = getArguments();
        titleString = bundle.getString("title");
        newsJson = loadJSONFromAsset();
        try{
            JSONArray jsonArray = new JSONArray(newsJson);
            JSONObject jsonObject = null;
            for (int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String news = jsonObject.optString("titulo");
                if (news.equalsIgnoreCase(titleString)){
                    break;
                }
            }
            title.setText(jsonObject.optString("titulo"));
            content.setText(jsonObject.optString("cuerpo"));
            url = jsonObject.getString("url");
        } catch (JSONException e){
            e.printStackTrace();
        }

        RequestQueue requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
        imageView.setImageUrl(url,imageLoader);

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
}
