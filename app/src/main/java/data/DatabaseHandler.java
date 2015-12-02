package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;

import model.Present;

/**
 * Created by Sparta on 1/12/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Present> listaRegalos = new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.PRESENT_NAME +
                " TEXT, " + Constants.PRESENT_PRICE_NAME + " REAL, " + Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create a new one
        onCreate(db);

    }


    //Get total items saved
    public int getTotalItems() {

        int totalItems = 0;

        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        totalItems = cursor.getCount();

        cursor.close();

        return totalItems;


    }

    //get total euros
    public double totalPrize() {
        double price = 0;

        SQLiteDatabase dba = this.getReadableDatabase();

        String query = "SELECT SUM( " + Constants.PRESENT_PRICE_NAME + " ) " +
                "FROM " + Constants.TABLE_NAME;

        Cursor cursor = dba.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            price = cursor.getDouble(0);
        }

        cursor.close();
        dba.close();

        return price;
    }


    //delete present item
    public void deletePresent(int id) {

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        dba.close();


    }

    //add content to db - add present
    public void addPresent(Present present) {

        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.PRESENT_NAME, present.getPresentName());
        values.put(Constants.PRESENT_PRICE_NAME, present.getPresentPrice());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        dba.insert(Constants.TABLE_NAME, null, values);

        Log.v("Regalo añadido", "Yesss!!");

        Log.d("ASD", values.get(Constants.PRESENT_PRICE_NAME).toString());

        dba.close();
    }


    //Get all present
    public ArrayList<Present> getPresent(){

        listaRegalos.clear();

        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.PRESENT_NAME, Constants.PRESENT_PRICE_NAME,
                        Constants.DATE_NAME}, null, null, null, null, Constants.DATE_NAME + " DESC ");

        //loop through...
        if (cursor.moveToFirst()) {
            do {

                Present present = new Present();
                present.setPresentName(cursor.getString(cursor.getColumnIndex(Constants.PRESENT_NAME)));
                present.setPresentPrice(cursor.getDouble(cursor.getColumnIndex(Constants.PRESENT_PRICE_NAME)));
                present.setPresentId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                present.setRecordDate(date);

                listaRegalos.add(present);

            }while (cursor.moveToNext());

        }

        cursor.close();
        dba.close();

        return listaRegalos;

    }

}


