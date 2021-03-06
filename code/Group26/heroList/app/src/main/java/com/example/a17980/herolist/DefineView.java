package com.example.a17980.herolist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class DefineView extends View {

    private int mWidth;
    private int mHeight;
    private Paint paint;
    private Bitmap bitmap;
    private String epigraph_type;
    private myDB m_db;
//    public DefineView(Context context) {
//        super(context);
//        init();
//    }

    public void setEpigraph(String Epigraph) {
        epigraph_type = Epigraph;
    }

    public String getEpigraph_type() {
        return epigraph_type;
    }

    public DefineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray type=context.obtainStyledAttributes(attrs, R.styleable.defineView);
        epigraph_type = type.getString(R.styleable.defineView_epigraph_type);
        init();
    }

    private void init() {
        if(epigraph_type.equals("blue")) {
            bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.blue);
        } else if(epigraph_type.equals("red")) {
            bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.red);
        } else if(epigraph_type.equals("green")){
            bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.green);
        } else {
            m_db = myDB.getInstance();
            byte[] b = m_db.get_epigraph(epigraph_type);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            Matrix matrix = new Matrix();
            matrix.postScale((float)2.5, (float)2.5);
            // 得到新的圖片
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
        }

        Matrix matrix = new Matrix();
        matrix.postScale((float)0.33, (float)0.33);
        // 得到新的圖片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        mWidth = getWidth()/10;
        mHeight = getHeight()/10;

        // 计算中心点

        double radian30 = 30 * Math.PI / 180;

        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(200);
        }

        Path path = new Path();
        path.moveTo(mWidth/2, 0);
        path.moveTo(mWidth, (float)(mWidth/2*Math.tan(radian30)));
        path.moveTo(mWidth, (float)(mHeight- mWidth/2*Math.tan(radian30)));
        path.moveTo(mWidth/2, mHeight);
        path.moveTo(0,(float)(mHeight- mWidth/2*Math.tan(radian30)));
        path.moveTo(0, (float)(mWidth/2*Math.tan(radian30)));
        path.close();

        canvas.drawPath(path, paint);

        Paint paintcontent = new Paint();
        Matrix matrix=new Matrix();
        canvas.drawBitmap(bitmap, matrix, paintcontent);
    }
}

