package top.yh.eap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import top.yh.eap.database.EmpDBHelper;
import top.yh.eap.entity.Employee;
import top.yh.eap.util.ViewUtil;

public class EmployeeRegisterActivity extends AppCompatActivity {
    private EditText etNewUsername;
    private EditText etNewPhone;
    private EditText etPassword;
    private int etpMaxLength = 11;
    private int etpwMaxLength = 6;
    private EmpDBHelper empDBHelper;
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        empDBHelper = EmpDBHelper.getInstance(this);
        empDBHelper.openReadLink();
        empDBHelper.openWriteLink();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
        etNewUsername = findViewById(R.id.et_new_username);
        etNewPhone = findViewById(R.id.et_new_phone);
        etPassword = findViewById(R.id.et_password);
        findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            String username = etNewUsername.getText().toString();
            String phone = etNewPhone.getText().toString();
            String password = etPassword.getText().toString();
            if (password.length() < etpwMaxLength) {
                ViewUtil.showToast(EmployeeRegisterActivity.this,"密码小于六位");
                return;
            }
            if (phone.length() < etpMaxLength) {
                ViewUtil.showToast(EmployeeRegisterActivity.this,"手机号码小于11位");
                return;
            }
            if (username.length() <= 0) {
                ViewUtil.showToast(EmployeeRegisterActivity.this,"请输入用户名");
                return;
            }
            Employee employee = new Employee(username, phone, password, false);
            empDBHelper.save(employee);
            //返回上一页面
            setResult(RESULT_OK,new Intent().putExtra("username",employee.getUsername()));
            finish();
        });
    }
}