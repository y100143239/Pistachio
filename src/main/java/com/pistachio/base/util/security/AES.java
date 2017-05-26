package com.pistachio.base.util.security;

import com.pistachio.base.util.Base64Helper;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class AES
{
    private static Logger       logger    = Logger.getLogger(com.pistachio.base.util.security.AES.class);

    private String              key       = "B49A86FA425D439dB510A234A3E25A3E";

    private final static String AES       = "AES";

    private final static String ALGORITHM = "SHA1PRNG";

    public AES()
    {

    }

    public AES(String key)
    {
        this.key = key;
    }

    /**
     * 加密
     *
     * @param byteContent 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(byte[] byteContent, byte[] password) throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        SecureRandom secureRandom = SecureRandom.getInstance(ALGORITHM);
        secureRandom.setSeed(password);
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
        Cipher cipher = Cipher.getInstance(AES); // 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, byte[] password) throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        SecureRandom secureRandom = SecureRandom.getInstance(ALGORITHM);
        secureRandom.setSeed(password);
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
        Cipher cipher = Cipher.getInstance(AES); // 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密

    }

    /**
     * 解密字符串
     * @param data
     * @deprecated
     * @return
     * @throws Exception
     */
    public final String decrypt(String data)
    {
        try
        {
            return new String(decrypt(hex2byte(data), key.getBytes()));
        }
        catch (Exception e)
        {
            logger.error("解密出错，返回null", e);
        }
        return null;
    }

    /**
     * 解密字符串
     * @param data
     * @return
     * @throws Exception
     */
    public final String decrypt(String data, String charsetName)
    {
        try
        {
            return new String(decrypt(hex2byte(data), key.getBytes()), charsetName);
        }
        catch (Exception e)
        {
            logger.error("解密出错，返回null", e);
        }
        return null;
    }

    /**
     * @deprecated
     * 加密字符串
     * @param data
     * @return
     * @throws Exception
     */
    public final String encrypt(String data)
    {
        try
        {
            return byte2hex(encrypt(data.getBytes(), key.getBytes()));
        }
        catch (Exception e)
        {
            logger.error("加密出错，返回null", e);
        }
        return null;
    }

    /**
     * 加密字符串
     * @param data
     * @return
     * @throws Exception
     */
    public final String encrypt(String data, String charsetName)
    {
        try
        {
            return byte2hex(encrypt(data.getBytes(charsetName), key.getBytes()));
        }
        catch (Exception e)
        {
            logger.error("加密出错，返回null", e);
        }
        return null;
    }

    private String byte2hex(byte b[])
    {
        return Base64Helper.encode(b);
    }

    private byte[] hex2byte(String hex) throws IOException
    {
        return Base64Helper.decode(hex);
    }

    /**
     *描述：设置密钥
     * @param key
     */
    public void setKey(String key)
    {
        this.key = key;
    }
}
