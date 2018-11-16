package com.example.a17980.herolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class EpigraphActivity extends Activity {
    private List<attr_collection> m_attr;
    private List<attr_collection> sample_attr;
    private List<String> epigraph_name;
    private EpigraphAdapter attr_adapter;  // 合计属性
    private EpigraphAdapter sample_adapter; // 单个铭文属性
    private EpigraphSelectAdapter select_adapter;  // 选择铭文
    private DefineView current = null;
    private DefineView m_sample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epigraph);

        m_sample = findViewById(R.id.sample);
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
        sample_adapter = new EpigraphAdapter(this, sample_attr);
        lv2.setAdapter(sample_adapter);

        ListView lv3 = findViewById(R.id.lv3);
        epigraph_name = new ArrayList<>();
        select_adapter = new EpigraphSelectAdapter(this, epigraph_name);
        lv3.setAdapter(select_adapter);

    }

    public void click1(View view) {
        current = (DefineView) view;
        if(current.getEpigraph_type().equals("blue")) {
            epigraph_name.clear();
            for(int i = 1; i < 6; i++) {
                epigraph_name.add("a");
            }
            select_adapter.notifyDataSetChanged();
            go("third");
        } else if(current.getEpigraph_type().equals("green")) {
            epigraph_name.clear();
            for(int i = 1; i < 6; i++) {
                epigraph_name.add("c");
            }
            select_adapter.notifyDataSetChanged();
            go("third");
        } else if(current.getEpigraph_type().equals("red")) {
            epigraph_name.clear();
            for(int i = 1; i < 6; i++) {
                epigraph_name.add("b");
            }
            select_adapter.notifyDataSetChanged();
            go("third");
        } else {
            sample_attr.clear();
            m_sample.setEpigraph(current.getEpigraph_type());
            m_sample.invalidate();
            for(int i = 0; i < 400; i+=100) {
                attr_collection c = new attr_collection("物理穿透"+i, i);
                sample_attr.add(c);
            }
            sample_adapter.notifyDataSetChanged();
            go("second");
        }
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

    public void go(String mode) {
        RelativeLayout r1 = findViewById(R.id.first);
        RelativeLayout r2 = findViewById(R.id.second);
        RelativeLayout r3 = findViewById(R.id.third);
        if(mode.equals("first")) {
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
        } else if(mode.equals("second")) {
            r2.setVisibility(View.VISIBLE);
            r1.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
        } else if(mode.equals("third")) {
            r3.setVisibility(View.VISIBLE);
            r2.setVisibility(View.GONE);
            r1.setVisibility(View.GONE);
        }
    }
}