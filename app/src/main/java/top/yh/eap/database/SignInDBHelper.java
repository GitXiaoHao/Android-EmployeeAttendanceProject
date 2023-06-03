package top.yh.eap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import top.yh.eap.entity.SignIn;

/**
 * @user
 * @date
 */
public class SignInDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sign.db";
    private static final String TABLE_NAME = "sign";
    private static final int DB_VERSION = 1;
    private static SignInDBHelper ud = null;
    /**
     * 读实例
     */
    private SQLiteDatabase rsd = null;
    /**
     * 写实例
     */
    private SQLiteDatabase wsd = null;

    private SignInDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SignInDBHelper getInstance(Context context) {
        if (ud == null) {
            ud = new SignInDBHelper(context);
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
                "time text(50) not null);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insert(SignIn SignIn) {
        ContentValues values = new ContentValues();
        values.put("username", SignIn.getUsername());
        values.put("phone", SignIn.getPhone());
        values.put("time", SignIn.getTime());
        return wsd.insert(TABLE_NAME, null, values);
    }
    public List<SignIn> queryByPhone(String phone){
        SignIn e = null;
        List<SignIn> list = new ArrayList<>();
        Cursor cursor = rsd.query(TABLE_NAME, null, "phone=?", new String[]{phone}, null, null, null);
        while (cursor.moveToNext()) {
            e = new SignIn();
            e.setId(cursor.getInt(0));
            e.setUsername(cursor.getString(1));
            e.setPhone(cursor.getString(2));
            e.setTime(cursor.getString(3));
            list.add(e);
        }
        return list;
    }
    public List<SignIn> queryAll(){
        SignIn e = null;
        List<SignIn> list = new ArrayList<>();
        Cursor cursor = rsd.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            e = new SignIn();
            e.setId(cursor.getInt(0));
            e.setUsername(cursor.getString(1));
            e.setPhone(cursor.getString(2));
            e.setTime(cursor.getString(3));
            list.add(e);
        }
        return list;
    }
}
