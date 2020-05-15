package team12.workoutmadness.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import team12.workoutmadness.BE.Profile;
import team12.workoutmadness.BE.Workout;

public class WorkoutDB implements IWorkoutDB{

    private static final String DATABASE_NAME = "sqlite.WorkoutDB";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_WORKOUT = "Workouts";
    private static final String TABLE_PROFILE = "Profiles";

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteStatement insertWorkoutStatement;
    private SQLiteStatement insertProfileStatement;

    public WorkoutDB(Context context){
        OpenHelper openHelper = new OpenHelper(context);
        sqLiteDatabase = openHelper.getWritableDatabase();

        String insertWorkout = "insert into " + TABLE_WORKOUT
                + "(name,days) values (?,?)";

        String insertProfile = "insert into " + TABLE_PROFILE
                + "(height,weight,arm,chest,hips,waist,thighs,calves) values (?,?,?,?,?,?,?,?)";

        insertWorkoutStatement = sqLiteDatabase.compileStatement(insertWorkout);
        insertProfileStatement = sqLiteDatabase.compileStatement(insertProfile);
    }
    @Override
    public long addWorkout(Workout workout) {

        insertWorkoutStatement.bindString(1, workout.getName());
        byte[] listOfDays = ObjectConverter.toByte(workout.getDays());
        insertWorkoutStatement.bindBlob(2, listOfDays);
        return  insertWorkoutStatement.executeInsert();
    }

    @Override
    public long addProfile(Profile profile) {
        insertProfileStatement.bindLong(1,profile.getHeight());
        insertProfileStatement.bindLong(2,profile.getWeight());
        insertProfileStatement.bindLong(3,profile.getArm());
        insertProfileStatement.bindLong(4,profile.getChest());
        insertProfileStatement.bindLong(5,profile.getHips());
        insertProfileStatement.bindLong(6,profile.getWaist());
        insertProfileStatement.bindLong(7,profile.getThighs());
        insertProfileStatement.bindLong(8,profile.getCalves());
        return insertProfileStatement.executeInsert();
    }

    @Override
    public void updateWorkout(Workout workout) {
        ContentValues cv = new ContentValues();
        cv.put("name",workout.getName());
        cv.put("days",ObjectConverter.toByte(workout.getDays()));
        sqLiteDatabase.update(TABLE_WORKOUT, cv, "id="+workout.getId(), null);
        System.out.println("Workout updated");
    }

    @Override
    public void updateProfile(Profile profile) {
        ContentValues cv = new ContentValues();
        cv.put("height",profile.getHeight());
        cv.put("weight",profile.getWeight());
        cv.put("arm",profile.getArm());
        cv.put("chest",profile.getChest());
        cv.put("hips",profile.getHips());
        cv.put("waist",profile.getWaist());
        cv.put("thighs",profile.getThighs());
        cv.put("calves",profile.getCalves());
        sqLiteDatabase.update(TABLE_PROFILE, cv, "id="+profile.getId(), null);
        System.out.println("Profile updated");
    }

    @Override
    public void deleteWorkout(int workoutID) {
        sqLiteDatabase.delete(TABLE_WORKOUT,"id=?",new String[]{String.valueOf(workoutID),});
    }

    @Override
    public ArrayList<Workout> getWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        Cursor cursor = sqLiteDatabase.query(TABLE_WORKOUT,
                new String[] {"id","name","days"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.v("Workout ID: ", cursor.getString(0));
                workouts.add(new Workout(
                        cursor.getInt(0),
                        cursor.getString(1),
                        ObjectConverter.fromByte(cursor.getBlob(2))
                ));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return workouts;
    }

    @Override
    public Profile getProfile() {
        Profile profile = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_PROFILE,
                new String[] {"id","height","weight","arm","chest","hips","waist","thighs","calves"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Log.v("Profile ID: ", cursor.getString(0));
                profile = new Profile((int) cursor.getLong(0));
                profile.setHeight((int) cursor.getLong(1));
                profile.setWeight((int) cursor.getLong(2));
                profile.setArm((int) cursor.getLong(3));
                profile.setChest((int) cursor.getLong(4));
                profile.setHips((int) cursor.getLong(5));
                profile.setWaist((int) cursor.getLong(6));
                profile.setThighs((int) cursor.getLong(7));
                profile.setCalves((int) cursor.getLong(8));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return profile;
    }

    @Override
    public int getNextWorkoutID(){
        String query = "SELECT MAX(id) FROM "+ TABLE_WORKOUT;
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
            db.execSQL("CREATE TABLE " + TABLE_WORKOUT
                    + "(id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "days BLOB)");
            db.execSQL("CREATE TABLE " + TABLE_PROFILE
                    + "(id INTEGER PRIMARY KEY, " +
                    "height INTEGER, " +
                    "weight INTEGER, " +
                    "arm INTEGER, " +
                    "chest INTEGER, " +
                    "hips INTEGER, " +
                    "waist INTEGER, "+
                    "thighs INTEGER, "+
                    "calves INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
            onCreate(db);
        }
    }
}
