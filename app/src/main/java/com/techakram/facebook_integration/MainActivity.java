package com.techakram.facebook_integration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView1, textView2;
    LoginButton loginButton;
    private CallbackManager callbackManager;
   AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    FacebookCallback<LoginResult> callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        textView1 = findViewById(R.id.tvUserName);
        textView2 = findViewById(R.id.tvUserEmail);
        loginButton = findViewById(R.id.login_btn);
        FacebookSdk.sdkInitialize(getApplicationContext( ));
        callbackManager = CallbackManager.Factory.create( );
        accessTokenTracker=new AccessTokenTracker( ) {
            @Override
            protected void onCurrentAccessTokenChanged
                    (AccessToken oldAccessToken, AccessToken currentAccessToken)
            {

            }
        };

        profileTracker=new ProfileTracker( ) {
            @Override
            protected void onCurrentProfileChanged
                    (Profile oldProfile, Profile currentProfile)
            {
                  //initialize to your profile
                userProfile(currentProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        callback= new FacebookCallback<LoginResult>( ) {
            @Override
            public void onSuccess(LoginResult loginResult) {
               //AccessToken accessToken=loginResult.getAccessToken();
               Profile profile=Profile.getCurrentProfile();
               //call to user profile
                userProfile(profile);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        };//closed callback..
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager,callback);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStop()
    {
        super.onStop( );
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume( );
        Profile profile=Profile.getCurrentProfile();
        //call to user profile
        userProfile(profile);
    }
    public void userProfile(Profile profile)
    {
        if(profile!=null)
        {
            textView1.setText(profile.getFirstName());
            textView2.setText(profile.getLastName());
            String imgUrl=profile.getProfilePictureUri(200,200).toString();
            Glide.with(this).load(imgUrl).into(imageView);

        }
    }
}