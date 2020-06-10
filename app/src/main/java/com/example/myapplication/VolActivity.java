package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class VolActivity extends AppCompatActivity {

    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol);
        Toolbar toolbar = findViewById(R.id.toolbar);
        number = findViewById(R.id.number);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void Add(View btn){
        if(btn.getId()==R.id.add){
            showScore(1);
        }else if(btn.getId()==R.id.add2){
            showScore(1);
        }else if(btn.getId()==R.id.add3) {
            showScore(1);
        }
    }

    private void showScore(int inc){
        String oldnumber= (String)number.getText();
        String newNumber=String.valueOf(Integer.parseInt(oldnumber)+inc);
        number.setText(newNumber);
    }

}
