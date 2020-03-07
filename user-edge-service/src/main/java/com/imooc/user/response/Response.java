package com.imooc.user.response;

import java.io.Serializable;

public class Response implements Serializable {

    public static final  Response USER_PASSWORD_INVALID = new Response("1001", "username or password invalid");
    public static final  Response REGISTER_ERROR = new Response("1002", "register error");
    public static final  Response SUCCESS = new Response("0", "success");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
