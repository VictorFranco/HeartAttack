package mx.ipn.heartattack;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vfran_000 on 2018.
 */

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String data[];
    TextView user,correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.relative_user_interface,new Home()).commit();
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        user=(TextView)header.findViewById(R.id.profile_user);
        correo=(TextView)header.findViewById(R.id.profile_correo);
        try {
            String txt_user=User_Data.getUser();
            user.setText(txt_user);
            correo.setText(User_Data.getEmail());
        }catch (Exception e){
            Toast.makeText(this,"Datos corruptos",Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.txt_questionnaires, R.string.txt_sign_off);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.fast_home) {
            FragmentManager fm=getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.relative_user_interface,new Home()).commit();
            return true;
        }
        if (id == R.id.fast_exit){
            Intent inicio=new Intent(this,MainActivity.class);
            inicio.putExtra("salir", true);
            startActivity(inicio);
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

        RelativeLayout espacio=(RelativeLayout)findViewById(R.id.relative_user_interface);
        if (id == R.id.nav_info) {
            fm.beginTransaction().replace(R.id.relative_user_interface,new Heart()).commit();
        }
        if (id == R.id.nav_questionnaires) {
            fm.beginTransaction().replace(R.id.relative_user_interface,new Questionnaires()).commit();
        }
        if (id == R.id.nav_stats) {
            fm.beginTransaction().replace(R.id.relative_user_interface,new Stats()).commit();
        }
        if (id == R.id.nav_sign_off) {
            Intent inicio=new Intent(this,MainActivity.class);
            inicio.putExtra("salir", true);
            startActivity(inicio);
        }
        if (id == R.id.nav_settings) {
            fm.beginTransaction().replace(R.id.relative_user_interface,new Settings()).commit();
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