package com.roy.base.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import android.support.annotation.Nullable;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {
    public static final String KEY_ALGORITHM_RSA = "RSA";
    public static final String KEY_ALGORITHM_AES = "AES";
    public static final String KEY_ALGORITHM_CHACHA20 = "CHACHA20";

    @Nullable
    public static byte[] decryptByRSA(byte[] encryptedData, byte[] key){
        byte[] ret = null;
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            ret = cipher.doFinal(encryptedData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @Nullable
    public static byte[] encryptByRSA(byte[] data, byte[] key){
        byte[] ret = null;
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            Key publicK = keyFactory.generatePublic(x509KeySpec);

            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            ret = cipher.doFinal(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @Nullable
    public static byte[] rsaEncrypt(byte[] data, Key key) {
        byte[] ret = null;
        if (data != null && data.length > 0 && key != null) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                ret = cipher.doFinal(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Nullable
    public static byte[] rsaDecrypt(byte[] data, Key key) {
        byte[] ret = null;
        if (data != null && data.length > 0 && key != null) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
                cipher.init(Cipher.DECRYPT_MODE, key);
                ret = cipher.doFinal(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Nullable
    public static KeyPair generateRSAKey(int keySize) {
        KeyPair ret = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
            // 设置密钥位数 也就是 n 的位数
            kpg.initialize(keySize);
            ret = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private static long doChecksum(String fileName) {
        CheckedInputStream cis = null;
        try {
            cis = new CheckedInputStream(new FileInputStream(fileName), new CRC32());
            byte[] buf = new byte[128];
            while (cis.read(buf) >= 0) {
            }

            long checksum = cis.getChecksum().getValue();
            return checksum;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (cis != null) {
                    cis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    @Nullable
    public static byte[] encryptByAES(byte[] content, String key) {
        byte[] result = null;
        try {
            if (key != null) {
                byte[] aesKey = parseHexStr2Byte(key);
                IvParameterSpec IV = new IvParameterSpec(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
                SecretKeySpec keySpec = new SecretKeySpec(aesKey, KEY_ALGORITHM_AES);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, IV);
                result = cipher.doFinal(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nullable
    public static byte[] decryptByAES(byte[] content, String key) {
        byte[] result = null;
        try {
            if (key != null) {
                byte[] aesKey = parseHexStr2Byte(key);
                SecretKeySpec keySpec = new SecretKeySpec(aesKey, KEY_ALGORITHM_AES);
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                IvParameterSpec IV = new IvParameterSpec(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
                cipher.init(Cipher.DECRYPT_MODE, keySpec,IV);
                result = cipher.doFinal(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nullable
    public static byte[] encryptByCHACHA20(byte[] content, String key) {
        byte[] result = null;
        try {
            if (key != null) {
                byte[] aesKey = parseHexStr2Byte(key);
                SecretKeySpec keySpec = new SecretKeySpec(aesKey, KEY_ALGORITHM_CHACHA20);
                Cipher cipher = Cipher.getInstance("CHACHA20");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                result = cipher.doFinal(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nullable
    public static byte[] decryptByCHACHA20(byte[] content, String key) {
        byte[] result = null;
        try {
            if (key != null) {
                byte[] aesKey = parseHexStr2Byte(key);
                SecretKeySpec keySpec = new SecretKeySpec(aesKey, KEY_ALGORITHM_CHACHA20);
                Cipher cipher = Cipher.getInstance("CHACHA20");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                result = cipher.doFinal(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
