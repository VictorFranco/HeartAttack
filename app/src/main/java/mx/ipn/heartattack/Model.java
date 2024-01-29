package mx.ipn.heartattack;

import android.app.ProgressDialog;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by vfran_000 on 2018.
 */

public class Model extends Fragment implements View.OnClickListener{
    RelativeLayout rl;
    LinearLayout ln;
    Button btn;
    MyAsynctask myAsynctask;
    ProgressDialog progressDialog;
    GLSurfaceView mView;
    OpenGLRender rend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_model,container,false);
        rl=(RelativeLayout) view.findViewById(R.id.space_heart);
        ln=(LinearLayout)view.findViewById(R.id.linear_modelo);
        btn=(Button)view.findViewById(R.id.btn_option_modelo);
        ln.setVisibility(View.VISIBLE);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        ln.setVisibility(View.GONE);
        mView = new GLSurfaceView(getContext());
        mView.setEGLContextClientVersion(2);
        rend = new OpenGLRender(getContext());
        mView.setRenderer(rend);
        rl.addView(mView);
        //ln.setVisibility(View.GONE);
        /*progressDialog=new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        *//*
        myAsynctask=new MyAsynctask();
        myAsynctask.execute();*/
    }

    class MyAsynctask extends AsyncTask<Void, Void, View> {
        private View view=null;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Cargando");
            progressDialog.show();
        }

        @Override
        protected View doInBackground(Void... voids) {
            try {
                GLSurfaceView mView = new GLSurfaceView(getContext());
                OpenGLRender rend = new OpenGLRender(getContext());
                mView.setEGLContextClientVersion(2);
                mView.setRenderer(rend);
                Thread.sleep(3000);
                view=mView;
                return view;
            }catch (Exception e){
                Log.e("doInBackground",e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(View s) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            rl.addView(s);
        }
    }
}