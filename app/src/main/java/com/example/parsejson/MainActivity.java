package com.example.parsejson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Items> items = new ArrayList<>();


    private GalleryAdapter adapter;
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    String after;
    String subreddits;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GalleryAdapter(this, items);
        recyclerView.setAdapter(adapter);

        getsubreddits();

       //String url = "https://www.reddit.com/r/AsianGuysNSFW/hot.json";
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override

            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = 0;
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItemPosition = (linearLayoutManager).findLastVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                //if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                if(!loading && (lastVisibleItemPosition + visibleThreshold)>(totalItemCount)){
                    Log.d("Response:", "Load More "+after);
                    JsonTask jsonTask = new JsonTask();
                    jsonTask.execute();
                    loading = true;
                }
            }

        });
    }


    private class JsonTask extends AsyncTask<Void, Void, Void> {
        String url1 = "https://www.reddit.com/r/"+subreddits+"/hot.json?after="+after;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Void doInBackground(Void... voids) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String data = "";

            try {

                URL url = new URL(url1);
                Log.d("Response:", url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    data = data + line;
                }

                if (!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject feed = jsonObject.getJSONObject("data");
                    after = feed.optString("after");
                    Log.d("Response:", after);
                    JSONArray children = feed.getJSONArray("children");
                    //Log.d("Response:", String.valueOf(children.length()));

                    for (int i = 0; i <= children.length(); i++) {
                        JSONObject child = children.getJSONObject(i).getJSONObject("data");
                        //Log.d("Response:",child.toString());
                        String subreddit = child.optString("subreddit");
                        //Log.d("Response:", subreddit);
                        String title = child.optString("title");
                        //Log.d("Response:", title);
                        String thumbnail = child.optString("thumbnail");
                        //Log.d("Response:", thumbnail);
                        String itemurl = child.optString("url_overridden_by_dest");
                        //Log.d("Response:", itemurl);
                        //Log.d("Response:",subreddit+"\n"+title+"\n"+itemurl);

                        items.add(new Items(subreddit,title,thumbnail,itemurl));

                    }
                }

                //return data;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                Log.e("error", jsonException.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();


        }
    }

    private void getsubreddits() {

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.getAssets().open("subreddits.txt"), "UTF-8"));
            subreddits = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
