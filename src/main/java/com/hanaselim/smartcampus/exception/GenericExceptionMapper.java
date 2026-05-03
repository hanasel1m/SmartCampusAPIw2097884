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
 * global fallback exception handler
 * (prevents exposing internal stack trace (security risk))
 *  & returns safe, generic error message to client
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{
    @Override
    public Response toResponse(Throwable ex){
        ErrorMessage error = new ErrorMessage(
                "Internal server error",
                500,
                "Unexpected error occured"
        );
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
