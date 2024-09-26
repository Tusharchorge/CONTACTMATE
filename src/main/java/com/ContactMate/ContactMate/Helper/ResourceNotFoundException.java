package com.ContactMate.ContactMate.Helper;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }

    ResourceNotFoundException(){
        super("Resource not found");

    }
}
