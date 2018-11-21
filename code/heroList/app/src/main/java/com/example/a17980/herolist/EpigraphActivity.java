package com.example.a17980.herolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        load();
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
            TextView text = findViewById(R.id.text4);
            text.setText(m_db.get_epigraph_level(current.getEpigraph_type())+"铭文:"+current.getEpigraph_type());
            String json = m_db.get_epigraph_attr(current.getEpigraph_type());
            try {
                    JSONObject jsonObject = new JSONObject(json);
                    Iterator p = jsonObject.keys();
                    while(p.hasNext()) {
                        String attr_name = p.next().toString();
                        String attr_count = jsonObject.getString(attr_name);
                        attr_collection c = new attr_collection(attr_name, attr_count);
                        sample_attr.add(c);
                    }
            } catch (Exception e) {
                System.out.print(e.getMessage());
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
        calculate_attr();
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

    public void calculate_attr() {
        DefineViewGroup g = findViewById(R.id.defineViewGroup);
        int count = 0;
        m_db = myDB.getInstance();
        m_attr.clear();
        attr_adapter.notifyDataSetChanged();
        TextView text = findViewById(R.id.sum);
        text.setText("0");
        Map<String, String> attr_map = new HashMap<>();
        for(int i = 0; i < g.getChildCount(); i++) {
            DefineView v = (DefineView)g.getChildAt(i);
            if(!v.getEpigraph_type().equals("red") && !v.getEpigraph_type().equals("blue") && !v.getEpigraph_type().equals("green")) {
                String name = v.getEpigraph_type();
                String level = m_db.get_epigraph_level(name);
                count += Integer.parseInt(level.substring(0,1));
                String json = m_db.get_epigraph_attr(name);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    Iterator p = jsonObject.keys();
                    while(p.hasNext()) {
                        String attr_name = p.next().toString();
                        String attr_count = jsonObject.getString(attr_name);
                        if(attr_map.containsKey(attr_name)) {
                            String c = attr_map.get(attr_name);
                            if(c.charAt(c.length()-1) == '%') {
                                float a = Float.parseFloat(c.substring(1,c.length()-1));
                                float b = Float.parseFloat(attr_count.substring(1,attr_count.length()-1));
                                BigDecimal a1 = new BigDecimal(String.valueOf(a));
                                BigDecimal b1 = new BigDecimal(String.valueOf(b));
                                BigDecimal d = a1.add(b1);
                                attr_map.put(attr_name, "+"+d.toString()+"%");
                            } else {
                                float a = Float.parseFloat(c.substring(1,c.length()));
                                float b = Float.parseFloat(attr_count.substring(1,attr_count.length()));
                                BigDecimal a1 = new BigDecimal(String.valueOf(a));
                                BigDecimal b1 = new BigDecimal(String.valueOf(b));
                                BigDecimal d = a1.add(b1);
                                attr_map.put(attr_name, "+"+d.toString());
                            }
                        } else {
                            attr_map.put(attr_name, attr_count);
                        }
                    }
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }
        for (String key : attr_map.keySet()) {
            attr_collection c = new attr_collection(key, attr_map.get(key));
            m_attr.add(c);
        }
        attr_adapter.notifyDataSetChanged();
        text.setText(count+"");
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
            calculate_attr();
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

    public void save(View view) {
        DefineViewGroup g = findViewById(R.id.defineViewGroup);
        try {
            JSONObject jsonObject = new JSONObject();
            for(int i = 0; i < 30; i++) {
                DefineView d =  (DefineView)g.getChildAt(i);
                String epigraph_type = d.getEpigraph_type();
                jsonObject.put(i+"", epigraph_type);
            }
            m_db.save_epigraph(jsonObject.toString());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void load() {
        String json =  m_db.get_saved_epigraph();
        if(!json.equals("")) {
            DefineViewGroup g = findViewById(R.id.defineViewGroup);
            try {
                JSONObject jsonObject = new JSONObject(json);
                Iterator p = jsonObject.keys();
                while(p.hasNext()) {
                    String epigraph_id = p.next().toString();
                    String epigraph_type = jsonObject.getString(epigraph_id);
                    DefineView d =  (DefineView)g.getChildAt(Integer.parseInt(epigraph_id));
                    d.setEpigraph(epigraph_type);
                    d.invalidate();
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            calculate_attr();
        }
    }

    public void back(View view) {
        this.finish();
    }
}