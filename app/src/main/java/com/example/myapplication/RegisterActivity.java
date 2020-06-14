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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register;
    private EditText id;
    private EditText pwd_1;
    private EditText pwd_2;
    private EditText email;
    private String result;
    private String username;
    private String pwd1;
    private String e_mail;
    private String pwd2;
    private int ResultCode=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.register_do);
        register.setOnClickListener(this);
        id = (EditText) findViewById(R.id.id_edit);
        pwd_1 = (EditText) findViewById(R.id.password_edit);
        pwd_2 = (EditText) findViewById(R.id.password_edit_1);
        email = (EditText) findViewById(R.id.email_edit);
    }

    @Override
    public void onClick(final View v) {
        username = id.getText().toString().trim();
        e_mail = email.getText().toString().trim();
        pwd1 = pwd_1.getText().toString().trim();
        pwd2 = pwd_2.getText().toString().trim();
        //判断输入框内容
        if(TextUtils.isEmpty(username)){
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(pwd1)){
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(pwd2)){
            Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }else if(!pwd1.equals(pwd2)){
            Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
            return;
            /**
             *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
             */
        }else if(isExistUserName(username)){
            Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            //把账号、密码和账号标识保存到sp里面
            /**
             * 保存账号和密码到SharedPreferences中
             */
            saveRegisterInfo(username, pwd1,e_mail);
            //注册成功后把账号传递到LoginActivity.java中
            // 返回值到loginActivity显示
            Intent intent = new Intent();
            intent.putExtra("id", username);
            intent.putExtra("password", pwd1);
            setResult(ResultCode, intent);
            //setResult(RESULT_OK, intent);
            //RESULT_OK为Activity系统常量，状态码为-1，
            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
            RegisterActivity.this.finish();
        }

//        new Thread(){public void run() {
//
//            switch (v.getId()) {
//                case R.id.register_do:
//                    username = id.getText().toString().trim();
//                    e_mail = email.getText().toString().trim();
//                    pwd1 = pwd_1.getText().toString().trim();
//                    pwd2 = pwd_2.getText().toString().trim();
//                    if(!pwd1.equals(pwd2)){
//                        runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(), "两次输入密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }else {
//                        try {
//                            //设置路径
//                            String path="http://192.168.43.226:8080/New/AndroidRegister?id="+username+"&password="+ pwd1+"&email="+e_mail+"";
//                            //创建URL对象
//                            URL url=new URL(path);
//                            //创建一个HttpURLconnection对象
//                            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
//                            //设置请求方法
//                            conn.setRequestMethod("POST");
//                            //设置请求超时时间
//                            conn.setReadTimeout(5000);
//                            //conn.setConnectTimeout(5000);
//                            //Post方式不能设置缓存，需要手动设置
//                            //conn.setUseCaches(false);
//                            //准备要发送的数据
//                            String data ="id="+ URLEncoder.encode(username,"utf-8")+"&password"+URLEncoder.encode(pwd1,"utf-8")+"&email"+URLEncoder.encode(e_mail,"utf-8");
//                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//使用的是表单请求类型
//                            conn.setRequestProperty("Content-Length", data.length()+"");
//                            conn.setDoOutput(true);
//                            //连接
//                            // conn.connect();
//                            //获得返回的状态码
//                            conn.getOutputStream().write(data.getBytes());
//                            int code=conn.getResponseCode();
//                            if(code==200){
//                                //获得一个文件的输入流
//                                InputStream inputStream= conn.getInputStream();
//
//                                result = readStream(inputStream);
//                                //更新UI
//                                showToast(result);
//                            }
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        };}.start();
    }
    public void showToast(final String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(result.equals("success")){
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.putExtra("id", username);
                    intent.putExtra("password", pwd1);
                    setResult(ResultCode, intent);
                    finish();
                }
            }
        });
    }

    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String userName,String psw,String email){
        //String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString("username", username);
        editor.putString("password", pwd1);
        editor.putString("email", e_mail);
        //提交修改 editor.commit();
        editor.commit();
        Log.i("saveRegisterInfo", "saveRegisterInfo: 已放入数据");
    }

    public static String readStream(InputStream in)throws Exception{
        //将传进来的流信息转换为字符串
        //创建1字节输出流对象
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        //定义读取长度
        int len=0;
        //定义缓存区
        byte buffer[]=new byte[2014];
        //按照缓存区大小循环读取
        while((len=in.read(buffer))!=-1){
            outputStream.write(buffer, 0, len);
        }
        in.close();
        outputStream.close();
        //将字符串数据返回
        String content=new String(outputStream.toByteArray());
        return content;
    }



}
