package com.pistachio.base.util;

import org.apache.log4j.Logger;

/**
 * 描述: 字节操作和字节转换类
 */
public class Convertor
{
    private static Logger logger = Logger.getLogger(Convertor.class);
    /**
     * 位移 4
     */
    private final static int DISPLACEMENT_FOUR = 4;

    /**
     * 位移 8
     */
    private final static int DISPLACEMENT_EIGHT = 8;

    /**
     * 位移 16
     */
    private final static int DISPLACEMENT_SIXTEEN =16;

    /**
     * 位移 24
     */
    private final static int DISPLACEMENT_TWENTYFOUR = 24;

    /**
     * 位移 32
     */
    private final static int DISPLACEMENT_THIRTYTWO = 32;

    /**
     * DOUBLE 3D
     */
    private final static double D_3 = 3D;

    /**
     * DOUBLE 7D
     */
    private final static double D_7 = 7D;

    /**
     * DOUBLE 10D
     */
    private final static double D_10 = 10D;

    /**
     * 16 进制 ff
     */
    private final static int HEX_FF = 0xff;

    /**
     * 16 进制 0x7fffffff
     */
    private final static int HEX_7FFFFFFF = 0x7fffffff;

    /**
     * 16 进制 0xffffffffL
     */
    private final static long HEX_FFFFFFFFL = 0xffffffffL;

    /**
     * 16 进制 0x7fffffffffffffffL
     */
    private final static long HEX_FFFFFFFFFFFFFFFL = 0x7fffffffffffffffL;

    /**
     * 16 进制 0x8000000000000000L
     */
    private final static long HEX_8000000000000000L = 0x8000000000000000L;

    /**
     * 常量 3
     */
    private final static int FINAL_THREE = 3;

    /**
     * 常量 4
     */
    private final static int FINAL_FOUR = 4;

    /**
     * 幂指数 3.402823E+038F
     */
    private final static float FLOAT_MAX = 3.402823E+038F;

    /**
     * 幂指数 3.402823E+038F
     */
    private final static double DOUBLE_MAX = 1.7976931348623157E+308D;
    /**
     * Use to concatenate two byte[] array to be one
     *
     * @param first  The first byte array which will be concatenated
     * @param second The second byte array which will be concatenated
     * @return byte[] Return the concatenated byte array
     */
    public static byte[] concatenate(byte[] first, byte[] second)
    {
        if (first == null && second == null)
        {
            return null;
        }
        else if (second == null)
        {
            return first;
        }
        else if (first == null)
        {
            return second;
        }
        else
        {
            byte[] temp = new byte[first.length + second.length];

            System.arraycopy(first, 0, temp, 0, first.length);
            System.arraycopy(second, 0, temp, first.length, second.length);

            return temp;
        }
    }

    /**
     * 从数组 buffer 中取出一子数组
     *
     * @param buffer     源数组
     * @param startIndex 起始下标
     * @param length     子数组长度
     * @return
     */
    public static byte[] getBytes(byte[] buffer, int startIndex, int length)
    {
        byte[] ret = null;

        if (buffer != null && startIndex >= 0 && length >= 0 && (buffer.length - startIndex) > 0)
        {
            if ((buffer.length - startIndex) < length)
            {
                length = buffer.length - startIndex;

            }
            byte[] tempBytes = new byte[length];
            for (int i = 0; i < length; i++)
            {
                try
                {
                    tempBytes[i] = buffer[startIndex + i];
                }
                catch (Exception ex)
                {
                    logger.info("buffer.length = " + String.valueOf(buffer.length)+",access offset = " + String.valueOf(startIndex + i));
                    logger.error("从数组buffer中取出子数组出错!!", ex);
                    break;
                }
            }

            ret = tempBytes;
        }

        return ret;
    }

