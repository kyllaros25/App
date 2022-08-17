package com.example.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    public static String name, height, mass;
    private static String JSON_URL = "https://swapi.dev/api/people/?format=json";
    ArrayList<HashMap<String, String>> starwars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        starwars = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);

        GetData getData = new GetData();
        getData.execute();


    }

    private class GetData extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    name = jsonObject1.getString("name");
                    height = jsonObject1.getString("height");
                    mass = jsonObject1.getString("mass");


                    HashMap<String, String> sw = new HashMap<>();
                    sw.put("name", name);
                    sw.put("height", height);
                    sw.put("mass", mass);

                    starwars.add(sw);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, starwars, R.layout.row_layout,
                    new String[]{"name"},
                    new int[]{R.id.textView});
            boolean isTablet = findViewById(R.id.frameLayout) != null;
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    name = starwars.get(position).get("name");
                    height = starwars.get(position).get("height");
                    mass = starwars.get(position).get("mass");

                    Bundle dataToPass = new Bundle();
                    dataToPass.putString("name", name);
                    dataToPass.putString("height", height);
                    dataToPass.putString("mass", mass);


                    if (isTablet) {
                        DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                        dFragment.setArguments(dataToPass); //pass it a bundle for information
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, dFragment) //Add the fragment in FrameLayout
                                .commit(); //actually load the fragment.
                    } else //isPhone
                    {
                        Intent nextActivity = new Intent(MainActivity.this, EmptyActivity.class);
                        nextActivity.putExtras(dataToPass); //send data to next activity
                        startActivity(nextActivity); //make the transition


                    }
                }


            });

        }

    }
}

