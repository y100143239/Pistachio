
package com.pistachio.base.jdbc.session;

import com.pistachio.base.jdbc.DBPage;
import com.pistachio.base.jdbc.DataRow;

import java.sql.Connection;
import java.util.List;

public interface Session
{

	/**
	 * 返回会话中的Connection
	 * @return
	 */
	public Connection connection();

	/**
	 * 返回数据库的类型,类型定义在DataBaseType中
	 * @return
	 */
	public int getDataBaseType();

	/**
	 * 在相应的表中增加一条记录
	 *
	 * @param tableName  需要添加的表名
	 * @param data  需要添加的信息
	 * @return
	 */
	public int insert(String tableName, DataRow data);

	/**
	 * 更新表中的一条记录信息
	 * @param tableName         需要更新的表名
	 * @param data               需要更新的信息
	 * @param identify          识别字段的名称
	 * @param identifyValue     识别字段的值
	 * @return
	 */
	public int update(String tableName, DataRow data, String identify, Object identifyValue);

	/**
	 * 更新表中的一条记录信息
	 * @param tableName         需要更新的表名
	 * @param data               需要更新的信息
	 * @param identifys          识别字段的名称
	 * @param identifyValues     识别字段的值
	 * @return
	 */
	public int update(String tableName, DataRow data, String[] identifys, Object[] identifyValues);

	/**
	 * 删除表中的一条记录
	 * @param tableName         需要删除的记录的表名
	 * @param identify          识别字段的名称
	 * @param identifyValue     识别字段的值
	 * @return
	 */
	public int delete(String tableName, String identify, Object identifyValue);

	/**
	 * 执行指定的sql并返回更新的记录数。
	 *
	 * @param sql SQL语句
	 * @return 更新的记录数
	 */
	public int update(String sql);

	/**
	 * 执行指定的sql并返回更新的记录数。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 更新的记录数
	 */
	public int update(String sql, Object[] args);

	/**
	 * 批量执行更新并返回每次的更新记录数
	 *
	 * @param sqlArray SQL语句数组
	 * @return 每次执行更新的记录数数组
	 */
	public int[] batchUpdate(String[] sqlArray);

	/**
	 * 批量执行更新并返回每次的更新记录数
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return
	 */
	public int[] batchUpdate(String sql, Object[][] args);

	/**
	 * 查询一个整型结果。
	 *
	 * @param sql SQL语句
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public int queryInt(String sql);

	/**
	 * 查询一个整型结果。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public int queryInt(String sql, Object[] args);

	/**
	 * 返回一个数字数组
	 *
	 * @param sql SQL语句
	 * @return 查询的多条记录第一个字段的整型值。
	 */
	public int[] queryIntArray(String sql);

	/**
	 * 返回一个数字数组
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值*
	 * @return 查询的多条记录第一个字段的整型值。
	 */
	public int[] queryIntArray(String sql, Object[] args);

	/**
	 * 查询一个长整型结果。
	 *
	 * @param sql SQL语句
	 * @return 查询的第一行的第一个字段的长整型值。
	 */
	public long queryLong(String sql);

	/**
	 * 查询一个长整型结果。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的第一行的第一个字段的长整型值。
	 */
	public long queryLong(String sql, Object[] args);

	/**
	 * 查询一个整型结果。
	 *
	 * @param sql SQL语句
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public double queryDouble(String sql);

	/**
	 * 查询一个整型结果。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的第一行的第一个字段的浮点值。
	 */
	public double queryDouble(String sql, Object[] args);

	/**
	 * 查询一个整型结果。
	 *
	 * @param sql SQL语句
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public float queryFloat(String sql);

	/**
	 * 查询一个整型结果。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的第一行的第一个字段的单精度浮点值。
	 */
	public float queryFloat(String sql, Object[] args);

	/**
	 * 返回一个长整型数组
	 *
	 * @param sql SQL语句
	 * @return 查询的多条记录第一个字段的长整型值。
	 */
	public long[] queryLongArray(String sql);

