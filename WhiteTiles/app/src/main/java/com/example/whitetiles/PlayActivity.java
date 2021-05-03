package com.example.whitetiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    private int numberOfTiles; // 3 / 4 / 5
    private int startingSpeed; // 0 slow / 1 fast

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button startButton = (Button) findViewById(R.id.startButton);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs",MODE_PRIVATE);
        numberOfTiles = Integer.parseInt(sharedPreferences.getString("list_tiles",""));
        startingSpeed = Integer.parseInt(sharedPreferences.getString("list_speed",""));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startButton.getVisibility() == View.VISIBLE) {
                    startButton.setVisibility(View.GONE);
                }


            }
        });
    }

}
