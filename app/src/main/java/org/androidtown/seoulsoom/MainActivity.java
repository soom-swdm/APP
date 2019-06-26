package org.androidtown.seoulsoom;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    //View for User Info
    TextView user_name_txtview, cur_point_txtview, next_level_txtview;
    ImageView cur_level_imgview, next_level_imgview;
    ProgressBar next_level_bar;
    FloatingActionButton qr_btn;

    String user_Id;

    //button
    Button history_btn, guide_btn;
    ImageButton history_btn2, guide_btn2;

    //retrofit
    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //retrofit 비동기 방식.. Background Thread
        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(ApiService.class);

        user_Id = "id2";    //사용할 user ID


        Call<ResponseBody> list = apiService.postList();
        list.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.v("*******SeeList test",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("*******SeeList test","fail");
            }
        });
/*
        //0:적립 1:사용
        Call<ResponseBody> pay = apiService.postPay("id2","003","2019-05-27T08:25:10",0,1000,1);
        pay.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.v("*******postPay test",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("*******postPay test","fail");
            }
        });
*/
/*
        Call<ResponseBody> history = apiService.postHistory("id2");
        history.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.v("*******History test",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("*******History test","fail");
            }
        });
*/
        Call<ResponseBody> userInfo = apiService.postUser("id2");
        userInfo.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                     String json = response.body().string();
                     Gson gson = new Gson();
                     UserRepo result = gson.fromJson(json, UserRepo.class);
                     Log.d("****postUser Test**", json);

                     String user_name = result.name;
                     int user_cur_point = result.point;
                     int user_acc_point = result.accumulate;
                     String user_cur_tier = result.tier; //씨앗, 새싹, 나무, 숲

                     cur_point_txtview.setText(user_cur_point+"");
                     user_name_txtview.setText(user_name+"님의 숨 마일리지");


                     if(user_cur_tier.equals("씨앗")) {
                         cur_level_imgview.setImageResource(R.drawable.level1_seed);
                         next_level_imgview.setImageResource(R.drawable.level2_leaf);
                         next_level_bar.setMax(10000);
                         next_level_bar.setProgress(user_acc_point,true);
                     }
                    else if(user_cur_tier.equals("새싹")) {
                        cur_level_imgview.setImageResource(R.drawable.level2_leaf);
                        next_level_imgview.setImageResource(R.drawable.level3_tree);
                        next_level_bar.setMax(50000);
                         next_level_bar.setProgress(user_acc_point,true);
                    }
                    else if(user_cur_tier.equals("나무")) {
                        cur_level_imgview.setImageResource(R.drawable.level3_tree);
                        next_level_imgview.setImageResource(R.drawable.level4_apple);
                        next_level_bar.setMax(100000);
                         next_level_bar.setProgress(user_acc_point,true);
                    }
                    else {
                        cur_level_imgview.setImageResource(R.drawable.level4_apple);
                        next_level_txtview.setVisibility(View.INVISIBLE);
                        next_level_imgview.setVisibility(View.INVISIBLE);
                        next_level_bar.setVisibility(View.INVISIBLE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("*********Retrofit Test","fail");
            }
        });
    }

    public void init() {
        //user info
        user_name_txtview =(TextView)findViewById(R.id.username_txtview);
        cur_point_txtview =(TextView)findViewById(R.id.cur_soom_txtview);
        cur_level_imgview =(ImageView)findViewById(R.id.cur_level_image);
        next_level_imgview =(ImageView)findViewById(R.id.next_level_image);
        next_level_txtview=(TextView)findViewById(R.id.next_level_txtview);
        next_level_bar = (ProgressBar)findViewById(R.id.next_level_bar);

        //button
        history_btn = (Button) findViewById(R.id.history_btn);
        guide_btn = (Button) findViewById(R.id.guide_btn);
        history_btn2=(ImageButton)findViewById(R.id.history_btn2);
        guide_btn2 = (ImageButton) findViewById(R.id.guide_btn2);
        qr_btn = (FloatingActionButton) findViewById(R.id.qr_btn);

        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });
        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(i);
            }
        });

        history_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });
        guide_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(i);
            }
        });
        qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), QrActivity.class);
                i.putExtra("ID", user_Id);
                startActivity(i);
            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int curId = item.getItemId();

        switch (curId) {
            case R.id.logout:
                Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
*/
}
