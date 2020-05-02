package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<String> data = new ArrayList<String>();
    private String TAG = "Mylist";
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListView listView = (ListView) findViewById(R.id.mylist);
        //into data
        for(int i=0;i<10;i++){
            data.add("item"+i);
        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position="+position);

        adapter.remove(parent.getItemAtPosition(position));
        adapter.notifyDataSetChanged();//提示系统删除数据进行刷新，但remove有自动刷新功能这里可省略
    }
}
