package top.yh.eap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.yh.eap.database.SignInDBHelper;
import top.yh.eap.entity.Employee;
import top.yh.eap.entity.SignIn;
import top.yh.eap.util.TimeUtil;
@SuppressLint("NonConstantResourceId")
public class EmployeeSignInActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPhone;
    private Button btnSignIn;
    private TextView tvSignSuccess;
    private Button btnAttendanceStatus;
    private Button btnLogOut;
    private SignInDBHelper signInDBHelper;
    private String phone;
    private String username;
    private ListView lvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_sign_in);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        signInDBHelper = SignInDBHelper.getInstance(this);
        signInDBHelper.openReadLink();
        signInDBHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        signInDBHelper.closeLink();
    }

    private void initView() {
        tvPhone = findViewById(R.id.tv_phone);
        btnSignIn = findViewById(R.id.btn_sign_in);
        tvSignSuccess = findViewById(R.id.tv_sign_in_success);
        btnAttendanceStatus = findViewById(R.id.btn_attendance_status);
        btnLogOut = findViewById(R.id.btn_log_out);
        lvStatus = findViewById(R.id.lv_status);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        phone = intent.getStringExtra("phone");
        tvPhone.setText(phone + ", 你好");
        btnSignIn.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnAttendanceStatus.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                //签到
                signInDBHelper.insert(new SignIn(username, phone,TimeUtil.getTime()));
                btnSignIn.setText(R.string.sign_in_success);
                //不能点击
                btnSignIn.setEnabled(false);
                signInDBHelper.queryAll();
                break;
            case R.id.btn_log_out:
                //注销登录
                //返回上一页面
                startActivity(new Intent(EmployeeSignInActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.btn_attendance_status:
                show();
               break;
            default:
        }
    }

    private void show() {
        List<SignIn> signInList = this.signInDBHelper.queryByPhone(phone);
        List<Map<String, String>> mapList = new ArrayList<>();
        signInList.forEach(signIn -> {
            Map<String, String> map = new HashMap<>(2);
            map.put("username", signIn.getUsername());
            map.put("time", signIn.getTime());
            mapList.add(map);
        });
        lvStatus.setAdapter(new SimpleAdapter(this, mapList, R.layout.activity_list_view_item,
                new String[]{"username", "time"}, new int[]{R.id.textTitle, R.id.textContent}));
    }
}