    /**
     * 从buffer中取出一个字节数组，并将其从buffer中删除
     *
     * @param buffer 要取出一个字节数组
     * @param length length
     * @return
     */
    public static byte[] fetchBytes(byte[] buffer, int length)
    {
        byte[] ret = getBytes(buffer, 0, length);
        byte[] temp = new byte[buffer.length - length];
        System.arraycopy(buffer, length, temp, 0, temp.length);
        buffer = temp;

        return ret;
    }

    /**
     * 描述：从数组 buffer （从 0 开始 ）中取出数据( 长度为k )到 abyte0 中( 从 i 开始，长度为k=j )
     * 返回被复制的长度 k
     * buffer  -->  abyte0
     * 作者：
     * 时间：Oct 29, 2008 1:45:22 PM
     * @param buffer 基础数组
     * @param abyte0 取出到数组
     * @param i 开始
     * @param j 长度
     * @return
     * @throws NullPointerException
     */
    public static int read(byte buffer[], byte abyte0[], int i, int j)
            throws NullPointerException
    {
        if (buffer == null)
        {
            return 0;
        }

        int k = j;
        if (k > buffer.length)
        {
            k = buffer.length;
        }
        if (k == 0)
        {
            return 0;
        }
        System.arraycopy(buffer, 0, abyte0, i, k);
        if (k == buffer.length)
        {
            buffer = null;
        }
        else
        {
            byte abyte1[] = new byte[buffer.length - k];
            System.arraycopy(buffer, k, abyte1, 0, buffer.length - k);
            buffer = abyte1;
            abyte1 = null;
        }
        return k;
    }

    /**
     * 描述：将 abyte0[] 增加到 buffer[] 中(从下标 i 开始，长度为 j)
     * 返回 buffer 的长度
     * 作者：
     * 时间：Oct 29, 2008 1:48:50 PM
     * @param buffer 基础数组
     * @param abyte0 取出到数组
     * @param startIndex 开始
     * @param setLength 长度
     * @return
     */
    public static int setBytes(byte[] buffer, byte abyte0[], int startIndex, int setLength)
    {
        if (setLength == 0)
        {
            return 0;
        }

        int k = setLength;
        if (buffer != null)
        {
            k += buffer.length;
        }

        byte tempBytes[] = new byte[k];
        if (buffer != null && buffer.length > 0)
        {
            System.arraycopy(buffer, 0, tempBytes, 0, buffer.length);
            System.arraycopy(abyte0, startIndex, tempBytes, buffer.length, setLength);
        }
        else
        {
            System.arraycopy(abyte0, startIndex, tempBytes, 0, setLength);
        }
        buffer = tempBytes;
        tempBytes = null;

        return setLength;
    }


    /**
     * Convert an int data into a four bytes array
     *
     * @param i The int type data which will be converted
     * @return Return the four bytes byteArray
     */
    /**
     * 描述：
     * 作者：
     * 时间：Oct 29, 2008 1:53:29 PM
     * @param i int参数
     * @return
     */
    public static byte[] intToBytes(int i)
    {
        try
        {
            byte[] aBytes = new byte[DISPLACEMENT_FOUR];
            aBytes[0] = (byte) i;
            aBytes[1] = (byte) (i >> DISPLACEMENT_EIGHT & HEX_FF);
            aBytes[2] = (byte) (i >> DISPLACEMENT_SIXTEEN & HEX_FF);
            aBytes[FINAL_THREE] = (byte) (i >> DISPLACEMENT_TWENTYFOUR & HEX_FF);
            return aBytes;
        }
        catch (NullPointerException ex2)
        {
            logger.error("转换失败", ex2);//这个地方会有空指针异常？？？？？？？？？
            return null;
        }
    }

