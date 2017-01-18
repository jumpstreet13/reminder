package com.example.somethingstrange;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final int REQUEST_CODE_TASK = 1;
    private Button btn1, btn2;
    private Intent intent1, intent2;
    private ArrayList<String> todayTasks = new ArrayList<String>();
    private Dialog dialog;
    private String somethingImportant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle(R.string.MainMenu);

        btn2 = (Button) findViewById(R.id.progress_btn);
        btn1 = (Button) findViewById(R.id.list_onToday);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(MainActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle(R.string.writeNameofList);
                dialog.show();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                Button allOkButton = (Button) dialog.findViewById(R.id.button_AllInformationConfirmed);
                allOkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ed = (EditText) dialog.findViewById(R.id.editSomethingImportant);
                        if(ed.getText().toString().equals("")){
                            makeNotification(MainActivity.this, getResources().getString(R.string.mainNotification));
                            dialog.dismiss();
                            return;
                        }
                        somethingImportant = ed.getText().toString();
                        intent1 = new Intent(MainActivity.this, TaskActivity.class);
                        intent1.putExtra("important", somethingImportant);
                        startActivity(intent1);
                        dialog.dismiss();
                    }
                });

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2 = new Intent(MainActivity.this, ProgressActivity.class);

                //intent2.putStringArrayListExtra("Tasks", todayTasks);
                startActivity(intent2);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public static void makeNotification(Context ctx, String notification){


        Toast toast = Toast.makeText(ctx, notification, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }

    /*public void fillTasks(ArrayList<String> arr, MyBase myBase){

    } */

}
