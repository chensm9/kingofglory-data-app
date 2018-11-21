package com.example.a17980.herolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Locale;

public class myDB {
    public static myDB instance = null;
    public String sqliePath;
    private SQLiteDatabase m_db;
    public myDB() {
        init_db();
    }

    static public myDB getInstance() {
        if(instance == null) {
            instance = new myDB();
        }
        return instance;
    }

    public void init_db() {
        try {
            sqliePath = CopySqliteFileFromRawToDatabases("kingofglory.db");
        }catch (IOException e) {
            System.out.print("error");
        }
        m_db = SQLiteDatabase.openDatabase(sqliePath, null, SQLiteDatabase.OPEN_READWRITE);

        String CREATE_EPIGRAPH_TABLE = "CREATE TABLE if not exists "
                + "user_epigraph"
                + " (_id INTEGER PRIMARY KEY, epigraph TEXT)";
        m_db.execSQL(CREATE_EPIGRAPH_TABLE);

        Cursor cursor = m_db.query("user_epigraph",
                new String[] {"_id"},
                null, null, null, null, null);
        if(cursor.getCount() == 0) {
            ContentValues cv = new ContentValues();
            cv.put("epigraph", "");
            m_db.insert("user_epigraph", null, cv);
        }
    }

    // 复制和加载区域数据库中的数据
    public String  CopySqliteFileFromRawToDatabases(String SqliteFileName) throws IOException {

        // 第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/<db_name>

        File dir = new File("data/data/com.example.a17980.herolist/kingofglory.db");

        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }

        File file = new File(dir, SqliteFileName);
        InputStream inputStream = null;
        OutputStream outputStream =null;

