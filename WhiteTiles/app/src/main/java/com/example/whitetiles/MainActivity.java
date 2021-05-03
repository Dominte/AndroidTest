package com.example.whitetiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int numberOfTiles= 3 ;
    private String difficulty = "Slow";


    public void openOptionsActivity(){
        Intent intent = new Intent(this,OptionsActivity.class);
        startActivity(intent);
    }

    public void openPlayActivity(){
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("EXTRA_DIFFICULTY",difficulty);
        intent.putExtra("EXTRA_NUMBER_OF_TILES",numberOfTiles);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button optionButton = (Button) findViewById(R.id.optionButton);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptionsActivity();
            }
        });

        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayActivity();
            }
        });
    }
}