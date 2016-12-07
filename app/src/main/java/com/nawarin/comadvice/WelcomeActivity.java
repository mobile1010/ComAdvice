package com.nawarin.comadvice;

import android.content.Intent;
import android.os.RecoverySystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class WelcomeActivity extends AppCompatActivity {

    RelativeLayout layoutW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        layoutW = (RelativeLayout) findViewById(R.id.layoutW);

    }

    public void ClickWelcome(View view){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
