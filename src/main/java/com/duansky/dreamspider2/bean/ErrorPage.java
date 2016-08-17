package com.duansky.dreamspider2.bean;

/**
 * Created by shikai.dsk on 2016/8/16.
 */
public class ErrorPage extends Page{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
