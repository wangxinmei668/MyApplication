package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    TextView score;
    TextView bscore;
    private final String TAG="Second";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        score=findViewById(R.id.score);
        bscore=findViewById(R.id.bscore);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = score.getText().toString();
        String scoreb = bscore.getText().toString();
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");
        Log.i(TAG, "onRestoreInstanceState: ");
        score.setText(scorea);
        bscore.setText(scoreb);
    }

    public void bttnAdd1(View btn){
        if(btn.getId()==R.id.buttonb1){
            showScore(1);
        }else if(btn.getId()==R.id.button){
            showScore(1);
        }
    }
    public void bttnAdd2(View btn){
        if(btn.getId()==R.id.button2){
            showScore(2);
        }else if(btn.getId()==R.id.buttonb2){
            showScore(2);
        }
    }
    public void bttnAdd3(View btn){
        if(btn.getId()==R.id.button4){
            showScore(3);
        }else if(btn.getId()==R.id.buttonb3){
            showScore(3);
        }

    }
    public void bttnRestart(View btn){
        score.setText("0");
        //TextView out = findViewById(R.id.score);
        //out.setText("0");
    }
    private void showScore(int inc){
        String oldscore= (String)score.getText();
        String newScore=String.valueOf(Integer.parseInt(oldscore)+inc);
        score.setText(newScore);
    }
}
