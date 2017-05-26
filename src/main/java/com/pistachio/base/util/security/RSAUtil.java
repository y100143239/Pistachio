package com.pistachio.base.util.security;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Properties;

/**
 * 描述:
 */

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 */
public class RSAUtil
{
    private static Logger logger = Logger.getLogger(RSAUtil.class.getName());

    private static Properties  props = new Properties();
    private static String CONFIG_FILE_NAME = "/key.properties";
    /**
     * * 生成密钥对 *
     *
     * @return KeyPair *
     */
    public static KeyPair generateKeyPair() throws Exception
    {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        saveKeyPair(keyPair);
        return keyPair;
    }

    public static KeyPair getKeyPair() throws Exception
    {
        FileInputStream fis = new FileInputStream("RSAKey.txt");
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair) oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    public static void saveKeyPair(KeyPair kp) throws Exception
    {
        RSAPublicKey publicKey = (RSAPublicKey)kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)kp.getPrivate();
        String n = publicKey.getModulus().toString(16);
        String e = publicKey.getPublicExponent().toString(16);
        String d = privateKey.getPrivateExponent().toString(16);
        //写入classes/tssg.properties文件中保存
        setProperties("n",n);
        setProperties("e",e);
        setProperties("d",d);
    }

    /**
     * * 生成公钥 *
     *
     * @param modulus        *
     * @param publicExponent *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception
    {
        KeyFactory keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
    }

    /**
     * * 生成私钥 *
     *
     * @param modulus         *
     * @param privateExponent *
     * @return RSAPrivateKey *
     * @throws Exception
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception
    {
        KeyFactory keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
    }

    /**
     * * 加密 *
     *
     * @param pk  加密的密钥 *
     * @param data 待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
        // 加密块大小为127
        // byte,加密后为128个byte;因此共有2个加密块，第一个127
        // byte第二个为1个byte
        int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
                : data.length / blockSize;
        byte[] raw = new byte[outputSize * blocksSize];
        int i = 0;
        while (data.length - i * blockSize > 0)
        {
            if (data.length - i * blockSize > blockSize)
            {
                cipher.doFinal(data, i * blockSize, blockSize, raw, i
                        * outputSize);
            }
            else
            {
                cipher.doFinal(data, i * blockSize, data.length - i
                        * blockSize, raw, i * outputSize);
            }
            // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
            // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
            // OutputSize所以只好用dofinal方法。
            i++;
        }
        return raw;
    }

    /**
     * * 加密 *
     *
     * @param pk  加密的密钥 *
     * @param dataStr 待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static String encrypt(PublicKey pk, String dataStr) throws Exception
    {
        byte[] data = new BigInteger(dataStr, 16).toByteArray();
        Cipher cipher = Cipher.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
        // 加密块大小为127
        // byte,加密后为128个byte;因此共有2个加密块，第一个127
        // byte第二个为1个byte
        int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
                : data.length / blockSize;
        byte[] raw = new byte[outputSize * blocksSize];
        int i = 0;
        while (data.length - i * blockSize > 0)
        {
            if (data.length - i * blockSize > blockSize)
            {
                cipher.doFinal(data, i * blockSize, blockSize, raw, i
                        * outputSize);
            }
            else
            {
                cipher.doFinal(data, i * blockSize, data.length - i
                        * blockSize, raw, i * outputSize);
            }
            // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
            // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
            // OutputSize所以只好用dofinal方法。
            i++;
        }

        return new String(raw,"UTF-8");
    }

    /**
     * * 解密 *
     *
     * @param pk 解密的密钥 *
     * @param raw 已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception
    {
        raw=unSignByteAry(raw);
        Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(cipher.DECRYPT_MODE, pk);
        int blockSize = cipher.getBlockSize();
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
        int j = 0;
        while (raw.length - j * blockSize > 0)
        {
            bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
            j++;
        }
        return bout.toByteArray();
    }

    /**
     * * 解密 *
     *
     * @param raw 已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    public static String decrypt(String raw) throws Exception
    {
        byte[] byRaw = unSignByteAry(new BigInteger(raw, 16).toByteArray());

        String modulusStr= RSAUtil.getProperties("n");
        String priExponentStr= RSAUtil.getProperties("d");
        byte[] modulusArray = new BigInteger(modulusStr, 16).toByteArray();
        byte[] priExponentArray = new BigInteger(priExponentStr, 16).toByteArray();

        RSAPrivateKey privateKey = RSAUtil.generateRSAPrivateKey(modulusArray, priExponentArray);
        Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(cipher.DECRYPT_MODE, privateKey);
        int blockSize = cipher.getBlockSize();
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
        int j = 0;
        while (byRaw.length - j * blockSize > 0)
        {
            bout.write(cipher.doFinal(byRaw, j * blockSize, blockSize));
            j++;
        }

        byte[] byresult = bout.toByteArray();
        StringBuffer str = new StringBuffer(new String(byresult));
        return str.reverse().toString();
    }

    /**
     *描述: 过滤0x00标志位
     *@param byteAry
     *@return
     */
    private static byte[] unSignByteAry(byte[] byteAry)
    {

        if (byteAry[0] != 0x00) {
            return byteAry;
        }
        if (byteAry.length == 1) {
            return byteAry;
        }
        byte[] unSignByteAry = new byte[byteAry.length - 1];
        for (int i = 0; i < unSignByteAry.length; i++) {
            unSignByteAry[i] = byteAry[i + 1];
        }
        return unSignByteAry;
    }


    private static void setProperties(String propsName,String value)
    {

        try
        {
            props.setProperty(propsName, value);
            FileOutputStream fileOut = new FileOutputStream(getProperitesPath());
            props.store(fileOut, null);
            if (fileOut != null)
            {
                fileOut.close();
                fileOut = null;
            }
        }
        catch (Exception ex)
        {
            logger.error("写入文件出错", ex);
        }

    }

    public static String getProperties(String propsName)
    {
        String value = "";
        InputStream in = null;
        try
        {
            in = RSAUtil.class.getResourceAsStream(CONFIG_FILE_NAME);
            props.load(in);
            value = props.getProperty(propsName);
            if (in != null)
            {
                in.close();
                in = null;
            }
        }
        catch (Exception ex)
        {
            logger.error("可能是配置文件未发现！", ex);
        }
        return value;
    }

    public static String getProperitesPath()
    {

        String classPath="";
        try
        {
            final ProtectionDomain pd = RSAUtil.class.getProtectionDomain();


            if (pd != null)
            {
                URL result = null;
                final CodeSource cs = pd.getCodeSource();
                if (cs != null)
                    result = cs.getLocation();
                if (result != null)
                {
                    classPath=result.getFile();
                }
            }
        }
        catch (Exception e)
        {
            logger.error("获取类路径出错", e);
        }
        int pos= classPath.indexOf("WEB-INF");
        String fixPath = classPath.substring(0, pos+7);
        String path =fixPath+"/classes"+CONFIG_FILE_NAME;
        return path;
    }

}
