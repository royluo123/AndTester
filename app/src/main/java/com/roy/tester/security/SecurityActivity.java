package com.roy.tester.security;

import java.security.KeyPair;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roy.base.util.SecurityUtils;
import com.roy.base.util.StringUtils;
import com.roy.tester.mytester.R;

public class SecurityActivity extends Activity {
    private TextView mTextView;
    private EditText mEditText;
    private byte[] mRsaPublicKey;
    private byte[] mRsaPrivateKey;
    private byte[] mRsaResult;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState, @Nullable final PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.security_layout);

        mTextView = (TextView)findViewById(R.id.security_text);
        mEditText = (EditText)findViewById(R.id.security_edit);

        Button btnRsak = (Button)findViewById(R.id.security_btn_rsak);
        btnRsak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                KeyPair keyPair = SecurityUtils.generateRSAKey(512);
                if(keyPair != null){
                    mRsaPrivateKey = keyPair.getPrivate().getEncoded();
                    mRsaPublicKey = keyPair.getPublic().getEncoded();
                }
            }
        });

        Button btnRsae = (Button)findViewById(R.id.security_btn_rsae);
        btnRsae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String src = mEditText.getText().toString();
                if(StringUtils.isNotEmpty(src) && mRsaPublicKey != null){
                    mRsaResult = SecurityUtils.encryptByRSA(src.getBytes(), mRsaPublicKey);
                    if(mRsaResult != null){
                        mTextView.setText(new String(mRsaResult));
                    }
                }
            }
        });

        Button btnRsad = (Button)findViewById(R.id.security_btn_rsad);
        btnRsad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                if(mRsaResult != null && mRsaPrivateKey != null){
                    byte[] data = SecurityUtils.decryptByRSA(mRsaResult, mRsaPrivateKey);
                    if(data != null){
                        mTextView.setText(new String(data));
                    }
                }
            }
        });
        Button btnAese = (Button)findViewById(R.id.security_btn_aese);
        Button btnAesd = (Button)findViewById(R.id.security_btn_aesd);
        Button btnChachae = (Button)findViewById(R.id.security_btn_chachae);
        Button btnChaChad = (Button)findViewById(R.id.security_btn_chachad);
    }
}

