package com.pistachio.server;

public abstract interface IASResponse
{
  public abstract void setErrorNo(int paramInt);

  public abstract void setErrorInfo(String paramString);

  public abstract void newDataSet();

  public abstract void setDataSetName(String paramString);

  public abstract void addField(String paramString);

  public abstract void addValue(String paramString);

  public abstract void clear();
}
