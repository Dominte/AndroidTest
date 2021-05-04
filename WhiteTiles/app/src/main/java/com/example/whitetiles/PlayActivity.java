package com.example.whitetiles;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private int numberOfTiles; // 3 / 4 / 5
    private int startingSpeed; // 0 slow / 1 fast
    private ImageView[] imageViews;
    private int height;
    private int width;
    public ArrayList<ImageView> tileList;
    private int interval = 2000;
    private Handler mHandler;
    private Handler tHandler;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button startButton = (Button) findViewById(R.id.startButton);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs",MODE_PRIVATE);
        numberOfTiles = Integer.parseInt(sharedPreferences.getString("list_tiles","3"));
        startingSpeed = Integer.parseInt(sharedPreferences.getString("list_speed","0"));

        //https://stackoverflow.com/questions/4743116/get-screen-width-and-height-in-android
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        System.out.println("Height: " + height);
        System.out.println("Width: "+ width);
        layout = findViewById(R.id.relativeLayout);

        /*   ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(Color.BLACK);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width/numberOfTiles,height/4));
        imageView.setX(0);
        imageView.setY(0);
        layout.addView(imageView);
     */

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startButton.getVisibility() == View.VISIBLE) {
                    startButton.setVisibility(View.GONE);
                }
                tileList  = new ArrayList<ImageView>();

                // Start game


                mHandler = new Handler();
                tHandler = new Handler();
                startRepeatingTask();
                startTileDropTask();
            }
        });
    }
    public ImageView AddTile(int row,RelativeLayout layout,int color){
        ImageView imageView = new ImageView(this);
        if(color==0){
            imageView.setBackgroundColor(Color.BLACK);}
        else{
            imageView.setBackgroundColor(Color.YELLOW);
        }
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width/numberOfTiles,height/4));
        imageView.setX((width/numberOfTiles)*(row-1));
        imageView.setY(0);
        layout.addView(imageView);


        return imageView;
    }


    //https://stackoverflow.com/questions/6242268/repeat-a-task-with-a-time-delay
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        stopTileDropTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(mStatusChecker, interval);
            Random rn = new Random();
            tileList.add(AddTile(rn.nextInt(numberOfTiles+1),layout,rn.nextInt(2)));

           
        }
    };

    Runnable DropTile = new Runnable() {
        @Override
        public void run() {
            tHandler.postDelayed(DropTile,100);
            for(int i = 0 ; i<tileList.size();i++){
                ImageView imageView = tileList.get(i);
                imageView.setY(tileList.get(i).getY()+20);

            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    void startTileDropTask(){
        DropTile.run();
    }

    void stopTileDropTask(){
        tHandler.removeCallbacks(DropTile);
    }

}
