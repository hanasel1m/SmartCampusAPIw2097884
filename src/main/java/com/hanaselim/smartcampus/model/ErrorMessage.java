/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.model;

/**
 *
 * @author Dell
 */
public class ErrorMessage {
    private String message;
    private int status;
    
    public ErrorMessage(){}
    
    public ErrorMessage(String message, int status){
        this.message = message;
        this.status = status;
    }
    
    public String getMessage(){
        return message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus (int status){
        this.status=status;
    }
}