	/**
	 * 返回一个长整型数组
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值*
	 * @return 查询的多条记录第一个字段的长整型值。
	 */
	public long[] queryLongArray(String sql, Object[] args);

	/**
	 * 查询一个字符串结果。
	 *
	 * @param sql SQL语句
	 * @return 查询的第一行的第一个字段的字符串值。
	 */
	public String queryString(String sql);

	/**
	 * 查询一个字符串结果。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的第一行的第一个字段的字符串值。
	 */
	public String queryString(String sql, Object[] args);

	/**
	 * 返回一个字符串数组
	 *
	 * @param sql SQL语句
	 * @return 查询的多条记录第一个字段的字符串值。
	 */
	public String[] queryStringArray(String sql);

	/**
	 * 返回一个字符串数组
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的多条记录第一个字段的字符串值。
	 */
	public String[] queryStringArray(String sql, Object[] args);

	/**
	 * 查询一条记录，返回类型为DataRow，
	 *
	 * @param sql SQL语句
	 * @return 查询的第一行的结果,反回结果中的字段名都为小写
	 */
	public DataRow queryMap(String sql);

	/**
	 * 查询一条记录，返回类型为DataRow。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询的第一行的结果,反回结果中的字段名都为小写。
	 */
	public DataRow queryMap(String sql, Object[] args);

	/**
	 * 查询一个对象列表结果,列表中的每一行为一个DataRow。
	 *
	 * @param sql SQL语句
	 * @return 查询所有结果列表。
	 */
	public List query(String sql);

	/**
	 * 查询一个对象列表结果,列表中的每一行为一个DataRow。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @return 查询所有结果。
	 */
	public List query(String sql, Object[] args);

	/**
	 * 查询一个对象列表结果,列表中的每一行为一个DataRow。
	 *
	 * @param sql  SQL语句
	 * @param rows 返回的记录数量
	 * @return 查询固定的记录数
	 */
	public List query(String sql, int rows);

	/**
	 * 查询一个对象列表结果,列表中的每一行为一个DataRow。
	 *
	 * @param sql  SQL语句
	 * @param args 参数中的值
	 * @param rows 返回的记录数量*
	 * @return 查询固定的记录数
	 */
	public List query(String sql, Object[] args, int rows);

	/**
	 * 查询一个对象列表结果,列表中的每一行为一个DataRow。
	 *
	 * @param sql      SQL语句
	 * @param startRow 起始的行数
	 * @param rows     记录的数量
	 * @return 查询所有结果并。
	 */
	public List query(String sql, int startRow, int rows);

	/**
	 * 查询一个对象列表结果,列表中的每一行为一个DataRow。
	 *
	 * @param sql      SQL语句
	 * @param args     参数中的值
	 * @param startRow 起始的行数
	 * @param rows     记录的数量
	 * @return 查询所有结果并。
	 */
	public List query(String sql, Object[] args, int startRow, int rows);

	/**
	 * 查询一个分页列表结果。返回的数据的每一行数据类型为DataRow
	 *
	 * @param sql        SQL语句
	 * @param curPage    当前页数
	 * @param numPerPage 每页显示的记录数
	 * @return 分页对象
	 */
	public DBPage queryPage(String sql, int curPage, int numPerPage);

	/**
	 * 查询一个分页列表结果。返回的数据的每一行数据类型为DataRow
	 *
	 * @param sql        SQL语句
	 * @param args       参数中的值
	 * @param curPage    当前页数
	 * @param numPerPage 每页显示的记录数
	 * @return 分页对象
	 */
	public DBPage queryPage(String sql, Object[] args, int curPage, int numPerPage);

	/**
	 * 开始数据库事务
	 */
	public void beginTrans();

	/**
	 * 提交数据库事务
	 */
	public void commitTrans();

	/**
	 * 回滚数据库事务
	 */
	public void rollbackTrans();

	/**
	 * 关闭数据库会话，同时会关闭相应的数据库连接
	 */
	public void close();

	/**
	 * 获取自增ID值
	 */
	public String getGeneratedKeys();
}
