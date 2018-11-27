package org.simon.reflect.parameterizedType;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-11-22 23:06
 * @Description:TODO
 */
public class ResponseDTO<T> {
    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
