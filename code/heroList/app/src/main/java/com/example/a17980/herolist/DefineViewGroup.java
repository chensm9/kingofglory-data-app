package com.example.a17980.herolist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class DefineViewGroup extends ViewGroup {
    private static final int SPACE = 10;// view与view之间的间隔
    private int width;//屏幕宽度
    private int mlength;//边长,即为view 的宽度的一半
    private float mheigth;//为view的高度的一半
    private int viewWidth;
    private int viewHeight;

    public DefineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        // TODO Auto-generated method stub
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        mlength=(width-3*SPACE)/6;//边长
        double radian30 = 30 * Math.PI / 180;
        mheigth = (float) (mlength * Math.cos(radian30));//竖向一半的view
        viewWidth=mlength*2;
        viewHeight=(int) (mheigth*2);
        int factor = 3;
        View view0=getChildAt(0);
        View view1=getChildAt(1);
        View view2=getChildAt(2);
        View view3=getChildAt(3);
        View view4=getChildAt(4);
        View view5=getChildAt(5);
        View view6=getChildAt(6);
        View view7=getChildAt(7);
        View view8=getChildAt(8);
        View view9=getChildAt(9);

        View view10=getChildAt(10);
        View view11=getChildAt(11);
        View view12=getChildAt(12);
        View view13=getChildAt(13);
        View view14=getChildAt(14);
        View view15=getChildAt(15);
        View view16=getChildAt(16);
        View view17=getChildAt(17);
        View view18=getChildAt(18);
        View view19=getChildAt(19);

        View view20=getChildAt(20);
        View view21=getChildAt(21);
        View view22=getChildAt(22);
        View view23=getChildAt(23);
        View view24=getChildAt(24);
        View view25=getChildAt(25);
        View view26=getChildAt(26);
        View view27=getChildAt(27);
        View view28=getChildAt(28);
        View view29=getChildAt(29);

        view0.layout(SPACE+viewWidth/factor, 0, viewWidth*2/factor+SPACE, viewHeight/factor+SPACE);
        view1.layout(SPACE+viewWidth*2/factor, 0, viewWidth+SPACE, viewHeight/factor+SPACE);
        view2.layout(SPACE+viewWidth/factor/2, viewHeight/factor, viewWidth/2+SPACE, viewHeight*2/factor+SPACE);
        view3.layout(SPACE+viewWidth/factor/2+viewWidth/factor, viewHeight/factor, viewWidth*4/5+SPACE, viewHeight*7/10);
        view4.layout(SPACE, viewHeight*2/factor, viewWidth/factor+SPACE, viewHeight*11/10);
        view5.layout(SPACE+viewWidth/factor/2, viewHeight*3/factor, viewWidth/2+SPACE, viewHeight*14/10);
        view6.layout(SPACE, viewHeight*4/factor, viewWidth/3+SPACE, viewHeight*17/10);
        view7.layout(SPACE+viewWidth/factor/2, viewHeight*5/factor, viewWidth/2+SPACE, viewHeight*21/10);
        view8.layout(SPACE+viewWidth/factor, viewHeight*6/factor, viewWidth*13/20+SPACE, viewHeight*24/10);
        view9.layout(SPACE+viewWidth*2/factor, viewHeight*6/factor, viewWidth+SPACE, viewHeight*24/10);

        view10.layout(SPACE+ viewWidth*3/factor, viewHeight*2/factor, viewWidth*13/10+SPACE, viewHeight*11/10);
        view11.layout(SPACE+ viewWidth*4/factor, viewHeight*2/factor, viewWidth*17/10+SPACE, viewHeight*11/10);
        view12.layout(SPACE+ viewWidth*2/factor+viewWidth/2/factor, viewHeight*3/factor, viewWidth*23/20+SPACE, viewHeight*14/10);
        view13.layout(SPACE+ viewWidth*3/factor+viewWidth/2/factor, viewHeight*3/factor, viewWidth*3/2+SPACE, viewHeight*14/10);
        view14.layout(SPACE+ viewWidth*4/factor+viewWidth/2/factor, viewHeight*3/factor, viewWidth*18/10+SPACE, viewHeight*14/10);
        view15.layout(SPACE+ viewWidth*2/factor, viewHeight*4/factor, viewWidth+SPACE, viewHeight*18/10);
        view16.layout(SPACE+ viewWidth*3/factor, viewHeight*4/factor, viewWidth*13/10+SPACE, viewHeight*17/10);
        view17.layout(SPACE+ viewWidth*4/factor, viewHeight*4/factor, viewWidth*33/20+SPACE, viewHeight*17/10);
        view18.layout(SPACE+ viewWidth*5/factor, viewHeight*4/factor, viewWidth*2+SPACE, viewHeight*17/10);
        view19.layout(SPACE+ viewWidth*3/factor+viewWidth/2/factor, viewHeight*5/factor, viewWidth*15/10+SPACE, viewHeight*21/10);

        view20.layout(SPACE+viewWidth*5/factor, 0, viewWidth*2+SPACE, viewHeight*4/10);
        view21.layout(SPACE+viewWidth*6/factor, 0, viewWidth*23/10+SPACE, viewHeight*4/10);
        view22.layout(SPACE+viewWidth*5/factor+viewWidth/2/factor, viewHeight/factor, viewWidth*22/10+SPACE, viewHeight*7/10);
        view23.layout(SPACE+viewWidth*6/factor+viewWidth/2/factor, viewHeight/factor, viewWidth*5/2+SPACE, viewHeight*7/10);
        view24.layout(SPACE+viewWidth*7/factor, viewHeight*2/factor, viewWidth*27/10+SPACE, viewHeight*21/20);
        view25.layout(SPACE+viewWidth*6/factor+viewWidth/2/factor, viewHeight*3/factor, viewWidth*25/10+SPACE, viewHeight*27/20);
        view26.layout(SPACE+viewWidth*7/factor, viewHeight*4/factor, viewWidth*27/10+SPACE, viewHeight*17/10);
        view27.layout(SPACE+viewWidth*6/factor+viewWidth/2/factor, viewHeight*5/factor, viewWidth*25/10+SPACE, viewHeight*41/20);
        view28.layout(SPACE+viewWidth*5/factor, viewHeight*6/factor, viewWidth*2+SPACE, viewHeight*24/10);
        view29.layout(SPACE+viewWidth*6/factor, viewHeight*6/factor, viewWidth*23/10+SPACE, viewHeight*24/10);

        // l t 定义位置 r b 定义边界，如果不能正确显示，增大r b



    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        //设置View 的高宽
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(viewWidth,viewHeight);
        }
    }

}
