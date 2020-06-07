package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FirstScoreActivity extends AppCompatActivity {
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_score);
        score=findViewById(R.id.score11);
    }

    public void bttnAdd1(View btn){
        if(btn.getId()==R.id.button11){
            showScore(1);
        }
    }
    public void bttnAdd2(View btn){
        if(btn.getId()==R.id.button22){
            showScore(2);
        }else if(btn.getId()==R.id.buttonb2){
            showScore(2);
        }
    }
    public void bttnAdd3(View btn){
        if(btn.getId()==R.id.button44){
            showScore(3);
        }

    }
    public void bttnRestart(View btn){
        TextView out = findViewById(R.id.score11);
        out.setText("0");
    }
    private void showScore(int inc){
        String oldscore= (String)score.getText();
        String newScore=String.valueOf(Integer.parseInt(oldscore)+inc);
        score.setText(newScore);
    }
}
