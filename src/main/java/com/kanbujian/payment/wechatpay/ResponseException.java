package com.kanbujian.payment.wechatpay;

import java.util.Map;

public class ResponseException extends RuntimeException {
    private String errCode;
    private String errMessage;
    private String comment;

    public ResponseException(Map data){
        this.errCode = (String) data.get("err_code");
        this.errMessage = (String) data.get("err_code_des");
    }

    public ResponseException(String errCode, String errMessage, String comment){
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.comment = comment;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
