package com.react.loginreact.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class BadRequestExeption extends RuntimeException {

    public BadRequestExeption(String msg){
        super(msg);
    }

    public  BadRequestExeption(String msg, Throwable e){
        super(msg,e);
    }
}
