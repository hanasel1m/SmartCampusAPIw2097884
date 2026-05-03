/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.exception;

/**
 *
 * thrown when attempting operation on a unavailable sensor (under maintenance)
 * enforces operational constraints of system
 */
public class SensorUnavailableException extends RuntimeException{
    public SensorUnavailableException (String message){
        super(message);
    }
}
