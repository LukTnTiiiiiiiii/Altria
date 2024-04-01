package com.el.altria.dto;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private int code;
    private String msg;
    private T data;

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(200, null, data);
    }

    public static <T> BaseResponse<T> fail(int code,String msg) {
        return new BaseResponse<>(code, msg, null);
    }

    public static <T> BaseResponse<T> badRequest(String msg) {
        return new BaseResponse<>(400, msg, null);
    }

    public static <T> BaseResponse<T> internalError(String msg) {
        return new BaseResponse<>(500, msg, null);
    }
}
