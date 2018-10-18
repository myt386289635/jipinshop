package com.example.administrator.jipinshop.util.mimi;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author     ： 莫小婷
 * CreateTime ： 2017/12/22 15:02
 * Description： AES加密工具-128
 */
public class AESEncrypt {
    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/CBC/PKCS7Padding";

    // /** 创建密钥 **/
    private static SecretKeySpec createKey(String key) {
        byte[] data = null;
        if (key == null) {
            key = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(key);

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
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

            SecretKeySpec key = createKey(password);

            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  加密
     * @param content  明文
     * @param password 密钥
     * @param iv     向量
     * @return 加密后的字符串
     *
     *   调用： AESEncrypt.encrypt("moxiaoting&123456&wujianing","danjiguanjia1234","danjiguanjia1234");
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
//        return value;
    }

    private static String padString(String source) {
        char paddingChar = '0';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }

    /**
     *
     * @param key 解密需要的KEY 同加密
     * @param iv 解密需要的向量 同加密
     * @param data 需要解密的数据
     * @return
     *
     *   调用：AESEncrypt.decrypt("danjiguanjia1234","danjiguanjia1234", Base64.decode(s,Base64.DEFAULT))
     */
    public static String decrypt(String key, String iv, byte[] data){
        String content = "";

        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = createKey(key);
        Cipher cipher = null;// 创建密码器
        try {
            cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,createIV(iv));// 初始化
            byte[] result = cipher.doFinal(data);
            content = new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return content; // 加密
    }

}
