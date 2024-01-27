package mx.ipn.heartattack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by vfran_000 on 2018.
 */

public class Anatomy extends Fragment{
    RelativeLayout t1,t2,t3,t4,t5,t6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_anatomy,container,false);
        return view;
    }
}
