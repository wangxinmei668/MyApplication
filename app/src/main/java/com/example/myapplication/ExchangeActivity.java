package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ExchangeActivity extends AppCompatActivity {

    private final String TAG="Rate";
    private float dollarRate =0.1f;
    private float euroRate =0.1f;
    private float wonRate =0.3f;
    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        rmb= findViewById(R.id.rmb);
        show= findViewById(R.id.rate);
    }
    public void exchange(View btn){
        //获取用户ID
        String str = rmb.getText().toString();
        float r=0;
        if(str.length()>0){
            r = Float.parseFloat(str);//考虑异常输出
        }else{
            //提示用户输入信息
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }


        if(btn.getId()==R.id.dollar){
            float var = r*dollarRate;
            show.setText(String.valueOf(var));
        }else if(btn.getId()==R.id.japan){
            float var = r*euroRate;
            show.setText(String.valueOf(var));
        }else if(btn.getId()==R.id.dollar){
            float var = r*wonRate;
            show.setText(String.valueOf(var));
        }

    }
    public void openOne(View btn){
        openConfig();
    }

    private void openConfig() {
        Log.i("open","openOne: ");
        Intent config =new Intent(this, ScoreActivity.class);
        //Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.con"));
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);

        Log.i(TAG,"openOne:dollarRate="+dollarRate);
        Log.i(TAG,"openOne:euroRate="+euroRate);
        Log.i(TAG,"openOne:wonRate="+wonRate);

        //startActivity(config);6跳转后只能按回退返回原来的界面
        startActivityForResult(config,1);//可以将新页面的内容返回给原页面发布命令的信号“1”
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.rate){
            openConfig();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultcode, Intent data) {
        if(requestCode==1&&resultcode==2){
            Bundle bundle =data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.2f);
            wonRate = bundle.getFloat("key_won",0.3f);
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate);
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);

        }

        super.onActivityResult(requestCode, resultcode, data);


    }

}
