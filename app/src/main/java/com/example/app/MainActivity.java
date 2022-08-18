package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

            class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
                private ArrayList<HashMap<String, String>> starwars;
                private LayoutInflater mLayoutInflater;

                public PeopleAdapter(Context context, ArrayList<HashMap<String, String>> peopleList) {
                    starwars = peopleList;
                    mLayoutInflater = LayoutInflater.from(context);
                }

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = mLayoutInflater.inflate(R.layout.activity_main, parent, false);
                    return new ViewHolder(view);
                }


                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    people = starwars.get(position);
                    holder.textName.setText(people.getName());
                    holder.textHeight.setText(people.getHeight());
                    holder.textMass.setText(people.mass());
                }

                @Override
                public int getItemCount() {
                    return starwars.size();
                }

                public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                    private TextView textName;
                    private TextView textHeight;
                    private TextView textMass;

                    public ViewHolder(View itemView) {
                        super(itemView);
                        textName = (TextView) itemView.findViewById(R.id.textName);
                        textHeight = (TextView) itemView.findViewById(R.id.textHeight);
                        textMass = (TextView) itemView.findViewById(R.id.textMass);
                        itemView.setOnClickListener(this);
                    }

                    @Override
                    public void onClick(View view) {
                        People people = starwars.get(getAdapterPosition());
                        DetailsFragment dFragment = new DetailsFragment();
                        dFragment.GetData(people.getName(), people.getHeight(), people.getMass());
                        dFragment.show(/*put the FragmentManager here*/, DetailsFragment.class.getSimpleName());
                    }
                }
            }
        }

    }
}