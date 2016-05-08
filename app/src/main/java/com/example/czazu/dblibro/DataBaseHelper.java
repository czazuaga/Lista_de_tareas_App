package com.example.czazu.dblibro;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by czazu on 08/05/2016.
 */
public class DataBaseHelper {

    private Context miContext = null;
    private DataBaseHelperInternal miDBHelper = null;
    private SQLiteDatabase miDB = null;
    private static final String DATABASE_NAME = "TODOLIST";
    private static final int DATABASE_VERSION = 3;

    //Tabla y campos

    private static final String DATABASE_TABLE_TODOLIST = "todoList";
    private static final String SL_ID = "_id";
    private static final String SL_ITEM = "task";
    private static final String SL_PLACE = "place";
    private static final String SL_IMPORTANCE = "importance";
    private static final String SL_DESCRIPTION = "description";

    //Sql de creación de la tabla

    private static final String CREATE_TODOLIST = "CREATE TABLE "+DATABASE_TABLE_TODOLIST+
            " ("+SL_ID+" INTEGER PRIMARY KEY,"+SL_ITEM+" TEXT NOT NULL, "+SL_PLACE+" TEXT NOT NULL, "+
            SL_IMPORTANCE+" INTEGER NOT NULL, "+SL_DESCRIPTION+" TEXT)";

    //Constructor

    public DataBaseHelper (Context context){
        this.miContext=context;

    }

    public DataBaseHelper open () throws SQLException{
        miDBHelper = new DataBaseHelperInternal(miContext);
        miDB = miDBHelper.getWritableDatabase();
        return this;
    }

    public void close () {
        miDBHelper.close();
    }

    private class DataBaseHelperInternal extends SQLiteOpenHelper {

        public DataBaseHelperInternal(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTables(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            deleteTables(db);
            createTables(db);
        }

        private void createTables (SQLiteDatabase dB) {
            dB.execSQL(CREATE_TODOLIST);

        }

        private void deleteTables (SQLiteDatabase dB){
            dB.execSQL("DROP TABLE IF EXIST "+DATABASE_TABLE_TODOLIST);
        }

    }

}
