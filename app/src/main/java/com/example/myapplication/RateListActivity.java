package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{

    private String longDate="";//保存从sp中获取的数据
    private final String DATE_SP_KEY="lastRateDateStr";//保存SP中的数据key
    private String[] list_data = {"one","tow","three","four"};
    private final String TAG="RateList";
    int msgWhat = 5;
    Handler handler;
    //第一个list_data是计划显示在列表中的数据项，该数据项可以是一个List列表，也可以是一个String[]数组 第二个msgWhat是消息标识 第三个handler是用于接收子线程的消息处理
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);父类里包含页面布局
        //因为其父类ListActicity中已经有默认的布局，这里不需要再加载布局文件，去掉下面语句

        //构造一个ArrayAdapter用于处理数据和页面的关联
        ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list_data);
        setListAdapter(adapter);

        //获取sp中的日期记录
        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        longDate = sp.getString(DATE_SP_KEY,"");
        Log.i(TAG, "onCreate: lastRateDateStr"+longDate);

        Thread t = new Thread(this);//一定不要忘记加this，当前对象，否则将找不到方法run
        t.start();
        
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    List<String> reList = (List<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,reList);
                    setListAdapter(adapter);
                    Log.i(TAG, "reset list....");
                    
                    
                }
                super.handleMessage(msg);
            }
        };
        

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rate,menu);
        return true;
    }
    public void run(){

    }

//    public void run(){
//        Log.i(TAG, "run: run....");
//        List<String> retList = new ArrayList<String>();
//        Message msg = handler.obtainMessage(5);
//        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        Log.i(TAG, "run: curDateStr:"+curDateStr +"longdata:"+longDate);
//        if(curDateStr.equals(longDate)){
//            //如果相等，则不从网络中获取数据
//            Log.i(TAG, "run: 日期相等，从数据库中获取数据");
////            DBManager dbManager = new DBManager(RateListActivity.this);
////            for(RateItem rateItem :dbManager.listAll()){
////                retList.add(rateItem.getCurName()+"=>"+rateItem.getCurName());
//
//            }
//        }else{
//            Log.i(TAG, "run: 日期不相等，从网络中获取在线数据");
//            //获取网络数据
//            Document doc = null;
//            try {
//                List<RateItem> rateList = new ArrayList<RateItem>();
//
//                doc = Jsoup.connect("http://www.moe.gov.cn/jyb_sy/sy_jyyw/").get();
//                Log.i(TAG, "run:" + doc.title());
//                Element list = doc.getElementById("list");
//                //Element table= tables.get(1);
//                Elements elements = list.getElementsByTag("li");
//                Log.i(TAG, "run:elements=" + elements);
//                //获取ID中的数据
//                for (Element element : elements) {
//                    String title2   = element.select("a").attr("title");
//                    Log.i(TAG,"title2"+title2);
//                    //title = element.select("p.desc js_title").text();
//                    //Log.i(TAG,"title:"+title);
//                    String date=element.select("span").text();
//                    String links=element.select("a").attr("href").trim().toString();
//                    Log.i(TAG, "url"+links);
//                    HashMap<String,String> map = new HashMap<String,String>();
//                    map.put("ItemTitle",title2);
//                    map.put("ItemDetail",links);
//                    //rateList.add(map);
//                    retList.add(title2 + "==>" + links);
//                    RateItem rateItem = new RateItem(title2,links);
//                    rateList.add(rateItem);
//
////                Document doc = Jsoup.connect("http://www.usd-cny.com/icbc.html").get();
////                Log.i(TAG, "run:" + doc.title());
////                Elements tables = doc.getElementsByTag("tableDateTable");
////                Element table = tables.get(0);
////                Log.i(TAG, "run:table1=" + table);
////                //获取ID中的数据
////                Elements tds = table.getElementsByTag("td");
////                for (int i = 0; i < tds.size(); i += 5) {
////                    Element td = tds.get(i);
////                    Element td2 = tds.get(i + 3);
////                    Log.i(TAG, "run:" + td.text() + "==>" + td2.text());
////                    String tdStr = td.text();
////                    String pStr = td2.text();
////                    float val = Float.parseFloat(pStr);
////                    val = 100/val;
////                    retList.add(tdStr + "==>" + val);
////
////                    RateItem rateItem = new RateItem(tdStr,pStr);
////                    rateList.add(rateItem);
//                }
//
////                DBManager dbManager = new DBManager(RateListActivity.this);
////                dbManager.deleteALL();
////                Log.i(TAG, "run: db删除所有数据");
////                dbManager.addAll(rateList);
////                Log.i(TAG, "run: db添加所有数据");
//
//            }catch (MalformedURLException e){
//                e.printStackTrace();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //更新记录日期
//        SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(DATE_SP_KEY,curDateStr);
//        editor.commit();
//        Log.i(TAG, "run: 更新日期结束："+curDateStr);
//
//
//        msg.obj=retList;
//        handler.sendMessage(msg);
//        Log.i(TAG, "sendMessage....");
//    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr"+titleStr);
        Log.i(TAG, "onItemClick: detailStr"+detailStr);

        TextView title = view.findViewById(R.id.itemTitle);
        TextView detail = view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2"+title2);
        Log.i(TAG, "onItemClick: detail2"+detail2);

        //开启一个新的页面，传入参数
        //Intent rateCalc = new Intent(this,RateCalActivity.class);
        //rateCalc.putExtra("title",titleStr);
        //rateCalc.putExtra("detail",detailStr);
        Uri uri =Uri.parse("http://www.moe.gov.cn/"+detailStr);
        Intent web = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(web);
//        try{
//
//        }catch (ActivityNotFoundException a){
//            a.getMessage();
//        }

    }
    
}
