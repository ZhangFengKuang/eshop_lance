package com.roncoo.eshop.inventory.vo;

/**
 * Date: 2018/10/28
 * Author: Lance
 * Class action:响应信息类
 */
public class Response {
    public static final String SUCCESS = "true";
    public static final String FAILURE = "failure";

    private String status;
    private String message;

    public Response(String status) {
        this.status = status;
    }
    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
