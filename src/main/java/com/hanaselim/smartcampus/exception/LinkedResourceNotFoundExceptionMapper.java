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
 * @author Dell
 */
@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException>{
    @Override
    public Response toResponse(LinkedResourceNotFoundException ex){
        ErrorMessage error = new ErrorMessage(
                ex.getMessage(),
                422,
                "Linked resource not found"
        );
        
        return Response.status(422).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
