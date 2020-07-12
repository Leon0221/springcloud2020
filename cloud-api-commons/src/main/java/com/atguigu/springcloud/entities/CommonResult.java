package com.atguigu.springcloud.entities;

import lombok.Data;

import java.io.Serializable;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class CommonResult<T> implements Serializable {

    private Integer code;  //返给前端的编码

    private String message;//返回前段信息

    private T dada;

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult() {
    }

    public CommonResult(Integer code, String message, T dada) {
        this.code = code;
        this.message = message;
        this.dada = dada;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDada() {
        return dada;
    }

    public void setDada(T dada) {
        this.dada = dada;
    }
}
