package com.example.levunam_ktra2_bai2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteCalendarHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "lichthi.db";
    private static final int DB_VERSION = 1;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public SQLiteCalendarHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE lichthiDB(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "date TEXT," +
                "time TEXT," +
                "type TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void addCalendar(Calendar calendar){
        String sql = "INSERT INTO lichthiDB(name, date, time, type) VALUES(?,?,?,?)";
        String[] args = {calendar.getTenMon(), calendar.getNgayThi(), calendar.getGioBatDau(), calendar.getKieuThi()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }

    public List<Calendar> getAllCalendar() throws ParseException {
        List<Calendar> calendars = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("lichthiDB", null, null, null, null, null, null);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            String name = resultSet.getString(1);
            String date = resultSet.getString(2);
            String time = resultSet.getString(3);
            String type = resultSet.getString(4);
            calendars.add(new Calendar(id, name, date, time, type));
        }
        return calendars;
    }

    public List<Calendar> searchCalendar(String clause) throws ParseException {
        List<Calendar> calendars = new ArrayList<>();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" + clause + "%"};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor cursor = statement.query("lichthiDB", null, whereClause, whereArgs, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String time = cursor.getString(3);
            String type = cursor.getString(4);
            calendars.add(new Calendar(id, name, date, time, type));
        }
        cursor.close();
        return calendars;
    }

    public int update(Calendar calendar){
        ContentValues values = new ContentValues();
        values.put("name", calendar.getTenMon());
        values.put("date", calendar.getNgayThi());
        values.put("time", calendar.getGioBatDau());
        values.put("type", calendar.getKieuThi());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(calendar.getId())};
        SQLiteDatabase statement = getWritableDatabase();
        return statement.update("lichthiDB", values, whereClause, whereArgs);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int deleteById(int id) throws ParseException {
        Calendar calendar = getCalendarById(id);
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date hienTai = dateFormat.parse(now.toString());
        Date ngayThi = dateFormat.parse(calendar.getNgayThi());
        if (hienTai.getDate() > ngayThi.getDate()
                || hienTai.getMonth() > ngayThi.getMonth()
                || hienTai.getYear() > ngayThi.getYear()){
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            SQLiteDatabase statement = getWritableDatabase();
            return statement.delete("lichthiDB", whereClause, whereArgs);
        }else return -1;
    }

    public Calendar getCalendarById(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor rs = statement.query("lichthiDB", null, whereClause, whereArgs, null, null, null);
        if (rs.moveToNext()){
            String name = rs.getString(1);
            String date = rs.getString(2);
            String time = rs.getString(3);
            String type = rs.getString(3);

            return new Calendar(id, name, date, time, type);
        }
        return null;
    }
}
