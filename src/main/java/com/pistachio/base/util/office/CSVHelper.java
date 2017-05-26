package com.pistachio.base.util.office;

import com.pistachio.base.jdbc.DataRow;
import com.pistachio.base.util.FileHelper;
import com.pistachio.base.util.StringHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 描述: CSV工具类
 */
public class CSVHelper
{
	private static Logger logger = Logger.getLogger(CSVHelper.class);

	/**
	 * 描述：直接输出CSV
	 * @param response HttpServletResponse
	 * @param fileName 文件名
	 * @param list 数据
	 * @param title 标题
	 * @param keys 键名
	 * @return
	 */
	public static void createCSVToPrintWriter(HttpServletResponse response,String fileName,List list, String[] title, String[] keys)
	{
//		response.setContentType("application/vnd.ms-excel");
		response.setContentType("application/x-msdownload");

		String extension = FilenameUtils.getExtension(fileName);

		if(!"csv".equals(extension.toLowerCase()))
		{
			fileName = FilenameUtils.getBaseName(fileName)+".csv";
		}
		else
		{
			fileName = FilenameUtils.getBaseName(fileName)+"."+FilenameUtils.getExtension(fileName);
		}

		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		String strCSV = csvStrSplice(list, title, keys);

		if(strCSV!=null && strCSV.length()>0)
		{
			PrintWriter pw = null;
			try
			{
				//				输出到页面
				pw = response.getWriter();
				pw.write(strCSV);
			}
			catch (IOException e)
			{
				logger.error("写文件出错", e);
			}
			finally
			{
				pw.flush();
				pw.close();
				pw=null;
			}
		}
	}


	/**
	 * 描述：生成csv所需字串
	 * @param list 数据
	 * @param title 标题
	 * @param keys 键名
	 * @return
	 */
	public static String csvStrSplice(List list, String[] title, String[] keys)
	{
		StringBuffer sb = new StringBuffer();
		//拼装标题
		if(title!=null && title.length>0)
		{
			for(int i=0;i<title.length;i++)
			{
				sb.append(title[i]).append(",");
			}
			sb.replace(sb.length()-1, sb.length(), "\r\n");
		}

		if(list!=null&&!list.isEmpty()){
			for (Iterator it = list.iterator(); it.hasNext();)
			{
				DataRow data = (DataRow) it.next();
				for(int j=0;j<keys.length;j++)
				{
					String value = data.getString(keys[j]);
					if(value.contains(",")||value.contains("\""))
					{
						sb.append("\"").append(value).append("\",");
					}
					else
					{
						sb.append(value).append(",");
					}
				}
				sb.replace(sb.length()-1, sb.length(), "\r\n");
			}
		}

		return sb.toString();
	}


	/**
	 * 描述：读取csv数据到List
	 * @param file 文件
	 * @param fieldsName 字段名
	 * @param isHasTitle 是否有title列
	 * @return
	 */
	public static List readCSVToList(File file, String[] fieldsName, boolean isHasTitle)
	{
		return readCSVToList(file.getPath(), fieldsName, isHasTitle, null);
	}


	/**
	 * 描述：读取csv数据到List
	 * @param file 文件
	 * @param fieldsName 字段名
	 * @param isHasTitle 是否有title列
	 * @return
	 */
	public static List readCSVToList(File file, String[] fieldsName, boolean isHasTitle, Map dafaultValue)
	{
		return readCSVToList(file.getPath(), fieldsName, isHasTitle, dafaultValue);
	}


	/**
	 * 描述：读取csv数据到List
	 * @param filePath 文件路径
	 * @param fieldsName 字段名
	 * @param isHasTitle 是否有title列
	 * @return
	 */
	public static List readCSVToList(String filePath, String[] fieldsName, boolean isHasTitle)
	{
		return readCSVToList(filePath, fieldsName, isHasTitle, null);
	}


	/**
	 * 描述：读取csv数据到List（通过sql方式，可以处理某个值中存在“逗号”，“引号的问题”）
	 * 依赖：csvjdbc.jar
	 * @param filePath 文件路径
	 * @param fieldsName 字段名
	 * @param dafaultValue 指定字段的默认值
	 * @return
	 */
	public static List readCSVToList(String filePath, String[] fieldsName, boolean isHasTitle, Map dafaultValue)
	{
		if(StringHelper.isEmpty(filePath) || fieldsName==null || fieldsName.length==0)
		{
			logger.error("filePath or fieldsName can not be empty!");
			return null;
		}

		boolean isNotEmpty = false;
		if(dafaultValue!=null && dafaultValue.size()>0)
		{
			isNotEmpty = true;
		}

		List toList = new ArrayList();
		try
		{
			Class.forName("org.relique.jdbc.csv.CsvDriver");

			String fileName = FileHelper.getFileName(filePath);
			String directory = filePath.replace(fileName, "");

			Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + directory);//csv文件所在目录
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select * from " + FileHelper.getBaseName(fileName));//不带后缀的文件名

			if(isHasTitle)
			{
				DataRow data = new DataRow();
				ResultSetMetaData rsmd = results.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 0; i < columnCount; i++)
				{
					data.put(fieldsName[i], rsmd.getColumnName(i+1));
				}
				toList.add(data);
			}

			// dump out the results
			while (results.next())
			{
				DataRow data = new DataRow();
				for(int i=0;i<fieldsName.length;i++)
				{
//					首行为索引名
					String str = results.getString(i+1);
					if(StringHelper.isEmpty(str) && isNotEmpty)
					{
						String sDafault = (String) dafaultValue.get(fieldsName[i]);
						if(StringHelper.isNotEmpty(sDafault))
						{
							str = (String) dafaultValue.get(fieldsName[i]);
						}
					}
					data.put(fieldsName[i], str);
				}
				toList.add(data);
			}
			// clean up
			results.close();
			stmt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage(), e);
		}//最后是文件路径
		catch (ClassNotFoundException e)
		{
			logger.error(e.getMessage(), e);
		}

		return toList;
	}

}
