package com.example.jkh.finerproject;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

//http://blog.opid.kr/353
public class MainActivity extends ActivityGroup {
    database dbHelper= new database(this);
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tabHost= findViewById(R.id.tabHost);
        tabHost.setup(getLocalActivityManager());

        // Tab을 추가하고 싶은 경우 아래와 같이 추가하시면 됩니다.
        // 물론, Tab에 관한 속성도 설정할 수 있으니
        // 그건 Android Developer 사이트를 참고하세요.
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Timer")
                .setContent(new Intent(this, Timer.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Counter")
                .setContent(new Intent(this, Counter.class)));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("data")
                .setContent(new Intent(this, dataTable.class)));

        tabHost.setCurrentTab(0);
       // createTab();
    }



}
