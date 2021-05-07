package com.example.whitetiles;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighscoresActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        int firstScore = Integer.parseInt(sharedPreferences.getString("First", "0"));
        int secondScore = Integer.parseInt(sharedPreferences.getString("Second", "0"));
        int thirdScore = Integer.parseInt(sharedPreferences.getString("Third", "0"));
        int fourthScore = Integer.parseInt(sharedPreferences.getString("Fourth", "0"));
        int fifthScore = Integer.parseInt(sharedPreferences.getString("Fifth", "0"));

        TextView firstView = (TextView) findViewById(R.id.firstHS);
        TextView secondView = (TextView) findViewById(R.id.secondHS);
        TextView thirdView = (TextView) findViewById(R.id.thirdHS);
        TextView fourthView = (TextView) findViewById(R.id.fourthHS);
        TextView fifthView = (TextView) findViewById(R.id.fifthHS);

        firstView.setText("1. " + firstScore + " points");
        secondView.setText("2. " + secondScore + " points");
        thirdView.setText("3. " +thirdScore + " points");
        fourthView.setText("4. "+ fourthScore+ " points");
        fifthView.setText("5. "+ fifthScore + " points ");
    }
}
