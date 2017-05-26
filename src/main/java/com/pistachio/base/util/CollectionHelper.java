package com.pistachio.base.util;

import com.pistachio.base.jdbc.DataRow;

import java.util.*;


/**
 * 描述: 数据集工具类
 */
public class CollectionHelper
{
	/**
	 * 描述： 添加指定 数组 中的所有元素到此列表的结尾
	 * @param list 列表
	 * @param objArr 数组
	 * @return
	 */
	public static List addAll(List list, Object[] objArr)
	{
		if(objArr!=null && objArr.length>0)
		{
			for (int i = 0; i < objArr.length; i++)
			{
				list.add(objArr[i]);
			}
		}
		return list;
	}

	/**
	 * 描述：获取list中元素的map的某个值
	 * @param list List
	 * @param key map中的键
	 * @return
	 */
	public static String[] getElemVal(List list, String key)
	{
		if(list==null || list.isEmpty())
		{
			return null;
		}
		String[] elems = new String[list.size()];
		int i=0;
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			DataRow data = (DataRow) iter.next();
			elems[i]=data.getString(key);
			i++;
		}
		return elems;
	}

	/**
	 * 描述：合并List并排除关键字相同的元素
	 * @param list1 List数据集
	 * @param list2 List数据集
	 * @param keys 关键字,多个时用逗号分隔
	 * @return 删除的个数
	 */
	public static int mergeList(List<DataRow> list1, List<DataRow> list2, String keys)
	{
		return mergeList(list1, list2, keys, true);
	}

	/**
	 * 描述：合并List并排除关键字相同的元素
	 * @param list1 List数据集
	 * @param list2 List数据集
	 * @param keys 关键字,多个时用逗号分隔
	 * @param isremoveLast
	 * @return
	 */
	public static int mergeList(List<DataRow> list1, List<DataRow> list2, String keys, boolean isRremoveLast)
	{
		int removeNum = 0;

		if(list1!=null && list2!=null && keys!=null && keys.trim().length()>0)
		{
			List temp1 = null;
			List temp2 = null;
			if(isRremoveLast)
			{
				temp1 = list1;
				temp2 = list2;
			}
			else
			{
				temp1 = list2;
				temp2 = list1;
			}

			List keyList = new ArrayList();
			for (Iterator iter = temp1.iterator(); iter.hasNext();)
			{
				DataRow temp1Elem = (DataRow) iter.next();
				String[] keyArr = keys.split(",");

				DataRow keyMap = new DataRow();
				for (int i = 0; i < keyArr.length; i++)
				{
					String value = temp1Elem.getString(keyArr[i]);
					keyMap.set(keyArr[i], value);
				}
				keyList.add(keyMap);
			}
			for (Iterator iter = temp2.iterator(); iter.hasNext();)
			{
				DataRow tmep2Elem = (DataRow) iter.next();

				boolean b = false;
				for (Iterator iterator = keyList.iterator(); iterator.hasNext();)
				{
					DataRow keyMap = (DataRow) iterator.next();

					if(containsMap(tmep2Elem, keyMap))
					{
						b = true;
						break;
					}
				}

				if(b)
				{
					removeNum++;
				}
				else
				{
					temp1.add(tmep2Elem);
				}
			}
		}
		return removeNum;
	}

	/**
	 * 描述：如果此映射包含指定映射的映射关系，则返回 true。
	 * @param mainMap
	 * @param keyMap
	 * @return
	 */
	public static boolean containsMap(Map mainMap, Map keyMap)
	{
		boolean b = true;
		if(mainMap!=null && keyMap!=null)
		{
			Set set = keyMap.keySet();
			for (Iterator iter = set.iterator(); iter.hasNext();)
			{
				String key = (String) iter.next();
				String mainValue = (String) mainMap.get(key);
				String keyValue = (String) keyMap.get(key);
				if(!mainValue.equals(keyValue))
				{
					b = false;
				}
			}
		}
		else
		{
			b=false;
		}
		return b;
	}
}
