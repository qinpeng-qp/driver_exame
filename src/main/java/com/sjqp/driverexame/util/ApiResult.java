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

    private int status;

    private String description;

    private T data;

    private int currentPageNo;

    private int pageSize;

    private int totalCount;


    public ApiResult() {

    }

    public ApiResult(int status) {
        this.status = status;
        if (status == SUCCESS_RESULT) {
            this.description = SUCCESS_DESCRIPTION;
        }
    }


    public ApiResult(T data) {
        this.status = SUCCESS_RESULT;
        this.description = SUCCESS_DESCRIPTION;
        this.data = data;
    }

    public ApiResult(int status, String description) {
        this.status = status;
        this.description = description;
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
                 .setStatus(status)
                 .setDescription(description);
        return apiResult;
    }

    public int getStatus() {
        return status;
    }

    public ApiResult setStatus(int status) {
        this.status = status;
        if (status == SUCCESS_RESULT) {
            this.description = SUCCESS_DESCRIPTION;
        }
        return this;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public ApiResult setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ApiResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public ApiResult setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiResult setDescription(String description) {
        this.description = description;
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
                "status=" + status +
                ", description='" + description + '\'' +
                ", data=" + data +
                ", currentPageNo=" + currentPageNo +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}

