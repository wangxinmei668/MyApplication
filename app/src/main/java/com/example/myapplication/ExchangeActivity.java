package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExchangeActivity2 extends AppCompatActivity {

    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;
    private String updateDate = "";

    EditText rmb;
    TextView show;
    Handler handler;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.rate);


    }

    public void exchange(View btn) {
        //获取用户ID
        String str = rmb.getText().toString();
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);//考虑异常输出
        } else {
            //提示用户输入信息
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }

        //计算
        if (btn.getId() == R.id.dollar) {
            float var = r * dollarRate;
            show.setText(String.valueOf(var));
        } else if (btn.getId() == R.id.japan) {
            float var = r * euroRate;
            show.setText(String.valueOf(var));
        } else if (btn.getId() == R.id.dollar) {
            float var = r * wonRate;
            show.setText(String.valueOf(var));
        }

    }

    public void openOne(View btn) {
        openConfig();
    }

    private void openConfig() {
        Log.i("open", "openOne: ");
        Intent config = new Intent(this, ScoreActivity.class);
        //Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.con"));


        //startActivity(config);6跳转后只能按回退返回原来的界面
        startActivityForResult(config, 1);//可以将新页面的内容返回给原页面发布命令的信号“1”
    }

}


