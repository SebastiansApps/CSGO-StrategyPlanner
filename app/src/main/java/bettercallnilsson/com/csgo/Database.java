//
// CREATED BY SEBASTIAN NILSSON
//

package bettercallnilsson.com.csgo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

/**
 * Created by sebastiannilsson on 2017-03-04.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "strategyDB.db";
    public static final String TABLE_STRATEGIES = "strategies";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MAPNAME = "mapname";
    public static final String COLUMN_TEAM = "team";
    public static final String COLUMN_DESCRIPTION = "description";


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STRATEGIES_TABLE = "CREATE TABLE " +
                TABLE_STRATEGIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_MAPNAME + " TEXT," +
                COLUMN_TEAM + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_STRATEGIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STRATEGIES);
        onCreate(db);

    }

    public void addStrategy(Strategy strategy) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, strategy.getTitle());
        values.put(COLUMN_MAPNAME, strategy.getMapname());
        values.put(COLUMN_TEAM, strategy.getTeam());
        values.put(COLUMN_DESCRIPTION, strategy.getDescription());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_STRATEGIES, null, values);
        db.close();
    }

    public Strategy findStrategy(String strategyname) {
        String query = "SELECT * FROM " + TABLE_STRATEGIES + " WHERE " + COLUMN_MAPNAME + " = \"" + strategyname + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Strategy strategy = new Strategy();

        if (cursor.moveToLast()) {
            cursor.moveToLast();
            strategy.setID(Integer.parseInt(cursor.getString(0)));
            strategy.setTitle(cursor.getString(1));
            strategy.setMapname(cursor.getString(2));
            strategy.setTeam(cursor.getString(3));
            strategy.setDescription(cursor.getString(4));

            cursor.moveToNext();
            cursor.close();
        } else {
            strategy = null;
        }

        db.close();
        return strategy;
    }

    public Strategy nextStrategy(String strategyname) {
        String query = "SELECT * FROM " + TABLE_STRATEGIES + " WHERE " + COLUMN_MAPNAME + " = \"" + strategyname + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Strategy strategy = new Strategy();

        Random rand = new Random();
        int a = 0;
        if (cursor.getCount() > 0) {
            a = rand.nextInt(cursor.getCount());

        }
        while (!cursor.isAfterLast()) {
            if (cursor.moveToFirst()) {
                cursor.move(a);
                strategy.setID(Integer.parseInt(cursor.getString(0)));
                strategy.setTitle(cursor.getString(1));
                strategy.setMapname(cursor.getString(2));
                strategy.setTeam(cursor.getString(3));
                strategy.setDescription(cursor.getString(4));

                cursor.close();
                return strategy;
            } else {
                strategy = null;
            }
        }

        db.close();
        return null;
    }

    public boolean deleteStrategy() {

        boolean result = false;

        String query = "SELECT * FROM " + TABLE_STRATEGIES;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Strategy strategy = new Strategy();

        if (cursor.moveToFirst()) {
            strategy.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_STRATEGIES, null, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public void deleteEntry(int row) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_STRATEGIES, COLUMN_ID + "=" + row, null);

    }

}