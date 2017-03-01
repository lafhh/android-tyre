package com.laf.network.http;

/**
 * Created by apple on 17/2/16.
 */
public class Response {

    public enum ResponseCode {
        /**
         * 操作成功
         */
        Succeed,

        /**
         * 超时
         */
        Timeout,

        /**
         * 网络错误
         */
        NetworkError,

        /**
         * 鉴权失败
         */
        AuthError,

        /**
         * 请求参数错误
         */
        ParamError,

        /**
         * 未知错误
         */
        Failed,
        /**
         * 错误请求
         */
        BadRequest,
        /**
         * 401需要鉴权
         */
        UnAuthorized,
        /**
         * 403鉴权未通过
         */
        Forbidden,
        /**
         * 404 请求路径未找到
         */
        NotFound,
        /**
         * 409 服务器在完成请求时发生冲突
         */
        Conflict,
        /**
         * 500 服务器错误
         */
        InternalError
    }

    private ResponseCode responseCode;

    private String data;

    private int resultCode;

    private String resultDesc;

    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
