package mx.ipn.heartattack;

import android.content.Intent;
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

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    Button btn_registry;
    EditText name,user,email,password;
    String txt[];
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        name=(EditText)findViewById(R.id.registry_name);
        user=(EditText)findViewById(R.id.registry_user);
        email=(EditText)findViewById(R.id.registry_email);
        password=(EditText)findViewById(R.id.registry_password);
        btn_registry=(Button)findViewById(R.id.btn_registry);
        btn_registry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_registry){
            try {
                txt=new String[4];
                txt[0] = name.getText().toString();
                txt[1] = user.getText().toString();
                txt[2] = email.getText().toString();
                txt[3] = password.getText().toString();
                validar();
                if (!txt[0].equals("") && !txt[1].equals("") && !txt[2].equals("") && !txt[3].equals("")&&validar()) {
                    db = openOrCreateDatabase("database.sql", MODE_PRIVATE, null);
                    createTable();
                    db.close();
                }
                else {
                    Toast.makeText(this,"Datos rechazados por sintaxis",Toast.LENGTH_LONG).show();
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
        data_user_comillas[2] = false;
        data_user_comillas[3] = false;
        data_user_espacios[0] = false;
        data_user_espacios[1] = false;
        data_user_espacios[2] = false;
        data_user_espacios[3] = false;
        for (int j = 0; j < 4; j++) {
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
        for (int i = 0; i < 4; i++) {
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
        findUser();
    }

    private void findUser() {
        String rUser;
        rUser="select user from users where user='"+txt[1]+"';";
        cursor=db.rawQuery(rUser,null);
        Boolean data=false;
        while (cursor.moveToNext()){
            data=true;
        }
        if (data){
            Toast.makeText(this,"Usuario ya existente",Toast.LENGTH_SHORT).show();
        }
        else {
            insertData();
        }

    }

    private void insertData() {
        String data_user;
        data_user="insert into users(name,user,email,password)values('"+txt[0]+"','"+txt[1]+"','"+txt[2]+"','"+txt[3]+"');";
        db.execSQL(data_user);
        Toast.makeText(this,"Bienvenido",Toast.LENGTH_LONG).show();
        User_Data.setName(txt[0]);
        User_Data.setUser(txt[1]);
        User_Data.setEmail(txt[2]);
        User_Data.setPassword(txt[3]);
        Intent menu=new Intent(this,Profile.class);
        startActivity(menu);
    }
}
