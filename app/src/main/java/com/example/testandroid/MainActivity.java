package com.example.testandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMessageBox(findViewById(R.layout.activity_main));
            }
        });
    }



    public void ShowMessageBox(View btn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog, null);
        // int number_2 = v.findViewById(R.id.edit_text_2).getValue();
        int number_2 = 1;
        if(number_2==0){
            Toast.makeText(getApplicationContext(),"Se incearca impartirea la 0",Toast.LENGTH_SHORT).show();
            return;
        }
        builder.setCustomTitle(v);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  spinner = v.findViewById(R.id.spinner).getOption();
                View view  = (View) v.findViewById(R.id.edit_text_1);
            }
        });
        builder.setNeutralButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}