package com.roy.tester.tpaint;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/11/11.
 */
public class PaintActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PaintTestView(this));
    }
}
