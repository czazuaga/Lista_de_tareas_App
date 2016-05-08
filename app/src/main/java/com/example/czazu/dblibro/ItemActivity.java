package com.example.czazu.dblibro;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by czazu on 08/05/2016.
 */
public class ItemActivity extends Activity {

TextView mItem = null;
TextView mPlace = null;
TextView mDescription = null;
TextView mImportance = null;

    //input indentifier
    Integer mRowId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);

        //Save button
        Button saveButton = (Button) findViewById(R.id.addButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                saveData();
                finish();
            }
        });

        mItem = (TextView) findViewById(R.id.item);
        mPlace = (TextView) findViewById(R.id.place);
        mDescription= (TextView) findViewById(R.id.description);
        mImportance = (TextView) findViewById(R.id.importance);

    }

    protected void saveData(){
        //Obtain data
        String itemText = mItem.getText().toString();
        String placeText = mPlace.getText().toString();
        String descriptionText = mDescription.getText().toString();
        String importanceText = mImportance.getText().toString();

        //Insert
        try{
            MainActivity.dataBaseHelper.open();
            MainActivity.dataBaseHelper.insertItem(itemText,placeText,descriptionText,
                    Integer.parseInt(importanceText));

        }catch (SQLException e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this,R.string.data_error, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}