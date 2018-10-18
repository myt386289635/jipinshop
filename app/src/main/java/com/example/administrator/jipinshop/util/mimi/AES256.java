package com.example.administrator.jipinshop.util.mimi;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author 莫小婷
 * @create 2018/9/5
 * @Describe  AES加密工具-256
 */
public class AES256 {
    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/CBC/PKCS7Padding";

    /**
     * 说明 :将密钥转行成SecretKeySpec格式
     * @param password 字符串密钥（位数可以不为32位，会自动补全）
     * @return SecretKeySpec格式密钥
     */
    private static SecretKeySpec getKey(String password)
            throws UnsupportedEncodingException {
        // 如果为128将长度改为128即可
        int keyLength = 256;
        byte[] keyBytes = new byte[keyLength / 8];
        // explicitly fill with zeros
        Arrays.fill(keyBytes, (byte) 0x0);
        byte[] passwordBytes = password.getBytes("UTF-8");
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length
                : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    private static IvParameterSpec createIV(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(password);

//        try {
        data = sb.toString().getBytes();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        return new IvParameterSpec(data);
    }

    // /** 加密字节数据 **/
    public static byte[] encrypt(byte[] content, String password, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = content;
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec key = getKey(password);

            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param content  明文
     * @param password 密钥
     * @param iv     向量
     * @return 加密后的字符串
     *
     *   调用： AES256.encrypt("moxiaoting&123456&wujianing","danjiguanjia1234","danjiguanjia1234");
     */
    public static String encrypt(String content, String password, String iv) {
        byte[] data = null;
        try {
            data = content.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt(data, password, iv);
        String value = Base64.encodeToString(data, Base64.DEFAULT);

        return value.replace("\n","");
    }


    /**
     *
     * 说明 :AES256解密
     *
     * @param text Base64格式密文
     * @param keyString 密钥
     * @return String 格式明文
     *
     *  调用： AES256.AES256Decrypt("Rso313AFxS1qzPNh+8GleINb/P9ur4Oc6GGMpl16DJM=","danjiguanjia1234","danjiguanjia1234");
     */
    public static String AES256Decrypt(String text, String keyString , String iv) {
        // byte[] rawKey = getRawKey(key);
        if (keyString.length() == 0 || keyString == null) {
            return null;
        }
        if (text.length() == 0 || text == null) {
            return null;
        }
        try {
            SecretKey key = getKey(keyString);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            byte[] data = Base64.decode(text, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] decrypedValueBytes = (cipher.doFinal(data));
            String decrypedValue = new String(decrypedValueBytes, "UTF-8");
            return decrypedValue;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

}
