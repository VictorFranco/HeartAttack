package mx.ipn.heartattack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by vfran_000 on 2018.
 */

public class Stats extends Fragment {
    String txt1,txt2,txt3,txt4,txt5;
    int color_verde,color_rosa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_stats, container, false);
        LinearLayout linearLayout=(LinearLayout) view.findViewById(R.id.stats);
        linearLayout.addView(new Vista(getActivity()));
        txt1=getResources().getString(R.string.txt1);
        txt2=getResources().getString(R.string.txt2);
        txt3=getResources().getString(R.string.txt3);
        txt4=getResources().getString(R.string.txt4);
        txt5=getResources().getString(R.string.txt5);
        color_verde=getResources().getColor(R.color.green);
        color_rosa=getResources().getColor(R.color.colorAccent);
        return view;
    }

    class Vista extends View{
        Paint paint=new Paint();
        public Vista(Context context) {
            super(context);
        }
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width=MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width,width*11/10);
        }
        public void onDraw(Canvas canvas){
            DisplayMetrics metrics=this.getResources().getDisplayMetrics();
            float wight=metrics.widthPixels;
            float txt_size= metrics.density/1.5f;
            float rotate=-90f;
            int tam[]=User_Data.getQuestionarios();
            int top1=(int)(wight*19/24+(-tam[0])*wight*3/40);
            int top2=(int)(wight*19/24+(-tam[1])*wight*3/40);
            Rect rect;
            if(tam[0]>5){
                paint.setColor(color_verde);
            }else {
                paint.setColor(color_rosa);
            }
            rect=new Rect((int)wight/6,top1,(int) wight*1/3,(int) wight*19/24);
            canvas.drawRect(rect,paint);
            if(tam[1]>5){
                paint.setColor(color_verde);
            }else {
                paint.setColor(color_rosa);
            }
            rect=new Rect((int) wight*3/8,top2,(int)wight*13/24,(int) wight*19/24);
            canvas.drawRect(rect,paint);
            paint.setColor(Color.BLACK);
            canvas.drawLine(wight/24,wight*13/16,wight*5/6,wight*13/16,paint);
            canvas.drawLine(wight*7/48,0,wight*7/48,wight*7/8,paint);

            paint.setTextSize(20*txt_size);
            for (int i=1;i<11;i++){
                canvas.drawText(Integer.toString(i),wight*3/32,wight*13/16-wight*119/1600*i,paint);
            }
            paint.setTextSize(30*txt_size);
            canvas.save();
            canvas.rotate(rotate,wight/16f,wight/2);
            canvas.drawText(txt1,wight/16,wight/2,paint);
            canvas.restore();
            paint.setTextSize(20*txt_size);
            canvas.save();
            canvas.rotate(rotate,wight*25/96,wight*35/32);
            canvas.drawText(txt2,wight*25/96,wight*35/32,paint);
            canvas.restore();
            canvas.save();
            canvas.rotate(rotate,wight*15/32,wight*35/32);
            canvas.drawText(txt3,wight*15/32,wight*35/32,paint);
            canvas.restore();
            paint.setColor(color_verde);
            rect=new Rect((int)wight*7/12,(int)wight*85/96,(int)wight*5/8,(int)wight*29/32);
            canvas.drawRect(rect,paint);
            paint.setColor(color_rosa);
            rect=new Rect((int)wight*7/12,(int)wight*15/16,(int)wight*5/8,(int)wight*23/24);
            canvas.drawRect(rect,paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(txt4,wight*5/8,wight*29/32,paint);
            canvas.drawText(txt5,wight*5/8,wight*23/24,paint);
        }
    }
}