
package com.hanaselim.smartcampus.exception;

import com.hanaselim.smartcampus.model.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * maps "sensor unavailable exception" to http 403 forbidden
 * 403: request understood, operation not allowed due to system state
 */
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException>{
    @Override
    public Response toResponse(SensorUnavailableException ex){
        ErrorMessage error = new ErrorMessage(
        ex.getMessage(),
        403,
        "Sensor is in maintenance mode");
        
        return Response.status(Response.Status.FORBIDDEN).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
