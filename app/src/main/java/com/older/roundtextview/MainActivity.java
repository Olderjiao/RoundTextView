package com.older.roundtextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ltb.myroundtextlibrary.widget.MyRoundTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final MyRoundTextView myRoundTextView = findViewById(R.id.btn2);
        myRoundTextView.setContent("确认", "取消");
        myRoundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRoundTextView.setFull(!myRoundTextView.isFull());
            }
        });

    }
}