package com.example.app;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class DetailsFragment extends Fragment {
    private Bundle dataFromActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        View view =  inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameText = (TextView) view.findViewById(R.id.textName);
        nameText.setText(dataFromActivity.getString(MainActivity.name));

        TextView heightText = (TextView) view.findViewById(R.id.textHeight);
        heightText.setText(dataFromActivity.getString(MainActivity.));

        TextView massText = (TextView) view.findViewById(R.id.textMass);
        massText.setText(dataFromActivity.getString(MainActivity.));


       return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        AppCompatActivity parentActivity = (AppCompatActivity) context;
    }
}
