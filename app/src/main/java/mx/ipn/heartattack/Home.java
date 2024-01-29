package mx.ipn.heartattack;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by vfran_000 on 2018.
 */

public class Home extends Fragment implements View.OnClickListener,View.OnTouchListener{
    TextView user_name;
    Button btn_options[];
    ImageView img;
    String wellcome;
    int color_green,color_green_dark;
    ScrollView scrollView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home,container,false);

        img=(ImageView)view.findViewById(R.id.img12);

        color_green=getResources().getColor(R.color.green_light);
        color_green_dark=getResources().getColor(R.color.green_dark);
        wellcome=getResources().getString(R.string.txt_welcome);

        user_name=(TextView)view.findViewById(R.id.txt_user);
        String txt_user=User_Data.getUser();
        String welcome=wellcome+" "+txt_user;
        user_name.setText(welcome);


        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.heart_in_the_body);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);

        img.setImageDrawable(roundedBitmapDrawable);
        btn_options=new Button[3];
        scrollView=(ScrollView)view.findViewById(R.id.scroll_home);
        btn_options[0]=(Button)view.findViewById(R.id.profile_btn1);
        btn_options[1]=(Button)view.findViewById(R.id.profile_btn2);
        btn_options[2]=(Button)view.findViewById(R.id.profile_btn3);
        btn_options[0].setOnTouchListener(this);
        btn_options[0].setOnClickListener(this);
        btn_options[1].setOnTouchListener(this);
        btn_options[1].setOnClickListener(this);
        btn_options[2].setOnTouchListener(this);
        btn_options[2].setOnClickListener(this);
        scrollView.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profile_btn1){
            FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.relative_user_interface,new Heart()).commit();
        }
        if(v.getId()==R.id.profile_btn2){
            FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.relative_user_interface,new Stats()).commit();
        }
        if(v.getId()==R.id.profile_btn3){
            FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.relative_user_interface,new Questionnaires()).commit();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent m) {
        int op=10;
        switch (v.getId()){
            case R.id.profile_btn1:
                op=0;
                break;
            case R.id.profile_btn2:
                op=1;
                break;
            case R.id.profile_btn3:
                op=2;
                break;
            case R.id.scroll_home:
                op=3;
                break;
        }
        if(op==0||op==1||op==2) {
            switch (m.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    btn_options[op].setBackgroundColor(color_green_dark);
                    break;
                case MotionEvent.ACTION_UP:
                    btn_options[op].setBackgroundColor(color_green);
                    break;
            }
        }
        if(op==3&&m.getAction()==MotionEvent.ACTION_MOVE) {
            for (int i=0;i<3;i++){
                btn_options[i].setBackgroundColor(color_green);
            }
        }
        return false;
    }
}