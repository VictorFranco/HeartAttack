package mx.ipn.heartattack;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vfran_000 on 2018.
 */

public class Anatomy extends Fragment implements View.OnClickListener{
    LinearLayout th,t,c,gg;
    ImageView img_back;
    String titulo[];
    String titulo2[][];
    String cont[][];
    JSONObject obj;
    JSONObject o;
    JSONArray a;
    JSONArray h;
    Button btn[],btn2[][];
    int contador[];
    int identificador=0;
    boolean b;
    LinearLayout.LayoutParams layoutParams;
    int color_blue_light,color_blue_dark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_anatomy,container,false);
        th=(LinearLayout)view.findViewById(R.id.anatomy_home);
        gg=(LinearLayout)view.findViewById(R.id.anatomy_home);
        t=(LinearLayout)view.findViewById(R.id.anatomy_option);
        c=(LinearLayout)view.findViewById(R.id.anatomy_cont);
        img_back=(ImageView)view.findViewById(R.id.icon_back_anatomy);

        th.setVisibility(View.VISIBLE);
        t.setVisibility(View.GONE);
        img_back.setVisibility(View.GONE);
        c.setVisibility(View.GONE);

        color_blue_light=getResources().getColor(R.color.btn_inf_light);
        color_blue_dark=getResources().getColor(R.color.btn_inf_dark);

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 10, 10);

        img_back.setOnClickListener(this);
        Information info=new Information();
        info.execute();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.icon_back_anatomy){
            if(t.getVisibility()==View.VISIBLE){
                t.setVisibility(View.GONE);
                img_back.setVisibility(View.GONE);
                th.setVisibility(View.VISIBLE);
                ((LinearLayout)t).removeAllViews();
            }
            if(c.getVisibility()==View.VISIBLE){
                c.setVisibility(View.GONE);
                th.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
                ((LinearLayout)c).removeAllViews();
            }
        }
    }

    class Information extends AsyncTask<Void,Void,Void> implements View.OnClickListener,View.OnTouchListener{

        @Override
        protected Void doInBackground(Void... voids) {
            information_json();
            return null;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        protected void onPostExecute(Void aVoid) {
            btn=new Button[a.length()];
            for(int i=0;i<a.length();i++){
                btn[i]=new Button(new ContextThemeWrapper(getContext(), R.style.Btn_information),null,0);
                btn[i].setText(titulo[i]);
                btn[i].setId(-i);
                //btn[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ((LinearLayout)th).addView(btn[i],layoutParams);
                btn[i].setOnClickListener(this);
                btn[i].setOnTouchListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            for(int i=0;i<a.length();i++){
                if(btn[i].getId()==view.getId()){
                    identificador=i;
                    b=true;
                    th.setVisibility(View.GONE);
                    img_back.setVisibility(View.VISIBLE);
                    t.setVisibility(View.VISIBLE);
                    btn2= new Button[a.length()][contador[i]];
                    for (int j=0;j<contador[i];j++){
                        btn2[i][j]=new Button(new ContextThemeWrapper(getActivity(),R.style.Btn_information), null,0);
                        btn2[i][j].setText(titulo2[i][j]);
                        btn2[i][j].setId(i*100+j*1000000+1);
                  //      btn2[i][j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout)t).addView(btn2[i][j],layoutParams);
                        btn2[i][j].setOnClickListener(this);
               //         btn2[i][j].setOnTouchListener(this);
                    }
                }
            }
            for (int j = 0; j < contador[identificador]; j++) {
                if (btn2[identificador][j].getId() == view.getId()) {
                    t.setVisibility(View.GONE);
                    c.setVisibility(View.VISIBLE);
                    TextView txt[]=new TextView[2];
                    txt[0]=new TextView(new ContextThemeWrapper(getActivity(),R.style.TextView_Title),null,0);
                    txt[1]=new TextView(new ContextThemeWrapper(getActivity(),R.style.TextView_txt),null,0);
                    txt[0].setText(titulo2[identificador][j]);
                    txt[1].setText(cont[identificador][j]);
                    for(int i=0;i<2;i++) {
                        txt[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout) c).addView(txt[i],layoutParams);
                    }
                }
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent m) {
            for(int i=0;i<a.length();i++) {
                if (v.getId() == btn[i].getId()) {
                    switch (m.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn[i].setBackgroundColor(color_blue_light);
                            break;
                        case MotionEvent.ACTION_UP:
                            btn[i].setBackgroundColor(color_blue_dark);
                            break;
                    }
                }
            }/*
            for(int i=0;i<contador[identificador];i++){
                if(v.getId()==btn2[identificador][i].getId()){
                    switch (m.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn2[identificador][i].setBackgroundColor(color_blue_light);
                            break;
                        case MotionEvent.ACTION_UP:
                            btn2[identificador][i].setBackgroundColor(color_blue_dark);
                            break;
                    }
                }
            }*/
            return false;
        }
    }

    private void information_json() {
        String json=null;
        try {
            InputStream is=getActivity().getAssets().open("info.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer,"UTF-8");
            obj=new JSONObject(json);
            o=obj.getJSONObject("informacion");
            a=o.getJSONArray("inf");
            titulo=new String[a.length()];
            contador=new int[a.length()];
            for (int i=0;i<a.length();i++) {
                JSONObject w = a.getJSONObject(i);
                h=w.getJSONArray("temas");
            }
            titulo2=new String[a.length()][h.length()];
            cont=new String[a.length()][h.length()];
            for (int i=0;i<a.length();i++) {
                JSONObject w = a.getJSONObject(i);
                titulo[i] = w.getString("titulo");
                h=w.getJSONArray("temas");
                contador[i]=h.length();
                for (int j=0;j<h.length();j++){
                    JSONObject ww = h.getJSONObject(j);
                    titulo2[i][j] = ww.getString("titulo");
                    cont[i][j]=ww.getString("cont");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}