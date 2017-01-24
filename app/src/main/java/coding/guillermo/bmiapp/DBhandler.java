package coding.guillermo.bmiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Guillermo on 11/29/2016.
 */
public class DBhandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="BMIresults.db";
    public static final String TABLE_NAME="Results";
    public static final String COLUMN_ID ="_id";
    public static final String USER_NAME="Name";
    public static final String USER_WEIGHT="resultWeight";
    public static final String USER_HEIGHT="resultHeight";
    public static final String WEIGHT_MEASUREMENT="weightMeasurement";
    public static final String RESULT_DATE="resultDate";

    public DBhandler(Context context){
        super(context,DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("+ COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_NAME+" TEXT, "+USER_WEIGHT+" REAL, "+USER_HEIGHT+" REAL, "+WEIGHT_MEASUREMENT+" TEXT, "+RESULT_DATE+" TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXIST "+ TABLE_NAME);
        onCreate(db);
    }
    // adding new BMI result to the database
    public void addResult(Result result){
        ContentValues values = new ContentValues();
        values.put(USER_NAME,result.getName());
        values.put(USER_WEIGHT,result.getWeight());
        values.put(USER_HEIGHT,result.getHeight());
        values.put(WEIGHT_MEASUREMENT,result.getWeightMeasurement());
        values.put(RESULT_DATE,result.getDate());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    // Deleting BMI result from the database
    public boolean deleteResult(Result result){
        SQLiteDatabase db = getWritableDatabase();
        if(db.delete(TABLE_NAME,COLUMN_ID+" = ?",new String[]{Long.toString(result.getId())}) > 0){
            db.close();
            return true;
        }
        else{
            return false;
        }
    }
    // Retriving BMI results from the database for display
    public Cursor getListContents(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return data;
    }
}
