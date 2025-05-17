package com.phoenix.blog.exception;

public class PostNotFoundException extends BlogException{

    public PostNotFoundException(String message) {
        super(message);
    }
}
