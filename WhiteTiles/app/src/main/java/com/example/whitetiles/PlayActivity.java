package com.example.whitetiles;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    private int numberOfTiles; // 3 / 4 / 5
    private int startingSpeed; // 0 slow / 1 fast
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button startButton = (Button) findViewById(R.id.startButton);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs",MODE_PRIVATE);
        numberOfTiles = Integer.parseInt(sharedPreferences.getString("list_tiles",""));
        startingSpeed = Integer.parseInt(sharedPreferences.getString("list_speed",""));

        //https://stackoverflow.com/questions/4743116/get-screen-width-and-height-in-android
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        RelativeLayout layout = findViewById(R.id.relativeLayout);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startButton.getVisibility() == View.VISIBLE) {
                    startButton.setVisibility(View.GONE);
                }

                // Start game


            }
        });
    }
    public void DrawTile(){
        
    }
}
