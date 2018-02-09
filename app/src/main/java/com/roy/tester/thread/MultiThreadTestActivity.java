package com.roy.tester.thread;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.roy.tester.mytester.R;
import com.roy.tester.tpaint.TempTestManager;

/**
 * Created by Administrator on 2018/2/5.
 */

public class MultiThreadTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muliti_thread_layout);

        Button bt1 = (Button)findViewById(R.id.thread_test_1);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MultiThreadTester tester = new MultiThreadTester();
                tester.testArrayList10();
            }
        });

        Button bt2 = (Button)findViewById(R.id.thread_test_2);
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MultiThreadTester tester = new MultiThreadTester();
                tester.testArrayList11();
            }
        });

        Button bt3 = (Button)findViewById(R.id.thread_test_3);
        bt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MultiThreadTester tester = new MultiThreadTester();
                tester.testArrayList12();
            }
        });

        Button bt4 = (Button)findViewById(R.id.thread_test_4);
        bt4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MultiThreadTester tester = new MultiThreadTester();
                tester.testArrayList13();
            }
        });

        Button bt5 = (Button)findViewById(R.id.thread_test_5);
        bt5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MultiThreadTester tester = new MultiThreadTester();
                tester.testArrayList2();
            }
        });
    }
}
