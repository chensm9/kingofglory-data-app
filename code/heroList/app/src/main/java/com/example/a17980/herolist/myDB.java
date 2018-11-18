package com.example.a17980.herolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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

    public void get_epigraph(List<String> s, String color) {
        Cursor cursor = m_db.query("rune",
                new String[] {"name"},
                "color=?", new String[] {color}, null, null, "level");
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
        cursor.close();
        return level;
    }

    public String get_epigraph_attr(String name) {
        Cursor cursor = m_db.query("rune",
                new String[] {"normalAttr"},
                "name=?", new String[] {name}, null, null, null);
        cursor.moveToFirst();
        String attr = cursor.getString(0);
        return attr;
    }

    public String get_saved_epigraph() {
        Cursor cursor = m_db.query("user_epigraph",
                new String[] {"epigraph"},
                "_id=?", new String[] {"1"}, null, null, null);
        cursor.moveToFirst();
        String epigraph = cursor.getString(0);  //一定会有一个默认值
        return epigraph;
    }

    public void save_epigraph(String json) {
        ContentValues cv = new ContentValues();
        cv.put("epigraph", json);
        String whereClause = "_id = ?";
        String[] whereArgs = {"1"};

        m_db.update("user_epigraph", cv, whereClause, whereArgs);
    }
}
