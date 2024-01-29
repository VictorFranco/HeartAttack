package mx.ipn.heartattack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

/**
 * Created by vfran_000 on 2018.
 */

public class Carousel extends Fragment implements View.OnClickListener{
    ViewFlipper vw;
    Button btn_start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_carousel, container, false);
        vw=(ViewFlipper)view.findViewById(R.id.img_switcher);
        vw.startFlipping();
        vw.setFlipInterval(2000);
        btn_start=(Button)view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm=getFragmentManager();
        fm.beginTransaction().replace(R.id.relative_start,new StartOptions()).commit();
    }
}