package com.practice.foodmart.pojo;

import com.github.pagehelper.PageInfo;

public class AjaxResponseBody {

    String msg;
    PageInfo<Employee> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PageInfo<Employee> getResult() {
        return result;
    }

    public void setResult(PageInfo<Employee> result) {
        this.result = result;
    }
}
