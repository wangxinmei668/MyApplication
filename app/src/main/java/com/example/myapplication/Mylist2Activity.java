package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Mylist2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private String TAG = "mylist2";
    Handler handler;
    //private ArrayList<HashMap<String,String>> listItems;//存放文字、图片信息
    private List<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;
    private int msgWhat=7;
    String title;
    String link;
    String todayStr;
    private String updateDate="";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();

        //MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item,listItems);
        //this.setListAdapter(myAdapter);
        this.setListAdapter(listItemAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("mynews", Activity.MODE_PRIVATE);
        updateDate = sharedPreferences.getString("update-date","");
        Log.i(TAG, "onCreate: updateDate"+updateDate);

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        todayStr = sdf.format(today);

        Thread t = new Thread(this);//一定不要忘记加this，当前对象，否则将找不到方法run
        t.start();//给子线程一个命令现在可以运行，没有开始命令就不运行



        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    listItems= (List<HashMap<String, String>>) msg.obj;
                    //List<HashMap<String,String>> list2= (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(Mylist2Activity.this,listItems,//listitems数据源
                            R.layout.list_item,//listitem的XML布局实现
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                    Log.i(TAG, "reset list....");

                    SharedPreferences sp = getSharedPreferences("mynews", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("update-date", todayStr);
                    editor.apply();

                    Log.i(TAG, "handleMessage: todayStr："+todayStr);

                    Toast.makeText(Mylist2Activity.this, "新闻已更新", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);

    }

    private void initListView(){
        listItems = new ArrayList<HashMap<String,String>>();
        for(int i =0; i<10;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItemTitle","News:"+i);//标题文字
            map.put("ItemDetail","detail"+i);//详情描述
            listItems.add(map);
        }
        //生成适配器的item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,listItems,//listitems数据源
                R.layout.list_item,//listitem的XML布局实现
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    }

    @Override
    public void run() {

        //获取网络数据，放入list中带回到主线程中
        Log.i(TAG, "run...");
        List<HashMap<String,String>> retlist=new ArrayList<HashMap<String,String>>();
        Document doc = null;
//        if(todayStr.equals(updateDate)){
//            Log.i(TAG, "onCreate: 不需要更新");
//            //Toast.makeText(Mylist2Activity.this, "不需要更新", Toast.LENGTH_LONG).show();
//            DBManager dbManager = new DBManager(Mylist2Activity.this);
//            for(NewsItem rateItem :dbManager.listAll()){
//                HashMap<String,String> map = new HashMap<String,String>();
//                map.put("ItemTitle",rateItem.getCurNews());
//                map.put("ItemDetail",rateItem.getCurlink());
//                retlist.add(map);
//            }
//        }else {
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            try {
                List<NewsItem> rateList = new ArrayList<NewsItem>();
                doc = Jsoup.connect("http://www.moe.gov.cn/jyb_sy/sy_jyyw/").get();
                Log.i(TAG, "run:" + doc.title());
                Element list = doc.getElementById("list");
                //Element table= tables.get(1);
                Elements elements = list.getElementsByTag("li");
                Log.i(TAG, "run:elements=" + elements);
                //获取ID中的数据
                for (Element element : elements) {
                    String title2 = element.select("a").attr("title");
                    Log.i(TAG, "title2" + title2);
                    //title = element.select("p.desc js_title").text();
                    //Log.i(TAG,"title:"+title);
                    String date = element.select("span").text();
                    link = element.select("a").attr("href").trim();
                    String links = link.toString();
                    Log.i(TAG, "url" + link);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", title2);
                    map.put("ItemDetail", links);
                    retlist.add(map);

                    NewsItem rateItem = new NewsItem(title2, links);
                    rateList.add(rateItem);
                }

                DBManager dbManager = new DBManager(Mylist2Activity.this);
                //dbManager.deleteALL();
                Log.i(TAG, "run: db删除所有数据");
                dbManager.addAll(rateList);
                Log.i(TAG, "run: db添加所有数据");


            } catch (IOException e) {
                e.printStackTrace();
            }

            Message msg = handler.obtainMessage(7);
            msg.obj = retlist;
            handler.sendMessage(msg);
        }

//    }

    @Override
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按数据列表：position="+position);
        //删除操作
        //构造对话框进行确认操作
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除数据").setPositiveButton("是", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框事件处理：");
                listItems.remove(position);
                listItemAdapter.notifyDataSetChanged();//listItemAdapter没有remove操作
            }
        })
                .setNegativeButton("否",null);

        builder.create().show();
        Log.i(TAG, "onItemLongClick: size="+listItems.size());

        return true;
    }
}
