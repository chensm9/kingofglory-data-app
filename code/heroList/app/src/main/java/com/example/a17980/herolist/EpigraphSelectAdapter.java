package com.example.a17980.herolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EpigraphSelectAdapter extends BaseAdapter {
    private List<String> list;
    LayoutInflater inflater;
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
        // 当view为空时才加载布局，否则，直接修改内容
        if (convertView == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            convertView = inflater.inflate(R.layout.epigraph_select, null);
            viewHolder = new ViewHolder();
            viewHolder.sample = convertView.findViewById(R.id.sample);
            viewHolder.name = convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.sample.setEpigraph(list.get(i));
        viewHolder.sample.invalidate();
        viewHolder.name.setText(list.get(i));
        // 将这个处理好的view返回
        return convertView;
    }
    private class ViewHolder {
        public DefineView sample;
        public TextView name;
    }


}
