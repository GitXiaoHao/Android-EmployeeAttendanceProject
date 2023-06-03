package top.yh.eap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import top.yh.eap.util.ViewUtil;

public class EmployeeLoginForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private String phone;
    private String verifyCode;
    private EditText etpwOne;
    private EditText etpwTwo;
    private EditText etvc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login_forget);
        //获取手机号码
        phone = getIntent().getStringExtra("phone");
        etpwOne = findViewById(R.id.et_password_first);
        etpwTwo = findViewById(R.id.et_password_second);
        etvc = findViewById(R.id.et_verify_code);
        findViewById(R.id.btn_verify_code).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_verify_code:
                verifyCode = ViewUtil.getVerifyCode(this);
                break;
            case R.id.btn_confirm:
                //点击了确定按钮
                String one = etpwOne.getText().toString();
                String two = etpwTwo.getText().toString();
                if (one.length() < 6) {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!one.equals(two)) {
                    Toast.makeText(this, "两次输入的验证码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!verifyCode.equals(etvc.getText().toString())) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                //返回上一页面
                setResult(RESULT_OK,new Intent().putExtra("newPassword",one));
                finish();
                break;
            default:
        }
    }
}