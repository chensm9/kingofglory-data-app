package com.example.a17980.herolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public abstract class EpigraphSelectAdapter extends BaseAdapter {
    private List<String> list;
    LayoutInflater inflater;
    myDB m_db;
    public abstract void click(String s);
    public EpigraphSelectAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        final String s = list.get(i);
        // 当view为空时才加载布局，否则，直接修改内容
        if (convertView == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            convertView = inflater.inflate(R.layout.epigraph_select, null);
            viewHolder = new ViewHolder();
            viewHolder.sample = convertView.findViewById(R.id.sample);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.sample_attr = convertView.findViewById(R.id.sample_attr);
            convertView.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                click(s);
            }
        });
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.sample.setEpigraph(list.get(i));
        viewHolder.sample.invalidate();
        m_db = myDB.getInstance();
        viewHolder.name.setText(m_db.get_epigraph_level(list.get(i))+"铭文:"+list.get(i));
        String json = m_db.get_epigraph_attr(list.get(i));
        viewHolder.sample_attr.setText("");
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator p = jsonObject.keys();
            while(p.hasNext()) {
                String attr_name = p.next().toString();
                String attr_count = jsonObject.getString(attr_name);
                String t = viewHolder.sample_attr.getText().toString()+attr_name+" "+attr_count;
                if(p.hasNext())
                    t += "\n";
                viewHolder.sample_attr.setText(t);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        // 将这个处理好的view返回
        return convertView;
    }
    private class ViewHolder {
        public DefineView sample;
        public TextView name;
        public TextView sample_attr;
    }


}
