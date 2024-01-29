package mx.ipn.heartattack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by vfran_000 on 2018.
 */

public class Search extends Fragment implements SearchView.OnQueryTextListener,AdapterView.OnItemClickListener,View.OnClickListener{
    SearchView search;
    String list[];
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    RelativeLayout th;
    LinearLayout t1,t2,t3,t4,t5,t6;
    ImageView img_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);
        th=(RelativeLayout)view.findViewById(R.id.search_home);
        t1=(LinearLayout)view.findViewById(R.id.search_t1);
        t2=(LinearLayout)view.findViewById(R.id.search_t2);
        t3=(LinearLayout)view.findViewById(R.id.search_t3);
        t4=(LinearLayout)view.findViewById(R.id.search_t4);
        t5=(LinearLayout)view.findViewById(R.id.search_t5);
        t6=(LinearLayout)view.findViewById(R.id.search_t6);
        img_back=(ImageView)view.findViewById(R.id.icon_back_search);

        th.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.GONE);
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        t4.setVisibility(View.GONE);
        t5.setVisibility(View.GONE);
        t6.setVisibility(View.GONE);

        img_back.setOnClickListener(this);


        search = (SearchView) view.findViewById(R.id.search);
        listView = (ListView) view.findViewById(R.id.list_view);
        list = new String[6];
        list[0] = "Cavidades Cardiacas";
        list[1] = "Válvulas Cardiacas";
        list[2] = "Estructura del corazón";
        list[3] = "El endocardio";
        list[4] = "El miocardio";
        list[5] = "El epicardio";
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        search.setOnQueryTextListener(this);
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        arrayAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
        switch (i){
            case 0:
                th.setVisibility(View.GONE);
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                break;
            case 1:
                th.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                break;
            case 2:
                th.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                break;
            case 3:
                th.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                break;
            case 4:
                th.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.VISIBLE);
                t6.setVisibility(View.GONE);
                break;
            case 5:
                th.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.VISIBLE);
                break;
        }
        img_back.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.icon_back_search){
            th.setVisibility(View.VISIBLE);
            img_back.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            t5.setVisibility(View.GONE);
            t6.setVisibility(View.GONE);
        }
    }
}
