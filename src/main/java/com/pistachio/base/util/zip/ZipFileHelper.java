package com.pistachio.base.util.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 描述:
 */
public final class ZipFileHelper
{
    /**
     * 十六进制 0x20000
     */
    private final static int HEX_20000 = 0x20000;

    /**
     * 描述： 构造方法
     */
    private ZipFileHelper()
    {
    }

    /**
     * 描述：解压文件
     * 作者：
     * @param zipFile 压缩文件
     * @return
     * @throws IOException
     */
    public static File expandZipFile(File zipFile)
            throws IOException
    {
        String parent = zipFile.getParent();
        String dirName = zipFile.getName().split(".zip")[0];
        File directory = new File(parent, dirName);
        expandZipFile(zipFile, directory);
        return directory;
    }

    /**
     * 描述：解压文件
     * 作者：
     * @param zipFile 压缩文件
     * @param target 解压到目标文件
     * @throws IOException
     */
    public static void expandZipFile(File zipFile, File target)
            throws IOException
    {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry;
        while ((entry = zipIn.getNextEntry()) != null)
        {
            outputFileFromStream(zipIn, entry, target);
        }
        zipIn.close();
    }

    /**
     * 描述：输出解压后文件
     * 作者：
     * @param in 输入流
     * @param entry 压缩编码
     * @param parent 要解压文件
     * @throws IOException
     */
    private static void outputFileFromStream(InputStream in, ZipEntry entry, File parent)
            throws IOException
    {
        File outFile = new File(parent, entry.getName());
        (new File(outFile.getParent())).mkdirs();
        if (entry.isDirectory())
        {
            outFile.mkdir();
        }
        else
        {
            outFile.createNewFile();
            FileOutputStream out = new FileOutputStream(outFile);
            byte buf[] = new byte[HEX_20000];
            int len;
            while ((len = in.read(buf)) != -1)
            {
                out.write(buf, 0, len);
            }
            out.close();
        }
    }
}

