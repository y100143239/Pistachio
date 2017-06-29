package com.pistachio.server;

public abstract interface IASRequest
{
  public abstract int getFieldCount();

  public abstract String getFieldName(int paramInt);

  public abstract String getFieldValue(String paramString);

  public abstract int getFuncNo();

  public abstract int getBranchNo();
}
