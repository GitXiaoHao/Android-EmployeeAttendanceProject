package top.yh.eap.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.Random;

/**
 * @user
 * @date
 */
public class ViewUtil {
    public static void hideOneInputMethod(Activity ac, View v) {
        //从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) ac.getSystemService(Context.INPUT_METHOD_SERVICE);
        //关闭软硬盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /***
     * 生成验证码
     * @return
     */
    public static String getVerifyCode(Activity activity) {
        //生成六位随机验证码
        String verifyCode = String.format("%06d", new Random().nextInt(999999));
        //弹出提醒对话框 提示用户记住验证码
        new AlertDialog.Builder(activity)
                .setTitle("请记住验证码")
                .setMessage("本次验证码是" + verifyCode)
                .setPositiveButton("好的", null)
                .create().show();
        return verifyCode;
    }

    public static void showToast(Context context, String desc) {
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }
}
