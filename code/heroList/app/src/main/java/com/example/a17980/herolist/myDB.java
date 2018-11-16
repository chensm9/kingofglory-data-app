package com.example.a17980.herolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class myDB {
    private static myDB instance = null;
    private String sqliePath;
    private SQLiteDatabase m_db;
    private Context context;

    static public myDB getInstance(Context context) {
        if(instance == null) {
            instance = new myDB(context);
        }
        return instance;
    }

    public myDB(Context context) {
        this.context = context;
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

        File dir = new File("data/data/" + context.getPackageName() + "/databases");

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
}
