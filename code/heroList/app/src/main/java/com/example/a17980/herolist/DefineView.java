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
    private int centreX;
    private int centreY;
    private int mLenght;
    private Paint paint;
    private Bitmap bitmap;
    private String epigraph_type;
    public DefineView(Context context) {
        super(context);
        init();
    }

    public void setEpigraph(String Epigraph) {
        epigraph_type = Epigraph;
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
            bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.d);
        }

        Matrix matrix = new Matrix();
        matrix.postScale((float)0.33, (float)0.33);
        // 得到新的圖片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
        bitmap = newbm;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        mWidth = getWidth()/10;
        mHeight = getHeight()/10;

        // 计算中心点
        centreX = mWidth / 2;
        centreY = mHeight / 2;

        mLenght = mWidth / 2;

        double radian30 = 30 * Math.PI / 180;

        if (null == paint) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
//            paint.setColor(Color.parseColor(paintcolor));
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float start = 1.0f;
//        float end = 0.92f;
//        Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//        Animation endAnimation = new ScaleAnimation(end, start, end, start,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//        scaleAnimation.setDuration(100);
//        scaleAnimation.setFillAfter(true);
//        endAnimation.setDuration(100);
//        endAnimation.setFillAfter(true);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //判断的方法实际上就是点击的点 a(x,y) 与View的中心点c(x,y)之间距离组成的正方形的面积dist
//                //与View内切园面积比较大小
//                float a = getWidth();
//                float b  = getHeight();
//                float c = event.getX();
//                float d = event.getY();
//                float e = this.getLeft();
//                float edgeLength = ((float) getWidth()) / 2/3;
//                float radiusSquare = edgeLength * edgeLength * 3 / 4;//内切圆半径的平方，内切园面积
//                float dist = (event.getX() - getWidth()/3 / 2)
//                        *      (event.getX() - getWidth()/3 / 2)
//                        +      (event.getY() - getHeight()/3 / 2)
//                        *      (event.getY() - getHeight()/3 / 2);
//                if (dist <= radiusSquare) {// 点中六边形区域
//                    paint.setAlpha(200);
//                    this.startAnimation(scaleAnimation);
//                    invalidate();
//                }
//
//                break;
//
//            case MotionEvent.ACTION_UP:
//                paint.setAlpha(200);
//                this.startAnimation(endAnimation);
//                invalidate();
//                break;
//            // 滑动出去不会调用action_up,调用action_cancel
//            case MotionEvent.ACTION_CANCEL:
//                this.startAnimation(endAnimation);
//                invalidate();
//                break;
//        }
//        return true;
//    }

}

