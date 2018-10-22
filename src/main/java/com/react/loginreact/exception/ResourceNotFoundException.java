package com.react.loginreact.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends  RuntimeException{

    private String resource;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resource,String fieldName,Object fieldValue){
        super(String.format("%s not found with %s : '%s'", resource,fieldName,fieldValue));
        this.resource=resource;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

}
