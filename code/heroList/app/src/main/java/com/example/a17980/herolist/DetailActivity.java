package com.example.a17980.herolist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DetailActivity extends Activity {

    ImageButton back;
    private myDB m_db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        init_back();

        Intent intent = getIntent();
        m_db = myDB.getInstance();
        String hero = (String)intent.getSerializableExtra("detail");
//        String hero = "伽罗";
        if(!hero.equals("")) {
            TextView name =  findViewById(R.id.name);
            TextView nick_name = findViewById(R.id.title);
            TextView role = findViewById(R.id.type);
            final TextView desc_part = findViewById(R.id.description);
            LinearLayout evaluate = findViewById(R.id.info);
            ImageView skin = findViewById(R.id.skin);
            final ConstraintLayout skill_list = findViewById(R.id.skills);
            ConstraintLayout equip_list = findViewById(R.id.equipCommend);
            ConstraintLayout epigraph_list = findViewById(R.id.MingWen);
            TextView skillCommend = findViewById(R.id.skillCommend);
            TextView warCommend = findViewById(R.id.warCommend);
            TextView againstCommend = findViewById(R.id.againstCommend);
            TextView difficulty = findViewById(R.id.difficulty);
            TextView attack = findViewById(R.id.attack);
            TextView survival = findViewById(R.id.survival);
            TextView skill = findViewById(R.id.skill);

            name.setText(hero);
            name.setBackgroundColor(Color.argb(100, 246,246, 246));
            nick_name.setText(m_db.get_nick_name(hero));
            nick_name.setBackgroundColor(Color.argb(100, 246,246, 246));
            role.setText(m_db.get_role(hero));
            role.setBackgroundColor(Color.argb(100, 246,246, 246));
            evaluate.setBackgroundColor(Color.argb(100, 246,246, 246));
            difficulty.setText("难度 "+m_db.get_difficulty(hero));
            attack.setText("攻击 "+m_db.get_attack(hero));
            survival.setText("生存 "+m_db.get_survival(hero));
            skill.setText("技能 "+m_db.get_skill(hero));

            Bitmap bitmap = m_db.get_one_skin(hero);
            skin.setImageBitmap(bitmap);
            Map<String, Bitmap> skill_map = new HashMap<>();
            m_db.get_skill(hero, skill_map);
            Iterator it = skill_map.entrySet().iterator();
            final List<Bitmap> grey = new ArrayList<>();
            for(int i = 0; i < skill_list.getChildCount()/2; i++) {
                if(i < skill_map.size()) {
                    Map.Entry e = (Map.Entry)it.next();
                    skill_list.getChildAt(i).setVisibility(View.VISIBLE);
                    skill_list.getChildAt(i+6).setVisibility(View.VISIBLE);
//                    skill_list.getChildAt(i).setTooltipText(e.getKey().toString());
                    TextView t = (TextView) skill_list.getChildAt(i+6);
                    t.setText(e.getKey().toString());
                    ImageButton bt = (ImageButton)skill_list.getChildAt(i);
                    final Bitmap origin = (Bitmap)e.getValue();
                    grey.add(setBitmap(origin, 0,0,1));

//                    bt.setImageBitmap((Bitmap)e.getValue());
                    final String desc = m_db.get_skill_description(e.getKey().toString());
                    final int pos = i;
                    if(i == 0) {
                        desc_part.setText(desc);
                        bt.setBackground(new BitmapDrawable(origin));
                        bt.setTag("origin");
                    }
                    else {
                        bt.setBackground(new BitmapDrawable(grey.get(i)));
                        bt.setTag("grey");
                    }
                    bt.setOnClickListener(new View.OnClickListener(){
                      @Override
                      public void onClick(View view) {
                          desc_part.setText(desc);
                          if(view.getTag().equals("grey")) {
                              view.setTag("origin");
                              view.setBackground(new BitmapDrawable(origin));
                              for(int i = 0; i < grey.size(); i++) {
                                  if(i != skill_list.indexOfChild(view)) {
                                      skill_list.getChildAt(i).setTag("grey");
                                      skill_list.getChildAt(i).setBackground(new BitmapDrawable(grey.get(i)));
                                  }
                              }
                          }
                      }
                    });

                } else {
                    skill_list.getChildAt(i).setVisibility(View.GONE);
                    skill_list.getChildAt(i+6).setVisibility(View.GONE);
                }
            }
            String json = m_db.get_collocation(hero);
            try {
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i < 6; i++)  {
                    if(i < jsonArray.length()) {
                        String equip_name = jsonArray.getString(i);
                        equip_list.getChildAt(i).setBackground(new BitmapDrawable(m_db.get_equip_icon(equip_name)));
                        TextView t = (TextView) equip_list.getChildAt(i+6);
                        t.setText(equip_name);

                        equip_list.getChildAt(i).setVisibility(View.VISIBLE);
                        equip_list.getChildAt(i+6).setVisibility(View.VISIBLE);
                    } else {
                        equip_list.getChildAt(i).setVisibility(View.GONE);
                        equip_list.getChildAt(i+6).setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            json = m_db.get_runes(hero);
            try {
                JSONObject jsonObject = new JSONObject(json);
                Iterator p = jsonObject.keys();
                int i = 0;
                while(p.hasNext()) {
                    String type = p.next().toString();
                    String epigraph_name = jsonObject.getString(type);
                    TextView t = (TextView) epigraph_list.getChildAt(i+3);
                    t.setText(m_db.get_epigraph_level(epigraph_name)+":"+epigraph_name);

                    t = (TextView) epigraph_list.getChildAt(i+6);
                    t.setText("");
                    JSONObject jsonObject1 = new JSONObject(m_db.get_epigraph_attr(epigraph_name));
                    Iterator p1 = jsonObject1.keys();
                    while(p1.hasNext()) {
                        String attr_name = p1.next().toString();
                        String attr_count = jsonObject1.getString(attr_name);
                        String s = t.getText().toString()+attr_name+" "+attr_count;
                        if(p1.hasNext())
                            s += "\n";
                        t.setText(s);
                    }
                    byte[] icon = m_db.get_epigraph(epigraph_name);
                    bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length);
                    Matrix matrix = new Matrix();
                    matrix.postScale((float)0.5, (float)0.5);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
                    ImageView img = (ImageView)epigraph_list.getChildAt(i);
                    img.setImageBitmap(bitmap);
//                    epigraph_list.getChildAt(i).setBackground(new BitmapDrawable(bitmap));
                    i++;
                }

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            skillCommend.setText(m_db.get_skillCommend(hero));
            warCommend.setText(m_db.get_warCommend(hero));
            againstCommend.setText(m_db.get_againstCommend(hero));

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

    // 将图片置灰
    public Bitmap setBitmap(Bitmap btm,float mHue,float mStauration ,float mLum){
        ColorMatrix colorMatrix =new ColorMatrix();
        colorMatrix.setRotate(0, mHue);
        colorMatrix.setRotate(1, mHue);
        colorMatrix.setRotate(2, mHue);
        ColorMatrix colorMatrix1 =new ColorMatrix();
        colorMatrix1.setSaturation(mStauration);
        ColorMatrix colorMatrix2 =new ColorMatrix();
//        colorMatrix2.setScale(mLum, mLum, mLum, 1);
        colorMatrix2.setScale(1,1,1,1);
        ColorMatrix colorMatrixs =new ColorMatrix();
        colorMatrixs.postConcat(colorMatrix);
        colorMatrixs.postConcat(colorMatrix1);
        colorMatrixs.postConcat(colorMatrix2);
        Bitmap bitmap = Bitmap.createBitmap(btm.getWidth(), btm.getHeight(), Bitmap.Config.ARGB_8888);
        final Paint paint =new Paint();
        paint.setAntiAlias(true);
        Canvas canvas =new Canvas(bitmap);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrixs));
        canvas.drawBitmap(btm,0,0, paint);
        return bitmap;
    }
}
