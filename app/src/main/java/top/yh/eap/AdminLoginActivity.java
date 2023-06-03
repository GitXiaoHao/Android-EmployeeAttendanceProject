package top.yh.eap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import top.yh.eap.database.AdminDBHelper;
import top.yh.eap.entity.Admin;
import top.yh.eap.util.ViewUtil;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private AdminDBHelper adminDBHelper;
    private int passwordMaxLength = 6;
    @Override
    protected void onStart() {
        super.onStart();
        adminDBHelper = AdminDBHelper.getInstance(this);
        adminDBHelper.openReadLink();
        adminDBHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //adminDBHelper.closeLink();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        initView();
    }

    private void initView() {
        this.editTextUsername = findViewById(R.id.et_user_name);
        this.editTextPassword = findViewById(R.id.et_password);
        this.btnLogin = findViewById(R.id.btn_login);
        this.btnLogin.setOnClickListener(view -> {
            String username = this.editTextUsername.getText().toString();
            String password = this.editTextPassword.getText().toString();
            if(username.length() <= 0){
                ViewUtil.showToast(AdminLoginActivity.this,"请输入用户名");
                return;
            }
            if (password.length() < passwordMaxLength) {
                ViewUtil.showToast(AdminLoginActivity.this,"密码长度小于6位");
                return;
            }
            //判断用户名和密码是否正确
            Admin admin = this.adminDBHelper.queryByUsernameAndPassword(username, password);
            if (admin != null) {
                //登录成功
                startActivity(new Intent(this,AdminMainActivity.class));
                finish();
            }
        });
    }
}