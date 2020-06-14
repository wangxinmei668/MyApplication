package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG="login";
    private EditText id;
    private EditText password;
    private Button login;
    private Button register;
    private String username;
    private String pwd;
    private String new_username;
    private String new_pwd;
    private SharedPreferences sp;
    private String result;
    private int RequestCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        //register.setOnClickListener(this);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //将数据显示到UI控件
        //把config.xml文件中的数据取出来显示到EditText控件中
        //如果没找到key键对应的值，会返回第二个默认的值
        username = sp.getString("username", "");
        pwd = sp.getString("password", "");
        Log.i(TAG, "onCreate: "+username);
        Log.i(TAG, "onCreate: "+pwd);
        id.setText(username);
        this.password.setText(pwd);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            id.setText(data.getStringExtra("id"));
            password.setText(data.getStringExtra("password"));
        }
    }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login:
                    new_username = id.getText().toString().trim();
                    new_pwd = password.getText().toString().trim();
                    Log.i("onclick", "onClick: "+username);
                    Log.i("onclick", "onClick: "+pwd);
                    if(pwd.equals(new_pwd) &&username.equals(new_pwd)) {
                        //一致登录成功
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                        //saveLoginStatus(true, userName);
                        //登录成功后关闭此页面进入主页
                        Intent data = new Intent();
                        //datad.putExtra( ); name , value ;
                        data.putExtra("isLogin", true);
                        //RESULT_OK为Activity系统常量，状态码为-1
                        // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                        setResult(RESULT_OK, data);
                        //销毁登录界面
                        LoginActivity.this.finish();
                        //跳转到主界面，登录成功的状态传递到 MainActivity 中
                        startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                        return;
                    }else if(TextUtils.isEmpty(new_username)||TextUtils.isEmpty(new_pwd)){
                        Toast.makeText(getApplicationContext(), "账号或密码不能为空", Toast.LENGTH_LONG).show();
                    }

//                        new Thread(){
//                            public void run(){
//                                try {
//                                    //设置路径
//                                    //设置路径
//                                    String path="http://192.168.227.1:8080/MyWebsite/androidlogin.do?id="+username+"&password="+pwd+"";
//                                    //创建URL对象
//                                    URL url=new URL(path);
//                                    //创建一个HttpURLConnection对象
//                                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
//                                    //设置请求方法
//                                    conn.setRequestMethod("POST");
//                                    //设置请求超时时间
//                                    conn.setReadTimeout(5000);
//                                    conn.setConnectTimeout(5000);
//
//                                    //Post方式不能设置缓存，需要手动设置
//                                    conn.setUseCaches(false);
//                                    //设置我们的请求数据
//                                    String data="id="+username+"&password="+pwd;
//                                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//使用的是表单请求类型
//                                    conn.setRequestProperty("Content-Length", data.length()+"");
//                                    conn.setDoOutput(true);
//                                    conn.getOutputStream().write(data.getBytes());
//		                    //连接
//		                    conn.connect();
//							//获取一个输出流
//							OutputStream out=conn.getOutputStream();
//							out.write(data.getBytes());
//                                    //获取服务器返回的状态吗
//                                    int code=conn.getResponseCode();
//                                    if(code==200){
//                                        //获取服务器返回的输入流对象
//                                        InputStream in= conn.getInputStream();
//                                        result = RegisterActivity.readStream(in);
//                                        //更新UI
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                if(result.equals("success"))
//                                                    // TODO Auto-generated method stub
//                                                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
//                                                else{
//                                                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
//                                                }
//                                            }
//                                        });
//                                    }
//                                } catch (Exception e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//
//                            };
//                        }.start();
                    //}
                    break;
                case R.id.register:
                    //跳转到登录页面
                    Intent intent=new Intent(this,RegisterActivity.class);
                    startActivityForResult(intent,RequestCode);
                    break;
                default:
                    break;
            }
        }

}
