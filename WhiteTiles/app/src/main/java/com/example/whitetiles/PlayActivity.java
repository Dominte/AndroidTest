package com.example.whitetiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private int numberOfTiles; // 3 / 4 / 5
    private int startingSpeed; // 0 slow / 1 fast
    private int currentSpeed;
    private ImageView[] imageViews;
    private int height;
    private int width;
    public ArrayList<ImageView> tileList;
    private int interval = 1000;
    private Handler mHandler;
    private Handler tHandler;
    private int black = -16777216;
    private Handler timerHandler;
    private TextView timerView;
    private int contor = 0;
    private int intervalContor = 0;
    private TextView scoreView;
    private int secondsPassed = 0;
    private int currentScore = 0;
    RelativeLayout layout;
    MediaPlayer goodMP;
    MediaPlayer badMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        goodMP = MediaPlayer.create(this, R.raw.zapsplat_cartoon_pop_high_pitched_002_64602);
        badMP = MediaPlayer.create(this, R.raw.zapsplat_cartoon_impact_thud_wobble_26699);

        timerView = (TextView) findViewById(R.id.time);
        scoreView = (TextView) findViewById(R.id.score);
        Button startButton = (Button) findViewById(R.id.startButton);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        numberOfTiles = Integer.parseInt(sharedPreferences.getString("list_tiles", "3"));
        System.out.println("Number of tiles " + numberOfTiles);
        startingSpeed = Integer.parseInt(sharedPreferences.getString("list_speed", "0"));

        if (startingSpeed == 0)
            startingSpeed = 10;
        else
            startingSpeed = 20;

        currentSpeed = startingSpeed;

        //https://stackoverflow.com/questions/4743116/get-screen-width-and-height-in-android
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        System.out.println("Height: " + height);
        System.out.println("Width: " + width);
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
                if (startButton.getVisibility() == View.VISIBLE) {
                    startButton.setVisibility(View.GONE);
                }
                tileList = new ArrayList<ImageView>();

                // Start game


                mHandler = new Handler();
                tHandler = new Handler();
                timerHandler = new Handler();
                startRepeatingTask();
                startTileDropTask();
                startTimer();
            }
        });
    }

    public void AddTile(int row, RelativeLayout layout, int color) {

        for (int i = 0; i < tileList.size(); i++) {
            float tileX = tileList.get(i).getX();
            float tileY = tileList.get(i).getY();

            if ((int) tileX == (int) width / numberOfTiles * row) {
                if (tileY < 0)
                    return;
            }

        }

        ImageView imageView = new ImageView(this);
        if (color == 0) {
            imageView.setBackgroundColor(Color.BLACK);
        } else {
            imageView.setBackgroundColor(Color.YELLOW);
        }
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width / numberOfTiles, height / 4));
        imageView.setX((int) (width / numberOfTiles) * (row));
        imageView.setY((int) (-height / 4));
        if (color == 0) {
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    goodMP.start();
                    imageView.setVisibility(View.GONE);
                    tileList.remove(imageView);
                    layout.removeView(imageView);
                    currentScore++;
                    scoreView.setText("Score: " + currentScore);
                    imageView.setOnTouchListener(null);
                    return true;
                }
            });
        } else {
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    badMP.start();

                    // Stop threads
                    stopRepeatingTask();
                    stopTileDropTask();
                    stopTimer();
                    for (int i = 0; i < tileList.size(); i++) {
                        tileList.get(i).setVisibility(View.GONE);
                        layout.removeView(tileList.get(i));
                    }
                    tileList.clear();
                    showGameOver();
                    imageView.setOnTouchListener(null);
                    return true;
                }
            });
        }

        layout.addView(imageView);

        tileList.add(imageView);
    }


    //https://stackoverflow.com/questions/6242268/repeat-a-task-with-a-time-delay
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        stopTileDropTask();
        stopTimer();
        tileList.clear();
    }

    Runnable timer = new Runnable() {
        @Override
        public void run() {
            timerHandler.postDelayed(timer, 1000);
            secondsPassed++;
            timerView.setText("Time: " + secondsPassed);
        }
    };

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {

            if (secondsPassed % 10 == 0 && interval > 500 && intervalContor <= secondsPassed / 10) {
                intervalContor++;
                interval = interval - 100;
            }
            mHandler.postDelayed(mStatusChecker, interval);
            Random rn = new Random();
            AddTile(rn.nextInt(numberOfTiles), layout, rn.nextInt(2));
        }
    };

    Runnable DropTile = new Runnable() {
        @Override
        public void run() {
            tHandler.postDelayed(DropTile, 50);

            if (secondsPassed % 10 == 0 && contor <= secondsPassed / 10) {
                contor++;
                currentSpeed = currentSpeed + 5;
            }
            for (int i = 0; i < tileList.size(); i++) {
                ImageView imageView = tileList.get(i);

                Drawable bgDrawable = imageView.getBackground();
                if (bgDrawable instanceof ColorDrawable) {
                    int color = ((ColorDrawable) bgDrawable).getColor();
                    if (color == black && imageView.getY() > height) {
                        stopRepeatingTask();
                        stopTileDropTask();
                        stopTimer();
                        for (int j = 0; j < tileList.size(); j++) {
                            tileList.get(i).setVisibility(View.GONE);
                            layout.removeView(tileList.get(j));
                        }
                        tileList.clear();
                        showGameOver();
                    } else {
                        imageView.setY(tileList.get(i).getY() + currentSpeed);
                    }
                }


            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    void startTileDropTask() {
        DropTile.run();
    }

    void stopTileDropTask() {
        tHandler.removeCallbacks(DropTile);
    }

    void startTimer() {
        timer.run();
    }

    void stopTimer() {
        timerHandler.removeCallbacks(timer);
    }

    void showGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over!");
        builder.setMessage("Your score is: " + currentScore);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goBackToMainMenu();
            }
        });

        builder.setNeutralButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void goBackToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
