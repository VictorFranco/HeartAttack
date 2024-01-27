package mx.ipn.heartattack;

import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by vfran_000 on 2018.
 */

public class LogIn extends AppCompatActivity implements View.OnClickListener{
    Button btn_login;
    EditText user,password;
    String roger_that;
    String txt[];
    SQLiteDatabase db;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btn_login=(Button)findViewById(R.id.btn_login);
        user=(EditText)findViewById(R.id.login_user);
        password=(EditText)findViewById(R.id.login_password);
        roger_that=getResources().getString(R.string.txt_roger_that);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_login){
            try {
                txt=new String[2];
                txt[0] = user.getText().toString();
                txt[1] = password.getText().toString();
                validar();
                if (validar()) {
                    db = this.openOrCreateDatabase("database.sql", MODE_PRIVATE, null);
                    createTable();
                    search();
                    db.close();
                } else {
                    Toast.makeText(this, "Datos rechazados por sintaxis", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean validar() {
        Boolean data_user_comillas[] = new Boolean[4];
        Boolean data_user_espacios[] = new Boolean[4];
        int vacio[] = new int[4];
        data_user_comillas[0] = false;
        data_user_comillas[1] = false;
        data_user_espacios[0] = false;
        data_user_espacios[1] = false;
        for (int j = 0; j < 2; j++) {
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
        for (int i = 0; i < 2; i++) {
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

    private void createTable() {
        String table;
        table="CREATE TABLE IF NOT EXISTS users( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, user TEXT, email TEXT, password TEXT);";
        db.execSQL(table);
    }

    private void search() {
        String data;
        data="select name,user,email,password from users;";
        cursor=db.rawQuery(data,null);
        Boolean userfound=false;
        String data_user[]=new String[4];
        data_user[0]="";
        data_user[1]="";
        data_user[2]="";
        data_user[3]="";
        while(cursor.moveToNext()){
            String nameR=cursor.getString(cursor.getColumnIndex("name"));
            String userR=cursor.getString(cursor.getColumnIndex("user"));
            String emailR=cursor.getString(cursor.getColumnIndex("email"));
            String passwordR=cursor.getString(cursor.getColumnIndex("password"));

            if (userR.equals(txt[0])&&passwordR.equals(txt[1])){
                userfound=true;
                data_user[0]=nameR;
                data_user[1]=userR;
                data_user[2]=emailR;
                data_user[3]=passwordR;
            }
        }
        if (userfound){
            try {
                User_Data.setName(data_user[0]);
                User_Data.setUser(data_user[1]);
                User_Data.setEmail(data_user[2]);
                User_Data.setPassword(data_user[3]);
                Intent menu = new Intent(this, Profile.class);
                //menu.putExtra("user", data_user);
                startActivity(menu);
            }catch (Exception e){
                Toast.makeText(this,"ufffffff",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"No coinside en los registros",Toast.LENGTH_LONG).show();
        }
    }
}