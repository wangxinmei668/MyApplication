package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerCActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {
    private String[] list_data = {"one","tow","three","four"};
    private String TAG = "search";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;
    private int msgWhat=7;
    String title;
    String link;
    EditText key;
    long todayl;
    List relist;
    ListView list;
    private long updateDate;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ser_c);

         key = findViewById(R.id.inpSearch);
         list=findViewById(R.id.list2);

        //initListView();
        //this.setListAdapter(listItemAdapter);

        //构造一个ArrayAdapter用于处理数据和页面的关联
        ListAdapter adapter = new ArrayAdapter<String>(SerCActivity.this,android.R.layout.activity_list_item,list_data);
        setListAdapter(adapter);

        //获取当前系统时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        todayl = today.getTime();
        Log.i(TAG, "onCreate: today"+todayl);
        int days = (int) ((todayl - updateDate) / (1000 * 60 * 60 * 24));
        Log.i(TAG, "onCreate: days"+days);


        //获取SP里保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("mysearch", Activity.MODE_PRIVATE);
        title =sharedPreferences.getString("title","");
        link=sharedPreferences.getString("link","");
        updateDate = sharedPreferences.getLong("update-date",0);
        Log.i(TAG, "onCreate: sp title = "+title);
        Log.i(TAG, "onCreate: sp date = "+updateDate);

        if(days>7){
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread t = new Thread(this);//一定不要忘记加this，当前对象，否则将找不到方法run
            t.start();//给子线程一个命令现在可以运行，没有开始命令就不运行
        }else{
            Log.i(TAG, "onCreate: 不需要更新");
        }


        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    List<String> reList = (List<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(SerCActivity.this,android.R.layout.activity_list_item,reList);
                    setListAdapter(adapter);
                    List<HashMap<String,String>> list2= (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(SerCActivity.this,list2,//listitems数据源
                            R.layout.list_item,//listitem的XML布局实现
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                    SharedPreferences sp = getSharedPreferences("mysearch", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("title",title);
                    editor.putString("link",link);
                    //存入更新的日期
                    editor.putLong("update-date", todayl);
                    editor.apply();

                    Log.i(TAG, "handleMessage: title="+title);
                    Log.i(TAG, "handleMessage: date="+todayl);

                    Toast.makeText(SerCActivity.this, "数据已更新", Toast.LENGTH_SHORT).show();
                }



                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);

    }

    public void search(View btn){
        String str = key.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("mysearch", Activity.MODE_PRIVATE);
        title =sharedPreferences.getString("title","");
        if(title.indexOf(str)!=-1){
            Intent rateCalc = new Intent(this,SerCActivity.class);
            rateCalc.putExtra("title",title);
            rateCalc.putExtra("detail",link);
            startActivity(rateCalc);
        }
    }

    public boolean compileKeyWord(String word, String keyWord) {
        Pattern pn = Pattern.compile(keyWord+"\\w|\\w"+keyWord+"\\w|\\w"+keyWord);
        Matcher mr = null;
        mr = pn.matcher(word);
        if (mr.find())  {
            return true;
        } else {
            return false;
        }
    }

//    private void initListView(){
//        listItems = new ArrayList<HashMap<String,String>>();
//        for(int i =0; i<10;i++){
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("ItemTitle","Title:"+i);//标题文字
//            map.put("ItemDetail","detail"+i);//详情描述
//            listItems.add(map);
//        }
//        //生成适配器的item和动态数组对应的元素
//        listItemAdapter = new SimpleAdapter(this,listItems,//listitems数据源
//                R.layout.list_item,//listitem的XML布局实现
//                new String[]{"ItemTitle","ItemDetail"},
//                new int[]{R.id.itemTitle,R.id.itemDetail});
//    }

    @Override
    public void run() {
        //获取网络数据，放入list中带回到主线程中
        List<HashMap<String,String>> retlist=new ArrayList<HashMap<String,String>>();
        Log.i(TAG, "run: run....");
        //List<String> rList = new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
            Log.i(TAG, "run: "+doc.title());
            Elements elements = doc.select("li[id]");
            Log.i(TAG, "run: "+elements.get(0));
            for(Element element : elements){
                //String title2   = element.select("a").attr("title");
                //Log.i(TAG,"title2"+title2);
                title = element.select("span.article-showTitle").text();
                Log.i(TAG,"title:"+title);
                link=element.select("a").attr("href").replace("/", "").trim();
                Log.i(TAG, "url"+link);

//                rList.add(title + "==>" + link);


                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",title);
                map.put("ItemDetail",link);
                relist.add(map);



            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        //msg.obj=rList;
        msg.obj = retlist;
        handler.sendMessage(msg);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //List<String> rList = new ArrayList<String>();
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr"+titleStr);
        Log.i(TAG, "onItemClick: detailStr"+detailStr);

//        TextView title = view.findViewById(R.id.itemTitle);
//        TextView detail = view.findViewById(R.id.itemDetail);
//        String title2 = String.valueOf(title.getText());
//        String detail2 = String.valueOf(detail.getText());
//        Log.i(TAG, "onItemClick: title2"+title2);
//        Log.i(TAG, "onItemClick: detail2"+detail2);

        //开启一个新的页面，传入参数
        Uri uri = Uri.parse(detailStr);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
