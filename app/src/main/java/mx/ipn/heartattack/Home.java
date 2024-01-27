package mx.ipn.heartattack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by vfran_000 on 2018.
 */

public class Home extends Fragment implements View.OnClickListener{
    TextView user_name;
    Button btn_heart,btn_stats,btn_questionnaires;
    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home,container,false);

        img=(ImageView)view.findViewById(R.id.img12);

        user_name=(TextView)view.findViewById(R.id.txt_user);
        String txt_user=User_Data.getUser();
        String welcome="Bienvenido: "+txt_user;
        user_name.setText(welcome);


        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.heart_in_the_body);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);

        img.setImageDrawable(roundedBitmapDrawable);

        btn_heart=(Button)view.findViewById(R.id.profile_btn1);
        btn_stats=(Button)view.findViewById(R.id.profile_btn2);
        btn_questionnaires=(Button)view.findViewById(R.id.profile_btn3);
        btn_heart.setOnClickListener(this);
        btn_stats.setOnClickListener(this);
        btn_questionnaires.setOnClickListener(this);
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
}
