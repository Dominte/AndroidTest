package com.example.whitetiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private int numberOfTiles= 3 ;
    private String difficulty = "Slow";
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ShareButton shareButton;


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

    public void openHighscoreActivity(){
        Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        shareButton = findViewById(R.id.shareButton);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success","Login successful");
            }
            @Override
            public void onCancel() {
                Log.d("Cancel","Login cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error","Login error");
            }
        });

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

        Button highscoresButton = (Button) findViewById(R.id.highscoresButton);
        highscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHighscoreActivity();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);


        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                    }
                });

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setQuote("My favourite game!")
                .setContentUrl(Uri.parse("www.whitetiles.ro"))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#WhiteTiles")
                        .build())
                .build();

        shareButton.setShareContent(shareLinkContent);

    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}