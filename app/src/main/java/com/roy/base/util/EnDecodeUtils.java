package com.roy.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

import android.text.TextUtils;
import android.util.Base64;

public class EnDecodeUtils {

    public static byte[] encodeByGZip(String content) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gout = new GZIPOutputStream(out);
        gout.write(content.getBytes("utf-8"));
        gout.flush();
        gout.close();
        byte[] encodeByte = out.toByteArray();
        out.close();
        return encodeByte;
    }


    public static byte[] encodeByGZip(byte[] content) {
        if (content == null) {
            return null;
        }
        GZIPOutputStream outputStream = null;
        ByteArrayOutputStream btsOut = null;
        try {
            btsOut = new ByteArrayOutputStream();
            outputStream = new GZIPOutputStream(btsOut);
            outputStream.write(content);
            outputStream.finish();
            return btsOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (btsOut != null)
                try {
                    btsOut.close();
                } catch (IOException e) {
                }
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
        }
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[3] = (byte) ((i >> 24) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[0] = (byte) (i & 0xFF);
        return result;
    }

    public static String encodeByBase64(String strSrc){
        String strBse64 ="";

        byte[] bytes = Base64.encode(strSrc.getBytes(), Base64.DEFAULT);
        strBse64 = new String(bytes);
        return strBse64;
    }

    /**
     * Base64解码
     * @param strBase64 需要解码的base64串
     * @return 解码后的串
     */
    public static String decodeByBase64(String strBase64){
        String strDes = "";
        byte[] bytes = Base64.decode(strBase64.getBytes(), Base64.DEFAULT);
        strDes = new String(bytes);
        return strDes;
    }

}
