package com.example.parsejson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url ="https://www.reddit.com/r/AsianGuysNSFW/hot.json";
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute(url);
    }



        private class JsonTask extends AsyncTask<String, String, String> {

            protected void onPreExecute() {
                super.onPreExecute();


            }

            protected String doInBackground(String... params) {


                HttpURLConnection connection = null;
                BufferedReader reader = null;
                String data ="";

                try {

                    URL url = new URL(params[0]);
                    Log.d("Response:",url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();


                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        data = data+line;
                        //buffer.append(line+"\n");
                        //Log.d("Response:", "> " + line);   //here u ll get whole response...... :-)

                    }
                    if(!data.isEmpty()){
                        JSONObject jsonObject = new JSONObject(data);
                        JSONObject feed = jsonObject.getJSONObject("data");
                        JSONArray children = feed.getJSONArray("children");
                        Log.d("Response:",String.valueOf(children.length()));

                        for(int i=0;i<=children.length();i++){
                            JSONObject child = children.getJSONObject(i).getJSONObject("data");
                            //Log.d("Response:",child.toString());
                            String subreddit = child.getString("subreddit");
                            Log.d("Response:",subreddit);
                            String title = child.optString("title");
                            Log.d("Response:",title);
                            String thumbnail = child.optString("thumbnail");
                            Log.d("Response:",thumbnail);
                            String itemurl = child.optString("url_overridden_by_dest");
                            Log.d("Response:",itemurl);
                            //Log.d("Response:",subreddit+"\n"+title+"\n"+itemurl);

                        }
                    }

                    return data;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                    Log.e("error",jsonException.getMessage());
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
            protected void onPostExecute(String result) {
                super.onPostExecute(result);


            }
        }
    }
