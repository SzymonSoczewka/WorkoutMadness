package team12.workoutmadness.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import team12.workoutmadness.BE.Workout;

public class WorkoutDB implements IWorkoutDB{

    private static final String DATABASE_NAME = "sqlite.WorkoutDB";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Workouts";

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteStatement insertStatement;

    public WorkoutDB(Context context){
        OpenHelper openHelper = new OpenHelper(context);
        sqLiteDatabase = openHelper.getWritableDatabase();

        String INSERT = "insert into " + TABLE_NAME
                + "(name,days) values (?,?)";

        insertStatement = sqLiteDatabase.compileStatement(INSERT);
    }
    @Override
    public long addWorkout(Workout workout) {

        insertStatement.bindString(1, workout.getName());
        byte[] listOfDays = ObjectConverter.makebyte(workout.getDays());
        insertStatement.bindBlob(2, listOfDays);
        return  insertStatement.executeInsert();
    }

    @Override
    public void updateWorkout(Workout workout) {
        ContentValues cv = new ContentValues();
        cv.put("name",workout.getName());
        cv.put("days",ObjectConverter.makebyte(workout.getDays()));
        sqLiteDatabase.update(TABLE_NAME, cv, "id="+workout.getId(), null);
        System.out.println("Workout updated");
    }

    @Override
    public void deleteWorkout(int workoutID) {
        sqLiteDatabase.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(workoutID),});
    }

    @Override
    public ArrayList<Workout> getWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                new String[] {"id","name","days"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.v("Workout ID: ", cursor.getString(0));
                workouts.add(new Workout(
                        cursor.getInt(0),
                        cursor.getString(1),
                        ObjectConverter.read(cursor.getBlob(2))
                ));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return workouts;
    }
    @Override
    public int getNextID(){
        String query = "SELECT MAX(id) FROM "+TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while(cursor.moveToNext());
        }
        return id+1;

    }
    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "days BLOB)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
