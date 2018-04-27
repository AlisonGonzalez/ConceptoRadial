package alisongonzalez.conceptoradial;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsPojo>{
    private Context context;

    public NewsAdapter(Context context, int resource, List<NewsPojo> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsPojo newsPojo = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.newsTitle);
        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.newsImage);

        textView.setText(newsPojo.title);

        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
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
        imageView.setImageUrl(newsPojo.url,imageLoader);

        return convertView;
    }
}