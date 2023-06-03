package top.yh.eap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import top.yh.eap.database.EmpDBHelper;
import top.yh.eap.entity.Employee;
import top.yh.eap.util.ViewUtil;


@SuppressLint({"NonConstantResourceId", "DefaultLocale"})
public class EmployeeLoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private TextView tvp;
    private EditText etpw;
    private Button bf;
    private CheckBox cr;
    private EditText etp;
    private Button btr;
    private int etpMaxLength = 11;
    private int etpwMaxLength = 6;
    private String verifyCode;
    private RadioButton rbp;
    private RadioButton rbvc;
    private ActivityResultLauncher<Intent> forget;
    private ActivityResultLauncher<Intent> register;
    private Button btl;
    private String phone;
    private EmpDBHelper empDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        //设置单选框
        RadioGroup rl = findViewById(R.id.rg_login);
        //密码输入选择框
        rbp = findViewById(R.id.rb_password);
        //验证码输入选择框
        rbvc = findViewById(R.id.rb_verify_code);
        //密码登录textView
        tvp = findViewById(R.id.tv_password);
        //密码登录editText
        etpw = findViewById(R.id.et_password);
        //忘记密码选择框
        bf = findViewById(R.id.btn_forget);
        //记住密码选择框
        cr = findViewById(R.id.ck_remember);
        //手机号码的输入框
        etp = findViewById(R.id.et_phone);
        //登录按钮
        btl = findViewById(R.id.btn_login);
        btr = findViewById(R.id.btn_register);
        //设置监听器
        btr.setOnClickListener(view -> {
            //携带手机号码跳转
            register.launch(new Intent(EmployeeLoginActivity.this, EmployeeRegisterActivity.class));
        });
        rl.setOnCheckedChangeListener(this);
        etp.addTextChangedListener(new HideTextWatcher(etp, etpMaxLength));
        etpw.addTextChangedListener(new HideTextWatcher(etpw, etpwMaxLength));
        etpw.setOnFocusChangeListener((view, b) -> {
            if(b){
                //获取焦点之后
                Employee employee = empDBHelper.queryByPhone(etp.getText().toString());
                if (employee != null) {
                    etpw.setText(employee.getPassword());
                    cr.setChecked(true);
                }else{
                    if("".equals(etpw.getText().toString())) {
                        etpw.setText("");
                        cr.setChecked(false);
                    }
                }
            }
        });
        bf.setOnClickListener(this);
        btl.setOnClickListener(this);
        forget = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent i = result.getData();
            if (i != null && result.getResultCode() == RESULT_OK) {
                //修改密码
                String password = i.getStringExtra("newPassword");
                Employee employee = empDBHelper.queryByPhone(phone);
                employee.setPassword(password);
                empDBHelper.save(employee);
            }
        });
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent i = result.getData();
            if (i != null && result.getResultCode() == RESULT_OK) {
                ViewUtil.showToast(EmployeeLoginActivity.this,"注册成功");
            }
        });
    }

    private void reload() {
        Employee employee = empDBHelper.queryTop();
        if (employee == null || !employee.isRemember()) {
            return;
        }
        etp.setText(employee.getPhone());
        etpw.setText(employee.getPassword());
        cr.setChecked(true);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            //密码登录
            case R.id.rb_password:
                //将验证码登录改为密码登录
                tvp.setText(getString(R.string.login_password));
                //输入框的提示消息
                etpw.setHint(getString(R.string.input_password));
                //改为忘记密码
                bf.setText(getString(R.string.forget_password));
                //记住密码设置为可见的
                cr.setVisibility(View.VISIBLE);
                break;
            //验证码登录
            case R.id.rb_verify_code:
                //将密码登录改为验证码登录
                tvp.setText(getString(R.string.verify_code));
                //输入框的提示信息
                etpw.setHint(getString(R.string.input_verify_code));
                //忘记密码改为获取验证码
                bf.setText(getString(R.string.get_verify_code));
                //将记住密码的选择框改为隐藏
                cr.setVisibility(View.GONE);
                break;

            default:
        }
    }

    @Override
    public void onClick(View view) {
        phone = etp.getText().toString();
        String password = etpw.getText().toString();
        //判断手机号码长度
        if (phone.length() < etpMaxLength) {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        Employee employee = empDBHelper.queryByPhone(phone);
        if (employee == null) {
            ViewUtil.showToast(this,"该用户没有注册");
            return;
        }
        switch (view.getId()) {
            case R.id.btn_forget:
                //忘记密码和 获取验证码的按钮
                //判断时验证码还是忘记密码
                if (rbp.isChecked()) {
                    //密码方式
                    //携带手机号码跳转
                    Intent intent = new Intent(this, EmployeeLoginForgetActivity.class);
                    intent.putExtra("phone", phone);
                    forget.launch(intent);
                } else if (rbvc.isChecked()) {
                    //验证码
                    verifyCode = ViewUtil.getVerifyCode(this);
                }
                break;
            case R.id.btn_login:
                //登录按钮
                //密码校验
                if (rbp.isChecked()) {
                    if(!password.equals(employee.getPassword())){
                        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //没有问题
                    loginSuccess(employee);
                } else if (rbvc.isChecked()) {
                    //验证码
                    if (!verifyCode.equals(etpw.getText().toString())) {
                        Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginSuccess(employee);
                }
                break;
            default:
        }
    }

    private void loginSuccess(Employee employee) {
        empDBHelper.save(employee);
        //登录之后
        Intent intent = new Intent(this, EmployeeSignInActivity.class);
        intent.putExtra("username",employee.getUsername());
        intent.putExtra("phone",employee.getPhone());
        startActivity(intent);
        finish();
    }

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
        reload();
    }

    private class HideTextWatcher implements TextWatcher {
        private EditText et;
        private int maxLength;

        public HideTextWatcher(EditText et, int maxLength) {
            this.et = et;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == maxLength) {
                //隐藏输入法
                ViewUtil.hideOneInputMethod(EmployeeLoginActivity.this, et);
            }
        }
    }
}