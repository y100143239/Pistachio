package com.pistachio.base.util.zip;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;


/**
 * 描述:
 */
public class ZipHelper
{
    private static Logger logger = Logger.getLogger(ZipHelper.class);

    /**
     * 递归压缩目录和文件
     * @param source  源路径,可以是文件,也可以目录
     * @param target  目标路径,压缩文件名
     */
    public static void compress(String source, String target)
    {
        try
        {
            File srcFile = new File(source);
            if (srcFile.isFile() || srcFile.isDirectory()) //若是文件和目录则处理
            {
                ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)));
                String rootPath = (srcFile.isFile())?srcFile.getParent():srcFile.getPath();
                rootPath = (rootPath == null)?"":rootPath;
                compressFile(rootPath,srcFile, zipOut);
                zipOut.close();
            }
        }
        catch (IOException ex)
        {
            logger.error("压缩文件或目录失败", ex);
        }
    }

    private static void compressFile(String rootPath,File srcFile, ZipOutputStream zipOut) throws IOException
    {
        String tempPath = rootPath.replace("\\","/");;
        String storePath = srcFile.toString().replace("\\","/");
        if(storePath.startsWith(tempPath))
        {
            storePath = storePath.substring(tempPath.length());
            if(storePath.startsWith("/")) //去掉最前面的目录符号
            {
                storePath = storePath.substring(1);
            }
        }

        if(srcFile.isFile()) //若是文件
        {
            int readedBytes = 0;
            byte[] buffer = new byte[4096];

            FileInputStream inStream = new FileInputStream(srcFile);
            zipOut.putNextEntry(new ZipEntry(storePath));
            while ((readedBytes = inStream.read(buffer)) > 0)
            {
                zipOut.write(buffer, 0, readedBytes);
            }
            inStream.close();
            zipOut.closeEntry();
        }
        else if(srcFile.isDirectory()) //若是目录
        {
            File[] files = srcFile.listFiles();

            if (files.length == 0) //若目录中没有文件
            {
                zipOut.putNextEntry(new ZipEntry(storePath + "/"));
                zipOut.closeEntry();
            }
            else  //若目录中有文件
            {
                for (int i = 0; i < files.length; i++)
                {
                    File file = files[i];
                    compressFile(rootPath,file,zipOut);
                }
            }
        }
    }

    /**
     * 解压特定的文件
     *
     * @param source
     * @param target
     */
    public static void decompress(String source, String target)
    {
        int readedBytes = 0;
        byte[] buffer = new byte[4096];

        try
        {
            File srcFile = new File(source);
            if (srcFile.isFile())
            {
                ZipFile zipFile = new ZipFile(source);

                for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements();)
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    File file = new File(target + "/" + entry.getName());

                    if (entry.isDirectory())
                    {
                        file.mkdirs();
                    }
                    else
                    {
                        //如果指定文件的目录不存在,则创建之.
                        File parent = file.getParentFile();
                        if (!parent.exists())
                        {
                            parent.mkdirs();
                        }

                        InputStream inputStream = zipFile.getInputStream(entry);
                        FileOutputStream outStream = new FileOutputStream(file);
                        while ((readedBytes = inputStream.read(buffer)) > 0)
                        {
                            outStream.write(buffer, 0, readedBytes);
                        }
                        outStream.close();
                        inputStream.close();
                    }
                }
                zipFile.close();
            }
        }
        catch (IOException ex)
        {
            logger.error("加压文件失败",ex);
        }
    }

}
