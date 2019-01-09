package activitytest.example.com.songofbest;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

import activitytest.example.com.songofbest.First.MainTopActivity;
import activitytest.example.com.songofbest.Fiveth.MainSixActivity;
import activitytest.example.com.songofbest.Fourth.MainFourActivity;
import activitytest.example.com.songofbest.Fourth.Remand;
import activitytest.example.com.songofbest.Second.MainSecondActivity;
import activitytest.example.com.songofbest.Thild.MainThildActivity;

public class MainActivity extends TabActivity implements View.OnClickListener{
    private TabHost tabhost;
    private TextView title_text;
    private RadioButton text_btn1,text_btn2,text_btn3,text_btn4,text_btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title_text=findViewById(R.id.title_text);
        text_btn1 = findViewById(R.id.text_btn1);
        text_btn2 = findViewById(R.id.text_btn2);
        text_btn3 = findViewById(R.id.text_btn3);
        text_btn4 = findViewById(R.id.text_btn4);
        text_btn5 = findViewById(R.id.text_btn5);
        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0").setContent(new Intent(this,MainTopActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1").setContent(new Intent(this,MainSecondActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag3").setIndicator("2").setContent(new Intent(this,MainThildActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag4").setIndicator("3").setContent(new Intent(this,MainFourActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag5").setIndicator("4").setContent(new Intent(this,MainSixActivity.class)));
        text_btn1.setOnClickListener(this);
        text_btn2.setOnClickListener(this);
        text_btn3.setOnClickListener(this);
        text_btn4.setOnClickListener(this);
        text_btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.text_btn1:
                tabhost.setCurrentTab(0);
                text_btn1.setTextSize(14);
                text_btn2.setTextSize(12);
                text_btn3.setTextSize(12);
                text_btn4.setTextSize(12);
                text_btn5.setTextSize(12);
                title_text.setText("社区");
                text_btn1.setTextColor(0xff80d3ff);
                text_btn2.setTextColor(0xff000000);
                text_btn3.setTextColor(0xff000000);
                text_btn4.setTextColor(0xff000000);
                text_btn5.setTextColor(0xff000000);
                break;
            case R.id.text_btn2:
                tabhost.setCurrentTab(1);
                text_btn2.setTextSize(14);
                text_btn1.setTextSize(12);
                text_btn3.setTextSize(12);
                text_btn4.setTextSize(12);
                text_btn5.setTextSize(12);
                title_text.setText("发现");
                text_btn1.setTextColor(0xff000000);
                text_btn2.setTextColor(0xff80d3ff);
                text_btn3.setTextColor(0xff000000);
                text_btn4.setTextColor(0xff000000);
                text_btn5.setTextColor(0xff000000);
                break;
            case R.id.text_btn3:
                tabhost.setCurrentTab(2);
                text_btn3.setTextSize(14);
                text_btn2.setTextSize(12);
                text_btn1.setTextSize(12);
                text_btn4.setTextSize(12);
                text_btn5.setTextSize(12);
                title_text.setText("K歌");
                text_btn1.setTextColor(0xff000000);
                text_btn2.setTextColor(0xff000000);
                text_btn3.setTextColor(0xff80d3ff);
                text_btn4.setTextColor(0xff000000);
                text_btn5.setTextColor(0xff000000);
                break;
            case R.id.text_btn4:
                tabhost.setCurrentTab(3);
                text_btn4.setTextSize(14);
                text_btn2.setTextSize(12);
                text_btn3.setTextSize(12);
                text_btn1.setTextSize(12);
                text_btn5.setTextSize(12);
                title_text.setText("美化");
                text_btn1.setTextColor(0xff000000);
                text_btn2.setTextColor(0xff000000);
                text_btn3.setTextColor(0xff000000);
                text_btn4.setTextColor(0xff80d3ff);
                text_btn5.setTextColor(0xff000000);
                break;
            case R.id.text_btn5:
                tabhost.setCurrentTab(4);
                text_btn5.setTextSize(14);
                text_btn2.setTextSize(12);
                text_btn3.setTextSize(12);
                text_btn4.setTextSize(12);
                text_btn1.setTextSize(12);
                title_text.setText("我的");
                text_btn1.setTextColor(0xff000000);
                text_btn2.setTextColor(0xff000000);
                text_btn3.setTextColor(0xff000000);
                text_btn4.setTextColor(0xff000000);
                text_btn5.setTextColor(0xff80d3ff);
                break;
        }
    }

}

