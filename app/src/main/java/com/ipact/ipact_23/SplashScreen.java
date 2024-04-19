package com.ipact.ipact_23;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.core.app.ActivityOptionsCompat;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.util.Pair;
import androidx.core.app.ActivityOptionsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    //variables

    Animation topAnim, bottomAnim;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        //bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //Hooks
        image = findViewById(R.id.imageView);
        //slogan = findViewById(R.id.textView);
        image.setAnimation(topAnim);
        //slogan.setAnimation(topAnim);

        View sharedView1 = findViewById(R.id.imageView); // Replace with your view
        //View sharedView2 = findViewById(R.id.textView);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent intent = new Intent(SplashScreen.this, Login.class);

                // Define the shared elements using Pair
                Pair<View, String> pair1 = Pair.create(sharedView1, "logo_image");
                //Pair<View, String> pair2 = Pair.create(sharedView2, "logo_text");

                // Create ActivityOptionsCompat with the shared elements
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, pair1);

                // Start the second activity with the shared element transition
                startActivity(intent, options.toBundle());
                finish();
            }
        },SPLASH_SCREEN);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}

