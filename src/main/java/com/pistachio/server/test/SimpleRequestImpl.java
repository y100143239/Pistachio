
package com.pistachio.server.test;


import com.pistachio.server.IASRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimpleRequestImpl implements IASRequest {
    private Map fieldValueMap = new HashMap();

    private List fieldNameList = new ArrayList();

    private int branchNo = 0;

    private int funcNo = 0;


    public int getFieldCount() {

        return this.fieldNameList.size();

    }


    public String getFieldName(int index) {

        if (this.fieldNameList.size() > 0) {

            if ((index >= 0) && (index < this.fieldNameList.size())) {

                return (String) this.fieldNameList.get(index);

            }

        }


        return "";

    }


    public String getFieldValue(String name) {

        Object value = this.fieldValueMap.get(name);

        return value == null ? "" : value.toString();

    }


    public void addFieldValue(String name, String value) {

        this.fieldValueMap.put(name, value);

        this.fieldNameList.add(name);

    }


    public void setFuncNo(int funcNo) {

        this.funcNo = funcNo;

    }


    public int getFuncNo() {

        return this.funcNo;

    }


    public void setBranchNo(int branchNo) {

        this.branchNo = branchNo;

    }


    public int getBranchNo() {

        return this.branchNo;

    }


    public void clear() {

        this.fieldValueMap.clear();

        this.fieldNameList.clear();

    }

}
