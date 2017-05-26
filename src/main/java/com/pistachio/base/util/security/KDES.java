package com.pistachio.base.util.security;

import com.sun.crypto.provider.SunJCE;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Security;

/**
 * 描述:  DES加解密类
 */
public final class KDES
{
    private static Logger logger = Logger.getLogger(KDES.class);

    private void selectAlgorithm(int al)
    {
        switch (al)
        {
            case 1: // '\001'
            default:
                p_Algorithm = "DES";
                break;

            case 2: // '\002'
                p_Algorithm = "DESede";
                break;

            case 3: // '\003'
                p_Algorithm = "Blowfish";
                break;
        }
    }

    public KDES(int algorithm)
            throws Exception
    {
        decoder = new BASE64Decoder();
        encoder = new BASE64Encoder();
        selectAlgorithm(algorithm);
        Security.addProvider(new SunJCE());
        p_Cipher = Cipher.getInstance(p_Algorithm);
    }

    public KDES()
            throws Exception
    {
        decoder = new BASE64Decoder();
        encoder = new BASE64Encoder();
        selectAlgorithm(1);
        Security.addProvider(new SunJCE());
        p_Cipher = Cipher.getInstance(p_Algorithm);
    }

    public void setKey(String key)
    {
        if (key == null || key.equals(""))
            key = "desdesde";
        int n = key.length() % 8;
        if (n != 0)
        {
            for (int i = 8; i > n; i--)
                key = key + "0";

        }
        p_Key = new SecretKeySpec(key.getBytes(), p_Algorithm);
    }

    private SecretKey checkKey()
    {
        try
        {
            if (p_Key == null)
            {
                KeyGenerator keygen = KeyGenerator.getInstance(p_Algorithm);
                p_Key = keygen.generateKey();
            }
        }
        catch (Exception e)
        {
            logger.error("生成加密key失败", e);
        }
        return p_Key;
    }

    public String encode(String data)
            throws Exception
    {
        p_Cipher.init(1, checkKey());
        return new String(byte2hex(p_Cipher.doFinal(data.getBytes("GBK"))));
    }

    public String decode(String encdata, String enckey)
            throws Exception
    {
        setKey(enckey);
        p_Cipher.init(2, p_Key);
        return new String(p_Cipher.doFinal(hex2byte(encdata)), "GBK");
    }

    public String byte2hex(byte b[])
    {
        return encoder.encode(b);
    }

    public byte[] hex2byte(String hex) throws IOException
    {
        return decoder.decodeBuffer(hex);
    }

    public static int DES = 1;
    public static int DESEDE = 2;
    public static int BLOWFISH = 3;
    private Cipher p_Cipher;
    private SecretKey p_Key;
    private String p_Algorithm;
    BASE64Decoder decoder;
    BASE64Encoder encoder;
}
