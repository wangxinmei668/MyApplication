package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        score=findViewById(R.id.score);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    public void bttnAdd1(View btn){
        showScore(1);
    }
    public void bttnAdd2(View btn){
        showScore(2);
    }
    public void bttnAdd3(View btn){
        showScore(3);
    }
    public void bttnRestart(View btn){
        score.setText("0");
    }
    private void showScore(int inc){
        String oldscore= (String)score.getText();
        int newScore=Integer.parseInt(oldscore)+inc;
        score.setText(""+newScore);
    }
}
