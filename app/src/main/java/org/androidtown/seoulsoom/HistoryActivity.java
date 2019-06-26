package org.androidtown.seoulsoom;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    public static final int LOAD_SUCCESS = 101;
    private final MyHandler myHandler = new MyHandler(this);
    private List<HashMap<String ,String >> historyList = null;
    private SimpleAdapter adapter = null;
    private String ID = "id2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView listView = (ListView)findViewById(R.id.list_container);
        historyList = new ArrayList<HashMap<String, String>>();

        String [] from = new String[]{"date","flag","amount","item"};
        int[] to = new int[]{R.id.date_txt, R.id.method_txt, R.id.mileage_txt, R.id.activity_txt};
        adapter = new SimpleAdapter(this, historyList, R.layout.activity_history_item_view, from, to);
        listView.setAdapter(adapter);

        listMaker();

    }

    //https://webnautes.tistory.com/471
    public void listMaker(){
        Log.v("history","Listmaker start");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonElement jsonElement =null;
                try {
                    JSON json=new JSON();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user", ID);
                    jsonElement=json.PostURL("http://54.180.81.90:3000/SeeHistory",jsonObject);;
                    //Log.v("histroy",result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonElement != null) {

//        jsonString = jsonString.replace("history(","");
//        jsonString= jsonString.replace(")","");

                    try {
                        Log.v("history","jsonparsing");
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        JsonArray records = jsonObject.getAsJsonArray("history");
                        historyList.clear();

                        for (int i = 0; i < records.size(); i++) {
                            JsonObject record = records.get(i).getAsJsonObject();

                            String user = record.get("user").toString();
                            String item = record.get("item").toString();
                            item = item.replace("\"","");
                            //int형?
                            String amount = record.get("amount").toString();
                            String flag = record.get("flag").toString();
                            if(flag.equals("0")==true)
                                flag="적립";
                            else
                                flag="사용";
                            //date형?
                            String date = record.get("date").toString();
                            date = date.split("T")[0];
                            date = date.replace("\"","");

                            HashMap<String, String> recordMap = new HashMap<String, String>();
                            recordMap.put("user", user);
                            recordMap.put("item", item);
                            recordMap.put("amount", amount);
                            recordMap.put("flag", flag);
                            recordMap.put("date", date);

                            historyList.add(recordMap);

                            Message message = myHandler.obtainMessage(LOAD_SUCCESS);
                            myHandler.sendMessage(message);
                        }
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private static class MyHandler extends Handler{
        private final WeakReference<HistoryActivity> weakReference;

        public MyHandler(HistoryActivity historyActivity){
            weakReference = new WeakReference<HistoryActivity>(historyActivity);
        }

        @Override
        public void handleMessage(Message msg){
            HistoryActivity historyActivity = weakReference.get();

            if(historyActivity !=null)
            {
                switch (msg.what)
                {
                    case LOAD_SUCCESS:
                        historyActivity.adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    }
}
