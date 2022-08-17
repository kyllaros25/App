package com.example.app;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
    private Bundle dataFromActivity;
    private long id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();


        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        TextView textName = (TextView)result.findViewById(R.id.textName);
        textName.setText(dataFromActivity.getString(MainActivity.name));
        //show the message
        TextView textHeight = (TextView)result.findViewById(R.id.textHeight);
        textHeight.setText(dataFromActivity.getString(MainActivity.height));

        TextView textMass = (TextView)result.findViewById(R.id.textMass);
        textMass.setText(dataFromActivity.getString(MainActivity.mass));
        //show the id:
        return result;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        AppCompatActivity parentActivity = (AppCompatActivity) context;
    }
}
