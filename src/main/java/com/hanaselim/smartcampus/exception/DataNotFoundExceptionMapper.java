/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.exception;

import com.hanaselim.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * maps data not found exception to http 404 (not found) response
 * provider tells jax-rs to automatically register class
 * (no raw java erorrs exposed, clients receive structured json responses)
 */
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
    @Override
    public Response toResponse(DataNotFoundException ex){
        
        //error message response to data not found exception
        ErrorMessage error = new ErrorMessage(
                ex.getMessage(),
                404,
                "Requested resource not found"
        );
        
        return Response.status(Response.Status.NOT_FOUND).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
    
}
