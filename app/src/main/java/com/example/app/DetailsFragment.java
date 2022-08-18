package com.example.app;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
    private Bundle dataFromActivity;
   private String name;
   private String height;
   private String mass;
    private long id;

    public DetailsFragment(){

    }
    public void GetData(String name, String height, String mass){
        this.name=name;
        this.height=height;
        this.mass=mass;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_details, null);
        TextView nameText = (TextView) view.findViewById(R.id.textName);
        TextView heightText = (TextView) view.findViewById(R.id.textHeight);
        TextView massText = (TextView) view.findViewById(R.id.textMass);

        nameText.setText(name);
        heightText.setText(height);
        massText.setText(mass);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        AppCompatActivity parentActivity = (AppCompatActivity) context;
    }
}
