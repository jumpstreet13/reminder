package com.example.somethingstrange;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abocha on 03.01.2017.
 */


public class MyAdapter extends BaseAdapter {

    MyBase mb;
    ArrayList<String> data = new ArrayList<String>();
    Context context;


    public MyAdapter(Context context, ArrayList<String> arr, MyBase mb) {
        if (arr != null) {
            data = arr;
        }
        this.mb = mb;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int num) {
        // TODO Auto-generated method stub
        return data.get(num);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int i, View someView, ViewGroup arg2) {
        //Получение объекта inflater из контекста
        LayoutInflater inflater = LayoutInflater.from(context);
        //Если someView (View из ListView) вдруг оказался равен
        //null тогда мы загружаем его с помошью inflater
        if (someView == null) {
            someView = inflater.inflate(R.layout.my_list, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView main = (TextView) someView.findViewById(R.id.tv);
        Cursor cursor = mb.getColors(data.get(i));
        if(cursor.moveToFirst()){
            if(cursor.getString(1).equals("green")){
                main.setBackgroundResource(R.color.grey);
            }else{
                main.setBackgroundResource(R.color.red);
            }
        }
        //Устанавливаем в каждую текствьюшку соответствующий текст
        // сначала заголовок
        main.setText(data.get(i));
        // потом подзаголовок
        return someView;
    }

}