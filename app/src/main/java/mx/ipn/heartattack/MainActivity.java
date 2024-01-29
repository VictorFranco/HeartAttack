package mx.ipn.heartattack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * Created by vfran_000 on 2018.
 */

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Boolean s=false,d=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent salir = getIntent();
            Bundle sal = salir.getExtras();
            s=sal.getBoolean("salir");
            d=sal.getBoolean("delete");
            if (s){
                User_Data.clear();
                finishAffinity();
            }
            if (d){
                db = openOrCreateDatabase("database.sql",MODE_PRIVATE,null);
                String delete="DELETE FROM users where user='"+User_Data.getUser()+"';";
                db.execSQL(delete);
                db.close();
                User_Data.clear();
                finishAffinity();
            }
        }catch (Exception e){ }
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.relative_start,new Start()).commit();
        if(!d && !s) {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(2);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class MyAsyncTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected String doInBackground(Integer... integers) {
            int w=integers[0];
            try {
                for(int i=0;i<w;i++) {
                    Thread.sleep(1000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "fin";
        }

        @Override
        protected void onPostExecute(String s) {
            FragmentManager fm=getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.relative_start,new Carousel()).commit();
        }
    }
}
