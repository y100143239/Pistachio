package com.pistachio.redis.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 *
 * 描述: 对象序列化
 */
public class SerializeHelper
{

    private static Logger logger = Logger.getLogger(SerializeHelper.class);

    /**
     *
     * 描述：序列化对象
     * @param object
     * @return
     */
    public static byte[] serialize(Object object)
    {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        finally
        {
            if ( baos != null )
            {
                try
                {
                    baos.close();
                    baos = null;
                }
                catch (IOException e)
                {
                    logger.error("", e);
                }
            }
            if ( oos != null )
            {
                try
                {
                    oos.close();
                }
                catch (IOException e)
                {
                    logger.error("", e);
                }
            }

        }
        return null;
    }

    /**
     *
     * 描述：反序列化对象
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes)
    {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try
        {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        finally
        {
            try
            {
                ois.close();
                ois = null;
            }
            catch (IOException e)
            {
                logger.error("", e);
            }
            if ( bais != null )
            {
                try
                {
                    bais.close();
                    bais = null;
                }
                catch (IOException e)
                {
                    logger.error("", e);
                }
            }

        }
        return null;
    }
}
