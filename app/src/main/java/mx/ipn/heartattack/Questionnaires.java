package mx.ipn.heartattack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by vfran_000 on 2018.
 */

public class Questionnaires extends Fragment implements View.OnClickListener{
    Button btn_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_questionnaires, container, false);
        btn_save=(Button)view.findViewById(R.id.btn_questionnaires_save);
        btn_save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_questionnaires_save){

        }
    }
}
