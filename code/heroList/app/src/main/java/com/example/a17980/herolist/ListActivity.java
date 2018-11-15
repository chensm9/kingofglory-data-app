package com.example.a17980.herolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    ListView listView;
    ListViewAdapter listViewAdapter;
    FloatingActionButton floatingActionButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //listView
        listView = findViewById(R.id.listView);
        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter);

        //悬浮按钮
        init_floatButton();
    }


    public class ListViewAdapter extends BaseAdapter {
        private List<Hero> Data;
        ListViewAdapter(){
            Data = new ArrayList<>();
            for (int i = 0;i < 10; ++i){
                Hero hero = new Hero(R.mipmap.icon, "沈梦溪");
                Data.add(hero);
            }
        }

        @Override
        public int getCount(){
            return Data.size();
        }

        @Override
        public Hero getItem(int i){
            if (Data == null) return null;
            return Data.get(i);
        }

        @Override
        public long getItemId(int i){
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            View convertView;
            ViewHolder viewHolder;
            Hero hero = Data.get(position);

            if (view == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);
                viewHolder = new ViewHolder();
                viewHolder.Icon = convertView.findViewById(R.id.heroIcon);
                viewHolder.Name = convertView.findViewById(R.id.heroName);
                convertView.setTag(viewHolder);
            }
            else {
                convertView = view;
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.Icon.setImageResource(hero.getHeroIcon());
            viewHolder.Name.setText(hero.getHeroName());

            return convertView;
        }

        private class ViewHolder {
            ImageView Icon;
            TextView Name;
        }
    }

    public void init_floatButton(){
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
}
