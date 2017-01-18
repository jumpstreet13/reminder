package com.example.somethingstrange;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ProgressActivity extends Activity {


    private ArrayList<String> deals = new ArrayList<String>();
    private ListView lv;
    private MyBase myBase;
    //private int position;
   /* private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<String> tasks = new ArrayList<String>();
*/
  /*  public ArrayList<String> getList(MyBase db, String title){
        String split="";
        String[] gg;
        ArrayList<String> ret = new ArrayList<String>();
        Cursor cursor = db.getAllData();
        if(cursor.moveToFirst()){
            split = cursor.getString(2);
        }
        gg = split.split(",");
        for(int i = 0; i<gg.length; i++){
            ret.add(gg[i]);
        }
        return ret;
    }
*/
    public ArrayList<String> getTitles(MyBase db){
        ArrayList<String> ret = new ArrayList<String>();
        Cursor cursor = db.getTitles();
        while(cursor.moveToNext()){
            ret.add(cursor.getString(0));
        }
        return ret;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getActionBar().setTitle(R.string.TasksList);
        myBase = new MyBase(this);
        myBase.open();

        lv = (ListView) findViewById(R.id.listView);

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_list, getTitles(myBase));
        MyAdapter adapter = new MyAdapter(this, getTitles(myBase), myBase);
        ViewGroup vw = (ViewGroup) findViewById(R.id.rel);
        lv.setAdapter(adapter);
        //View item = lv.getAdapter().getView(0,null, lv);
        //item.setBackgroundResource(R.color.green);
        //Log.e("Pizdec", item.toString());


        //lv.getAdapter().getItem(1,null,lv).setBackgroundColor(getResources().getColor(R.color.green));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProgressActivity.this, Progress2Activity.class);
                intent.putStringArrayListExtra("titles", getTitles(myBase));
                intent.putExtra("position", position);
                Log.e("Position", position+"");
                startActivity(intent);
            }
        });
      /*  Intent intent = getIntent();
        int position =  intent.getIntExtra("name", 0);
        //if(position != 0){
            lv.getAdapter().getView(1,null,lv).getResources().getColor(R.color.green);
          //  Log.e("Pizdec",  lv.getAdapter().getView(1,null,lv).toString() );
            adapter.notifyDataSetChanged();
        //}*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ProgressActivity.this, MainActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProgressActivity.this, MainActivity.class));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        myBase.close();
    }
}

