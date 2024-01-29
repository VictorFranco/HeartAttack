package mx.ipn.heartattack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vfran_000 on 2018.
 */

public class Questionnaires extends Fragment implements View.OnClickListener{
    Button btn_save,btn_quest1,btn_quest2;
    SQLiteDatabase db;
    Cursor cursor;
    RadioGroup radioGroup[];
    int r;
    TextView pregunta[];
    View ln;
    String busqueda,buscar,you_had,good;
    int quest=0, color_ok,color_fail;
    ImageView back;
    RelativeLayout option,rl_questions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_questionnaires, container, false);
        btn_save=(Button)view.findViewById(R.id.btn_questionnaires_save);
        btn_save.setOnClickListener(this);
        ln=(LinearLayout)view.findViewById(R.id.prueba);
        option=(RelativeLayout)view.findViewById(R.id.relative_quest_options);
        rl_questions=(RelativeLayout)view.findViewById(R.id.relative_quest);
        btn_quest1=(Button)view.findViewById(R.id.btn_quest_1);
        btn_quest2=(Button)view.findViewById(R.id.btn_quest_2);
        back=(ImageView)view.findViewById(R.id.icon_back_question);
        you_had=getResources().getString(R.string.txt_you_had);
        good=getResources().getString(R.string.txt_good);
        option.setVisibility(View.VISIBLE);
        rl_questions.setVisibility(View.GONE);
        btn_quest1.setOnClickListener(this);
        btn_quest2.setOnClickListener(this);
        back.setOnClickListener(this);
        color_ok=getResources().getColor(R.color.green);
        color_fail=getResources().getColor(R.color.colorAccent);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.icon_back_question){
            FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.relative_user_interface,new Questionnaires()).commit();
        }
        if (v.getId()==R.id.btn_quest_1||v.getId()==R.id.btn_quest_2){
            option.setVisibility(View.GONE);
            rl_questions.setVisibility(View.VISIBLE);
            if (v.getId()==R.id.btn_quest_1) {
                busqueda = "select*from questions where questionary=1";
                quest=1;
            }
            if (v.getId()==R.id.btn_quest_2){
                busqueda = "select*from questions where questionary=2";
                quest=2;
            }
            db=getActivity().openOrCreateDatabase("database.sql",MODE_PRIVATE,null);
            cursor=db.rawQuery(busqueda,null);
            r=0;
            while(cursor.moveToNext()){
                r++;
            }
            pregunta=new TextView[r];

            radioGroup=new RadioGroup[r];
            RadioButton h1[]=new RadioButton[r];
            RadioButton h2[]=new RadioButton[r];
            RadioButton h3[]=new RadioButton[r];
            RadioButton h4[]=new RadioButton[r];

            String p[]=new String[r];
            String r1[]=new String[r];
            String r2[]=new String[r];
            String r3[]=new String[r];
            String r4[]=new String[r];
            RadioGroup jj[]=new RadioGroup[r];
            cursor=db.rawQuery(busqueda,null);
            int o=0;
            while(cursor.moveToNext()){
                p[o]=cursor.getString(cursor.getColumnIndex("question"));
                r1[o]=cursor.getString(cursor.getColumnIndex("r1"));
                r2[o]=cursor.getString(cursor.getColumnIndex("r2"));
                r3[o]=cursor.getString(cursor.getColumnIndex("r3"));
                r4[o]=cursor.getString(cursor.getColumnIndex("r4"));

                radioGroup[o]= new RadioGroup(getContext());

                pregunta[o]= new TextView(getContext());
                String txt_pregunta=(o+1)+".- "+p[o];
                pregunta[o].setText(txt_pregunta);
                pregunta[o].setId(o);
                pregunta[o].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ((LinearLayout)ln).addView(pregunta[o]);

                int uno=1,dos=2,tres=3,cuatro=4;
                h1[o]=new RadioButton(getContext());
                h1[o].setText(r1[o]);
                h1[o].setId(uno);
                if(!r1[o].equals("")) {
                    radioGroup[o].addView(h1[o]);
                }
                h2[o]=new RadioButton(getContext());
                h2[o].setText(r2[o]);
                h2[o].setId(dos);
                if(!r2[o].equals("")) {
                    radioGroup[o].addView(h2[o]);
                }
                h3[o]=new RadioButton(getContext());
                h3[o].setText(r3[o]);
                h3[o].setId(tres);
                if(!r3[o].equals("")) {
                    radioGroup[o].addView(h3[o]);
                }
                h4[o]=new RadioButton(getContext());
                h4[o].setText(r4[o]);
                h4[o].setId(cuatro);
                if(!r4[o].equals("")) {
                    radioGroup[o].addView(h4[o]);
                }
                radioGroup[o].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ((LinearLayout)ln).addView(radioGroup[o]);
                o++;
            }

            db.close();

        }
        if (v.getId()==R.id.btn_questionnaires_save){
            int rr[]=new int[r];
            for (int i=0;i<r;i++){
                rr[i]=radioGroup[i].getCheckedRadioButtonId();
            }
            db=getActivity().openOrCreateDatabase("database.sql",MODE_PRIVATE,null);
            if(quest==1){
                buscar="select r from questions where questionary=1";
            }
            if(quest==2){
                buscar="select r from questions where questionary=2";
            }
            cursor=db.rawQuery(buscar,null);
            int contador=0;
            int correctas=0;
            while (cursor.moveToNext()){
                int validador=cursor.getInt(cursor.getColumnIndex("r"));
                if(validador==rr[contador]){
                    correctas++;
                    pregunta[contador].setTextColor(color_ok);
                }
                else{
                    pregunta[contador].setTextColor(color_fail);
                }
                contador++;
            }
            int co[]=new int[2];
            buscar="select quest1,quest2 from users where user='"+User_Data.getUser()+"';";
            cursor=db.rawQuery(buscar,null);
            while(cursor.moveToNext()){
                co[0]=cursor.getInt(cursor.getColumnIndex("quest1"));
                co[1]=cursor.getInt(cursor.getColumnIndex("quest2"));
            }
            if(quest==1) {
                String avance="update users set quest1="+correctas+" where user='"+User_Data.getUser()+"';";
                db.execSQL(avance);
                Toast.makeText(getContext(),you_had+correctas+good,Toast.LENGTH_LONG).show();
                co[0] = correctas;
            }
            if(quest==2) {
                String avance="update users set quest2="+correctas+" where user='"+User_Data.getUser()+"';";
                db.execSQL(avance);
                Toast.makeText(getContext(),you_had+" "+correctas+" "+good,Toast.LENGTH_LONG).show();
                co[1] = correctas;
            }
            User_Data.setQuestionarios(co);
            db.close();
        }
    }
}
