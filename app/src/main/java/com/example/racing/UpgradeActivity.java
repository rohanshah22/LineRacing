package com.example.racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UpgradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        engineUpgrades = getIntent().getIntExtra("eUpgrades",0);
        moneyUpgrades = getIntent().getIntExtra("mUpgrades",0);
        score = getIntent().getIntExtra("score",0);
        setContentView(R.layout.activity_upgrade);
        scoreView = (TextView)findViewById(R.id.scoreView);
        scoreView.setText("Score: " + score);
    }
    private int score = 0;
    private int engineUpgrades = 0;
    private int moneyUpgrades = 0;
    private Intent intent;
    TextView scoreView;
    public void setEngineUpgrades(View v) {
        if(score >=5*(1+engineUpgrades)) {
            engineUpgrades++;
            score -= (5 *engineUpgrades);
        }
        scoreView.setText("Score: " + score);

    }
    public void setMoneyUpgrades(View v) {
        if(score>=5 *(1+moneyUpgrades)) {
            moneyUpgrades++;
            score -= (5 *moneyUpgrades);
        }
        scoreView.setText("Score: " + score);
    }
    public void launchGameActivity(View v) {
        moneyUpgrades -= 2;
        intent = new Intent(this, GameActivity.class);
        intent.putExtra("score",score);
        intent.putExtra("eUpgrades",engineUpgrades);
        intent.putExtra("mUpgrades",moneyUpgrades);
        startActivity(intent);
    }
}
