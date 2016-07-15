package com.example.wang.scrollerLayoutView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button btn2;
    private int leftBorder;
    private int rightBorder;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.relative);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.scrollTo(-100, -50);
                int scrollerX = relativeLayout.getScrollX();
                textView.setText("scrollTo>>>scrollerX:" + scrollerX);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.scrollBy(-30, -20);
                int scrollerX = relativeLayout.getScrollX();
                textView2.setText("scrollBy>>>>scrollerX:" + scrollerX);
            }
        });
    }


}
