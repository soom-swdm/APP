package org.androidtown.seoulsoom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {

    private String address = "54.180.81.90/3000";
    private String fuction = "/SeeHistory";
    private String id = "&id=userID";
    private String requestURL = address + fuction + id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }
}
