package com.example.a17980.herolist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DetailActivity extends Activity {

    ImageButton back;
    private myDB m_db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        init_back();

//        Intent intent = getIntent();
        m_db = myDB.getInstance();
//        String hero = (String)intent.getSerializableExtra("detail");
        String hero = "伽罗";
        if(hero != null) {
            TextView name =  findViewById(R.id.name);
            TextView nick_name = findViewById(R.id.title);
            TextView role = findViewById(R.id.type);
            RelativeLayout r = findViewById(R.id.top);
            LinearLayout skill_list = findViewById(R.id.skills);


            name.setText(hero);
            nick_name.setText(m_db.get_nick_name(hero));
            role.setText(m_db.get_role(hero));
            r.setBackground(new BitmapDrawable(m_db.get_one_skin(hero)));
            Map<String, Bitmap> skill_map = new HashMap<>();
            m_db.get_skill(hero, skill_map);
            Iterator it = skill_map.entrySet().iterator();
            for(int i = 0; i < skill_list.getChildCount(); i++) {
                if(i < skill_map.size()) {
                    Map.Entry e = (Map.Entry)it.next();
                    skill_list.getChildAt(i).setVisibility(View.VISIBLE);
                    skill_list.getChildAt(i).setTooltipText(e.getKey().toString());
                    skill_list.getChildAt(i).setBackground(new BitmapDrawable((Bitmap)e.getValue()));
                } else {
                    skill_list.getChildAt(i).setVisibility(View.GONE);
                }
            }
        }

    }

    public void init_back(){
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