    /**
     * Convert a four bytes ByteArray into an int type data
     *
     * @param aBytes The byte array part of which will be converted
     * @param i      Indicate from where of the ByteArray to converte
     * @return Return the converted int type data
     */
    public static int bytesToInt(byte[] aBytes, int i)
    {
        try
        {
            int j = ((aBytes[i + FINAL_THREE] & HEX_FF) << DISPLACEMENT_TWENTYFOUR) + ((aBytes[i + 2] & HEX_FF) << DISPLACEMENT_SIXTEEN) +
                    ((aBytes[i + 1] & HEX_FF) << DISPLACEMENT_EIGHT) + (aBytes[i] & HEX_FF);
            if (j == HEX_7FFFFFFF)
            {
                j = 0;
            }
            return j;
        }
        catch (Exception ex)
        {
            logger.error("转换失败", ex);
            return 0;
        }
    }

    /**
     * Convert a four bytes float type data to be four bytes
     *
     * @param f The float data will be converted
     * @return Return the four bytes converted from f
     */
    public static byte[] floatToBytes(float f)
    {
        byte[] ret = null;

        try
        {
            byte[] aBytes = new byte[FINAL_FOUR];
            int temp = Float.floatToIntBits(f);
            aBytes = intToBytes(temp);
            ret = aBytes;
        }
        catch (Exception ex)
        {
            logger.error("ERROR : Convert the float data to byte[] error!!", ex);
        }

        return ret;
    }

    /**
     * Convert four bytes to be a float data
     *
     * @param aBytes The byte array which will be converted
     * @param i      The start point of the byte array abyte0 from where the data will be converted
     * @return Return the float data's value converted from the abyte0
     */
    public static float bytesToFloat(byte[] aBytes, int i)
    {
        float ret = 0.0F;

        try
        {
            int j = bytesToInt(aBytes, i);
            float f = Float.intBitsToFloat(j);
            if ((new Float(f)).isNaN())
            {
                f = 0.0F;
            }
            if (f == FLOAT_MAX)
            {
                f = 0.0F;
            }
            ret = f;
        }
        catch (NullPointerException ex2)
        {
            logger.error("ERROR : Convert the byte[] into floast error!!", ex2);
        }

        return ret;
    }

    /**
     * Convert part of the the byte array to be a String object
     *
     * @param abyte0     The byte array which will be converted
     * @param startIndex The start point of the byte array from where the bytes will be converted
     * @param len        The end point of the byte array before here the bytes will be converted
     * @return Return a String object converted from the byte array named abyte0
     */
    public static String bytesToString(byte abyte0[], int startIndex, int len)
    {
        String ret = null;

        try
        {
            int i = 0;
            for (i = startIndex; i < startIndex + len; i++)
            {
                if (abyte0[i] == 0)
                {
                    break;
                }
            }

            len = i - startIndex;
            if (len != 0)
            {
                ret = new String(abyte0, startIndex, len);
            }
        }
        catch (NullPointerException ex2)
        {
            logger.error("ERROR : Convert the byte[] into String error!!", ex2);
        }

        return ret;
    }

    /**
     * 描述：将字节转换为字串
     * 作者：
     * 时间：Oct 29, 2008 2:16:49 PM
     * @param bytes 要转换为String的bytes
     * @return
     */
    public static String bytesToString(byte[] bytes)
    {
        return bytesToString(bytes, 0, bytes.length);
    }

    /**
     * Convert 2 bytes into short
     *
     * @param abyte0 The byte array which contains the 2 byte which will be converted.
     * @param i      Which indicate which position the array will be converted.
     * @return Return the converted short number
     */
    public static short bytesToShort(byte abyte0[], int i)
    {
        return (short) (((abyte0[i + 1] & HEX_FF) << DISPLACEMENT_EIGHT) + (abyte0[i] & HEX_FF));
    }

    /**
     * Convert a short type data to be two bytes
     *
     * @param word0 The short type data which will be converted
     * @return Return the two bytes value of the short data
     */
    public static byte[] shortToBytes(short word0)
    {
        byte[] ret = null;

        try
        {
            byte abyte0[] = new byte[2];
            abyte0[0] = (byte) word0;
            abyte0[1] = (byte) (word0 >> DISPLACEMENT_EIGHT & HEX_FF);
            ret = abyte0;
        }
        catch (NullPointerException ex2)
        {
            logger.error("ERROR : Convert the short data into byte[] error!!", ex2);
        }

        return ret;
    }

