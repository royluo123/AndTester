package com.roy.base.util;

import android.database.Cursor;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public final class IOUtils {

    private IOUtils(){
        
    }
 
    public static int readShort(byte[] srcArray, int offset) {
        return (srcArray[offset] & 0xff) << 8 | srcArray[offset + 1] & 0xff;
    }

 
    public static void writeShort(byte[] destArray, int offset, short value) {
        destArray[offset] = (byte)((value >> 8) & 0xFF);
        destArray[offset+1] = (byte)((value >> 0) & 0xFF);
    }
 
    public static int readInt(byte[] srcArray, int offset) {
        return (int) (((srcArray[offset] & 0xff) << 24) + ((srcArray[offset + 1] & 0xff) << 16) + ((srcArray[offset + 2] & 0xff) << 8) + ((srcArray[offset + 3] & 0xff) << 0));
    }

 
    public static void writeInt(byte[] destArray, int offset, int value){
        destArray[offset] = (byte)((value >> 24)&0xFF);
        destArray[offset+1] = (byte)((value >> 16)&0xFF);
        destArray[offset+2] = (byte)((value >> 8)&0xFF);
        destArray[offset+3] = (byte)((value >> 0)&0xFF);
    }

 
    public static long readLong(byte[] srcArray, int offset) {
        return (((long)(srcArray[offset  ] & 255) << 56) +
                ((long)(srcArray[offset+1] & 255) << 48) +
                ((long)(srcArray[offset+2] & 255) << 40) +
                ((long)(srcArray[offset+3] & 255) << 32) +
                ((long)(srcArray[offset+4] & 255) << 24) +
                ((long)(srcArray[offset+5] & 255) << 16) +
                ((long)(srcArray[offset+6] & 255) <<  8) +
                ((long)(srcArray[offset+7] & 255) <<  0));
    }

 
    public static void writeLong(byte[] destArray, int offset, long value) {
        destArray[offset+0] = (byte)(value >>> 56);
        destArray[offset+1] = (byte)(value >>> 48);
        destArray[offset+2] = (byte)(value >>> 40);
        destArray[offset+3] = (byte)(value >>> 32);
        destArray[offset+4] = (byte)(value >>> 24);
        destArray[offset+5] = (byte)(value >>> 16);
        destArray[offset+6] = (byte)(value >>>  8);
        destArray[offset+7] = (byte)(value >>>  0);
    }

 
    public static byte[] readUTFBytes(DataInput input) throws IOException {
        int sUtfLength = input.readUnsignedShort();
        byte sByteArray[] = new byte[sUtfLength];
        input.readFully(sByteArray, 0, sUtfLength);
        return sByteArray;
    }
 
    public static void writeUTFBytes(DataOutputStream output, byte[] srcArray) throws Exception {
        if (srcArray != null) {
            output.writeShort(srcArray.length);
            output.write(srcArray, 0, srcArray.length);
        } else
            output.writeShort(0);
    }
 
    public static byte[] readBytes(InputStream input, int readLen, int bufferLen) throws IOException {
        if (input == null || readLen <= 0)
            return null;

        byte[] data = new byte[readLen];

        if (bufferLen <= 0)
            bufferLen = 2048;

        int len = 0;
        for (int j = 0; j < readLen;) {
            if (readLen - j < bufferLen)
                len = input.read(data, j, readLen - j);
            else
                len = input.read(data, j, bufferLen);

            if (len == -1)
                break;
            j += len;
        }

        return data;
    }

 
    public static byte[] readFullBytes(InputStream input){
        if (input == null)
            return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);

        try{
            byte[] buffer = new byte[2048];
            int offset = 0;
            while ((offset = input.read(buffer, 0, buffer.length)) >= 0) {
                baos.write(buffer, 0, offset);
            }

            byte[] data = baos.toByteArray();
            return data;
        }catch (Exception e) {
            //#if (debug == true)
            e.printStackTrace();
            //#endif
        }finally{
            closeOutputStream(baos);
            baos = null;
        }

        return null;

    }

 
    public static int readIntFormCharArray(char[] srcArray, int offset){
        return ((srcArray[offset] & 0xFFFF) << 16) + (srcArray[offset + 1] & 0xFFFF);
    }

 
    public static void writeIntToCharArray(char[] srcArray, int offset, int value){
        srcArray[offset] = (char)(value >> 16 & 0xFFFF);
        srcArray[offset + 1] = (char)(value & 0xFFFF);
    }
 
    public static void writeUTFBytes(byte[] destArray,int offset , byte[] srcUTFBytes){
        if (srcUTFBytes == null) {
            IOUtils.writeShort(destArray, offset, (short) 0);
        } else {
            IOUtils.writeShort(destArray, offset, (short) srcUTFBytes.length);
            System.arraycopy(srcUTFBytes, 0, destArray, offset + 2, srcUTFBytes.length);
        }
    }
 
    public static void closeInputStream(InputStream input){
        if(input != null){
            try {
                input.close();
                input = null;
            } catch (Exception e) {
                //#if (debug == true)
                e.printStackTrace();
                //#endif
            }
        }
    }

 
    public static void closeOutputStream(OutputStream output){
        if(output != null){
            try {
                output.close();
                output = null;
            } catch (Exception e) {
                //#if (debug == true)
                e.printStackTrace();
                //#endif
            }
        }
    }
    
    
    /**
     * byte[] ºÏÆ´
     * @param a byte[]
     * @param b byte[]
     * @return byte[]
     */
    public final static byte[] bytesCombine(byte[] a,byte[] b) { 
        return bytesCombine(a, 0, a.length, b, 0, b.length);
    }
    
    public final static byte[] bytesCombine(byte[] a, int offset1, int length1, byte[] b, int offset2, int length2) {
        if(a == null && b == null) return null;
        if(a == null) return b;
        if(b == null) return a;
        byte[] c = new byte[length1 + length2];
        System.arraycopy(a, offset1, c, 0, length1);
        System.arraycopy(b, offset2, c, length1, length2);
        return c;
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    public static void safeClose(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
            }
        }
    }

}
