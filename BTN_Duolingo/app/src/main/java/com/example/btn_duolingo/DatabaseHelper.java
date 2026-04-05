package com.example.btn_duolingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "duolingo.db";
    private static final int DATABASE_VERSION = 1;

    // Table Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULLNAME = "full_name";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_XP = "xp";
    public static final String COLUMN_STREAK = "streak";

    // Table Exercises
    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EX_ID = "id";
    public static final String COLUMN_EX_TITLE = "title";
    public static final String COLUMN_EX_DESC = "description";
    public static final String COLUMN_EX_QUESTION = "question";
    public static final String COLUMN_EX_OPTIONS = "options";
    public static final String COLUMN_EX_ANSWER = "answer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_FULLNAME + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_XP + " INTEGER DEFAULT 0, " +
                COLUMN_STREAK + " INTEGER DEFAULT 0)";

        String createExercisesTable = "CREATE TABLE " + TABLE_EXERCISES + " (" +
                COLUMN_EX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EX_TITLE + " TEXT, " +
                COLUMN_EX_DESC + " TEXT, " +
                COLUMN_EX_QUESTION + " TEXT, " +
                COLUMN_EX_OPTIONS + " TEXT, " +
                COLUMN_EX_ANSWER + " TEXT)";

        db.execSQL(createUsersTable);
        db.execSQL(createExercisesTable);

        // Chèn dữ liệu mẫu 10 bài tập
        insertSampleExercises(db);
    }

    private void insertSampleExercises(SQLiteDatabase db) {
        String[][] exercises = {
                {"Lesson 1: Basics", "Learn basic greetings", "Hello, how are you?", "Xin chào|tôi|bạn|khỏe|không?|cảm ơn", "Xin chào bạn khỏe không?"},
                {"Lesson 2: Family", "Names of family members", "This is my mother", "Đây|là|mẹ|của|tôi|bố", "Đây là mẹ của tôi"},
                {"Lesson 3: Food", "Common food items", "I like eating apples", "Tôi|thích|ăn|táo|quả|uống", "Tôi thích ăn quả táo"},
                {"Lesson 4: Colors", "Primary colors", "The sky is blue", "Bầu trời|màu|xanh|đỏ|vàng|là", "Bầu trời là màu xanh"},
                {"Lesson 5: Animals", "Common pets", "I have a small dog", "Tôi|có|một|con|chó|nhỏ|mèo", "Tôi có một con chó nhỏ"},
                {"Lesson 6: Time", "Telling time", "What time is it?", "Mấy|giờ|rồi?|bây giờ|là", "Bây giờ là mấy giờ rồi?"},
                {"Lesson 7: School", "Classroom objects", "Where is my book?", "Sách|của|tôi|ở|đâu?|cái", "Sách của tôi ở đâu?"},
                {"Lesson 8: Weather", "Talking about weather", "It is raining today", "Hôm nay|trời|đang|mưa|nắng|lạnh", "Hôm nay trời đang mưa"},
                {"Lesson 9: Places", "City locations", "The park is very beautiful", "Công viên|rất|đẹp|xấu|to|là", "Công viên là rất đẹp"},
                {"Lesson 10: Numbers", "Counting 1-10", "I have three oranges", "Tôi|có|ba|quả|cam|bốn|năm", "Tôi có ba quả cam"}
        };

        for (String[] ex : exercises) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EX_TITLE, ex[0]);
            values.put(COLUMN_EX_DESC, ex[1]);
            values.put(COLUMN_EX_QUESTION, ex[2]);
            values.put(COLUMN_EX_OPTIONS, ex[3]);
            values.put(COLUMN_EX_ANSWER, ex[4]);
            db.insert(TABLE_EXERCISES, null, values);
        }
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXERCISES, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EX_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_QUESTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_OPTIONS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EX_ANSWER))
                );
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return exercises;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }
}