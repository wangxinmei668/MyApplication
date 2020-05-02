package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowSearchActivity extends AppCompatActivity {
    String TAG = "showResearch";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);
        key = findViewById(R.id.inp2Search);
        String title = getIntent().getStringExtra("title");
        String link = getIntent().getStringExtra("link");
        Log.i(TAG, "onCreate: title="+title);
        Log.i(TAG, "onCreate: link="+link);

        key = (EditText)findViewById(R.id.inp2Search);
        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
//                String str = key.getText().toString();
//                SharedPreferences sharedPreferences = getSharedPreferences("mysearch", Activity.MODE_PRIVATE);
//                title =sharedPreferences.getString("title","");
//                if(title.indexOf(str)!=-1){
//                    Intent rateCalc = new Intent(this,ShowSearchActivity.class);
//                    rateCalc.putExtra("title",title);
//                    rateCalc.putExtra("detail",link);
//                    startActivity(rateCalc);
                }


//            }
        });

    }


}
