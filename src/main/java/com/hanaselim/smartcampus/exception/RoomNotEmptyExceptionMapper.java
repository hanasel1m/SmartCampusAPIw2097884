/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.exception;

import com.hanaselim.smartcampus.model.ErrorMessage;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Dell
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException>{
    @Override
    public Response toResponse(RoomNotEmptyException ex){
        
        ErrorMessage error = new ErrorMessage(
                ex.getMessage(),
                409
        );
        
        return Response.status(Response.Status.CONFLICT).entity(error).build();
    }
}
