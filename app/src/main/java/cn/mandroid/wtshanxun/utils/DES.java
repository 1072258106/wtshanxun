package cn.mandroid.wtshanxun.utils;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;

/**
 * Created by wangtao on 2016-06-17.
 */
public class DES {
    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, long timestamp) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(getIvKey(timestamp));
        SecretKeySpec key = new SecretKeySpec(getSecKey(timestamp), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(encryptBytes);
        return new String(decryptedData);
    }
    private static byte[] getIvKey(long timestamp) {
        byte[] bytes = new byte[8];
        int tmp = (int) (timestamp / 1000 % 100);
        for (int i = 0; i < 8; i++) {
            bytes[i] = Constant.APP_V[tmp];
            tmp = bytes[i];
        }
        return bytes;
    }
    private static byte[] getSecKey(long timestamp) {
        return getIvKey(timestamp * 2);
    }
    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, long timestamp) throws Exception {
        return TextUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(Base64.decode(encryptStr.toCharArray()), timestamp);
    }
}
