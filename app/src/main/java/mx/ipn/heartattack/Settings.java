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
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vfran_000 on 2018.
 */

public class Settings extends Fragment implements View.OnClickListener{
    String txt[];
    Cursor cursor;
    Button btn_save;
    EditText edit_name,edit_user,edit_email,edit_password;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_settings,container,false);
        btn_save=(Button)view.findViewById(R.id.btn_data_save);
        edit_name=(EditText)view.findViewById(R.id.settings_name);
        edit_user=(EditText)view.findViewById(R.id.settings_user);
        edit_email=(EditText)view.findViewById(R.id.settings_email);
        edit_password=(EditText)view.findViewById(R.id.settings_password);
        edit_name.setText(User_Data.getName());
        edit_user.setText(User_Data.getUser());
        edit_email.setText(User_Data.getEmail());
        edit_password.setText(User_Data.getPassword());
        btn_save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_data_save){
            db=getActivity().openOrCreateDatabase("database.sql",MODE_PRIVATE,null);
            txt=new String[4];
            txt[0]=edit_name.getText().toString();
            txt[1]=edit_user.getText().toString();
            txt[2]=edit_email.getText().toString();
            txt[3]=edit_password.getText().toString();
            if (validar()) {
                findUser();
            }
        }
    }

    private void findUser(){
        String rUser;
        rUser = "select user from users where user='" + txt[1] + "';";
        cursor = db.rawQuery(rUser, null);
        Boolean data = false;
        while (cursor.moveToNext()) {
            String u = "";
            u = cursor.getString(cursor.getColumnIndex("user"));
            if (!u.equals("") && !u.equals(User_Data.getUser())) {
                data = true;
            }
        }
        if (data) {
            Toast.makeText(getContext(), "Usuario ya existente", Toast.LENGTH_SHORT).show();
        } else {
            String update;
            update = "UPDATE users set name='" + txt[0] + "',user='" + txt[1] + "',email='" + txt[2] + "',password='" + txt[3] + "'where user='" + User_Data.user + "';";
            db.execSQL(update);
            db.close();
            User_Data.setName(txt[0]);
            User_Data.setUser(txt[1]);
            User_Data.setEmail(txt[2]);
            User_Data.setPassword(txt[3]);
            Toast.makeText(getContext(),"hecho",Toast.LENGTH_LONG).show();
            FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.relative_user_interface,new Home()).commit();
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
}