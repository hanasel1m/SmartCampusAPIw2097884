/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hanaselim.smartcampus.resource;

import com.hanaselim.smartcampus.database.DataStore;
import com.hanaselim.smartcampus.exception.DataNotFoundException;
import com.hanaselim.smartcampus.exception.LinkedResourceNotFoundException;
import com.hanaselim.smartcampus.model.ErrorMessage;
import com.hanaselim.smartcampus.model.Sensor;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * sensor resource: handles all sensor operations
 * 
 */
@Path("/sensors")
public class SensorResource {

    // creates a sensor and links it to a room
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        
        //validate request body
        if(sensor == null){
            
            ErrorMessage error = new ErrorMessage(
                    "Sensor body is missing",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validate sensor ID
        if(sensor.getId()==null || sensor.getId().isEmpty()){
            ErrorMessage error = new ErrorMessage(
                    "Sensor ID is required",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validate sensor type
        if (sensor.getType() == null || sensor.getType().isEmpty()) {
            
            ErrorMessage error = new ErrorMessage(
                    "Sensor type is required",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validate sensor status
        if (sensor.getStatus() == null || sensor.getStatus().isEmpty()) {
            ErrorMessage error = new ErrorMessage(
                    "Sensor status is required",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        if (!sensor.getStatus().equalsIgnoreCase("ACTIVE") &&
            !sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {
            
            ErrorMessage error = new ErrorMessage(
                    "Invalid status. Must be ACTIVE or MAINTENANCE",
                    400,
                    "Invalid input"
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //check duplicate sensor
        if (DataStore.sensors.containsKey(sensor.getId())){
            
            ErrorMessage error = new ErrorMessage(
                    "Sensor already exists",
                    409,
                    "Duplicate resource"
            );
            return Response.status(Response.Status.CONFLICT).entity(error).type(MediaType.APPLICATION_JSON).build();
        }
        
        //validate roomId exists in request
        if (sensor.getRoomId() == null || sensor.getRoomId().isEmpty() ) {
            throw new LinkedResourceNotFoundException("Room ID is missing in request");
        }
        
        //check if room actually exists
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException(
                    "Room with ID " + sensor.getRoomId() + " does not exist"
            );
        }

        DataStore.sensors.put(sensor.getId(), sensor);
        DataStore.rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).header("Location", "/api/v1/sensors/" + sensor.getId()).build();
    }

    // optionally filter sensors by type
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return DataStore.sensors.values();
        }

        return DataStore.sensors.values().stream().filter(s -> s.getType() != null && s.getType().equalsIgnoreCase(type)).toList();
    }
    
    // sub-resource locator for sensor readings
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId) {
        if(!DataStore.sensors.containsKey(sensorId)){
            throw new DataNotFoundException("Sensor with ID "+ sensorId + " not found");
        }
        return new SensorReadingResource(sensorId);
    }
}
