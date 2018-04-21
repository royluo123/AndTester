package com.roy.tester.tpaint;

import java.security.KeyPair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.base.temp.KeyGenerator;
import com.roy.base.util.SecurityUtils;
import com.roy.base.util.StringUtils;
import com.roy.tester.mytester.R;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/11/11.
 */
public class PaintActivity extends Activity {
    private TempTestManager manager;

    private TextView mTextView;
    private EditText mEditText;
    private byte[] mRsaPublicKey;
    private byte[] mRsaPrivateKey;
    private byte[] mRsaResult;
    private byte[] mAesResult;
    private byte[] mChachaResult;

    private static String gAESKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_layout);
        mTextView = (TextView)findViewById(R.id.security_text);
        mEditText = (EditText)findViewById(R.id.security_edit);
        mEditText.setText("This is encrypt and decrept test!");

        gAESKey = KeyGenerator.generateRondomHexKey(32);

        Button btnRsak = (Button)findViewById(R.id.security_btn_rsak);
        btnRsak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(PaintActivity.this,"RSA Key isClicked", Toast.LENGTH_SHORT).show();
                KeyPair keyPair = SecurityUtils.generateRSAKey(2048);
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
                Toast.makeText(PaintActivity.this, "RSA Encode isClicked", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PaintActivity.this,"RSA Decode isClicked", Toast.LENGTH_SHORT).show();
                if(mRsaResult != null && mRsaPrivateKey != null){
                    byte[] data = SecurityUtils.decryptByRSA(mRsaResult, mRsaPrivateKey);
                    if(data != null){
                        mTextView.setText(new String(data));
                    }
                }
            }
        });
        Button btnAese = (Button)findViewById(R.id.security_btn_aese);
        btnAese.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                Toast.makeText(PaintActivity.this,"AES Encode isClicked", Toast.LENGTH_SHORT).show();
                String src = mEditText.getText().toString();
                if(StringUtils.isNotEmpty(src)){
                    mAesResult = SecurityUtils.encryptByAES(src.getBytes(), gAESKey);
                    if(mAesResult != null){
                        mTextView.setText(StringUtils.utf8ByteToString(mAesResult));
                    }
                }
            }
        });
        Button btnAesd = (Button)findViewById(R.id.security_btn_aesd);
        btnAesd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                Toast.makeText(PaintActivity.this,"AES decode isClicked", Toast.LENGTH_SHORT).show();
                if(mAesResult != null ){
                    byte[] data = SecurityUtils.decryptByAES(mAesResult, gAESKey);
                    if(data != null){
                        mTextView.setText(StringUtils.utf8ByteToString(data));
                    }
                }
            }
        });


        Button btnChachae = (Button)findViewById(R.id.security_btn_chachae);
        btnChachae.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                Toast.makeText(PaintActivity.this,"Chacha Encode isClicked", Toast.LENGTH_SHORT).show();
                String src = mEditText.getText().toString();
                if(StringUtils.isNotEmpty(src)){
                    mChachaResult = SecurityUtils.encryptByCHACHA20(src.getBytes(), gAESKey);
                    if(mChachaResult != null){
                        mTextView.setText(new String(mChachaResult));
                    }
                }
            }
        });
        Button btnChaChad = (Button)findViewById(R.id.security_btn_chachad);
        btnChaChad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                Toast.makeText(PaintActivity.this, "Chacha Decode isClicked", Toast.LENGTH_SHORT).show();
                if(mChachaResult != null){
                    byte[] data = SecurityUtils.decryptByCHACHA20(mRsaResult, gAESKey);
                    if(data != null){
                        mTextView.setText(new String(data));
                    }
                }
            }
        });


        //manager = new TempTestManager(this);
        //setContentView(manager.getTempView());
    }

    @Override
    protected void onStart() {
        super.onStart();

//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
//        EventBus.getDefault().unregister(this);

        super.onStop();
    }
}
