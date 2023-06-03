package top.yh.eap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import top.yh.eap.entity.Admin;

/**
 * @user
 * @date
 */
public class AdminDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "admin.db";
    private static final String TABLE_NAME = "admin";
    private static final int DB_VERSION = 1;
    private static AdminDBHelper ud = null;
    /**
     * 读实例
     */
    private SQLiteDatabase rsd = null;
    /**
     * 写实例
     */
    private SQLiteDatabase wsd = null;

    private AdminDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static AdminDBHelper getInstance(Context context) {
        if (ud == null) {
            ud = new AdminDBHelper(context);
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
                "phone varchar not null," +
                "username varchar not null," +
                "password integer not null);";
        sqLiteDatabase.execSQL(sql);
        //提前插入一条数据
        sqLiteDatabase.execSQL("insert into " + TABLE_NAME + " (phone, username, password) values ('17371909807','yu','123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void save(Admin admin) {
        //存在则先删除再添加
        try {
            wsd.beginTransaction();
            delete(admin);
            insert(admin);
            wsd.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wsd.endTransaction();
        }
    }

    public long delete(Admin e) {
        return wsd.delete(TABLE_NAME, "phone=?", new String[]{e.getPhone()});
    }

    public long insert(Admin admin) {
        ContentValues values = new ContentValues();
        values.put("phone", admin.getPhone());
        values.put("username", admin.getUsername());
        values.put("password", admin.getPassword());
        return wsd.insert(TABLE_NAME, null, values);
    }


    public Admin queryByUsernameAndPassword(String username,String password) {
        Admin e = null;
        Cursor cursor = rsd.query(TABLE_NAME, null, "username=? and password=?", new String[]{username,password}, null, null, null);
        if (cursor.moveToNext()) {
            e = new Admin();
            e.setId(cursor.getInt(0));
            e.setPhone(cursor.getString(1));
            e.setUsername(cursor.getString(2));
            e.setPassword(cursor.getString(3));
        }
        return e;
    }
}
