package com.example.a17980.herolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EpigraphActivity extends Activity {
    private List<attr_collection> m_attr;
    private List<attr_collection> sample_attr;
    private EpigraphAdapter attr_adapter;  // 合计属性
    private EpigraphAdapter sample_adapter; // 单个铭文属性
    private DefineView current = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epigraph);
        ListView lv = findViewById(R.id.lv);
        m_attr = new ArrayList<>();
        for(int i = 0; i < 800; i+=100) {
            attr_collection c = new attr_collection("物理穿透"+i, i);
            m_attr.add(c);
        }
        attr_adapter = new EpigraphAdapter(this, m_attr);
        lv.setAdapter(attr_adapter);

        ListView lv2 = findViewById(R.id.lv2);
        sample_attr = new ArrayList<>();
        for(int i = 0; i < 400; i+=100) {
            attr_collection c = new attr_collection("物理穿透"+i, i);
            sample_attr.add(c);
        }
        sample_adapter = new EpigraphAdapter(this, sample_attr);
        lv2.setAdapter(sample_adapter);

    }

    public void click1(View view) {
        current = (DefineView) view;

        current.setEpigraph("other");
        current.invalidate();
    }

    public void clear(View view) {
        DefineViewGroup g = findViewById(R.id.defineViewGroup);
        for(int i = 0; i < g.getChildCount(); i++) {
            DefineView v = (DefineView) g.getChildAt(i);
            if(i >= 0 && i < 10)
                v.setEpigraph("blue");
            else if(i >= 10 && i < 20)
                v.setEpigraph("green");
            else
                v.setEpigraph("red");
            v.invalidate();
        }
    }

    public void clear_one(View view) {
        DefineViewGroup g = findViewById(R.id.defineViewGroup);
        if(current != null) {
            int i = g.indexOfChild(current);
            if(i >= 0 && i < 10)
                current.setEpigraph("blue");
            else if(i >= 10 && i < 20)
                current.setEpigraph("green");
            else
                current.setEpigraph("red");
            current.invalidate();
        }
    }
}