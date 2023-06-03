package top.yh.eap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.yh.eap.database.SignInDBHelper;
import top.yh.eap.entity.SignIn;

public class AdminMainActivity extends AppCompatActivity {
    private Button btnLogOut;
    private Button btnEmpStatus;
    private ListView lvStatus;
    private SignInDBHelper signInDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        btnLogOut = findViewById(R.id.btn_log_out);
        btnEmpStatus = findViewById(R.id.btn_emp_check_status);
        lvStatus = findViewById(R.id.lv);
        btnLogOut.setOnClickListener(view -> {
            //返回上一页面
            startActivity(new Intent(AdminMainActivity.this,MainActivity.class));
            finish();
        });
        btnEmpStatus.setOnClickListener(view -> {
            show();
        });
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
        //signInDBHelper.closeLink();
    }
    private void show() {
        List<SignIn> signInList = this.signInDBHelper.queryAll();
        List<Map<String, String>> mapList = new ArrayList<>();
        signInList.forEach(signIn -> {
            Map<String, String> map = new HashMap<>(2);
            map.put("username", signIn.getUsername());
            map.put("time", signIn.getTime() );
            mapList.add(map);
        });
        lvStatus.setAdapter(new SimpleAdapter(this, mapList, R.layout.activity_list_view_item,
                new String[]{"username", "time"}, new int[]{R.id.textTitle, R.id.textContent}));
    }
}