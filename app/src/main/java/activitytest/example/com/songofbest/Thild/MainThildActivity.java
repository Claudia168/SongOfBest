package activitytest.example.com.songofbest.Thild;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import activitytest.example.com.songofbest.R;

public class MainThildActivity extends TabActivity implements View.OnClickListener{
    private TabHost tabhost;
    private Button thild_button1,thild_button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thild);
         thild_button1 = findViewById(R.id.thild_button1);
         thild_button2 = findViewById(R.id.thild_button2);
        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0").setContent(new Intent(this,ThildButtonOneActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1").setContent(new Intent(this,ThildButtonTwoActivity.class)));
        thild_button1.setOnClickListener(this);
        thild_button2.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.thild_button1:
                tabhost.setCurrentTab(0);
                thild_button1.setTextSize(14);
                thild_button2.setTextSize(12);
                break;
            case R.id.thild_button2:
                tabhost.setCurrentTab(1);
                thild_button2.setTextSize(14);
                thild_button1.setTextSize(12);
                break;
        }
    }
}