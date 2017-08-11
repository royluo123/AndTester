package com.roy.tester.vpn;

import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.roy.tester.mytester.R;

/**
 * Created by Administrator on 2017/3/8.
 */
public class ToyVpnActivity extends Activity implements View.OnClickListener {
    private EditText mServerAddress;
    private EditText mServerPort;
    private EditText mSharedSecret;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpn_layout);
        mServerAddress = (EditText) findViewById(R.id.vpn_ev_server);
        mServerPort = (EditText) findViewById(R.id.vpn_ev_port);
        mSharedSecret = (EditText) findViewById(R.id.vn_ev_secret);
        findViewById(R.id.vpn_btn_connnect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, 0);
        } else {
            onActivityResult(0, RESULT_OK, null);
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        if (result == RESULT_OK) {
            String prefix = getPackageName();
            Intent intent = new Intent(this, ToyVpnService.class)
                    .putExtra(prefix + ".ADDRESS", mServerAddress.getText().toString())
                    .putExtra(prefix + ".PORT", mServerPort.getText().toString())
                    .putExtra(prefix + ".SECRET", mSharedSecret.getText().toString());
            startService(intent);
        }
    }
}
