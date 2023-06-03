package top.yh.eap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_admin_login).setOnClickListener(view -> {
            startActivity(new Intent(this,AdminLoginActivity.class));
            finish();
        });
        findViewById(R.id.btn_emp_login).setOnClickListener(view -> {
            startActivity(new Intent(this,EmployeeLoginActivity.class));
            finish();
        });
    }
}