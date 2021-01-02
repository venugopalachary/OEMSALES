package com.mike.oemsales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

     private static  int SPLASH_SCREEN=4000;

     // Variables
    Animation topanim,bottomanim;

    ImageView logo;

    TextView oemslogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animations
         topanim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
         bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

         // Hooks
         logo=findViewById(R.id.oemlogo);
        oemslogan=findViewById(R.id.oemslogan);

       // Applying animations
        logo.setAnimation(topanim);
        oemslogan.setAnimation(bottomanim);

           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent intent=new Intent(MainActivity.this,Login.class);
                   startActivity(intent);
                   finish();
               }
           },SPLASH_SCREEN);

    }
}