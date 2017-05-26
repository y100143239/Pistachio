package com.pistachio.base.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 描述:文件夹压缩
 */
public class FolderArchiver
{
	/**
	 * 缓冲区大小
	 */
	private static final int BUFFER_SIZE = 10240;

	/**
	 *
	 */
	private File folderToArchive;

	/**
	 *
	 */
	private File archiveFile;



	/**
	 * 描述： 构造方法
	 * @param folderToArchive 要保存文件
	 * @param archiveFile 被保存文件
	 */
	public FolderArchiver(File folderToArchive, File archiveFile)
	{
		this.folderToArchive = folderToArchive;
		this.archiveFile = archiveFile;
	}


	/**
	 * 描述：压缩
	 * 作者：
	 * @throws Exception
	 */
	public void doArchive() throws Exception
	{
		FileOutputStream stream = new FileOutputStream(archiveFile);
		ZipOutputStream output = new ZipOutputStream(stream);
		compressFile(folderToArchive, output);
		output.close();
		stream.close();
	}


	/**
	 * 描述：压缩文件
	 * 作者：
	 * @param file 要压缩文件
	 * @param output 压缩输出流
	 * @throws IOException
	 */
	private void compressFile(File file, ZipOutputStream output) throws IOException
	{
		if (file == null || !file.exists())
		{
			return;
		}
		if (file.isFile() && !file.equals(archiveFile))
		{
			byte buffer[] = new byte[BUFFER_SIZE];
			String path = file.getPath().substring(folderToArchive.getPath().length());
			path = path.replaceAll("\\\\", "/");
			if (path.length() > 0 && path.charAt(0) == '/')
			{
				path = path.substring(1);
			}
			ZipEntry entry = new ZipEntry(path);
			entry.setTime(file.lastModified());
			output.putNextEntry(entry);
			FileInputStream in = new FileInputStream(file);
			int data;
			while ((data = in.read(buffer, 0, buffer.length)) > 0)
			{
				output.write(buffer, 0, data);
			}
			in.close();
		}
		else if (file.isDirectory())
		{
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				compressFile(files[i], output);
			}

		}
	}
}
