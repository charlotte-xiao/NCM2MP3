package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author charlottexiao
 */
public class AES {
    /**
     * 加密CR4密钥的密钥
     */
    public static final byte[] CORE_KEY = {0x68, 0x7A, 0x48, 0x52, 0x41, 0x6D, 0x73, 0x6F, 0x35, 0x6B, 0x49, 0x6E, 0x62, 0x61, 0x78, 0x57};

    /**
     * 加密MATA信息的密钥
     */
    public static final byte[] MATA_KEY = {0x23, 0x31, 0x34, 0x6C, 0x6A, 0x6B, 0x5F, 0x21, 0x5C, 0x5D, 0x26, 0x30, 0x55, 0x3C, 0x27, 0x28};

    /**
     * 转换(加密算法/加密模式/填充模式)
     */
    public static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 加密算法
     */
    public static final String ALGORITHM = "AES";

    /**
     * 功能:AES/ECB/PKCS5Padding解密
     *
     * @param str            密文
     * @param key            密钥
     * @param transformation 加密算法(加密类型/加密模式/填充模式)
     * @param algorithm      加密类型
     * @return 原文
     */
    public static byte[] decrypt(byte[] str, byte[] key, String transformation, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(str);
    }
}
