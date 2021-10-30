package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView ivTop,ivIcon;
    TextView tvSlogan;
    CharSequence charSequence;
    int index;
    long delay = 20;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivTop = findViewById(R.id.headerView);
        ivIcon = findViewById(R.id.logoView);
        tvSlogan = findViewById(R.id.textView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
        ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //initialize top animation
        Animation animation1 = AnimationUtils.loadAnimation(this
        ,R.anim.splash_top_wave);
        ivTop.setAnimation(animation1);

        //initialize icon animation
        Animation animation2 = AnimationUtils.loadAnimation(this
                ,R.anim.splash_icon_wave);
        ivIcon.setAnimation(animation2);

        animateText("Work together to overcome the epidemic");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this
                ,LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                finish();
            }
        },3000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //when runnable is run, set text
            tvSlogan.setText(charSequence.subSequence(0,index++));
            //check condition
            if(index <= charSequence.length()){
                //when index is equal to text length, run handler
                handler.postDelayed(runnable,delay);
            }
        }
    };

    //create animated text method
    public void animateText(CharSequence cs){
        charSequence = cs;
        index = 0;
        tvSlogan.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}