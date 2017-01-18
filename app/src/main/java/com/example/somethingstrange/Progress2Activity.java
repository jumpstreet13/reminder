package com.example.somethingstrange;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Progress2Activity extends ListActivity {

    private MyBase myBase;
    private ArrayList<String> arr = new ArrayList<String>();
    private ArrayList<String> cheked = new ArrayList<String>();
    private ArrayList<String> tasks = new ArrayList<String>();
    private int positi = 0;


   /* public ArrayList<String> getData(MyBase mb){

        ArrayList<String> arr = new ArrayList<String>();
        Cursor cursor = mb.getNameData(title);
        while(cursor.moveToNext()){
            arr.add(cursor.getString(0));
        }
        return arr;
    }*/

    public ArrayList<String> getCheckedItem(MyBase mb){
        ArrayList<String> ret = new ArrayList<String>();
        Cursor cursor = mb.getCheked(arr.get(positi));
        while(cursor.moveToNext()){
            ret.add(cursor.getString(2));
        }
        return ret;
    }

    public ArrayList<String> getList(MyBase mb) {
        String split = "";
        String[] gg;
        ArrayList<String> ret = new ArrayList<String>();
        Cursor cursor = mb.getNameData(arr.get(positi));
        if (cursor.moveToFirst()) {
            split = cursor.getString(1);
        }
        gg = split.split(",");
        for (int i = 0; i < gg.length; i++) {
            ret.add(gg[i]);
        }
        //Collections.shuffle(ret);
        return ret;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        arr.addAll(intent.getStringArrayListExtra("titles"));
        positi = intent.getIntExtra("position", 0);
        getActionBar().setTitle(arr.get(positi));
        myBase = new MyBase(this);
        myBase.open();
        tasks = getList(myBase);
        // title = intent.getStringExtra("text");
        final ListView listView = getListView();
        listView.setBackground(getResources().getDrawable(R.drawable.background));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, getList(myBase));
        setListAdapter(adapter);


        if(makeVerify(myBase, arr.get(positi))){
            for(int i = 0; i<tasks.size(); i++){
                myBase.addDefaultCheck(arr.get(positi),tasks.get(i), "-");
                Log.e("WTF", "he is clearing checkboxes man, what a hell is going on!");
            }
        }else {
            ArrayList<String> her = new ArrayList<String>();
            her.addAll(getCheckedItem(myBase));
            Log.e("SUKA", her.size()+"");

            for (int i = 0; i < listView.getCount(); i++) {
                if (her.get(i).equals("1")) {
                    listView.setItemChecked(i, true);
                    adapter.getView(i,null,listView).setEnabled(false);
                    adapter.getView(i,null,listView).setClickable(false);

                    adapter.notifyDataSetChanged();
                } else {
                    listView.setItemChecked(i, false);
                }
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listView.isItemChecked(position)) {
                        myBase.addUpdateCheck(arr.get(positi), tasks.get(position), "1");
                        view.setEnabled(false);
                        view.setClickable(false);
                        view.setVisibility(View.VISIBLE);
                        Log.e("Add",  arr.get(positi)  +" "+ tasks.get(position));
                        if(isCheked(listView)){
                            Intent intent = new Intent(getBaseContext(), ProgressActivity.class);
                            intent.putExtra("name", positi);
                            myBase.changeColors(arr.get(positi), "green");
                            startActivity(intent);
                        }
                    } else {
                        myBase.addUpdateCheck(arr.get(positi),tasks.get(position), "-");
                        Log.e("Add", "unAdd " + arr.get(positi) + " " + tasks.get(position));
                    }
            }
        });
    }

    public boolean isCheked(ListView lv){
        int count = 0;
        for(int i = 0; i<lv.getCount(); i++){
            if(lv.isItemChecked(i))
                count++;
        }
        if(count == lv.getCount())
            return true;
        else
            return false;
    }



    public boolean makeVerify(MyBase myBase, String title){
        Cursor cursor = myBase.getCheked(title);
        if(cursor.getCount()>0) {
            Log.e("Count", cursor.getCount()+"");
            return false;
        }
        else
            Log.e("Count", cursor.getCount()+"");
            return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(Progress2Activity.this, ProgressActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Progress2Activity.this, ProgressActivity.class));
    }

    @Override
    public void onDestroy() {
        Log.e("Destroy", "destroyed");
        super.onDestroy();
        myBase.close();
    }

}