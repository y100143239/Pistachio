package com.pistachio.server;

public abstract interface IASContext
{
  public abstract IASRequest getRequest();

  public abstract IASResponse getResponse();
}
