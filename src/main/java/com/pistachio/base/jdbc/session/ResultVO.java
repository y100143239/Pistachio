package com.pistachio.base.jdbc.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * result value object class
 */
public class ResultVO implements Serializable
{
	// result message
	private String rsmsg = "";
	
	// result code
	private String rscode = "";
	
	private Serializable extraInfo = null;
	
	private List results = Collections.EMPTY_LIST;
	
	private int totalRow = 0;
	
	
	
	
	/**
	 * create a ResultVo instance
	 */
	public ResultVO()
	{
	}
	
	
	
	/**
	 * create a ResultVo instance
	 * 
	 * @param msg message
	 *
	 */
	public ResultVO(String msg)
	{
		rsmsg = msg;
	}
	
	
	
	/**
	 * message
	 * 
	 * @return message string
	 */
	public String getRsMsg()
	{
		return rsmsg;
	}
	
	
	
	/**
	 * result list
	 * 
	 * @return result list
	 */
	public List getResults()
	{
		return new ArrayList(results);
	}
	
	
	
	/**
	 * set message
	 * @param msg message
	 */
	public void setRsMsg(String msg)
	{
		this.rsmsg = msg;
	}
	
	
	
	/**
	 * set result list
	 * @param results result list
	 */
	public void setResults(List results)
	{
		this.results = new ArrayList(results);
	}
	
	
	
	/**
	 * get extra information
	 * 
	 * @return extra information object
	 */
	public Serializable getExtraInfo()
	{
		return extraInfo;
	}
	
	
	
	/**
	 * set extra information
	 * @param extraInfo  extra information object
	 */
	public void setExtraInfo(Serializable extraInfo)
	{
		this.extraInfo = extraInfo;
	}
	
	
	
	/**
	 * get total rows (use to page)
	 * 
	 * @return total rows
	 */
	public int getTotalRow()
	{
		return totalRow;
	}
	
	
	
	/**
	 * set total rows, used to page
	 * 
	 * @param totalRow
	 *
	 */
	public void setTotalRow(int totalRow)
	{
		this.totalRow = totalRow;
	}
	
	
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return rsmsg;
	}
	
	
	public String getRscode()
	{
		return rscode;
	}
	
	
	public void setRscode(String rscode)
	{
		this.rscode = rscode;
	}
}
