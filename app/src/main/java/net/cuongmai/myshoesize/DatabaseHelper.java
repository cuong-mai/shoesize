package net.cuongmai.myshoesize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "database";
    private static final int DB_VERSION = 6;

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

    // Constructor
    //
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    // When created
    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    } // End of onCreate()

    // When upgraded
    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    } // End of onUpgrade()

    // Get a Size object from database
    //
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
    } // End of getSize()

    // Get all Size objects from database
    //
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
    } // End of getAllSizes()

    // Update bookmark name of a Size in database
    //
    public void updateSizeBookmarkName(String tableName, int id, String newBookmarkName) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE " + tableName + "SET " + KEY_BOOKMARK_NAME + " = " + newBookmarkName + " WHERE _id = " + Integer.toString(id) + ";");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_BOOKMARK_NAME, newBookmarkName);

        db.update(tableName, contentValues, DatabaseHelper.KEY_ID + "=?", new String[] {String.valueOf(id)});
    } // End of updateSizeBookmarkName()

    // Install/Update the database stored in a device
    //
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            createTable(db, TABLE_MEN);
            createTable(db, TABLE_WOMEN);
            createTable(db, TABLE_YOUTH);
            createTable(db, TABLE_KIDS);
            createTable(db, TABLE_BABIES);

            insertData(db, TABLE_MEN, "7", "39", "6", "8.8", "25", "");
            insertData(db, TABLE_MEN, "7.5", "40", "6.5", "10", "25.5", "");
            insertData(db, TABLE_MEN, "8", "40.5", "7", "10.2", "26", "");
            insertData(db, TABLE_MEN, "8.5", "41", "7.5", "10.4", "26.5", "");
            insertData(db, TABLE_MEN, "9", "42", "8", "10.6", "27", "");
            insertData(db, TABLE_MEN, "9.5", "42.5", "8.5", "10.8", "27.5", "");
            insertData(db, TABLE_MEN, "10", "43", "9", "11", "28", "");
            insertData(db, TABLE_MEN, "10.5", "44", "9.5", "11.2", "28.5", "");
            insertData(db, TABLE_MEN, "11", "44.5", "10", "11.4", "29", "");
            insertData(db, TABLE_MEN, "11.5", "45", "10.5", "11.6", "29.5", "");
            insertData(db, TABLE_MEN, "12", "45.5", "11", "11.8", "30", "");
            insertData(db, TABLE_MEN, "12.5", "46", "11.5", "12", "30.5", "");
            insertData(db, TABLE_MEN, "13", "47", "12", "12.2", "31", "");
            insertData(db, TABLE_MEN, "13.5", "48", "12.5", "12.4", "31.5", "");
            insertData(db, TABLE_MEN, "14", "48.5", "13", "12.6", "32", "");
            insertData(db, TABLE_MEN, "14.5", "49", "13.5", "12.8", "32.5", "");
            insertData(db, TABLE_MEN, "15", "50", "14", "13", "33", "");

            insertData(db, TABLE_WOMEN, "5", "35", "2.5", "8.7", "22", "");
            insertData(db, TABLE_WOMEN, "5.5", "35.5", "3", "8.9", "22.5", "");
            insertData(db, TABLE_WOMEN, "6", "36", "3.5", "9.1", "23", "");
            insertData(db, TABLE_WOMEN, "6.5", "37", "4", "9.3", "23.5", "");
            insertData(db, TABLE_WOMEN, "7", "37.5", "4.5", "9.4", "24", "");
            insertData(db, TABLE_WOMEN, "7.5", "38", "5", "9.6", "24.5", "");
            insertData(db, TABLE_WOMEN, "8", "38.5", "5.5", "9.8", "25", "");
            insertData(db, TABLE_WOMEN, "8.5", "39", "6", "10", "25.5", "");
            insertData(db, TABLE_WOMEN, "9", "40", "6.5", "10.2", "26", "");
            insertData(db, TABLE_WOMEN, "9.5", "40.5", "7", "10.4", "26.5", "");
            insertData(db, TABLE_WOMEN, "10", "41", "7.5", "10.6", "27", "");
            insertData(db, TABLE_WOMEN, "10.5", "42", "8", "10.8", "27.5", "");
            insertData(db, TABLE_WOMEN, "11", "42.5", "8.5", "11", "28", "");
            insertData(db, TABLE_WOMEN, "12", "44", "9.5", "11.4", "29", "");

            insertData(db, TABLE_YOUTH, "3", "34", "2.5", "8.3", "21", "");
            insertData(db, TABLE_YOUTH, "3.5", "34.5", "3", "8.9", "22.5", "");
            insertData(db, TABLE_YOUTH, "4", "35", "3.5", "9.1", "23", "");
            insertData(db, TABLE_YOUTH, "4.5", "36", "4", "9.2", "23.3", "");
            insertData(db, TABLE_YOUTH, "5", "36.5", "4.5", "9.3", "23.5", "");
            insertData(db, TABLE_YOUTH, "5.5", "37", "5", "9.4", "24", "");
            insertData(db, TABLE_YOUTH, "6", "38", "5.5", "9.6", "24.5", "");
            insertData(db, TABLE_YOUTH, "6.5", "38.5", "6", "9.7", "24.6", "");
            insertData(db, TABLE_YOUTH, "7", "39", "6.5", "9.8", "25", "");

            insertData(db, TABLE_KIDS, "10.5", "27", "10", "6.5", "16.5", "");
            insertData(db, TABLE_KIDS, "11", "27.5", "10.5", "6.7", "17", "");
            insertData(db, TABLE_KIDS, "11.5", "28", "11", "6.9", "17.5", "");
            insertData(db, TABLE_KIDS, "12", "29", "11.5", "7.1", "18", "");
            insertData(db, TABLE_KIDS, "12.5", "30", "12", "7.3", "18.5", "");
            insertData(db, TABLE_KIDS, "13", "30.5", "12.5", "7.5", "19", "");
            insertData(db, TABLE_KIDS, "13.5", "31", "13", "7.7", "19.5", "");
            insertData(db, TABLE_KIDS, "1", "31.5", "13.5", "7.9", "20", "");
            insertData(db, TABLE_KIDS, "2", "32.5", "1", "8.1", "20.5", "");
            insertData(db, TABLE_KIDS, "2.5", "33", "2", "8.5", "21.5", "");
            insertData(db, TABLE_KIDS, "3", "34", "2.5", "8.7", "22", "");

            insertData(db, TABLE_BABIES, "0", "15", "0", "2.4", "6", "");
            insertData(db, TABLE_BABIES, "1", "16", "0.5", "2.8", "7", "");
            insertData(db, TABLE_BABIES, "1.5", "16.5", "1", "3.0", "7.5", "");
            insertData(db, TABLE_BABIES, "2", "17", "1.5", "3.1", "8", "");
            insertData(db, TABLE_BABIES, "2.5", "18", "2", "3.5", "9", "");
            insertData(db, TABLE_BABIES, "3", "18.5", "2.5", "3.7", "9.5", "");
            insertData(db, TABLE_BABIES, "3.5", "19", "3", "3.9", "10", "");
            insertData(db, TABLE_BABIES, "4", "19.5", "3.5", "4.1", "10.5", "");
            insertData(db, TABLE_BABIES, "4.5", "20", "4", "4.3", "11", "");
            insertData(db, TABLE_BABIES, "5", "21", "4.5", "4.7", "12", "");
            insertData(db, TABLE_BABIES, "5.5", "21.5", "5", "4.9", "12.5", "");
            insertData(db, TABLE_BABIES, "6", "22", "5.5", "5.1", "13", "");
            insertData(db, TABLE_BABIES, "6.5", "22.5", "6", "5.3", "13.5", "");
            insertData(db, TABLE_BABIES, "7", "23.5", "6.5", "5.7", "14.5", "");
            insertData(db, TABLE_BABIES, "7.5", "24", "7", "5.9", "15", "");
            insertData(db, TABLE_BABIES, "8", "24.5", "7.5", "6.1", "15.5", "");
            insertData(db, TABLE_BABIES, "8.5", "25", "8", "6.3", "16", "");
            insertData(db, TABLE_BABIES, "9", "25.5", "8.5", "6.5", "16.5", "");
            insertData(db, TABLE_BABIES, "9.5", "26", "9", "6.7", "17", "");
            insertData(db, TABLE_BABIES, "10", "26.5", "9.5", "6.9", "17.5", "");
        }
    } // End of updateDatabase()

    // Create a table
    //
    private void createTable(SQLiteDatabase db, String tableName) {
        db.execSQL("CREATE TABLE " + tableName + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_US + " TEXT, " +
                KEY_EURO + " TEXT, " +
                KEY_UK + " TEXT, " +
                KEY_IN + " TEXT, " +
                KEY_CM + " TEXT, " +
                KEY_BOOKMARK_NAME + " TEXT);");
    } // End of createTable()

    // Drop a table if exists
    //
    private void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    } // End of dropTable

    // Insert data to a row in table
    //
    private void insertData(SQLiteDatabase db, String tableName, String us, String euro, String uk, String inches,  String cm, String bookmarkName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_US, us);
        contentValues.put(KEY_EURO, euro);
        contentValues.put(KEY_UK, uk);
        contentValues.put(KEY_IN, inches);
        contentValues.put(KEY_CM, cm);
        contentValues.put(KEY_BOOKMARK_NAME, bookmarkName);
        db.insert(tableName, null, contentValues);
    } // End of insertData()
}
