package mx.ipn.heartattack;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vfran_000 on 2018.
 */

public class StartOptions extends Fragment implements View.OnClickListener,View.OnTouchListener{
    Button btn_option_login,btn_option_registry;
    SQLiteDatabase db;
    Button btn_login,btn_registry;
    EditText name,user,email,age,password;
    String roger_that,wellcome,existing_user;
    String txt[],txt_login[],it_doesnt_match,sintaxis;
    Cursor cursor;
    int color_purple,color_purple_dark;
    ScrollView scrollview_login,scrollview_register;
    int num_campos;
    String progress_dialog,json_return;
    int w[];
    AlertDialog.Builder alert_option;
    AlertDialog alertDialog;
    String sinConexion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_start_options, container, false);
        btn_option_login=(Button)view.findViewById(R.id.btn_option_login);
        btn_option_registry=(Button)view.findViewById(R.id.btn_option_registry);
        color_purple=getResources().getColor(R.color.colorPrimary);
        color_purple_dark=getResources().getColor(R.color.color_purple_dark);
        btn_option_login.setOnClickListener(this);
        btn_option_registry.setOnClickListener(this);

        txt=new String[6];
        w=new int[2];
        json_return="";
        sinConexion="";
        w[0]=0;
        w[1]=0;
        num_campos=0;
        roger_that=getResources().getString(R.string.txt_roger_that);
        it_doesnt_match=getResources().getString(R.string.txt_it_doesnt_match);
        sintaxis=getResources().getString(R.string.txt_sintaxis);
        wellcome=getResources().getString(R.string.txt_welcome);
        sintaxis=getString(R.string.txt_sintaxis);
        existing_user=getResources().getString(R.string.txt_existing_user);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onClick(View v) {
        View obtener;
        alert_option=new AlertDialog.Builder(getContext());
        switch (v.getId()){
            case R.id.btn_option_login:
                obtener=getLayoutInflater().inflate(R.layout.activity_log_in,null);
                scrollview_login=(ScrollView) obtener.findViewById(R.id.scroll_login);
                btn_login=(Button)obtener.findViewById(R.id.btn_login);
                user=(EditText)obtener.findViewById(R.id.login_user);
                password=(EditText)obtener.findViewById(R.id.login_password);
                btn_login.setOnTouchListener(this);
                btn_login.setOnClickListener(this);
                scrollview_login.setOnTouchListener(this);
                alert_option.setView(obtener);
                alertDialog=alert_option.create();
                alertDialog.show();
                break;
            case R.id.btn_option_registry:
                obtener=getLayoutInflater().inflate(R.layout.activity_register_user,null);
                scrollview_register=(ScrollView)obtener.findViewById(R.id.scroll_register);
                name=(EditText)obtener.findViewById(R.id.registry_name);
                user=(EditText)obtener.findViewById(R.id.registry_user);
                email=(EditText)obtener.findViewById(R.id.registry_email);
                age=(EditText)obtener.findViewById(R.id.registry_age);
                password=(EditText)obtener.findViewById(R.id.registry_password);
                btn_registry=(Button)obtener.findViewById(R.id.btn_registry);
                btn_registry.setOnTouchListener(this);
                btn_registry.setOnClickListener(this);
                scrollview_register.setOnTouchListener(this);
                alert_option.setView(obtener);
                alertDialog=alert_option.create();
                alertDialog.show();
                break;
            case R.id.btn_login:
                try {
                    num_campos=2;
                    txt[0] = user.getText().toString();
                    txt[1] = password.getText().toString();

                    validar();
                    if (validar()){
                            progress_dialog="Buscando";
                            Get_in get_in=new Get_in();
                            get_in.execute(1);
                    } else {
                        Toast.makeText(getContext(),sintaxis , Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_registry:
                try {
                    num_campos=5;

                    txt[0] = name.getText().toString();
                    txt[1] = user.getText().toString();
                    txt[2] = email.getText().toString();
                    txt[3] = age.getText().toString();
                    txt[4] = password.getText().toString();

                    validar();
                    if (!txt[0].equals("") && !txt[1].equals("") && !txt[2].equals("") && !txt[3].equals("")&& !txt[4].equals("")&&validar()) {
                        progress_dialog="Registrando";
                        Get_in get_in = new Get_in();
                        get_in.execute(2);
                    } else {
                        Toast.makeText(getContext(),sintaxis,Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent m) {
        Button btn_touch=new Button(getContext());
        int op=0;
        switch (v.getId()){
            case R.id.btn_login:
                btn_touch=btn_login;
                op=1;
                break;
            case R.id.btn_registry:
                btn_touch=btn_registry;
                op=1;
                break;
            case R.id.scroll_login:
                op=2;
                break;
            case R.id.scroll_register:
                op=3;
                break;
        }
        if(op==1) {
            switch (m.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    btn_touch.setBackgroundColor(color_purple_dark);
                    break;
                case MotionEvent.ACTION_UP:
                    btn_touch.setBackgroundColor(color_purple);
                    break;
            }
        }
        if(op==2&&m.getAction()==MotionEvent.ACTION_MOVE){
            btn_login.setBackgroundColor(color_purple);
        }
        if(op==3&&m.getAction()==MotionEvent.ACTION_MOVE){
            btn_registry.setBackgroundColor(color_purple);
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    class Get_in extends AsyncTask<Integer, Void, Boolean> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(progress_dialog);
            progressDialog.setCancelable(false);
            progressDialog.show();
            db = getActivity().openOrCreateDatabase("database.sql", MODE_PRIVATE, null);
            createTable();
        }

        @Override
        protected Boolean doInBackground(Integer... i) {
            Boolean resp=false;
            try {
                if(connectivity()) {
                    if (i[0] == 1 && findWebUser()) {
                        if (searchSqlUser()) {
                            resp = updateData();
                        } else {
                            resp = insertData();
                        }
                    }
                    if (i[0] == 2 && registerWebUser()) {
                        resp = insertData();
                    }
                }else{
                    if(i[0]==1){
                        if(search()){
                            sinConexion="Sin conexion tendras opciones limitadas";
                        }else {
                            json_return="No tienes conexion";
                            sinConexion="Debes haber iniciado sesion previamente para acceder";
                        }
                        resp=search();
                    }
                    if(i[0]==2){
                        resp=false;
                        json_return="No tienes conexion";
                    }
                }
            }catch (Exception e){
                resp= false;
            }
            // return resp;
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            progressDialog.dismiss();
            alertDialog.dismiss();
            db.close();
            if(b) {
                User_Data.setName(txt[0]);
                User_Data.setUser(txt[1]);
                User_Data.setEmail(txt[2]);
                User_Data.setPassword(txt[4]);
                User_Data.setQuestionarios(w);
                User_Data.set_id(txt[5]);
                Toast.makeText(getContext(), wellcome + " " + User_Data.getUser(), Toast.LENGTH_LONG).show();
                Intent menu = new Intent(getContext(), Profile.class);
                startActivity(menu);
            }else {
                Toast.makeText(getContext(),json_return,Toast.LENGTH_SHORT).show();
                if(sinConexion!=""){
                    Toast.makeText(getContext(),sinConexion,Toast.LENGTH_LONG).show();
                    sinConexion="";
                }
            }
        }
    }

    private Boolean validar() {
        Boolean data_user_comillas[] = new Boolean[num_campos];
        Boolean data_user_espacios[] = new Boolean[num_campos];
        int vacio[] = new int[num_campos];
        for (int i=0;i<num_campos;i++){
            data_user_comillas[i]=false;
            data_user_espacios[i]=false;
        }
        for (int j = 0; j < num_campos; j++) {
            for (int i = 0; i < txt[j].length(); i++) {
                if (txt[j].charAt(i) == '"') {
                    data_user_comillas[j] = true;
                }
                if (txt[j].charAt(i) == ' ') {
                    vacio[j]++;
                }
                if (vacio[j] == txt[j].length()) {
                    data_user_espacios[j] = true;
                }
            }
        }
        Boolean esp = false, com = false;
        for (int i = 0; i < num_campos; i++) {
            if (data_user_comillas[i]) {
                com = true;
            }
            if (data_user_espacios[i]) {
                esp = true;
            }
        }
        if (com.equals(true) || esp.equals(true)) {
            return false;
        } else {
            return true;
        }
    }

    public void createTable() {
        try {
            String table, prueba, quest;
            table = "CREATE TABLE IF NOT EXISTS users( _id INTEGER PRIMARY KEY AUTOINCREMENT, id_web TEXT, name TEXT, user TEXT, email TEXT, password TEXT, quest1 INTEGER,quest2 INTEGER);";
            db.execSQL(table);
            table = "CREATE TABLE IF NOT EXISTS questions( _id INTEGER PRIMARY KEY AUTOINCREMENT,questionary INTEGER,question TEXT,r1 TEXT,r2 TEXT,r3 TEXT,r4 TEXT,r INTEGER);";
            db.execSQL(table);
            prueba = "select * from questions";
            cursor = db.rawQuery(prueba, null);
            Boolean prueb = false;
            while (cursor.moveToNext()) {
                prueb = true;
            }
            if (!prueb) {
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'¿Cuál es la función principal del corazón?','Bombear sangre alrededor del cuerpo','Llevar oxígeno al cerebro','Obtener nutrientes','Segregar sustancias',1)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'¿Qué tamaño tiene el corazón?','El de un perro','El de un puño','El de una canica ','El de una cabeza',2)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'¿En cuantas cámaras esta dividido el corazón?','En seis cámaras','En dos cámaras','No hay cámaras','En cuatro cámaras',4)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'La tricúspide es la válvula de...','La derecha','La izquierda','','',1)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'El corazón está situado entre los pulmones, a la izquierda del tórax, apoyado sobre el diafragma y detrás del esternón.','Verdadero','Falso','','',1)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'¿Cuántas auriculas tiene el corazón?','Una','Cinco','Dos','Tres',3)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'¿Cómo se llama la masa muscular que compone al corazón?','Miocardio','Pericardio','Heliocardio','Ventricular',1)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'El tejido muscular de tipo cardíaco se caracteriza por...','Funcionar de manera voluntaria','Funcionar de manera automática','No funcionar','Funcionar en ciertos periodos de tiempo',2)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'¿Qué son los ventriculos?','Masa muscular','Valvúlas','Cámaras','Nutrientes',3)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(1,'El mitral es la parte izquierda del corazón que...','No tiene sangre','Es rica en sangre','Es pobre en sangre','Solo es músculo',3)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'¿El corazón no es el órgano principal del sistema circulatorio?','Verdadero','Falso','','',2)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'¿Cuánto pesa el corazón aproximadamente?','1-2 kg','500-750 g','50-100 g','250-350 g',4)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'A diferencia de como están constituidos los corazones de los mamíferos, los reptiles cuentan con...','Dos cámaras','Tres cámaras','Cuatro cámaras','No tienen ninguna cámara',2)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'La palabra corazón proviene del latín cor, cordis a través de una forma proto-romance coratione','Verdadero','Falso','','',1)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'Si comparamos la forma del corazón este tiene forma de...','Cono invertido','Cuadrado','Pirámide','Esfera',1)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'Los vasos sanguineos...','Tocan superficialmente el corazón','No existen','No interceptan el corazón','Entran y salen de el corazón',4)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'¿Cómo actuan las 2 gruesas paredes musculares que separan al corazón en derecho e izquierdo?','Como corazones separados','Como corazones coordinados','No hay separación en derecha e izquierda','Como un solo corazón',2)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'¿Cómo es la sangre arterial con respecto a la venosa?','Pobre en oxígeno','Igual en oxígeno','Es rica en oxígeno','Independiente',3)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'¿Qué tipo de animal tiene más divisiones en cuanto a cámaras del corazón?','Ninguno, todos tienen la misma','Los mamíferos','Los reptiles','Las aves',2)";
                db.execSQL(quest);
                quest = "insert into questions(questionary,question,r1,r2,r3,r4,r)values(2,'Las auriculas y los ventriculos son...','Cámaras','Válvulas','No existen','Masa muscular',1)";
                db.execSQL(quest);
            }
        }catch (Exception e){
            Log.e("createTable",e.getMessage());
        }
    }

    private Boolean findWebUser() {
        URL url;
        String strURL="https://heartattack.herokuapp.com/api/login-users";
        HttpURLConnection httpURLConnection = null;
        String strAux ="";
        Boolean w=false;
        try {
            url = new URL(strURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            String data = "email="+ URLEncoder.encode(txt[0],"UTF-8");
            data=data+"&password="+URLEncoder.encode(txt[1],"UTF-8");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setFixedLengthStreamingMode(data.getBytes().length);
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            OutputStream out = new BufferedOutputStream(httpURLConnection.getOutputStream());

            out.write(data.getBytes());
            out.flush();
            out.close();
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader;
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader;
                bufferedReader= new BufferedReader(inputStreamReader);
                String linea = bufferedReader.readLine();
                while(linea != null){
                    strAux += linea;
                    linea = bufferedReader.readLine();
                }
                bufferedReader.close();
                inputStreamReader.close();

                //Crear objeto JSON
                JSONObject jsonObject;
                jsonObject = new JSONObject(strAux);
                try {
                    json_return = jsonObject.getString("message");
                }catch(Exception e){
                    w = true;
                }
                if(w){
                    try {
                        JSONObject o = jsonObject.getJSONObject("user");
                        txt[4]=txt[1];
                        txt[0] = o.getString("nombre");
                        txt[1] = o.getString("userName");
                        txt[2] = o.getString("email");
                        txt[3] = o.getString("age");
                        //txt_login[4]=jsonObject.getString("state");
                        txt[5] = o.getString("_id");
                    }catch (Exception e){
                        w=false;
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection !=null){
                httpURLConnection.disconnect();

            }
        }
        txt[4] = txt[1];
        txt[1] = txt[0];
        return w;
    }

    private Boolean registerWebUser() {
        URL url;
        String strURL="https://heartattack.herokuapp.com/api/register-users";
        HttpURLConnection httpURLConnection = null;
        String strAux ="";
        Boolean w=false;
        try {
            url = new URL(strURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            String data = "name="+ URLEncoder.encode(txt[0],"UTF-8");
            data=data+"&userName="+URLEncoder.encode(txt[1],"UTF-8");
            data=data+"&email="+URLEncoder.encode(txt[2],"UTF-8");
            data=data+"&age="+URLEncoder.encode(txt[3],"UTF-8");
            data=data+"&password="+URLEncoder.encode(txt[4],"UTF-8");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setFixedLengthStreamingMode(data.getBytes().length);
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            OutputStream out = new BufferedOutputStream(httpURLConnection.getOutputStream());

            out.write(data.getBytes());
            out.flush();
            out.close();
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader;
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader;
                bufferedReader= new BufferedReader(inputStreamReader);
                String linea = bufferedReader.readLine();
                while(linea != null){
                    strAux += linea;
                    linea = bufferedReader.readLine();
                }
                bufferedReader.close();
                inputStreamReader.close();

                //Crear objeto JSON
                JSONObject jsonObject;
                jsonObject= new JSONObject(strAux);
                try {
                    json_return = jsonObject.getString("message");
                }catch(Exception e){
                    w = true;
                }
                if(w){
                    try {
                        JSONObject o = jsonObject.getJSONObject("user");
                        txt[5]=o.getString("_id");
                    }catch (Exception e){
                        w=false;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection !=null){
                httpURLConnection.disconnect();

            }
        }
        return w;
    }

    private Boolean searchSqlUser() {
        String rUser;
        rUser="select*from users where id_web='"+txt[5]+"';";
        cursor=db.rawQuery(rUser,null);
        Boolean data=false;
        while (cursor.moveToNext()){
            data=true;
            w[0]=cursor.getInt(cursor.getColumnIndex("quest1"));
            w[1]=cursor.getInt(cursor.getColumnIndex("quest2"));
        }
        return data;
    }

    private Boolean insertData() {
        try {
            String data_user;
            w[0] = 0;
            w[1] = 0;
            data_user = "insert into users(id_web,name,user,email,password,quest1,quest2)values('"+txt[5]+"','"+txt[0]+ "','"+txt[1]+"','"+txt[2]+"','"+txt[4]+"',0,0);";
            db.execSQL(data_user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private Boolean updateData() {
        try {
            String avance = "update users set name='" + txt[0] + "',user='" + txt[1] + "',email='" + txt[2] + "',password='" + txt[4] + "' where id_web='" + txt[5] + "';";
            db.execSQL(avance);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private Boolean search() {
        String data;
        Boolean ret=false;
        try {
            //buscar="select quest1,quest2 from users where user='"+User_Data.getUser()+"';";
            data = "select * from users where email='"+txt[0]+"' and password='"+txt[1]+"';";
            cursor = db.rawQuery(data, null);
            while (cursor.moveToNext()) {
                txt[0] = cursor.getString(cursor.getColumnIndex("name"));
                txt[1] = cursor.getString(cursor.getColumnIndex("user"));
                txt[2] = cursor.getString(cursor.getColumnIndex("email"));
                txt[4] = cursor.getString(cursor.getColumnIndex("password"));
                txt[5] = cursor.getString(cursor.getColumnIndex("id_web"));
                w[0] = cursor.getInt(cursor.getColumnIndex("quest1"));
                w[1] = cursor.getInt(cursor.getColumnIndex("quest2"));
                ret=true;
            }
        }catch (Exception e){
            ret=false;
        }
        return ret;
    }

    private Boolean connectivity(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}