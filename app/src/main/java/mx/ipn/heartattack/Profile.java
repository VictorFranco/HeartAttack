package mx.ipn.heartattack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vfran_000 on 2018.
 */

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String data[],corrupt_data,txt_salir,txt_yes,txt_no,txt_peticion;
    TextView user,correo,txt;
    int espacio,color_purple;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        espacio=(int)R.id.relative_user_interface;
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(espacio,new Home()).commit();
        corrupt_data=getResources().getString(R.string.txt_corrupt_data);
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        user=(TextView)header.findViewById(R.id.profile_user);
        correo=(TextView)header.findViewById(R.id.profile_correo);
        txt_salir=getResources().getString(R.string.txt_salir);
        txt_yes=getResources().getString(R.string.txt_yes);
        txt_no=getResources().getString(R.string.txt_no);
        txt_peticion=getResources().getString(R.string.txt_peticion);
        color_purple=getResources().getColor(R.color.colorPrimary);

        try {
            String txt_user=User_Data.getUser();
            user.setText(txt_user);
            correo.setText(User_Data.getEmail());
        }catch (Exception e){
            Toast.makeText(this,corrupt_data,Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.txt_questionnaires, R.string.txt_sign_off);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        View check=getLayoutInflater().inflate(R.layout.alert_dialog,null);
        checkBox=(CheckBox) check.findViewById(R.id.checkbox_cerrar);
        builder.setTitle(txt_salir)
                .setView(check)
                .setPositiveButton(txt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean c=checkBox.isChecked();
                        if(c){
                            Intent inicio=new Intent(Profile.this,MainActivity.class);
                            inicio.putExtra("salir", true);
                            startActivity(inicio);
                        }
                        else{
                            Intent intent=new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }})
                .setNegativeButton(txt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }})
                .setIcon(R.mipmap.ic_power_purple);
        Dialog d=builder.show();
        int id_txt=d.getContext().getResources().getIdentifier("android:id/alertTitle",null,null);
        TextView tv=(TextView)d.findViewById(id_txt);
        tv.setTextColor(color_purple);
        int id_barra_color=d.getContext().getResources().getIdentifier("android:id/titleDivider",null,null);
        View barra_color=(View)d.findViewById(id_barra_color);
        barra_color.setBackgroundColor(color_purple);
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        InputMethodManager inputMethodManager=(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);

        if (id == R.id.fast_home) {
            FragmentManager fm=getSupportFragmentManager();
            fm.beginTransaction().replace(espacio,new Home()).commit();
            return true;
        }
        if (id == R.id.fast_exit){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm=getSupportFragmentManager();

        InputMethodManager inputMethodManager=(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);

        if (id == R.id.nav_info) {
            fm.beginTransaction().replace(espacio,new Heart()).commit();
        }
        if (id == R.id.nav_questionnaires) {
            fm.beginTransaction().replace(espacio,new Questionnaires()).commit();
        }
        if (id == R.id.nav_stats) {
            fm.beginTransaction().replace(espacio,new Stats()).commit();
        }
        if (id == R.id.nav_sign_off) {
            finish();
        }
        if (id == R.id.nav_settings) {
            fm.beginTransaction().replace(espacio,new Settings()).commit();
        }
        if (id == R.id.nav_unsubscribe) {
            unsubscribe();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void unsubscribe() {
        try {
            Intent inicio=new Intent(this,MainActivity.class);
            inicio.putExtra("delete", true);
            startActivity(inicio);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
