package top.yh.eap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import top.yh.eap.entity.Employee;

/**
 * @user
 * @date
 */
public class EmpDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "emp.db";
    private static final String TABLE_NAME = "emp";
    private static final int DB_VERSION = 1;
    private static EmpDBHelper ud = null;
    /**
     * 读实例
     */
    private SQLiteDatabase rsd = null;
    /**
     * 写实例
     */
    private SQLiteDatabase wsd = null;

    private EmpDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static EmpDBHelper getInstance(Context context) {
        if (ud == null) {
            ud = new EmpDBHelper(context);
        }
        return ud;
    }

    /**
     * 打开数据库的读连接
     *
     * @return
     */
    public SQLiteDatabase openReadLink() {
        if (rsd == null || !rsd.isOpen()) {
            rsd = ud.getReadableDatabase();
        }
        return rsd;
    }

    /**
     * 打开数据库的写连接
     *
     * @return
     */
    public SQLiteDatabase openWriteLink() {
        if (wsd == null || !wsd.isOpen()) {
            wsd = ud.getWritableDatabase();
        }
        return wsd;
    }

    public void closeLink() {
        if (rsd != null && rsd.isOpen()) {
            rsd.close();
            rsd = null;
        }
        if (wsd != null && wsd.isOpen()) {
            wsd.close();
            wsd = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + TABLE_NAME +
                "(id integer primary key autoincrement not null," +
                "username varchar not null," +
                "phone varchar not null," +
                "password integer not null," +
                "remember integer not null);";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void save(Employee employee) {
        //存在则先删除再添加
        try {
            wsd.beginTransaction();
            delete(employee);
            insert(employee);
            wsd.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wsd.endTransaction();
        }
    }

    public long delete(Employee e) {
        return wsd.delete(TABLE_NAME, "phone=?", new String[]{e.getPhone()});
    }

    public long insert(Employee e) {
        ContentValues values = new ContentValues();
        values.put("phone", e.getPhone());
        values.put("username", e.getUsername());
        values.put("password", e.getPassword());
        values.put("remember", e.isRemember());
        return wsd.insert(TABLE_NAME, null, values);
    }

    public Employee queryTop() {
        Employee e = null;
        String sql = "select * from " + TABLE_NAME + " where remember = 1 order by id desc limit 1";
        Cursor cursor = rsd.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            e = new Employee();
            e.setId(cursor.getInt(0));
            e.setUsername(cursor.getString(1));
            e.setPhone(cursor.getString(2));
            e.setPassword(cursor.getString(3));
            e.setRemember(cursor.getInt(4) != 0);
        }
        cursor.close();
        return e;
    }

    public Employee queryByPhone(String phone) {
        Employee e = null;
        Cursor cursor = rsd.query(TABLE_NAME, null, "phone=?", new String[]{phone}, null, null, null);
        if (cursor.moveToNext()) {
            e = new Employee();
            e.setId(cursor.getInt(0));
            e.setUsername(cursor.getString(1));
            e.setPhone(cursor.getString(2));
            e.setPassword(cursor.getString(3));
            e.setRemember(cursor.getInt(4) != 0);
        }
        cursor.close();
        return e;
    }
}
