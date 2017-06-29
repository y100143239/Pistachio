
package com.pistachio.server.test;


import com.pistachio.server.IASContext;
import com.pistachio.server.IASRequest;
import com.pistachio.server.IASResponse;


public class SimpleContextImpl implements IASContext {
    private IASRequest request = null;
    private IASResponse response = null;


    public void setRequest(IASRequest request) {

        this.request = request;

    }


    public IASRequest getRequest() {

        return this.request;

    }


    public void setResponse(IASResponse response) {

        this.response = response;

    }


    public IASResponse getResponse() {

        return this.response;

    }


    public void clear() {

        this.request = null;

        this.response = null;

    }

}
