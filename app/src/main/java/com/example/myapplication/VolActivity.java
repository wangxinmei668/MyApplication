package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class VolActivity extends AppCompatActivity {

    TextView number1;
    TextView number2;
    TextView number3;
    TextView renumber1;
    TextView renumber2;
    TextView renumber3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol);
        Toolbar toolbar = findViewById(R.id.toolbar);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        renumber1=findViewById(R.id.renumber1);
        renumber2=findViewById(R.id.renumber2);
        renumber3=findViewById(R.id.renumber3);

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

    public void Add1(View btn){
        String oldnumber1= (String)number1.getText();
        String oldrenumber1= (String)renumber1.getText();
        String newNumber1=String.valueOf(Integer.parseInt(oldnumber1)+1);
        number1.setText(newNumber1);
        String newreNumber1=String.valueOf(Integer.parseInt(oldrenumber1)-1);
        renumber1.setText(newreNumber1);
        Toast.makeText(VolActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
    }

    public void add2(View btn){
        String oldnumber2= (String)number2.getText();
        String oldrenumber2= (String)renumber2.getText();
        String newNumber2=String.valueOf(Integer.parseInt(oldnumber2)+1);
        number2.setText(newNumber2);
        String newreNumber2=String.valueOf(Integer.parseInt(oldrenumber2)-1);
        renumber2.setText(newreNumber2);
        Toast.makeText(VolActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
    }

    public void add3(View btn){
        String oldnumber3= (String)number3.getText();
        String oldrenumber3= (String)renumber3.getText();
        String newNumber3=String.valueOf(Integer.parseInt(oldnumber3)+1);
        String newreNumber3=String.valueOf(Integer.parseInt(oldrenumber3)-1);
        number3.setText(newNumber3);
        renumber3.setText(newreNumber3);
        Toast.makeText(VolActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
    }

}
