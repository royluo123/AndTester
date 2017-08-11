package com.roy.tester.tpaint;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/11/11.
 */
public class PaintActivity extends Activity {
    private TempTestManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new TempTestManager(this);
        setContentView(manager.getTempView());
    }
}