        //通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
        if (!file.exists()) {
            try {
                file.createNewFile();
                inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/" + SqliteFileName);
                outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len ;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer,0,len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return file.getPath();
    }

    // 获取指定颜色的所有铭文
    public void get_epigraph(List<String> s, String color) {
        Cursor cursor = m_db.query("rune",
                new String[] {"name"},
                "color=?", new String[] {color}, null, null, "level");
        while(cursor.moveToNext()) {
            s.add(cursor.getString(0));
        }
        cursor.close();
    }

    // 获取指定名称的铭文
    public byte[] get_epigraph(String name) {
        Cursor cursor = m_db.query("rune",
                new String[] {"rune_image"},
                "name=?", new String[] {name}, null, null, null);
        cursor.moveToFirst();
        byte[] img = cursor.getBlob(0);
        cursor.close();
        return img;
    }

    // 获取铭文等级
    public String get_epigraph_level(String name) {
        Cursor cursor = m_db.query("rune",
                new String[] {"level"},
                "name=?", new String[] {name}, null, null, null);
        cursor.moveToFirst();
        String level = cursor.getString(0);
        cursor.close();
        return level;
    }

    // 获取铭文属性
    public String get_epigraph_attr(String name) {
        Cursor cursor = m_db.query("rune",
                new String[] {"normalAttr"},
                "name=?", new String[] {name}, null, null, null);
        cursor.moveToFirst();
        String attr = cursor.getString(0);
        return attr;
    }

    // 获取铭文记录
    public String get_saved_epigraph() {
        Cursor cursor = m_db.query("user_epigraph",
                new String[] {"epigraph"},
                "_id=?", new String[] {"1"}, null, null, null);
        cursor.moveToFirst();
        String epigraph = cursor.getString(0);  //一定会有一个默认值
        return epigraph;
    }

    // 保存当前铭文
    public void save_epigraph(String json) {
        ContentValues cv = new ContentValues();
        cv.put("epigraph", json);
        String whereClause = "_id = ?";
        String[] whereArgs = {"1"};

        m_db.update("user_epigraph", cv, whereClause, whereArgs);
    }

    // 获取所有皮肤
    public void get_skin(List<Hero> data) {
        Cursor cursor = m_db.query("hero",
                new String[] {"name", "big_icon"},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(0);
            byte[] icon = cursor.getBlob(1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length);
            data.add(new Hero(bitmap, name));
        }
        cursor.close();
    }

    // 获取指定英雄皮肤
    public Bitmap get_one_skin(String name) {
        Cursor cursor = m_db.query("skin",
                new String[] {"skin_image"},
                "price=? and belongTo=?", new String[] {"初始皮肤", name}, null, null, null);
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] skin = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(skin, 0, skin.length);
        }
        cursor.close();
        return bitmap;
    }

    // 获取英雄昵称
    public String get_nick_name(String origin_name) {
        Cursor cursor = m_db.query("skin",
                new String[] {"name"},
                "belongTo=? and price=?", new String[] {origin_name,"初始皮肤"}, null, null, null);
        String nick_name = "";
        if(cursor.moveToFirst())
            nick_name = cursor.getString(0);
        cursor.close();
        return nick_name;
    }

    public String get_role(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"role"},
                "name=?", new String[] {name}, null, null, null);
        String role = "";
        if(cursor.moveToFirst())
            role = cursor.getString(0);
        cursor.close();
        return role;
    }

    // 获取技能名称和图标
    public void get_skill(String name, Map<String, Bitmap> skill_map) {
        Cursor cursor = m_db.query("skill",
                new String[]{"name", "skill_icon"},
                "belongTo=?", new String[]{name}, null, null, null);
        while (cursor.moveToNext()) {
            String skill_name = cursor.getString(0);
            byte[] skill_icon = cursor.getBlob(1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(skill_icon, 0, skill_icon.length);
            Matrix matrix = new Matrix();
            matrix.postScale((float) 3, (float) 3);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            skill_map.put(skill_name, bitmap);
        }
        cursor.close();
    }

    // 获取指定技能描述
    public String get_skill_description(String name) {
        Cursor cursor = m_db.query("skill",
                new String[] {"effect"},
                "name=?", new String[] {name}, null, null, null);
        String desc = "";
        if(cursor.moveToFirst()) {
            desc = cursor.getString(0);
        }
        cursor.close();
        return desc;
    }

    // 获取装备配置json
    public String get_collocation(String name) {
        Cursor cursor = m_db.query("collocation",
                new String[] {"collocation"},
                "belongTo=?", new String[] {name}, null, null, null);
        String collocation = "";
        if(cursor.moveToFirst())
            collocation = cursor.getString(0);
        cursor.close();
        return collocation;
    }

    // 获取装备图标
    public Bitmap get_equip_icon(String name) {
        Cursor cursor = m_db.query("equip",
                new String[] {"equip_icon"},
                "name=?", new String[] {name}, null, null, null);
        Bitmap bitmap = null;
        if(cursor.moveToFirst()) {
            byte[] equip_icon = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(equip_icon, 0, equip_icon.length);
            Matrix matrix = new Matrix();
            matrix.postScale((float)2.3, (float)2.3);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
        }
        cursor.close();
        return bitmap;
    }

    // 获取指定英雄的铭文装配方案
    public String get_runes(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"runes"},
                "name=?", new String[] {name}, null, null, null);
        String runes = "";
        if(cursor.moveToFirst())
            runes = cursor.getString(0);
        cursor.close();
        return runes;
    }

    // 获取技能用法
    public String get_skillCommend(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"use_skill"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    }

    // 获取克制方法
    public String get_againstCommend(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"against_skill"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    }

    // 获取团战技巧
    public String get_warCommend(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"melee_ideas"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    }

    // 获取生存指数
    public String get_survival(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"survival"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    }

    // 获取攻击指数
    public String get_attack(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"attack"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    }

    // 获取技能指数
    public String get_skill(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"skill"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    }

    // 获取难度指数
    public String get_difficulty(String name) {
        Cursor cursor = m_db.query("hero",
                new String[] {"difficulty"},
                "name=?", new String[] {name}, null, null, null);
        String data = "";
        if(cursor.moveToFirst())
            data = cursor.getString(0);
        cursor.close();
        return data;
    
    public List<EquipItem> get_equip_list(String type) {
        String sql = String.format("SELECT * FROM equip WHERE category = '%s' ORDER BY price", type);
        Cursor cursor = m_db.rawQuery(sql, null);

        List<EquipItem> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            EquipItem equip= new EquipItem();
            equip.setName(cursor.getString(cursor.getColumnIndex("name")));
            equip.setPrice(""+cursor.getInt(cursor.getColumnIndex("price")));
            byte[] data = cursor.getBlob(cursor.getColumnIndex("equip_icon"));
            Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
            equip.setImage(image);
            list.add(equip);
        }
        cursor.close();
        return list;
    }

    public EquipItem get_equip(String name) {
        String sql = String.format("SELECT * FROM equip WHERE name = '%s'", name);
        Cursor cursor = m_db.rawQuery(sql, null);
        EquipItem equip = new EquipItem();
        if (cursor.moveToFirst()) {
            equip.setName(cursor.getString(cursor.getColumnIndex("name")));
            equip.setPrice(""+cursor.getInt(cursor.getColumnIndex("price")));
            String base_attr = cursor.getString(cursor.getColumnIndex("baseAttr"));
            base_attr = base_attr.replace("{", "").replace("}", "");
            base_attr = base_attr.replace("\"", "").replace(", ", "\n");
            equip.setBase_attr(base_attr);
            String equip_skill = cursor.getString(cursor.getColumnIndex("equipSkill"));
            equip_skill = equip_skill.replace("[", "").replace("]", "");
            equip_skill = equip_skill.replace("\"", "").replace(", 唯一被动", "\n唯一被动");
            equip.setEquip_skill(equip_skill);
            byte[] data = cursor.getBlob(cursor.getColumnIndex("equip_icon"));
            Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
            equip.setImage(image);
        }
        return equip;
    }
}
