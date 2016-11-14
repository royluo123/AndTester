package com.roy.tester.tpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by Administrator on 2016/10/20.
 */
public class PaintTestView extends View {
    private String mText = "af_tranid=fQ90RBPOoyjcNOkstFANkA&pid=yandexdirect_int&c= AxFeIE4WbozhisemAxFeIE4W &clickid={LOGID}&google_aid={GOOGLEAID}&android_id={ANDROIDID}";
    private TextPaint mTextPaint;
    private int mTextSize = 20;
    private int mWidth=-1;
    private int mHeight=-1;
    private int mTextColor = Color.BLACK;

    private int mTextCenterX;
    private int mTextCenterY;

    public PaintTestView(Context context) {
        super(context);

        String CH_FLAG  = "AxFeIE4W";
        String ch = stringBetween(mText, CH_FLAG, CH_FLAG);
        if(!isEmpty(ch)){
            mText = ch;
        }

        if(!mText.contains("@")){
            mText = mText + "@";
        }

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(this.mTextColor);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setUnderlineText(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTextCenterX = getWidth() / 2;
        int offset = (int) ((mTextPaint.ascent() + mTextPaint.descent()) / 2);
        mTextCenterY = getHeight() / 2 - offset;

        canvas.save();
        canvas.translate(0, 100);
        canvas.drawText(mText, mTextCenterX, mTextCenterY, mTextPaint);
        canvas.restore();
    }

    public static String stringBetween(String value, String beginStr, String endStr ){
        String result = null;
        if(!isEmpty(value) && !isEmpty(beginStr) && !isEmpty(endStr)){
            int index1 = value.indexOf(beginStr);
            if(index1 != -1){
                String sub = value.substring(index1 + beginStr.length());
                int index2 = sub.indexOf(endStr);
                if(index2 > 0){
                    result = sub.substring(0, index2);
                }
            }
        }

        return result;
    }

    public static boolean isEmpty(String aText) {
//        return aText == null || aText.length() == 0;
        return aText == null || aText.trim().length() == 0;
    }
}
