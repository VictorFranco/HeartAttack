package mx.ipn.heartattack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by vfran_000 on 2018.
 */

public class StartOptions extends Fragment implements View.OnClickListener{
    Button btn_login,btn_registry;
    Intent login,registry;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_start_options, container, false);
        btn_login=(Button)view.findViewById(R.id.btn_option_login);
        btn_registry=(Button)view.findViewById(R.id.btn_option_registry);
        btn_login.setOnClickListener(this);
        btn_registry.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_option_login){
            login=new Intent(getContext(),LogIn.class);
            startActivity(login);
        }
        if(v.getId()==R.id.btn_option_registry){
            registry=new Intent(getContext(),RegisterUser.class);
            startActivity(registry);
        }
    }
}