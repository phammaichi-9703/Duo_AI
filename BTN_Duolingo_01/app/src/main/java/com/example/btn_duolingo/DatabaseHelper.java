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
    private static final int DATABASE_VERSION = 2;

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
                COLUMN_USERNAME + " TEXT UNIQUE, " +
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

        insertSampleExercises(db);
        insertSampleUsers(db);
    }

    private void insertSampleUsers(SQLiteDatabase db) {
        String[][] users = {
                {"nguyena", "pass123", "Nguyen Van A", "01/01/1990", "Hanoi", "0987654321", "100", "5"},
                {"lethib", "pass123", "Le Thi B", "05/05/1995", "HCM City", "0912345678", "250", "10"},
                {"tranvanc", "pass123", "Tran Van C", "10/10/1988", "Da Nang", "0905556667", "50", "2"},
                {"phamthid", "pass123", "Pham Thi D", "15/03/2000", "Hai Phong", "0944332211", "300", "15"},
                {"hoangvane", "pass123", "Hoang Van E", "20/12/1992", "Can Tho", "0933221100", "150", "7"},
                {"vuthif", "pass123", "Vu Thi F", "25/08/1997", "Hue", "0922110099", "400", "20"},
                {"dangvang", "pass123", "Dang Van G", "12/06/1985", "Nha Trang", "0911009988", "80", "4"},
                {"buithih", "pass123", "Bui Thi H", "30/01/1993", "Vinh", "0900998877", "200", "8"},
                {"duongvani", "pass123", "Duong Van I", "18/04/1991", "Nam Dinh", "0988776655", "120", "6"},
                {"ngohtk", "pass123", "Ngo Thi K", "22/11/1999", "Bac Ninh", "0977665544", "350", "12"}
        };

        for (String[] user : users) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, user[0]);
            values.put(COLUMN_PASSWORD, user[1]);
            values.put(COLUMN_FULLNAME, user[2]);
            values.put(COLUMN_DOB, user[3]);
            values.put(COLUMN_ADDRESS, user[4]);
            values.put(COLUMN_PHONE, user[5]);
            values.put(COLUMN_XP, Integer.parseInt(user[6]));
            values.put(COLUMN_STREAK, Integer.parseInt(user[7]));
            db.insert(TABLE_USERS, null, values);
        }
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

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
    }

    public boolean updateUserInfo(String oldUsername, String newUsername, String fullName, String password, String dob, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        values.put(COLUMN_FULLNAME, fullName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_PHONE, phone);

        int result = db.update(TABLE_USERS, values, COLUMN_USERNAME + "=?", new String[]{oldUsername});
        return result > 0;
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