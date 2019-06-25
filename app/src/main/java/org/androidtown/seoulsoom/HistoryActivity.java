package org.androidtown.seoulsoom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<HashMap<String ,String >> historyList = null;
    private String ID = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try {
            JSON json=new JSON();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", ID);
            JsonElement result=json.PostURL("http://54.180.81.90:3000/SeeHistory",jsonObject);
            Log.v("soom",result.getAsString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //https://webnautes.tistory.com/471
    public boolean listMaker(String jsonString){
        if(jsonString == null) return false;

//        jsonString = jsonString.replace("history(","");
//        jsonString= jsonString.replace(")","");

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject history = jsonObject.getJSONObject("history");
            JSONArray records = history.getJSONArray("properties");

            historyList.clear();

            for(int i =0; i<records.length(); i++)
            {
                JSONObject record =  records.getJSONObject(i);

                String user = record.getString("user");
                String item = record.getString("item");
                //int형?
                String amount = record.getString("amount");
                String flag = record.getString("flag");
                //date형?
                String date = record.getString("date");

                HashMap<String, String> recordMap = new HashMap<String, String>();
                recordMap.put("user",user);
                recordMap.put("item",item);
                recordMap.put("amount",amount);
                recordMap.put("flag",flag);
                recordMap.put("date",date);

                historyList.add(recordMap);

            }

            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
}
