
package com.hanaselim.smartcampus.exception;

/**
 * thrown when referenced resource doesn't exist
 * represents validation error in request data
 * 
 */
public class LinkedResourceNotFoundException extends RuntimeException{
    public LinkedResourceNotFoundException(String message){
        super(message);
    }
}