    /**
     * Convert the byte array to be double type data
     *
     * @param abyte0 The byte array which will converted
     * @param i        The start point of the byte array from where the data will be converted
     */
    public static double bytesToDouble(byte abyte0[], int i)
    {
        try
        {
            int j = bytesToInt(abyte0, i);
            i += FINAL_FOUR;
            int k = bytesToInt(abyte0, i);
            i += FINAL_FOUR;
            long l = ((long) j & HEX_FFFFFFFFL) + ((long) k << DISPLACEMENT_THIRTYTWO);
            if (l >= HEX_FFFFFFFFFFFFFFFL || l <= HEX_8000000000000000L)
            {
                l = 0L;
            }
            double d = Double.longBitsToDouble(l);
            if ((new Double(d)).isNaN())
            {
                d = 0.0D;
            }
            if (d == DOUBLE_MAX)
            {
                d = 0.0D;
            }
            return d;
        }
        catch (NullPointerException ex2)
        {
            logger.error("转换失败", ex2);
            return 0.0D;
        }
    }

    /**
     * Convert the double data to be an ASCII String
     *
     * @param d The double data which will be converted
     * @return Return a String object which stand by the double data
     */
    public static String doubleToString(double d)
    {
        if (Math.abs(d) < Math.pow(D_10, -D_3) || Math.abs(d) > Math.pow(D_10, D_7))
        {
            boolean flag = false;
            String s5 = Double.toString(Math.abs(d));
            int l = s5.lastIndexOf("E");
            if (l == -1)
            {
                if (d < 0.0D)
                {
                    s5 = "-" + s5;
                }
                return s5;
            }
            flag = s5.lastIndexOf("-") != -1;
            String s = s5.substring(l + 1, s5.getBytes().length);
            int i1 = Math.abs(Integer.parseInt(s));
            int i = s5.lastIndexOf(".");
            if (i != 0)
            {
                String s1 = s5.substring(0, i);
                String s3 = s5.substring(i + 1, l);
                if (!s3.equalsIgnoreCase("0"))
                {
                    s5 = s1 + s3;
                }
                else
                {
                    s5 = s1;
                }
            }
            else
            {
                s5 = s5.substring(0, l);
            }
            int j1 = s5.getBytes().length;
            if (!flag && j1 > i1)
            {
                String s2 = s5.substring(0, i1 + 1);
                String s4 = s5.substring(i1 + 1, j1);
                s5 = s2 + "." + s4;
            }
            else if (flag)
            {
                for (int j = 0; j < i1 - 1; j++)
                {
                    s5 = "0" + s5;

                }
            }
            else
            {
                for (int k = 0; k <= i1 - j1; k++)
                {
                    s5 += "0";

                }
            }
            if (flag)
            {
                s5 = "0." + s5;
            }
            if (d < 0.0D)
            {
                s5 = "-" + s5;
            }
            return s5;
        }
        else
        {
            return Double.toString(d);
        }
    }

    //--------------------------------------------------------------数据转换 END

//    /**
//     * 将int转换为表示时间的字符串
//     *
//     * @param i 整形
//     * @return
//     */
//    static public String int2Time(int i)
//    {
////        if(i>690)     //如果是下午的时间，需要加上中午休市的90分钟
////            i=i+90;
//
//        int ihour = i / 60;
//        String hour = String.valueOf(ihour);
//        if (hour.length() == 1)
//        {
//            hour = "0" + hour;
//
//        }
//        int iminute = i % 60;
//        String sminute = String.valueOf(iminute);
//        if (sminute.length() == 1)
//        {
//            sminute = "0" + sminute;
//
//        }
//        String strTime = hour + ":" + sminute;
//        return strTime;
//    }
}
