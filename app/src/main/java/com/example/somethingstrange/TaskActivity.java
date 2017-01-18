package com.example.somethingstrange;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.somethingstrange.MainActivity;

import java.util.ArrayList;

public class TaskActivity extends Activity implements View.OnClickListener {

    private Button ok, notOk;
    private EditText editText;
    private ArrayList<String> tasks = new ArrayList<String>();
    private MyBase myBase;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getActionBar().setTitle(R.string.TaskMenu);
        ok = (Button) findViewById(R.id.buttonOk);
        notOk = (Button) findViewById(R.id.buttonNotOk);
        editText = (EditText) findViewById(R.id.editText);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ok.setOnClickListener(this);
        notOk.setOnClickListener(this);
        Intent inte = getIntent();
        title = inte.getStringExtra("important");
        myBase = new MyBase(this);
        myBase.open();
    }

    @Override
    public void onClick(View v) {

        String slTv="";
        switch (v.getId()){
            case R.id.buttonOk:
                if(editText.getText().toString().equals("")){
                    MainActivity.makeNotification(TaskActivity.this, getResources().getString(R.string.taskNotification));
                    break;
                }
                tasks.add(editText.getText().toString());
                editText.setText("");
                break;
            case R.id.buttonNotOk:
                if(tasks.isEmpty()){
                    MainActivity.makeNotification(TaskActivity.this, getResources().getString(R.string.taskNotification2));
                    break;
                }
                for(String ar : tasks){
                    slTv+=ar+",";
                }
                myBase.addRec(title, slTv);
                myBase.addColors(title, "red");
                myBase.close();
                Intent intent = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(TaskActivity.this, MainActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TaskActivity.this, MainActivity.class));
    }
}
