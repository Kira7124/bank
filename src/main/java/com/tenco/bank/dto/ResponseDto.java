package com.tenco.bank.dto;

public class ResponseDto<T> {
    private int status;
    private T data;

    public ResponseDto() {
    }

    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}