package net.cuongmai.shoeclothingsizeconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Cuong Mai on 9/09/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "database";
    private static final int DB_VERSION = 2;

    public static final String TABLE_MEN = "Men";
    public static final String TABLE_WOMEN = "Women";
    public static final String TABLE_YOUTH = "Youth";
    public static final String TABLE_KIDS = "Kids";
    public static final String TABLE_BABIES = "Babies";

    private static final String KEY_ID = "_id";
    private static final String KEY_US = "_us";
    private static final String KEY_EURO = "euro";
    private static final String KEY_UK = "uk";
    private static final String KEY_IN = "inches";
    private static final String KEY_CM = "cm";
    private static final String KEY_BOOKMARK_NAME = "bookmark_name";

    private static final String[] COLUMN_LIST = {KEY_ID, KEY_US, KEY_EURO, KEY_UK, KEY_IN, KEY_CM, KEY_BOOKMARK_NAME};

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    public Size getSize(String tableName, int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, COLUMN_LIST, "_id = ?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Size size = new Size(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5),cursor.getString(6));

        return size;
    }

    public ArrayList<Size> getAllSizes(String tableName) {
        ArrayList<Size> sizeList = new ArrayList<Size>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        if (cursor.moveToFirst()) {
            do {
                Size size = new Size(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5),cursor.getString(6));
                sizeList.add(size);
            } while (cursor.moveToNext());
        }

        return sizeList;
    }

    public void updateSizeBookmarkName(String tableName, int id, String newBookmarkName) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE " + tableName + "SET " + KEY_BOOKMARK_NAME + " = " + newBookmarkName + " WHERE _id = " + Integer.toString(id) + ";");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_BOOKMARK_NAME, newBookmarkName);

        db.update(tableName, contentValues, DatabaseHelper.KEY_ID + "=?", new String[] {String.valueOf(id)});
    }

    // Private Methods
    //
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= 1) {
            dropTable(db, TABLE_MEN);
            dropTable(db, TABLE_WOMEN);
            dropTable(db, TABLE_YOUTH);
            dropTable(db, TABLE_KIDS);
            dropTable(db, TABLE_BABIES);
        }

        createTable(db, TABLE_MEN);
        createTable(db, TABLE_WOMEN);
        createTable(db, TABLE_YOUTH);
        createTable(db, TABLE_KIDS);
        createTable(db, TABLE_BABIES);

        insertData(db, TABLE_MEN, "7", "39", "6", "8.8", "25", "James");
        insertData(db, TABLE_MEN, "7.5", "40", "6.5", "25.5", "10", "");
        insertData(db, TABLE_MEN, "8", "40.5", "7", "26", "10.2", "");
        insertData(db, TABLE_MEN, "8.5", "41", "7.5", "26.5", "10.4", "");
        insertData(db, TABLE_MEN, "9", "42", "8", "27", "10.6", "");
        insertData(db, TABLE_MEN, "9.5", "42.5", "8.5", "27.5", "10.8", "");
        insertData(db, TABLE_MEN, "10", "43", "9", "28", "11", "");
        insertData(db, TABLE_MEN, "10.5", "44", "9.5", "28.5", "11.2", "");
        insertData(db, TABLE_MEN, "11", "44.5", "10", "29", "11.4", "");
        insertData(db, TABLE_MEN, "11.5", "45", "10.5", "29.5", "11.6", "");
        insertData(db, TABLE_MEN, "12", "45.5", "11", "30", "11.8", "");
        insertData(db, TABLE_MEN, "12.5", "46", "11.5", "30.5", "12", "");
        insertData(db, TABLE_MEN, "13", "47", "12", "31", "12.2", "");
        insertData(db, TABLE_MEN, "13.5", "48", "12.5", "31.5", "12.4", "");
        insertData(db, TABLE_MEN, "14", "48.5", "13", "32", "12.6", "");
        insertData(db, TABLE_MEN, "14.5", "49", "13.5", "32.5", "12.8", "");
        insertData(db, TABLE_MEN, "15", "50", "14", "33", "13", "");
    }

    private void createTable(SQLiteDatabase db, String tableName) {
        db.execSQL("CREATE TABLE " + tableName + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_US + " TEXT, " +
                KEY_EURO + " TEXT, " +
                KEY_UK + " TEXT, " +
                KEY_IN + " TEXT, " +
                KEY_CM + " TEXT, " +
                KEY_BOOKMARK_NAME + " TEXT);");
    }

    private void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    private void insertData(SQLiteDatabase db, String tableName, String us, String euro, String uk, String inches,  String cm, String bookmarkName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_US, us);
        contentValues.put(KEY_EURO, euro);
        contentValues.put(KEY_UK, uk);
        contentValues.put(KEY_IN, inches);
        contentValues.put(KEY_CM, cm);
        contentValues.put(KEY_BOOKMARK_NAME, bookmarkName);
        db.insert(tableName, null, contentValues);
    }

}
