package com.example.a17980.herolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

    GridView gridView;
    GridViewAdapter adapter;
    FloatingActionButton floatingActionButton;
    ImageButton imageButton;
    RadioGroup type1;
    RadioGroup type2;
    boolean num;
    myDB m_db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        m_db = myDB.getInstance();
        //gridView
        gridView = findViewById(R.id.gridView);
        adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);

        //悬浮按钮
        init_floatButton();

        //搜索按钮
        init_search();

        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        num = true;
        //第一个RadioGroup
        type1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (num) {
                    num = false;
                    type2.clearCheck();
                    //...
                }
                num = true;
            }
        });
        //第二个RadioGroup
        type2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (num) {
                    num = false;
                    type1.clearCheck();
                    //...
                }
                num = true;
            }
        });
    }

    //适配器
    public class GridViewAdapter extends BaseAdapter {
        private List<Hero> Data;
        GridViewAdapter(){
            Data = new ArrayList<>();
            m_db.get_skin(Data);
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

            viewHolder.Icon.setImageBitmap(hero.getHeroIcon());
            viewHolder.Name.setText(hero.getHeroName());

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("detail", adapter.Data.get(position).getHeroName());
                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            });

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                    builder.setTitle("提示").setMessage("确定删除" +
                            adapter.getItem(position).getHeroName() + "?").setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Data.remove(position);
                                    notifyDataSetChanged();
                                }
                            }).setNegativeButton("取消", null).create().show();
                    return true;
                }
            });

            return convertView;
        }

        private class ViewHolder {
            ImageView Icon;
            TextView Name;
        }
    }

    //悬浮按钮
    public void init_floatButton(){
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("请输入添加英雄的名称：");
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog,null);
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //如果数据库中有该英雄则成功添加，否则失败
                        if (true){
                            Toast.makeText(ListActivity.this, "该英雄不存在", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //...
                            Toast.makeText(ListActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
    }

    public void init_search(){
        imageButton = findViewById(R.id.search_image);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("请输入搜索英雄的名称：");
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog,null);
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //如果英雄在列表中则成功添加，否则失败
                        if (true){
                            Toast.makeText(ListActivity.this, "该英雄不存在", Toast.LENGTH_SHORT).show();
                        }
                        else{

                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
    }
}
