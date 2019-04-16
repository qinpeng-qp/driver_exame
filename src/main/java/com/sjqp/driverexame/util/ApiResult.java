package com.sjqp.driverexame.util;


import java.io.Serializable;

/**
 * @author qinpeng
 * @date 2018/12/5 16:49
 */
public class ApiResult<T> implements Serializable {

    public static final String SUCCESS_DESCRIPTION = "成功";

    public static final int SUCCESS_RESULT = 0;

    public static final int FAIL_RESULT = -1;

    private int code;

    private String msg;

    private T data;

    private int page;

    private int limit;

    private int count;


    public ApiResult() {

    }

    public ApiResult(int code) {
        this.code = code;
        if (code == SUCCESS_RESULT) {
            this.msg = SUCCESS_DESCRIPTION;
        }
    }


    public ApiResult(T data) {
        this.code = SUCCESS_RESULT;
        this.msg = SUCCESS_DESCRIPTION;
        this.data = data;
    }

    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 成功返回
     * @return
     */
    public static <T> ApiResult<T> success(){
        return newInstance(null, SUCCESS_RESULT, SUCCESS_DESCRIPTION);
    }

    public static <T> ApiResult<T> success(T data){
        return newInstance(data, SUCCESS_RESULT, SUCCESS_DESCRIPTION);
    }

    /**
     * 失败返回
     * @return
     */
    public static <T> ApiResult<T> error(String description){
        return newInstance(null, FAIL_RESULT, description);
    }

    public static <T> ApiResult<T> error(T data, String description){
        return newInstance(null, FAIL_RESULT, description);
    }

    public static <T> ApiResult<T> newInstance(T data, int status, String description){
        ApiResult apiResult = new ApiResult();
        apiResult.setData(data)
                 .setCode(status)
                 .setMsg(description);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        if (code == SUCCESS_RESULT) {
            this.msg = SUCCESS_DESCRIPTION;
        }
        return this;
    }

    public int getPage() {
        return page;
    }

    public ApiResult setPage(int page) {
        this.page = page;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public ApiResult setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getCount() {
        return count;
    }

    public ApiResult setCount(int count) {
        this.count = count;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", page=" + page +
                ", limit=" + limit +
                ", count=" + count +
                '}';
    }
}

