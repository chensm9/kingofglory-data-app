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
    private myDB m_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epigraph);

        m_db = myDB.getInstance();
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
        select_adapter = new EpigraphSelectAdapter(this, epigraph_name) {
            @Override
            public void click(String s) {
                current.setEpigraph(s);
                current.invalidate();
                go("first", "");
            }
        };
        lv3.setAdapter(select_adapter);

    }

    public void click1(View view) {
        current = (DefineView) view;
        if(current.getEpigraph_type().equals("blue")) {
            go("third", "blue");
        } else if(current.getEpigraph_type().equals("green")) {
            go("third", "green");
        } else if(current.getEpigraph_type().equals("red")) {
            go("third", "red");
        } else {
            sample_attr.clear();
            m_sample.setEpigraph(current.getEpigraph_type());
            m_sample.invalidate();
            for(int i = 0; i < 400; i+=100) {
                attr_collection c = new attr_collection("物理穿透"+i, i);
                sample_attr.add(c);
            }
            sample_adapter.notifyDataSetChanged();
            go("second", "");
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

    public String index_to_type(int i) {
        if(i >= 0 && i < 10)
            return "blue";
        else if(i >= 10 && i < 20)
            return "green";
        else
            return "red";
    }

    public void clear_one(View view) {
        DefineViewGroup g = findViewById(R.id.defineViewGroup);
        if(current != null) {
            int i = g.indexOfChild(current);
            current.setEpigraph(index_to_type(i));
            current.invalidate();
            go("third", index_to_type(i));
        }
    }

    // type 用于跳转到third
    public void go(String mode, String type) {
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
        if(!type.equals("")) {  // 获取所有指定颜色铭文
            epigraph_name.clear();
            if(type.equals("blue")) {
                m_db.get_epigraph(epigraph_name, "蓝色");
            } else if(type.equals("green")) {
                m_db.get_epigraph(epigraph_name, "绿色");
            } else if(type.equals("red")){
                m_db.get_epigraph(epigraph_name, "红色");
            }
            select_adapter.notifyDataSetChanged();
        }
    }

    public void click2(View view) {
        DefineViewGroup g = findViewById(R.id.defineViewGroup);
        int i = g.indexOfChild(current);
        go("third", index_to_type(i));
    }

    public void click3(View view) {
        go("first", "");
    }
}