/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.exception;

import com.hanaselim.smartcampus.model.ErrorMessage;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

/**
 *
 * maps "room not empty exception" to http 409 conflict 
 * (conflict with current resource state)
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException>{
    @Override
    public Response toResponse(RoomNotEmptyException ex){
        
        ErrorMessage error = new ErrorMessage(
                ex.getMessage(),
                409,
                "Room still has sensors assigned"
        );
        
return Response.status(Response.Status.CONFLICT)
        .entity(error)
        .type(MediaType.APPLICATION_JSON)
        .build();    }
}
