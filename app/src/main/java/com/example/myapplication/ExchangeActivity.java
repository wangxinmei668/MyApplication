package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExchangeActivity extends AppCompatActivity implements Runnable{

    private final String TAG="Rate";
    private float dollarRate =0.1f;
    private float euroRate =0.2f;
    private float wonRate =0.3f;
    private String updateDate="";

    EditText rmb;
    TextView show;
    Handler handler;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        rmb= findViewById(R.id.rmb);
        show= findViewById(R.id.rate);



        //获取SP里保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //SharedPreferences sp =PreferenceManager.getDefaultSharedPreferences(this);这个方法也可以获取，但是他的xml文件是默认的只有一个也不可以更改，并且只适合高版本
        dollarRate =sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate =sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate =sharedPreferences.getFloat("won_rate",0.0f);
        updateDate = sharedPreferences.getString("update_date","");

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);



        Log.i(TAG,"openCreate: sp dollarRate="+dollarRate);
        Log.i(TAG,"openCreate: sp euroRate="+euroRate);
        Log.i(TAG,"openCreate: sp wonRate="+wonRate);
        Log.i(TAG,"openCreate: sp updateDate="+updateDate);
        Log.i(TAG,"openCreate: sp todayStr="+todayStr);
        //log.i是跟踪程序，可以选断点，也可以进行debug调试来一步一步追踪程序过程
        if(!todayStr.equals(updateDate)){
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread t = new Thread(this);//一定不要忘记加this，当前对象，否则将找不到方法run
            t.start();//给子线程一个命令现在可以运行，没有开始命令就不运行
        }else{
            Log.i(TAG, "onCreate: 不需要更新");
        }


        //将子线程内容加到主线程上用handler
        handler = new Handler() {//类方法的重写
            @Override//主线程收到消息如何处理，handler相当于一个收件工人，不断接受子线程发送过来的信息
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    Bundle bd1 = (Bundle) msg.obj;
                    dollarRate = bd1.getFloat("dollar-rate");
                    euroRate = bd1.getFloat("euro-date");
                    wonRate = bd1.getFloat("won-rate");

                    //String str = (String) msg.obj;//强转但要可以强转的类型才能强转
                    //Log.i(TAG,"handleMessag:getMessage msg="+str);
                    //show.setText(str);
                    SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putFloat("dollar-rate", dollarRate);
                    editor.putFloat("euro-rate", euroRate);
                    editor.putFloat("won-rate", wonRate);
                    //存入更新的日期
                    editor.putString("update-date", todayStr);
                    editor.apply();

                    Log.i(TAG, "handleMessage: dollarRate:" + dollarRate);
                    Log.i(TAG, "handleMessage: euroRate" + euroRate);
                    Log.i(TAG, "handleMessage: wonRate" + wonRate);

                    Toast.makeText(ExchangeActivity.this, "汇率已更新", Toast.LENGTH_SHORT).show();

                }
                super.handleMessage(msg);
            }
        };

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
            return;
        }

        //计算
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
        }else if(item.getItemId()==R.id.open_list){
            //打开列表窗口
            Intent list = new Intent(this,Mylist2Activity.class);
            startActivity(list);

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

            //将新设置的汇率写到SP文件里
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            //editor.putString("update_date",todayStr);
            editor.apply();//写完后一定要保存，保存可以选择commit也可以选择apply
            Log.i(TAG,"onActivityResult:数据已保存到sharedPreferences");
        }

        super.onActivityResult(requestCode, resultcode, data);


    }

    @Override
    public void run() {
        Log.i(TAG,"run run()……");
        for(int i=0 ;i<=6;i++){
            Log.i(TAG,"run i="+i);
            try {
                Thread.sleep(2000);//让其睡觉，不要跑那么快
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Bundle bundle= getFormBOC();
        //用于保存获取的汇率
        //Bundle bundle = new Bundle();
        //获取msg对象，用于返回主线程
        Message msg =handler.obtainMessage(5);
        msg.obj=bundle;
        //msg.what=5;
        //msg.obj="hello from run()";
        handler.sendMessage(msg);//消息准备好后将msg发送队列中，这个队列归Android平台管，handle可以识别它

        //获取网络数据
//        URL url = null;
//        try {
//            url = new URL("https://www.boc.cn/sourcedb/whpj/");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();//网络数据就是输入流
//
//            String html = inputStream2String(in);
//            Document doc=Jsoup.parse(html);
//            Log.i(TAG,"run html=" +html);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



//        for(Element table:tables){
//            Log.i(TAG,"run:table["+i+"]="+table);
//            i++
//        }
//        Elements newsHeadlines = doc.select("#mp-itn b a");
//        for (Element headline : newsHeadlines) {
//            Log.i(TAG,"%s\n\t%s"+headline.attr("title"), headline.absUrl("href"));
//        }




    }

    private Bundle getFormBOC() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run:"+doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table= tables.get(0);
            Log.i(TAG,"run:table1="+table);
            //获取ID中的数据
            Elements tds = table.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                Log.i(TAG,"run:"+td1.text() + "==>" +td2.text());
                String strl = td1.text();
                String val =td2.text();
                if("美元".equals(strl)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }else if("欧元".equals(strl)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }else if("韩国元".equals(strl)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }
            }

            //handler.postDelayed(this, 24*60*60*1000);//每隔24H执行
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }


    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuffer out = new StringBuffer();
        Reader in = new InputStreamReader(inputStream,"UTF-8");
        for(;;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);

        }
        return out.toString();

    }
}
