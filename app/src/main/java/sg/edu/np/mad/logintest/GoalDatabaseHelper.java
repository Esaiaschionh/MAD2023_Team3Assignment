package sg.edu.np.mad.logintest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GoalDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "goal_tracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GOALS = "goals";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DUE_DATE = "due_date";
    private static final String COLUMN_PROGRESS = "progress";

    private static final String CREATE_TABLE_GOALS = "CREATE TABLE " + TABLE_GOALS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DUE_DATE + " INTEGER,"
            + COLUMN_PROGRESS + " INTEGER)";

    public GoalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GOALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        onCreate(db);
    }

    public long insertGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, goal.getName());
        values.put(COLUMN_DESCRIPTION, goal.getDescription());
        values.put(COLUMN_DUE_DATE, goal.getDueDateMillis());
        values.put(COLUMN_PROGRESS, goal.getProgress());
        long id = db.insert(TABLE_GOALS, null, values);
        db.close();
        return id;
    }

    public List<Goal> getAllGoals() {
        List<Goal> goalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_GOALS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                long dueDateMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_DUE_DATE));
                int progress = cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRESS));

                Goal goal = new Goal(id, name, description, dueDateMillis, progress);
                goalList.add(goal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return goalList;
    }

}