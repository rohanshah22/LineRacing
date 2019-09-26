package com.example.racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private Intent intent;
    public void launchGameActivity(View v) {
        intent = new Intent(this, GameActivity.class);
        intent.putExtra("score",20);
        intent.putExtra("engineUpgrades",0);
        intent.putExtra("moneyUpgrades",0);
        startActivity(intent);
    }
}
