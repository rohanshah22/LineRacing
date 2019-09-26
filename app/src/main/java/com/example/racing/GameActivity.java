package com.example.racing;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        score = getIntent().getIntExtra("score",score);
        for(int i = 1; i < getIntent().getIntExtra("eUpgrades",0);i++){
            myCar.changeSpeed(5);
        }

        moneyUpgrades += (getIntent().getIntExtra("mUpgrades",0));
        Toast.makeText(getApplicationContext(), "myCar" +moneyUpgrades, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_game);
        scoreView = (TextView)findViewById(R.id.scoreGameView);
        wl1View = (TextView)findViewById(R.id.wl1);
        wl2View = (TextView)findViewById(R.id.wl2);
        wl3View = (TextView)findViewById(R.id.wl3);
        wl4View = (TextView)findViewById(R.id.wl4);
        scoreView.setText("Score: "+ score);
        for(int i =0; i< opponents.length;i++) {
            opponents[i] = new Car((int)(Math.random() * 5) + 4);
        }
    }
    CountDownTimer cTimer;
    Button raceBtn;
    public void race(View v) {
        raceBtn = (Button)findViewById(R.id.raceBTN);
        raceBtn.setVisibility(View.INVISIBLE);
        cTimer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                raceBtn.setVisibility(View.VISIBLE);
                resetPositions();
            }
        };
        cTimer.start();
        if(myCar.getSpeed() > 40) {
            duration = 20000;
        }
        if(myCar.getSpeed() > 60) {
            duration = 30000;
        }
        // increases duration of animation based off of player's speed

        animateFirst();
        animateSecond();
        animateThird();
        animateFour();
        calculateResults();
    }
    TextView scoreView;
    TextView wl1View;
    TextView wl2View;
    TextView wl3View;
    TextView wl4View;
    // finds each cars total speed and compares them
    // winner gets some extra speed but all the opponents are reset compared to the player
    private void calculateResults() {
        int[] speedValues = new int[4];
        speedValues[0] = myCar.getSpeed();
        for(int i = 0;i < opponents.length;i++) {
            speedValues[i+1] = opponents[i].getSpeed();
        }
        int winner = findMax(speedValues);
        if(winner == 0) {
            score += 6*(moneyUpgrades/2);
            for(int i =0; i< opponents.length;i++) {
                opponents[i].resetSpeed((int)(Math.random() * (myCar.getSpeed()/2 + 8)) + (myCar.getSpeed()/2+1));
            }
        }
        else {
            for(int i =0; i< opponents.length;i++) {
                opponents[i].resetSpeed((int)(Math.random() * 2) + 4);
            }
            opponents[winner - 1].changeSpeed(3);
        }
        updateWins(winner);
//        Toast.makeText(getApplicationContext(),winner + "speed " + speedValues[winner],Toast.LENGTH_SHORT).show();
        score += 1;
        scoreView.setText("Score: " + score);
    }

    //depending on result adds losses and wins to each car
    private void updateWins(int winnerIndex) {
        if(winnerIndex==0) {
            myCar.addWin();
            opponents[0].addLoss();
            opponents[1].addLoss();
            opponents[2].addLoss();
        }
        else {
            winnerIndex -= 1;
            myCar.addLoss();
            for(int i = 0;i<opponents.length;i++) {
                if(winnerIndex == i) {
                    opponents[i].addWin();
                }
                else {
                    Toast.makeText(getApplicationContext(),i +"" + winnerIndex,Toast.LENGTH_LONG).show();
                    opponents[i].addLoss();
                }
            }
        }
        wl1View.setText(myCar.getWins() + "/" + myCar.getLosses());
        wl2View.setText(opponents[0].getWins() + "/" + opponents[0].getLosses());
        wl3View.setText(opponents[1].getWins() + "/" + opponents[1].getLosses());
        wl4View.setText(opponents[2].getWins() + "/" + opponents[2].getLosses());
    }
    //iterates through array, every value higher than the last is the max
    private int findMax(int[] oldAr) {
        int max = 0;
        int maxIndex =0;
        for (int i = 0; i < oldAr.length;i++) {
            if (oldAr[i] > max) {
                max = oldAr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    private int duration = 10000;


    //reset positions by restarting the animation for each car, but not having it go anywhere
    private void resetPositions() {
        ImageView tv1 = findViewById(R.id.car1);
        ObjectAnimator imageViewAnimator1 = ObjectAnimator.ofFloat(tv1, "translationY", 0f,0f);
        imageViewAnimator1.setDuration(0);
        imageViewAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator1.start();

        ImageView tv2 = findViewById(R.id.car2);
        ObjectAnimator imageViewAnimator2 = ObjectAnimator.ofFloat(tv2, "translationY", 0f,0f);
        imageViewAnimator2.setDuration(0);
        imageViewAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator2.start();

        ImageView tv3 = findViewById(R.id.car3);
        ObjectAnimator imageViewAnimator3 = ObjectAnimator.ofFloat(tv3, "translationY", 0f,0f);
        imageViewAnimator3.setDuration(0);
        imageViewAnimator3.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator3.start();

        ImageView tv4 = findViewById(R.id.car4);
        ObjectAnimator imageViewAnimator4 = ObjectAnimator.ofFloat(tv4, "translationY", 0f,0f);
        imageViewAnimator4.setDuration(0);
        imageViewAnimator4.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator4.start();
    }
    private void animateFirst() {
        ImageView tv = findViewById(R.id.car1);
        //create an ObjectAnimator that translates TextView tv along the Y-axis (translationY) from 0 to 500 back to 0
        ObjectAnimator imageViewAnimator = ObjectAnimator.ofFloat(tv, "translationY", 0f,-900f);
        //duration of animation in ms
        imageViewAnimator.setDuration(duration/myCar.getSpeed());
        //setInterpolator determines how the object moves,
        //AccelerateDecelerateInterpolator makes object accelerate then decelerate to speed 0 at the end
        imageViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        //starts animation
        imageViewAnimator.start();
    }
    private void animateSecond() {
        ImageView tv = findViewById(R.id.car2);
        ObjectAnimator imageViewAnimator = ObjectAnimator.ofFloat(tv, "translationY", 0f,-900f);
        imageViewAnimator.setDuration(duration/opponents[0].getSpeed());
        imageViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator.start();
    }
    private void animateThird() {
        ImageView tv = findViewById(R.id.car3);
        ObjectAnimator imageViewAnimator = ObjectAnimator.ofFloat(tv, "translationY", 0f,-900f);
        imageViewAnimator.setDuration(duration/opponents[1].getSpeed());
        imageViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator.start();
    }
    private void animateFour() {
        ImageView tv = findViewById(R.id.car4);
        ObjectAnimator imageViewAnimator = ObjectAnimator.ofFloat(tv, "translationY", 0f,-900f);
        imageViewAnimator.setDuration(duration/opponents[2].getSpeed());
        imageViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        imageViewAnimator.start();
    }
    private int moneyUpgrades = 2;
    private int score;
    private Car myCar = new Car(5);
    private Car[] opponents = new Car[3];
    private Intent intent;
    public void switchActivity(View v) {
        intent = new Intent(this, UpgradeActivity.class);
        intent.putExtra("score",score);
        intent.putExtra("eUpgrades",myCar.getSpeed()/5);
        intent.putExtra("mUpgrades",moneyUpgrades);
        startActivity(intent);
    }
}