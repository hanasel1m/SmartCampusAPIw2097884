/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.exception;

/**
 *
 * custom exception for cases where requested resource doesn't exist (room not found, sensor not found)
 * clean separation between business logic and http responses 
 * (actual http response handled by an exception mapper)
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message){
        super(message);
    }
}
