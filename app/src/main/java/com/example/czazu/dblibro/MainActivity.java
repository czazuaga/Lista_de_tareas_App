package com.example.czazu.dblibro;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    public static final int NEW_ITEM = 1;
    public static final int EDIT_ITEM = 2;
    public static final int SHOW_ITEM = 3;

    private int mLastrowSelected = 0;
    public static  DataBaseHelper dataBaseHelper=null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_item:
                Intent intent = new Intent(this,ItemActivity.class);
                startActivityForResult(intent,NEW_ITEM);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Abrir la base de datos
        dataBaseHelper = new DataBaseHelper(this);
        try{
           fillData();
        }catch (SQLException e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this,R.string.data_error, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void fillData(){
        //Se abre la base de datos y se obtienen los elementos
        dataBaseHelper.open();

        Cursor itemCursor = dataBaseHelper.getItems();
        ListEntry item = null;
        ArrayList<ListEntry> resultList = new ArrayList<ListEntry>();

        //se procesa el resultado
        while (itemCursor.moveToNext()){
            int id = itemCursor.getInt(itemCursor.getColumnIndex(DataBaseHelper.SL_ID));
            String task = itemCursor.getString(itemCursor.getColumnIndex(dataBaseHelper.SL_ITEM));
            String place = itemCursor.getString(itemCursor.getColumnIndex(dataBaseHelper.SL_PLACE));
            int importance = itemCursor.getInt(itemCursor.getColumnIndex(dataBaseHelper.SL_IMPORTANCE));

            item = new ListEntry();
            item.id=id;
            item.task=task;
            item.place=place;
            item.importance=importance;
            resultList.add(item);
        }
//cerramos la base de datos
        itemCursor.close();
        dataBaseHelper.close();
        //Se genera el adaptador
        TaskAdapter items = new TaskAdapter(this, R.layout.row_list, resultList, getLayoutInflater());
        //asignar adaptador a la lista
        setListAdapter(items);
    }

    private class ListEntry{
        int id;
        String task;
        String place;
        int importance;

    }



}
