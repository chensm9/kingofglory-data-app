package com.example.a17980.herolist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
        m_db = SQLiteDatabase.openDatabase(sqliePath, null, SQLiteDatabase.OPEN_READONLY);
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

    public void get_epigraph(List<String> s, String color) {
        Cursor cursor = m_db.query("rune",
                new String[] {"name"},
                "color=?", new String[] {color}, null, null, null);
        while(cursor.moveToNext()) {
            s.add(cursor.getString(0));
        }
        cursor.close();
    }

    public byte[] get_epigraph(String name) {
        Cursor cursor = m_db.query("rune",
                new String[] {"rune_image"},
                "name=?", new String[] {name}, null, null, null);
        cursor.moveToFirst();
        byte[] img = cursor.getBlob(0);
        cursor.close();
        return img;
    }

    public String get_epigraph_level(String name) {
        Cursor cursor = m_db.query("rune",
                new String[] {"level"},
                "name=?", new String[] {name}, null, null, null);
        cursor.moveToFirst();
        String level = cursor.getString(0);
        if(level.equals("一级")) {
            level = "1级";
        } else if(level .equals("二级")) {
            level = "2级";
        } else if(level.equals("三级")) {
            level = "3级";
        } else if(level.equals("四级")) {
            level = "4级";
        } else {
            level = "5级";
        }
        cursor.close();
        return level;
    }

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
