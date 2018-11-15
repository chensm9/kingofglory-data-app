package com.example.a17980.herolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class EpigraphActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epigraph);
    }

    public void click1(View view) {
        DefineView d = (DefineView)view;
        d.setEpigraph("other");
//        d.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        d.refresh();
    }
}