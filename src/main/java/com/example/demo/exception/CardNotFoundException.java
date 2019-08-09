package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "cardRequest Not found")
public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String errorMsg){
        super(errorMsg);
    }

    @Override
    public String getMessage(){
        return super.getMessage();
    }
}
