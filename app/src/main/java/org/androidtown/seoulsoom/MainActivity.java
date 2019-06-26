package org.androidtown.seoulsoom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button history_btn,guide_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        history_btn = (Button)findViewById(R.id.history_btn);
        guide_btn=(Button)findViewById(R.id.guide_btn);

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


//        // JSON오브젝트 받아오기
//        try {
//            JSON json=new JSON();
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("ID", "test1");
//            jsonObject.accumulate("name", "test1");
//            jsonObject.accumulate("password", "test1");
//            JsonElement result=json.PostURL("http://54.180.81.90:3000/UserInform",jsonObject);
//            Log.v("soom",result.getAsString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //QR code creation
        //Todo : user id?
        ImageView qr = (ImageView)findViewById(R.id.QR_image);
        String id = "id2";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE, 170,170);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qr.setImageBitmap(bitmap);
        }catch (Exception e){}


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int curId = item.getItemId();

        switch (curId){
            case R.id.logout:
                Toast.makeText(this,"로그아웃",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
